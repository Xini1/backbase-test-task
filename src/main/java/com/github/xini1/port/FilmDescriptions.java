package com.github.xini1.port;

import java.util.Collection;

/**
 * @author Maxim Tereshchenko
 */
public interface FilmDescriptions {

    Page byName(String apiToken, String name, int pageNumber);

    FilmDescription byId(String apiToken, String imdbId);

    boolean isNotExists(String apiToken, String imdbId);

    interface FilmDescription {

        String imdbId();

        String name();

        int boxOffice();
    }

    interface Page {

        Collection<FilmDescription> filmDescriptions();

        int page();

        int totalPages();
    }
}
