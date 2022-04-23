package com.github.xini1.exception;

/**
 * @author Maxim Tereshchenko
 */
public final class IncorrectPageNumber extends RuntimeException {

    public IncorrectPageNumber() {
        super("Page number can not be less than 0");
    }
}
