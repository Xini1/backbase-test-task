package com.github.xini1.application.dto;

import com.github.xini1.port.usecase.SearchFilmUseCase;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Maxim Tereshchenko
 */
public class JsonPage {

    private final Collection<JsonFilmDto> films;
    private final int page;
    private final int totalPages;

    public JsonPage(SearchFilmUseCase.Page page) {
        films = page.films()
                .stream()
                .map(JsonFilmDto::new)
                .collect(Collectors.collectingAndThen(Collectors.toList(), List::copyOf));
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
