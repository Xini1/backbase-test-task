package com.github.xini1.port;

import java.util.Collection;

/**
 * @author Maxim Tereshchenko
 */
public interface SearchFilmUseCase {

    Collection<Response> search(String apiToken, String name);

    interface Response {

        String imdbId();

        String name();

        boolean isWonOscar();
    }
}
