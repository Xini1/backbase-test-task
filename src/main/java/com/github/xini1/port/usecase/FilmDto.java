package com.github.xini1.port.usecase;

import java.util.Objects;

/**
 * @author Maxim Tereshchenko
 */
public abstract class FilmDto {

    public abstract String imdbId();

    public abstract String name();

    public abstract boolean isOscarWinner();

    public abstract int rating();

    public abstract int boxOffice();

    @Override
    public int hashCode() {
        return Objects.hash(imdbId(), name(), isOscarWinner(), rating(), boxOffice());
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof FilmDto)) {
            return false;
        }

        FilmDto that = (FilmDto) obj;
        return imdbId().equals(that.imdbId()) &&
                name().equals(that.name()) &&
                isOscarWinner() == that.isOscarWinner() &&
                rating() == that.rating();
    }

    @Override
    public String toString() {
        return "FilmDtoAdapter{" +
                "imdbId=" + imdbId() +
                ", name=" + name() +
                ", isOscarWinner=" + isOscarWinner() +
                ", rating=" + rating() +
                '}';
    }
}
