package com.github.xini1.port;

import java.util.Collection;

/**
 * @author Maxim Tereshchenko
 */
public interface SearchFilmUseCase {

    Collection<FilmDto> search(String apiToken, String name);
}
