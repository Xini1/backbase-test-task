package com.github.xini1.port;

import java.util.Collection;
import java.util.Objects;

/**
 * @author Maxim Tereshchenko
 */
public interface SearchFilmUseCase {

    Collection<Response> search(String apiToken, String name);

    class Response {

        private final String imdbId;
        private final String name;

        public Response(String imdbId, String name) {
            this.imdbId = Objects.requireNonNull(imdbId);
            this.name = Objects.requireNonNull(name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(imdbId, name);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            var response = (Response) o;
            return imdbId.equals(response.imdbId) && name.equals(response.name);
        }

        @Override
        public String toString() {
            return "Response{" +
                    "imdbId='" + imdbId + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
