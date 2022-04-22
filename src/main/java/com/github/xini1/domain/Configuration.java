package com.github.xini1.domain;

import com.github.xini1.port.FilmDescriptions;
import com.github.xini1.port.SearchFilmUseCase;

/**
 * @author Maxim Tereshchenko
 */
public final class Configuration {

    private final FilmDescriptions filmDescriptions;

    public Configuration(FilmDescriptions filmDescriptions) {
        this.filmDescriptions = filmDescriptions;
    }

    public SearchFilmUseCase searchFilmUseCase() {
        return new FilmService(filmDescriptions);
    }
}
