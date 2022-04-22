package com.github.xini1.domain;

import com.github.xini1.port.FilmDescriptions;
import com.github.xini1.port.SearchFilmUseCase;

import java.util.Objects;

/**
 * @author Maxim Tereshchenko
 */
final class SearchUseCaseResponseAdapter implements SearchFilmUseCase.Response {

    private final FilmDescriptions.FilmDescription filmDescription;
    private final boolean wonOscar;

    SearchUseCaseResponseAdapter(FilmDescriptions.FilmDescription filmDescription, boolean wonOscar) {
        this.filmDescription = Objects.requireNonNull(filmDescription);
        this.wonOscar = wonOscar;
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
    public boolean isWonOscar() {
        return wonOscar;
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
        return imdbId().equals(that.imdbId()) && name().equals(that.name()) && isWonOscar() == that.isWonOscar();
    }
}
