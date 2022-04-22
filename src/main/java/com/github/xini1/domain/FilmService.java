package com.github.xini1.domain;

import com.github.xini1.exception.ApiTokenMissing;
import com.github.xini1.exception.FilmNameRequired;
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
        if (apiToken == null || apiToken.isBlank()) {
            throw new ApiTokenMissing();
        }
        if (name == null || name.isBlank()) {
            throw new FilmNameRequired();
        }
        return filmDescriptions.byName(apiToken, name)
                .stream()
                .map(SearchUseCaseResponseAdapter::new)
                .collect(Collectors.toList());
    }
}
