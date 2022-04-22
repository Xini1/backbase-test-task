package com.github.xini1.domain;

import com.github.xini1.exception.ApiTokenMissing;
import com.github.xini1.exception.FilmNameRequired;
import com.github.xini1.port.FilmDescriptions;
import com.github.xini1.port.FilmDto;
import com.github.xini1.port.List10TopRatedFilmsUseCase;
import com.github.xini1.port.OscarWinners;
import com.github.xini1.port.RateFilmUseCase;
import com.github.xini1.port.Ratings;
import com.github.xini1.port.SearchFilmUseCase;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Maxim Tereshchenko
 */
final class FilmService implements SearchFilmUseCase, RateFilmUseCase, List10TopRatedFilmsUseCase {

    private final FilmDescriptions filmDescriptions;
    private final OscarWinners oscarWinners;
    private final Ratings ratings;

    FilmService(FilmDescriptions filmDescriptions, OscarWinners oscarWinners, Ratings ratings) {
        this.filmDescriptions = filmDescriptions;
        this.oscarWinners = oscarWinners;
        this.ratings = ratings;
    }

    @Override
    public Collection<FilmDto> search(String apiToken, String name) {
        if (apiToken == null || apiToken.isBlank()) {
            throw new ApiTokenMissing();
        }
        if (name == null || name.isBlank()) {
            throw new FilmNameRequired();
        }
        return filmDescriptions.byName(apiToken, name)
                .stream()
                .map(this::filmDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<FilmDto> byBoxOffice(String apiToken) {
        return ratings.top10()
                .entrySet()
                .stream()
                .map(entry -> filmDto(apiToken, entry))
                .sorted(byBoxOffice())
                .collect(Collectors.toList());
    }

    @Override
    public void rate(String apiToken, String imdbId, int rating) {
        ratings.tryAdd(apiToken, imdbId, rating);
    }

    private Comparator<FilmDto> byBoxOffice() {
        return Comparator.comparing(FilmDto::boxOffice).reversed();
    }

    private FilmDto filmDto(String apiToken, Map.Entry<String, Integer> entry) {
        var filmDescription = filmDescriptions.byId(apiToken, entry.getKey());
        return new FilmDtoAdapter(filmDescription, isOscarWinner(filmDescription), entry.getValue());
    }

    private FilmDto filmDto(FilmDescriptions.FilmDescription filmDescription) {
        return new FilmDtoAdapter(filmDescription, isOscarWinner(filmDescription), rating(filmDescription));
    }

    private int rating(FilmDescriptions.FilmDescription filmDescription) {
        return ratings.average(filmDescription.imdbId());
    }

    private boolean isOscarWinner(FilmDescriptions.FilmDescription filmDescription) {
        return oscarWinners.isWinner(filmDescription.name());
    }
}
