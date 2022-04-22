package com.github.xini1.port;

/**
 * @author Maxim Tereshchenko
 */
public interface FilmDto {

    String imdbId();

    String name();

    boolean isOscarWinner();

    int rating();

    int boxOffice();
}
