package com.github.xini1.domain;

import com.github.xini1.exception.ApiTokenRequired;
import com.github.xini1.exception.FilmNameRequired;
import com.github.xini1.exception.IncorrectPageNumber;
import com.github.xini1.port.usecase.Page;
import com.github.xini1.port.usecase.SearchFilmUseCase;

/**
 * @author Maxim Tereshchenko
 */
final class ValidatingSearchFilmUseCase implements SearchFilmUseCase {

    private final SearchFilmUseCase original;

    ValidatingSearchFilmUseCase(SearchFilmUseCase original) {
        this.original = original;
    }

    @Override
    public Page search(String apiToken, String name, int page) {
        if (apiToken == null || apiToken.isBlank()) {
            throw new ApiTokenRequired();
        }
        if (name == null || name.isBlank()) {
            throw new FilmNameRequired();
        }
        if (page < 0) {
            throw new IncorrectPageNumber();
        }
        return original.search(apiToken, name, page);
    }
}
