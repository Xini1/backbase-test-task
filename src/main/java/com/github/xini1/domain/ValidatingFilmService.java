package com.github.xini1.domain;

import com.github.xini1.exception.ApiTokenMissing;
import com.github.xini1.exception.FilmNameRequired;
import com.github.xini1.exception.ImdbIdRequired;
import com.github.xini1.exception.IncorrectPageNumber;
import com.github.xini1.exception.IncorrectRating;
import com.github.xini1.port.usecase.FilmDto;
import com.github.xini1.port.usecase.List10TopRatedFilmsUseCase;
import com.github.xini1.port.usecase.RateFilmUseCase;
import com.github.xini1.port.usecase.SearchFilmUseCase;

import java.util.List;

/**
 * @author Maxim Tereshchenko
 */
final class ValidatingFilmService implements SearchFilmUseCase, RateFilmUseCase, List10TopRatedFilmsUseCase {

    private final FilmService original;

    public ValidatingFilmService(FilmService original) {
        this.original = original;
    }

    @Override
    public Page search(String apiToken, String name, int pageNumber) {
        if (apiToken == null || apiToken.isBlank()) {
            throw new ApiTokenMissing();
        }
        if (name == null || name.isBlank()) {
            throw new FilmNameRequired();
        }
        if (pageNumber < 0) {
            throw new IncorrectPageNumber();
        }
        return original.search(apiToken, name, pageNumber);
    }

    @Override
    public List<FilmDto> top10RatedSortedByBoxOffice(String apiToken) {
        if (apiToken == null || apiToken.isBlank()) {
            throw new ApiTokenMissing();
        }
        return original.top10RatedSortedByBoxOffice(apiToken);
    }

    @Override
    public void rate(String apiToken, String imdbId, int rating) {
        if (apiToken == null || apiToken.isBlank()) {
            throw new ApiTokenMissing();
        }
        if (imdbId == null || imdbId.isBlank()) {
            throw new ImdbIdRequired();
        }
        if (rating < 1 || rating > 10) {
            throw new IncorrectRating();
        }
        original.rate(apiToken, imdbId, rating);
    }
}
