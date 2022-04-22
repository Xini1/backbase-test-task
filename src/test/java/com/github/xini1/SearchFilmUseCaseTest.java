package com.github.xini1;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.xini1.domain.Configuration;
import com.github.xini1.port.SearchFilmUseCase;
import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 * @author Maxim Tereshchenko
 */
final class SearchFilmUseCaseTest {

    private final SearchFilmUseCase useCase = new Configuration(
            new InMemoryFilmDescriptions(
                    Map.of(
                            "id1", "Unique",
                            "id2", "Non unique",
                            "id3", "Non unique"
                    )
            )
    )
            .searchFilmUseCase();

    @Test
    void givenExistingFilmName_thenFilmDescriptionReturned() {
        assertThat(useCase.search("token", "Unique"))
                .containsOnly(new SearchFilmUseCase.Response("id1", "Unique"));
    }

    @Test
    void givenTwoFilmsWithSameNameExist_thenFilmDescriptionsReturned() {
        assertThat(useCase.search("token", "Non unique"))
                .containsOnly(
                        new SearchFilmUseCase.Response("id2", "Non unique"),
                        new SearchFilmUseCase.Response("id3", "Non unique")
                );
    }
}
