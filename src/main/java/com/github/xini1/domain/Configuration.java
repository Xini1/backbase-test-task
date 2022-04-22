package com.github.xini1.domain;

import com.github.xini1.port.SearchFilmUseCase;

/**
 * @author Maxim Tereshchenko
 */
public final class Configuration {

    public SearchFilmUseCase searchFilmUseCase() {
        return new FilmService();
    }
}
