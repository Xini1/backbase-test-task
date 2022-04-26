package com.github.xini1.exception;

/**
 * @author Maxim Tereshchenko
 */
public final class ElementsOnPageLessThanZero extends RuntimeException {

    public ElementsOnPageLessThanZero() {
        super("Number of elements on page can not be less than 0");
    }
}
