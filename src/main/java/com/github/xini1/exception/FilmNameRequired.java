package com.github.xini1.exception;

/**
 * @author Maxim Tereshchenko
 */
public final class FilmNameRequired extends RuntimeException {

    public FilmNameRequired() {
        super("Film name to search for is required");
    }
}
