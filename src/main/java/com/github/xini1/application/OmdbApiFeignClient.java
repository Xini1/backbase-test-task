package com.github.xini1.application;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.List;

/**
 * @author Maxim Tereshchenko
 */
@FeignClient(name = "omdbapi", url = "http://www.omdbapi.com/")
interface OmdbApiFeignClient {

    @GetMapping
    SearchResponse find(
            @RequestParam("apikey") String apiToken,
            @RequestParam("s") String search,
            @RequestParam("page") int page
    );

    @GetMapping
    FilmByIdResponse find(@RequestParam("apikey") String apiToken, @RequestParam("i") String imdbId);

    class SearchResponse {

        private final Collection<SearchResult> searchResults;
        private final int total;

        @JsonCreator
        public SearchResponse(
                @JsonProperty("Search") Collection<SearchResult> searchResults,
                @JsonProperty("totalResults") int total
        ) {
            this.searchResults = List.copyOf(searchResults);
            this.total = total;
        }

        Collection<SearchResult> searchResults() {
            return searchResults;
        }

        int total() {
            return total;
        }
    }

    class SearchResult {

        private final String title;
        private final String imdbId;

        @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
        public SearchResult(@JsonProperty("Title") String title, @JsonProperty("imdbID") String imdbId) {
            this.title = title;
            this.imdbId = imdbId;
        }

        String title() {
            return title;
        }

        String imdbId() {
            return imdbId;
        }
    }

    class FilmByIdResponse {

        private final String title;
        private final String imdbId;
        private final String boxOffice;

        @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
        public FilmByIdResponse(
                @JsonProperty("Title") String title,
                @JsonProperty("imdbID") String imdbId,
                @JsonProperty("BoxOffice") String boxOffice
        ) {
            this.title = title;
            this.imdbId = imdbId;
            this.boxOffice = boxOffice;
        }

        String title() {
            return title;
        }

        String imdbId() {
            return imdbId;
        }

        String boxOffice() {
            return boxOffice;
        }
    }
}
