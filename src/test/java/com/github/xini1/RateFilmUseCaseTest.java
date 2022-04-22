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
                new InMemoryFilmDescriptions(new InMemoryFilmDescriptions.Stub("id1", "Rated")),
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
                .containsExactly(new FilmDtoStub("id1", "Rated", false, 10, 0));
    }
}
