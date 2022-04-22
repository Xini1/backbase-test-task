package com.github.xini1;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.xini1.domain.Configuration;
import com.github.xini1.port.List10TopRatedFilmsUseCase;
import com.github.xini1.port.RateFilmUseCase;
import org.junit.jupiter.api.Test;

/**
 * @author Maxim Tereshchenko
 */
final class RateFilmUseCaseTest {

    private final RateFilmUseCase rateFilmUseCase;
    private final List10TopRatedFilmsUseCase list10TopRatedFilmsUseCase;

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
    }

    @Test
    void givenUserRatedFilm_thenFilmHasExactRating() {
        rateFilmUseCase.rate("token", "id1", 10);

        assertThat(list10TopRatedFilmsUseCase.byBoxOffice("token"))
                .containsExactly(new FilmDtoStub("id1", "Rated1", false, 10, 4));
    }

    @Test
    void givenTwoUsersRatedFilm_thenFilmHasAverageOfTwoRating() {
        rateFilmUseCase.rate("token1", "id1", 10);
        rateFilmUseCase.rate("token2", "id1", 4);

        assertThat(list10TopRatedFilmsUseCase.byBoxOffice("token1"))
                .containsExactly(new FilmDtoStub("id1", "Rated1", false, 7, 4));
    }

    @Test
    void givenAverageRatingHasFractionPart_thenFilmHasRoundedRating() {
        rateFilmUseCase.rate("token1", "id1", 10);
        rateFilmUseCase.rate("token2", "id1", 5);

        assertThat(list10TopRatedFilmsUseCase.byBoxOffice("token1"))
                .containsExactly(new FilmDtoStub("id1", "Rated1", false, 8, 4));
    }

    @Test
    void givenSeveralRatedFilms_thenFilmsOrderedByBoxOffice() {
        rateFilmUseCase.rate("token", "id1", 10);
        rateFilmUseCase.rate("token", "id2", 10);
        rateFilmUseCase.rate("token", "id3", 10);
        rateFilmUseCase.rate("token", "id4", 10);

        assertThat(list10TopRatedFilmsUseCase.byBoxOffice("token"))
                .containsExactly(
                        new FilmDtoStub("id4", "Rated4", false, 10, 4),
                        new FilmDtoStub("id3", "Rated3", false, 10, 3),
                        new FilmDtoStub("id2", "Rated2", false, 10, 2),
                        new FilmDtoStub("id1", "Rated1", false, 10, 1)
                );
    }
}
