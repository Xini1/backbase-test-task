package com.github.xini1.domain;

import com.github.xini1.port.SearchFilmUseCase;

import java.util.Collection;
import java.util.Set;

/**
 * @author Maxim Tereshchenko
 */
final class FilmService implements SearchFilmUseCase {
    @Override
    public Collection<Response> search(String apiToken, String name) {
        return Set.of(new Response("id", name));
    }
}
