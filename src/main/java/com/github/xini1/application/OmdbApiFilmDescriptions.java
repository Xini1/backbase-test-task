package com.github.xini1.application;

import com.github.xini1.port.FilmDescriptions;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author Maxim Tereshchenko
 */
final class OmdbApiFilmDescriptions implements FilmDescriptions {

    private final OmdbApiFeignClient omdbApiFeignClient;

    OmdbApiFilmDescriptions(OmdbApiFeignClient omdbApiFeignClient) {
        this.omdbApiFeignClient = omdbApiFeignClient;
    }

    @Override
    public Page byName(String apiToken, String name, int pageNumber) {
        return new OmdbPage(omdbApiFeignClient.find(apiToken, name, pageNumber + 1), pageNumber, apiToken);
    }

    @Override
    public FilmDescription byId(String apiToken, String imdbId) {
        return new OmdbFilmDescription(omdbApiFeignClient.find(apiToken, imdbId));
    }

    @Override
    public boolean isNotExists(String apiToken, String imdbId) {
        return byId(apiToken, imdbId).imdbId() == null;
    }

    private static final class OmdbFilmDescription implements FilmDescription {

        private final OmdbApiFeignClient.FilmByIdResponse response;

        private OmdbFilmDescription(OmdbApiFeignClient.FilmByIdResponse response) {
            this.response = response;
        }

        @Override
        public String imdbId() {
            return response.imdbId();
        }

        @Override
        public String name() {
            return response.title();
        }

        @Override
        public int boxOffice() {
            if (hasNotBoxOfficeValue()) {
                return 0;
            }

            return Integer.parseInt(
                    response.boxOffice()
                            .replace("$", "")
                            .replace(",", "")
            );
        }

        private boolean hasNotBoxOfficeValue() {
            return response.boxOffice().isBlank() || response.boxOffice().equals("N/A");
        }
    }

    private final class OmdbPage implements Page {

        private final OmdbApiFeignClient.SearchResponse searchResponse;
        private final int page;
        private final String apiToken;

        private OmdbPage(OmdbApiFeignClient.SearchResponse searchResponse, int page, String apiToken) {
            this.searchResponse = searchResponse;
            this.page = page;
            this.apiToken = apiToken;
        }

        @Override
        public Collection<FilmDescription> filmDescriptions() {
            return searchResponse.searchResults()
                    .stream()
                    .map(OmdbFilmDescriptionOnSearch::new)
                    .collect(Collectors.toList());
        }

        @Override
        public int page() {
            return page;
        }

        @Override
        public int totalPages() {
            return searchResponse.total() / 10 + accountForLastPage();
        }

        private int accountForLastPage() {
            if (searchResponse.total() % 10 == 0) {
                return 0;
            }

            return 1;
        }

        private final class OmdbFilmDescriptionOnSearch implements FilmDescription {

            private final OmdbApiFeignClient.SearchResult searchResult;

            private OmdbFilmDescriptionOnSearch(OmdbApiFeignClient.SearchResult searchResult) {
                this.searchResult = searchResult;
            }

            @Override
            public String imdbId() {
                return searchResult.imdbId();
            }

            @Override
            public String name() {
                return searchResult.title();
            }

            @Override
            public int boxOffice() {
                return byId(apiToken, searchResult.imdbId()).boxOffice();
            }
        }
    }
}
