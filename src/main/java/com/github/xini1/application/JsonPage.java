package com.github.xini1.application;

import com.github.xini1.port.usecase.SearchFilmUseCase;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author Maxim Tereshchenko
 */
public class JsonPage {

    private final Collection<JsonFilmDto> films;
    private final int page;
    private final int totalPages;

    JsonPage(SearchFilmUseCase.Page page) {
        films = page.films()
                .stream()
                .map(JsonFilmDto::new)
                .collect(Collectors.toList());
        this.page = page.page();
        totalPages = page.totalPages();
    }

    public Collection<JsonFilmDto> getFilms() {
        return films;
    }

    public int getPage() {
        return page;
    }

    public int getTotalPages() {
        return totalPages;
    }
}
