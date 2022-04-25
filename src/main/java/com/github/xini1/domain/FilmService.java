package com.github.xini1.domain;

import com.github.xini1.exception.FilmNotFound;
import com.github.xini1.port.FilmDescriptions;
import com.github.xini1.port.OscarWinners;
import com.github.xini1.port.Ratings;
import com.github.xini1.port.usecase.FilmDto;
import com.github.xini1.port.usecase.ListTopRatedFilmsUseCase;
import com.github.xini1.port.usecase.Page;
import com.github.xini1.port.usecase.RateFilmUseCase;
import com.github.xini1.port.usecase.SearchFilmUseCase;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Maxim Tereshchenko
 */
final class FilmService implements SearchFilmUseCase, RateFilmUseCase, ListTopRatedFilmsUseCase {

    private final FilmDescriptions filmDescriptions;
    private final OscarWinners oscarWinners;
    private final Ratings ratings;

    FilmService(FilmDescriptions filmDescriptions, OscarWinners oscarWinners, Ratings ratings) {
        this.filmDescriptions = filmDescriptions;
        this.oscarWinners = oscarWinners;
        this.ratings = ratings;
    }

    @Override
    public Page search(String apiToken, String name, int page) {
        return page(filmDescriptions.byName(apiToken, name, page));
    }

    @Override
    public Page topRatedSortedByBoxOffice(String apiToken, int page, int elementsOnPage) {
        return new TopRatedFilmsPage(ratings.top(page, elementsOnPage), apiToken);
    }

    @Override
    public void rate(String apiToken, String imdbId, int rating) {
        if (filmDescriptions.isNotExists(apiToken, imdbId)) {
            throw new FilmNotFound(imdbId);
        }
        ratings.add(apiToken, imdbId, rating);
    }

    private Page page(FilmDescriptions.Page filmDescriptionsPage) {
        return new Page() {
            @Override
            public Collection<FilmDto> films() {
                return filmDescriptionsPage.filmDescriptions()
                        .stream()
                        .map(filmDescription -> filmDto(filmDescription))
                        .collect(Collectors.toList());
            }

            @Override
            public int page() {
                return filmDescriptionsPage.page();
            }

            @Override
            public int totalPages() {
                return filmDescriptionsPage.totalPages();
            }
        };
    }

    private FilmDto filmDto(FilmDescriptions.FilmDescription filmDescription) {
        return new FilmDtoAdapter(filmDescription, isOscarWinner(filmDescription), rating(filmDescription));
    }

    private int rating(FilmDescriptions.FilmDescription filmDescription) {
        return ratings.average(filmDescription.imdbId());
    }

    private boolean isOscarWinner(FilmDescriptions.FilmDescription filmDescription) {
        return oscarWinners.isWinner(filmDescription.name());
    }

    private final class TopRatedFilmsPage extends Page {

        private final Ratings.Page original;
        private final String apiToken;

        private TopRatedFilmsPage(Ratings.Page original, String apiToken) {
            this.original = original;
            this.apiToken = apiToken;
        }

        @Override
        public Collection<FilmDto> films() {
            return original.imdbIdToRating()
                    .entrySet()
                    .stream()
                    .map(entry -> filmDto(apiToken, entry))
                    .sorted(byBoxOffice())
                    .collect(Collectors.toList());
        }

        @Override
        public int page() {
            return original.page();
        }

        @Override
        public int totalPages() {
            return original.totalPages();
        }

        private Comparator<FilmDto> byBoxOffice() {
            return Comparator.comparing(FilmDto::boxOffice).reversed();
        }

        private FilmDto filmDto(String apiToken, Map.Entry<String, Integer> entry) {
            var filmDescription = filmDescriptions.byId(apiToken, entry.getKey());
            return new FilmDtoAdapter(filmDescription, isOscarWinner(filmDescription), entry.getValue());
        }
    }
}
