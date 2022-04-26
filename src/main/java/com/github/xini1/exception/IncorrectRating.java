package com.github.xini1.exception;

/**
 * @author Maxim Tereshchenko
 */
public final class IncorrectRating extends RuntimeException {

    public IncorrectRating() {
        super("Rating can not be less than 1 and greater than 10");
    }
}
