package com.github.xini1.domain;

import com.github.xini1.port.FilmDescriptions;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Maxim Tereshchenko
 */
final class InMemoryFilmDescriptions implements FilmDescriptions {

    private final Set<Stub> stubs;

    InMemoryFilmDescriptions(Stub... stubs) {
        this.stubs = Set.of(stubs);
    }

    @Override
    public Page byName(String apiToken, String name, int pageNumber) {
        return new StubsPage(stubs(name), pageNumber);
    }

    @Override
    public FilmDescription byId(String apiToken, String imdbId) {
        return stubs.stream()
                .filter(stub -> stub.imdbId.equals(imdbId))
                .findAny()
                .orElseThrow();
    }

    @Override
    public boolean isNotExists(String apiToken, String imdbId) {
        return stubs.stream()
                .noneMatch(stub -> stub.imdbId.equals(imdbId));
    }

    private List<Stub> stubs(String name) {
        return stubs.stream()
                .filter(stub -> stub.name.equals(name))
                .collect(Collectors.toList());
    }

    static final class Stub implements FilmDescription {

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

        @Override
        public String toString() {
            return "Stub{" +
                    "imdbId='" + imdbId + '\'' +
                    ", name='" + name + '\'' +
                    ", boxOffice=" + boxOffice +
                    '}';
        }
    }

    private static final class StubsPage implements Page {

        private final Collection<Stub> allFoundStubs;
        private final int pageNumber;

        private StubsPage(Collection<Stub> allFoundStubs, int pageNumber) {
            this.allFoundStubs = allFoundStubs;
            this.pageNumber = pageNumber;
        }

        @Override
        public Collection<FilmDescription> filmDescriptions() {
            return allFoundStubs.stream()
                    .skip(10L * pageNumber)
                    .limit(10)
                    .collect(Collectors.toList());
        }

        @Override
        public int page() {
            return pageNumber;
        }

        @Override
        public int totalPages() {
            return allFoundStubs.size() / 10 + accountForLastPage();
        }

        private int accountForLastPage() {
            if (allFoundStubs.size() % 10 == 0) {
                return 0;
            }

            return 1;
        }
    }
}
