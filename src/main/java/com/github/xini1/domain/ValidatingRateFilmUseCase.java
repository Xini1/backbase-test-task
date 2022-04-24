package com.github.xini1.domain;

import com.github.xini1.exception.ApiTokenRequired;
import com.github.xini1.exception.ImdbIdRequired;
import com.github.xini1.exception.IncorrectRating;
import com.github.xini1.port.usecase.RateFilmUseCase;

/**
 * @author Maxim Tereshchenko
 */
final class ValidatingRateFilmUseCase implements RateFilmUseCase {

    private final RateFilmUseCase original;

    ValidatingRateFilmUseCase(RateFilmUseCase original) {
        this.original = original;
    }

    @Override
    public void rate(String apiToken, String imdbId, int rating) {
        if (apiToken == null || apiToken.isBlank()) {
            throw new ApiTokenRequired();
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
