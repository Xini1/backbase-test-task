package com.github.xini1.port;

import java.util.Collection;

/**
 * @author Maxim Tereshchenko
 */
public interface FilmDescriptions {

    Collection<FilmDescription> byName(String apiToken, String name);

    interface FilmDescription {

        String imdbId();

        String name();
    }
}
