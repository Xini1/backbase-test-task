package com.github.xini1.domain;

import com.github.xini1.port.FilmDescriptions;
import com.github.xini1.port.OscarWinners;
import com.github.xini1.port.SearchFilmUseCase;

/**
 * @author Maxim Tereshchenko
 */
public final class Configuration {

    private final FilmDescriptions filmDescriptions;
    private final OscarWinners oscarWinners;

    public Configuration(FilmDescriptions filmDescriptions, OscarWinners oscarWinners) {
        this.filmDescriptions = filmDescriptions;
        this.oscarWinners = oscarWinners;
    }

    public SearchFilmUseCase searchFilmUseCase() {
        return new FilmService(filmDescriptions, oscarWinners);
    }
}
