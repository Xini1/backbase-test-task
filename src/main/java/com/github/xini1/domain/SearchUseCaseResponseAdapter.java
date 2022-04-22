package com.github.xini1.domain;

import com.github.xini1.port.FilmDescriptions;
import com.github.xini1.port.SearchFilmUseCase;

import java.util.Objects;

/**
 * @author Maxim Tereshchenko
 */
final class SearchUseCaseResponseAdapter implements SearchFilmUseCase.Response {

    private final FilmDescriptions.FilmDescription filmDescription;

    SearchUseCaseResponseAdapter(FilmDescriptions.FilmDescription filmDescription) {
        this.filmDescription = Objects.requireNonNull(filmDescription);
    }

    @Override
    public String imdbId() {
        return filmDescription.imdbId();
    }

    @Override
    public String name() {
        return filmDescription.name();
    }

    @Override
    public int hashCode() {
        return Objects.hash(filmDescription);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || o.getClass().isAssignableFrom(SearchFilmUseCase.Response.class)) {
            return false;
        }

        SearchFilmUseCase.Response that = (SearchFilmUseCase.Response) o;
        return imdbId().equals(that.imdbId()) && name().equals(that.name());
    }
}
