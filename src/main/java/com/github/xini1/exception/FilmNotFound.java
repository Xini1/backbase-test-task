package com.github.xini1.exception;

/**
 * @author Maxim Tereshchenko
 */
public final class FilmNotFound extends RuntimeException {

    public FilmNotFound(String imdbId) {
        super("Could not find film with IMDB ID " + imdbId);
    }
}
