package com.github.xini1.domain;

import com.github.xini1.port.FilmDescriptions;
import com.github.xini1.port.OscarWinners;
import com.github.xini1.port.Ratings;
import com.github.xini1.port.usecase.List10TopRatedFilmsUseCase;
import com.github.xini1.port.usecase.RateFilmUseCase;
import com.github.xini1.port.usecase.SearchFilmUseCase;

/**
 * @author Maxim Tereshchenko
 */
public final class FilmsModuleConfiguration {

    private final FilmDescriptions filmDescriptions;
    private final OscarWinners oscarWinners;
    private final Ratings ratings;

    public FilmsModuleConfiguration(FilmDescriptions filmDescriptions, OscarWinners oscarWinners, Ratings ratings) {
        this.filmDescriptions = filmDescriptions;
        this.oscarWinners = oscarWinners;
        this.ratings = ratings;
    }

    public SearchFilmUseCase searchFilmUseCase() {
        return service();
    }

    public RateFilmUseCase rateFilmUseCase() {
        return service();
    }

    public List10TopRatedFilmsUseCase list10TopRatedFilmsUseCase() {
        return service();
    }

    private ValidatingFilmService service() {
        return new ValidatingFilmService(new FilmService(filmDescriptions, oscarWinners, ratings));
    }
}
