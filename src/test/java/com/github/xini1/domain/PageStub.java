package com.github.xini1.domain;

import com.github.xini1.port.usecase.FilmDto;
import com.github.xini1.port.usecase.SearchFilmUseCase;

import java.util.Collection;
import java.util.List;

/**
 * @author Maxim Tereshchenko
 */
final class PageStub extends SearchFilmUseCase.Page {

    private final Collection<FilmDto> films;

    PageStub(FilmDto... films) {
        this.films = List.of(films);
    }

    @Override
    public Collection<FilmDto> films() {
        return films;
    }

    @Override
    public int page() {
        return 0;
    }

    @Override
    public int totalPages() {
        return 1;
    }
}
