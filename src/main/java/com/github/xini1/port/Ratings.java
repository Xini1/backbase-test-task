package com.github.xini1.port;

import java.util.Map;

/**
 * @author Maxim Tereshchenko
 */
public interface Ratings {

    void add(String apiToken, String imdbId, int rating);

    Page top(int page, int elementsOnPage);

    int average(String imdbId);

    interface Page {

        Map<String, Integer> imdbIdToRating();

        int page();

        int totalPages();
    }
}
