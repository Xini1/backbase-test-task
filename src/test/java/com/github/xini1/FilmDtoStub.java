package com.github.xini1;

import com.github.xini1.port.FilmDto;

/**
 * @author Maxim Tereshchenko
 */
final class FilmDtoStub implements FilmDto {

    private final String imdbId;
    private final String name;
    private final boolean oscarWinner;
    private final int rating;
    private final int boxOffice;

    FilmDtoStub(String imdbId, String name) {
        this(imdbId, name, false);
    }

    FilmDtoStub(String imdbId, String name, boolean oscarWinner) {
        this(imdbId, name, oscarWinner, 0, 0);
    }

    FilmDtoStub(String imdbId, String name, boolean oscarWinner, int rating, int boxOffice) {
        this.imdbId = imdbId;
        this.name = name;
        this.oscarWinner = oscarWinner;
        this.rating = rating;
        this.boxOffice = boxOffice;
    }

    @Override
    public String imdbId() {
        return imdbId;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public boolean isOscarWinner() {
        return oscarWinner;
    }

    @Override
    public int rating() {
        return rating;
    }

    @Override
    public int boxOffice() {
        return boxOffice;
    }

    @Override
    public String toString() {
        return "FilmDtoStub{" +
                "imdbId='" + imdbId + '\'' +
                ", name='" + name + '\'' +
                ", oscarWinner=" + oscarWinner +
                ", rating=" + rating +
                ", boxOffice=" + boxOffice +
                '}';
    }
}
