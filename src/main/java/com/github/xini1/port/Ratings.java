package com.github.xini1.port;

import java.util.Map;

/**
 * @author Maxim Tereshchenko
 */
public interface Ratings {

    void tryAdd(String apiToken, String imdbId, int rating);

    Map<String, Integer> top10();

    int average(String imdbId);
}
