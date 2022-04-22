package com.github.xini1.port;

import java.util.Collection;

/**
 * @author Maxim Tereshchenko
 */
public interface FilmDescriptions {

    Collection<FilmDescription> byName(String apiToken, String name);

    FilmDescription byId(String apiToken, String imdbId);

    boolean isNotExists(String apiToken, String imdbId);

    interface FilmDescription {

        String imdbId();

        String name();

        int boxOffice();
    }
}
