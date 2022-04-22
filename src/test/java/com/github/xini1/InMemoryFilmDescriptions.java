package com.github.xini1;

import com.github.xini1.port.FilmDescriptions;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Maxim Tereshchenko
 */
final class InMemoryFilmDescriptions implements FilmDescriptions {

    private final Set<Stub> stubs;

    InMemoryFilmDescriptions(Set<Stub> stubs) {
        this.stubs = stubs;
    }

    InMemoryFilmDescriptions(Stub stub) {
        this(Set.of(stub));
    }

    @Override
    public Collection<FilmDescription> byName(String apiToken, String name) {
        return stubs.stream()
                .filter(stub -> stub.name.equals(name))
                .collect(Collectors.toList());
    }

    @Override
    public FilmDescription byId(String apiToken, String imdbId) {
        return stubs.stream()
                .filter(stub -> stub.imdbId.equals(imdbId))
                .findAny()
                .orElseThrow();
    }

    static class Stub implements FilmDescription {

        private final String imdbId;
        private final String name;
        private final int boxOffice;

        Stub(String imdbId, String name) {
            this(imdbId, name, 0);
        }

        Stub(String imdbId, String name, int boxOffice) {
            this.imdbId = imdbId;
            this.name = name;
            this.boxOffice = boxOffice;
        }

        @Override
        public String imdbId() {
            return imdbId;
        }

        @Override
        public String name() {
            return name;
        }

        @Override
        public int boxOffice() {
            return boxOffice;
        }

        @Override
        public int hashCode() {
            return Objects.hash(imdbId);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Stub stub = (Stub) o;
            return imdbId.equals(stub.imdbId);
        }
    }
}
