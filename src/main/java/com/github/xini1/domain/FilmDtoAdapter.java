package com.github.xini1.domain;

import com.github.xini1.port.FilmDescriptions;
import com.github.xini1.port.FilmDto;

import java.util.Objects;

/**
 * @author Maxim Tereshchenko
 */
final class FilmDtoAdapter implements FilmDto {

    private final FilmDescriptions.FilmDescription filmDescription;
    private final boolean oscarWinner;
    private final int rating;

    FilmDtoAdapter(FilmDescriptions.FilmDescription filmDescription, boolean oscarWinner, int rating) {
        this.filmDescription = Objects.requireNonNull(filmDescription);
        this.oscarWinner = oscarWinner;
        this.rating = rating;
    }

    @Override
    public String imdbId() {
        return filmDescription.imdbId();
    }

    @Override
    public String name() {
        return filmDescription.name();
    }

    @Override
    public boolean isOscarWinner() {
        return oscarWinner;
    }

    @Override
    public int rating() {
        return rating;
    }

    @Override
    public int boxOffice() {
        return filmDescription.boxOffice();
    }

    @Override
    public int hashCode() {
        return Objects.hash(filmDescription);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || o.getClass().isAssignableFrom(FilmDto.class)) {
            return false;
        }

        FilmDto that = (FilmDto) o;
        return imdbId().equals(that.imdbId()) &&
                name().equals(that.name()) &&
                isOscarWinner() == that.isOscarWinner() &&
                rating() == that.rating();
    }
}
