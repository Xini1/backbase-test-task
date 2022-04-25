package com.github.xini1.application.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.xini1.port.usecase.Page;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Maxim Tereshchenko
 */
public class JsonPage {

    private final Collection<JsonFilmDto> films;
    private final int page;
    private final int totalPages;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public JsonPage(
            @JsonProperty("films") Collection<JsonFilmDto> films,
            @JsonProperty("page") int page,
            @JsonProperty("totalPages") int totalPages
    ) {
        this.films = List.copyOf(films);
        this.page = page;
        this.totalPages = totalPages;
    }

    public JsonPage(Page page) {
        this(
                page.films()
                        .stream()
                        .map(JsonFilmDto::new)
                        .collect(Collectors.toList()),
                page.page(),
                page.totalPages()
        );
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

    @Override
    public int hashCode() {
        return Objects.hash(films, page, totalPages);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        var jsonPage = (JsonPage) o;
        return page == jsonPage.page && totalPages == jsonPage.totalPages && films.equals(jsonPage.films);
    }

    @Override
    public String toString() {
        return "JsonPage{" +
                "films=" + films +
                ", page=" + page +
                ", totalPages=" + totalPages +
                '}';
    }
}
