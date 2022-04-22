package com.github.xini1.port.usecase;

/**
 * @author Maxim Tereshchenko
 */
public interface RateFilmUseCase {

    void rate(String apiToken, String imdbId, int rating);
}
