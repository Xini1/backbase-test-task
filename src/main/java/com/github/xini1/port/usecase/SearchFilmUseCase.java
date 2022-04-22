package com.github.xini1.port.usecase;

import java.util.Collection;
import java.util.Objects;

/**
 * @author Maxim Tereshchenko
 */
public interface SearchFilmUseCase {

    Page search(String apiToken, String name, int pageNumber);

    abstract class Page {

        public abstract Collection<FilmDto> films();

        public abstract int page();

        public abstract int totalPages();

        @Override
        public int hashCode() {
            return Objects.hash(films(), page(), totalPages());
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Page)) {
                return false;
            }

            Page that = (Page) obj;
            return films().equals(that.films()) &&
                    page() == that.page() &&
                    totalPages() == that.totalPages();
        }

        @Override
        public String toString() {
            return "Page{" +
                    "films=" + films() +
                    ", page=" + page() +
                    ", totalPages=" + totalPages() +
                    '}';
        }
    }
}
