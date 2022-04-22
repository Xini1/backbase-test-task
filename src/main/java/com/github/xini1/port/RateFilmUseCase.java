package com.github.xini1.port;

/**
 * @author Maxim Tereshchenko
 */
public interface RateFilmUseCase {

    void rate(String apiToken, String imdbId, int rating);
}
