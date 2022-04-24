package com.github.xini1.domain;

import com.github.xini1.exception.ApiTokenRequired;
import com.github.xini1.port.usecase.FilmDto;
import com.github.xini1.port.usecase.List10TopRatedFilmsUseCase;

import java.util.List;

/**
 * @author Maxim Tereshchenko
 */
final class ValidatingList10TopRatedFilmsUseCase implements List10TopRatedFilmsUseCase {

    private final List10TopRatedFilmsUseCase original;

    ValidatingList10TopRatedFilmsUseCase(List10TopRatedFilmsUseCase original) {
        this.original = original;
    }

    @Override
    public List<FilmDto> top10RatedSortedByBoxOffice(String apiToken) {
        if (apiToken == null || apiToken.isBlank()) {
            throw new ApiTokenRequired();
        }
        return original.top10RatedSortedByBoxOffice(apiToken);
    }
}
