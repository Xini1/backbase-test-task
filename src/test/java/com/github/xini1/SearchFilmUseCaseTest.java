package com.github.xini1;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.xini1.domain.Configuration;
import com.github.xini1.port.SearchFilmUseCase;
import org.junit.jupiter.api.Test;

/**
 * @author Maxim Tereshchenko
 */
final class SearchFilmUseCaseTest {

    private final SearchFilmUseCase useCase = new Configuration().searchFilmUseCase();

    @Test
    void givenExistingFilmName_thenFilmDescriptionReturned() {
        assertThat(useCase.search("token", "Avengers"))
                .contains(new SearchFilmUseCase.Response("id", "Avengers"));
    }
}
