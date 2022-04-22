package com.github.xini1;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.github.xini1.domain.Configuration;
import com.github.xini1.exception.ApiTokenMissing;
import com.github.xini1.exception.FilmNotFound;
import com.github.xini1.exception.ImdbIdRequired;
import com.github.xini1.exception.IncorrectRating;
import com.github.xini1.port.usecase.List10TopRatedFilmsUseCase;
import com.github.xini1.port.usecase.RateFilmUseCase;
import com.github.xini1.port.usecase.SearchFilmUseCase;
import org.junit.jupiter.api.Test;

/**
 * @author Maxim Tereshchenko
 */
final class RateFilmUseCaseTest {

    private final RateFilmUseCase rateFilmUseCase;
    private final List10TopRatedFilmsUseCase list10TopRatedFilmsUseCase;
    private final SearchFilmUseCase searchFilmUseCase;

    RateFilmUseCaseTest() {
        var configuration = new Configuration(
                new InMemoryFilmDescriptions(
                        new InMemoryFilmDescriptions.Stub("id1", "Rated1", 1),
                        new InMemoryFilmDescriptions.Stub("id2", "Rated2", 2),
                        new InMemoryFilmDescriptions.Stub("id3", "Rated3", 3),
                        new InMemoryFilmDescriptions.Stub("id4", "Rated4", 4),
                        new InMemoryFilmDescriptions.Stub("id5", "Unrated")
                ),
                new InMemoryOscarWinners(),
                new InMemoryRatings()
        );
        rateFilmUseCase = configuration.rateFilmUseCase();
        list10TopRatedFilmsUseCase = configuration.list10TopRatedFilmsUseCase();
        searchFilmUseCase = configuration.searchFilmUseCase();
    }

    @Test
    void givenUserRatedFilm_thenFilmHasExactRating() {
        rateFilmUseCase.rate("token", "id1", 10);

        assertThat(list10TopRatedFilmsUseCase.top10RatedSortedByBoxOffice("token"))
                .containsExactly(new FilmDtoStub("id1", "Rated1", false, 10, 4));
    }

    @Test
    void givenTwoUsersRatedFilm_thenFilmHasAverageOfTwoRating() {
        rateFilmUseCase.rate("token1", "id1", 10);
        rateFilmUseCase.rate("token2", "id1", 4);

        assertThat(list10TopRatedFilmsUseCase.top10RatedSortedByBoxOffice("token1"))
                .containsExactly(new FilmDtoStub("id1", "Rated1", false, 7, 4));
    }

    @Test
    void givenAverageRatingHasFractionPart_thenFilmHasRoundedRating() {
        rateFilmUseCase.rate("token1", "id1", 10);
        rateFilmUseCase.rate("token2", "id1", 5);

        assertThat(list10TopRatedFilmsUseCase.top10RatedSortedByBoxOffice("token1"))
                .containsExactly(new FilmDtoStub("id1", "Rated1", false, 8, 4));
    }

    @Test
    void givenSeveralRatedFilms_thenFilmsOrderedByBoxOffice() {
        rateFilmUseCase.rate("token", "id1", 10);
        rateFilmUseCase.rate("token", "id2", 10);
        rateFilmUseCase.rate("token", "id3", 10);
        rateFilmUseCase.rate("token", "id4", 10);

        assertThat(list10TopRatedFilmsUseCase.top10RatedSortedByBoxOffice("token"))
                .containsExactly(
                        new FilmDtoStub("id4", "Rated4", false, 10, 4),
                        new FilmDtoStub("id3", "Rated3", false, 10, 3),
                        new FilmDtoStub("id2", "Rated2", false, 10, 2),
                        new FilmDtoStub("id1", "Rated1", false, 10, 1)
                );
    }

    @Test
    void givenApiTokenIsNull_thenApiTokenMissingThrown() {
        assertThatThrownBy(() -> rateFilmUseCase.rate(null, "whatever", 1))
                .isInstanceOf(ApiTokenMissing.class);
    }

    @Test
    void givenApiTokenIsEmpty_thenApiTokenMissingThrown() {
        assertThatThrownBy(() -> rateFilmUseCase.rate("", "whatever", 1))
                .isInstanceOf(ApiTokenMissing.class);
    }

    @Test
    void givenImdbIdIsNull_thenImdbIdRequiredThrown() {
        assertThatThrownBy(() -> rateFilmUseCase.rate("token", null, 1))
                .isInstanceOf(ImdbIdRequired.class);
    }

    @Test
    void givenImdbIdIsEmpty_thenImdbIdRequiredThrown() {
        assertThatThrownBy(() -> rateFilmUseCase.rate("token", "", 1))
                .isInstanceOf(ImdbIdRequired.class);
    }

    @Test
    void givenRatingBelow1_thenIncorrectRatingThrown() {
        assertThatThrownBy(() -> rateFilmUseCase.rate("token", "id", 0))
                .isInstanceOf(IncorrectRating.class);
    }

    @Test
    void givenRatingGreater10_thenIncorrectRatingThrown() {
        assertThatThrownBy(() -> rateFilmUseCase.rate("token", "id", 11))
                .isInstanceOf(IncorrectRating.class);
    }

    @Test
    void givenUserRateNonexistentFilm_thenFilmNotFoundThrown() {
        assertThatThrownBy(() -> rateFilmUseCase.rate("token", "id", 1))
                .isInstanceOf(FilmNotFound.class);
    }

    @Test
    void givenUserRatedFilm_thenFilmHasExactRatingInSearchResult() {
        rateFilmUseCase.rate("token", "id1", 10);

        assertThat(searchFilmUseCase.search("token", "Rated1"))
                .containsExactly(new FilmDtoStub("id1", "Rated1", false, 10, 4));
    }
}
