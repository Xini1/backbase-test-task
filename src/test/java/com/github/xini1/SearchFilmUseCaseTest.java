package com.github.xini1;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.github.xini1.domain.Configuration;
import com.github.xini1.exception.ApiTokenMissing;
import com.github.xini1.exception.FilmNameRequired;
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
                            "id3", "Non unique",
                            "id4", "Won Oscar"
                    )
            ),
            new InMemoryOscarWinners("Won Oscar")
    )
            .searchFilmUseCase();

    @Test
    void givenExistingFilmName_thenFilmReturned() {
        assertThat(useCase.search("token", "Unique"))
                .containsOnly(new ResponseStub("id1", "Unique"));
    }

    @Test
    void givenTwoFilmsWithSameNameExist_thenFilmsReturned() {
        assertThat(useCase.search("token", "Non unique"))
                .containsOnly(
                        new ResponseStub("id2", "Non unique"),
                        new ResponseStub("id3", "Non unique")
                );
    }

    @Test
    void givenNoFilmWithSuchNameExist_thenEmptyCollectionReturned() {
        assertThat(useCase.search("token", "Non existent"))
                .isEmpty();
    }

    @Test
    void givenApiTokenIsNull_thenApiTokenMissingThrown() {
        assertThatThrownBy(() -> useCase.search(null, "Whatever"))
                .isInstanceOf(ApiTokenMissing.class);
    }

    @Test
    void givenApiTokenIsEmpty_thenApiTokenMissingThrown() {
        assertThatThrownBy(() -> useCase.search("", "Whatever"))
                .isInstanceOf(ApiTokenMissing.class);
    }

    @Test
    void givenFilmNameIsNull_thenFilmNameRequiredThrown() {
        assertThatThrownBy(() -> useCase.search("whatever", null))
                .isInstanceOf(FilmNameRequired.class);
    }

    @Test
    void givenFilmNameIsEmpty_thenFilmNameRequiredThrown() {
        assertThatThrownBy(() -> useCase.search("whatever", ""))
                .isInstanceOf(FilmNameRequired.class);
    }

    @Test
    void givenNameOfFilmWhichWonOscar_thenFilmWhichWonOscarReturned() {
        assertThat(useCase.search("token", "Won Oscar"))
                .containsOnly(new ResponseStub("id4", "Won Oscar", true));
    }
}
