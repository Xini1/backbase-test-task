package com.github.xini1.exception;

/**
 * @author Maxim Tereshchenko
 */
public final class ImdbIdRequired extends RuntimeException {

    public ImdbIdRequired() {
        super("IMDB ID is required");
    }
}
