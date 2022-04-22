package com.github.xini1.domain;

import com.github.xini1.port.FilmDescriptions;
import com.github.xini1.port.List10TopRatedFilmsUseCase;
import com.github.xini1.port.OscarWinners;
import com.github.xini1.port.RateFilmUseCase;
import com.github.xini1.port.Ratings;
import com.github.xini1.port.SearchFilmUseCase;

/**
 * @author Maxim Tereshchenko
 */
public final class Configuration {

    private final FilmDescriptions filmDescriptions;
    private final OscarWinners oscarWinners;
    private final Ratings ratings;

    public Configuration(FilmDescriptions filmDescriptions, OscarWinners oscarWinners, Ratings ratings) {
        this.filmDescriptions = filmDescriptions;
        this.oscarWinners = oscarWinners;
        this.ratings = ratings;
    }

    public SearchFilmUseCase searchFilmUseCase() {
        return new FilmService(filmDescriptions, oscarWinners, ratings);
    }

    public RateFilmUseCase rateFilmUseCase() {
        return new FilmService(filmDescriptions, oscarWinners, ratings);
    }

    public List10TopRatedFilmsUseCase list10TopRatedFilmsUseCase() {
        return new FilmService(filmDescriptions, oscarWinners, ratings);
    }
}
