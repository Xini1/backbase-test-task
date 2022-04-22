package com.github.xini1.application;

import com.github.xini1.port.usecase.FilmDto;

/**
 * @author Maxim Tereshchenko
 */
public class JsonFilmDto {

    private final String imdbId;
    private final String name;
    private final boolean oscarWinner;
    private final int rating;
    private final int boxOffice;

    JsonFilmDto(FilmDto filmDto) {
        imdbId = filmDto.imdbId();
        name = filmDto.name();
        oscarWinner = filmDto.isOscarWinner();
        rating = filmDto.rating();
        boxOffice = filmDto.boxOffice();
    }

    public String getImdbId() {
        return imdbId;
    }

    public String getName() {
        return name;
    }

    public boolean isOscarWinner() {
        return oscarWinner;
    }

    public int getRating() {
        return rating;
    }

    public int getBoxOffice() {
        return boxOffice;
    }
}
