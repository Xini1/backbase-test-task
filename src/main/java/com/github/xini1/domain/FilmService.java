package com.github.xini1.domain;

import com.github.xini1.port.FilmDescriptions;
import com.github.xini1.port.SearchFilmUseCase;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author Maxim Tereshchenko
 */
final class FilmService implements SearchFilmUseCase {

    private final FilmDescriptions filmDescriptions;

    FilmService(FilmDescriptions filmDescriptions) {
        this.filmDescriptions = filmDescriptions;
    }

    @Override
    public Collection<Response> search(String apiToken, String name) {
        return filmDescriptions.byName(apiToken, name)
                .stream()
                .map(this::response)
                .collect(Collectors.toList());
    }

    private Response response(FilmDescriptions.FilmDescription filmDescription) {
        return new Response(filmDescription.imdbId(), filmDescription.name());
    }
}
