package com.github.xini1;

import com.github.xini1.port.FilmDescriptions;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Maxim Tereshchenko
 */
final class InMemoryFilmDescriptions implements FilmDescriptions {

    private final Map<String, String> filmDescriptionsById;

    InMemoryFilmDescriptions(Map<String, String> filmDescriptionsById) {
        this.filmDescriptionsById = filmDescriptionsById;
    }

    @Override
    public Collection<FilmDescription> byName(String apiToken, String name) {
        return filmDescriptionsById.entrySet()
                .stream()
                .filter(entry -> entry.getValue().equals(name))
                .map(entry -> filmDescription(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    private FilmDescription filmDescription(String id, String name) {
        return new FilmDescription() {
            @Override
            public String imdbId() {
                return id;
            }

            @Override
            public String name() {
                return name;
            }
        };
    }
}
