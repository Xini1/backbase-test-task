package com.github.xini1.application.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.xini1.port.usecase.FilmDto;

import java.util.Objects;

/**
 * @author Maxim Tereshchenko
 */
public class JsonFilmDto {

    private final String imdbId;
    private final String name;
    private final boolean oscarWinner;
    private final int rating;
    private final int boxOffice;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public JsonFilmDto(
            @JsonProperty("imdbId") String imdbId,
            @JsonProperty("name") String name,
            @JsonProperty("oscarWinner") boolean oscarWinner,
            @JsonProperty("rating") int rating,
            @JsonProperty("boxOffice") int boxOffice
    ) {
        this.imdbId = imdbId;
        this.name = name;
        this.oscarWinner = oscarWinner;
        this.rating = rating;
        this.boxOffice = boxOffice;
    }

    public JsonFilmDto(FilmDto filmDto) {
        this(
                filmDto.imdbId(),
                filmDto.name(),
                filmDto.isOscarWinner(),
                filmDto.rating(),
                filmDto.boxOffice()
        );
    }

    public String getImdbId() {
        return imdbId;
    }

    public String getName() {
        return name;
    }

    public boolean isOscarWinner() {
        return oscarWinner;
    }

    public int getRating() {
        return rating;
    }

    public int getBoxOffice() {
        return boxOffice;
    }

    @Override
    public int hashCode() {
        return Objects.hash(imdbId, name, oscarWinner, rating, boxOffice);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        var that = (JsonFilmDto) o;
        return oscarWinner == that.oscarWinner &&
                rating == that.rating &&
                boxOffice == that.boxOffice &&
                imdbId.equals(that.imdbId) &&
                name.equals(that.name);
    }

    @Override
    public String toString() {
        return "JsonFilmDto{" +
                "imdbId='" + imdbId + '\'' +
                ", name='" + name + '\'' +
                ", oscarWinner=" + oscarWinner +
                ", rating=" + rating +
                ", boxOffice=" + boxOffice +
                '}';
    }
}
