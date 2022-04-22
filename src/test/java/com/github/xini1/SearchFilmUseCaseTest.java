package com.github.xini1;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.github.xini1.domain.Configuration;
import com.github.xini1.exception.ApiTokenMissing;
import com.github.xini1.exception.FilmNameRequired;
import com.github.xini1.port.usecase.SearchFilmUseCase;
import org.junit.jupiter.api.Test;

/**
 * @author Maxim Tereshchenko
 */
final class SearchFilmUseCaseTest {

    private final SearchFilmUseCase useCase = new Configuration(
            new InMemoryFilmDescriptions(
                    new InMemoryFilmDescriptions.Stub("id1", "Unique"),
                    new InMemoryFilmDescriptions.Stub("id2", "Non unique"),
                    new InMemoryFilmDescriptions.Stub("id3", "Non unique"),
                    new InMemoryFilmDescriptions.Stub("id4", "Won Oscar"),
                    new InMemoryFilmDescriptions.Stub("id5", "Has 10 box office", 10)
            ),
            new InMemoryOscarWinners("Won Oscar"),
            new InMemoryRatings()
    )
            .searchFilmUseCase();

    @Test
    void givenExistingFilmName_thenFilmReturned() {
        assertThat(useCase.search("token", "Unique"))
                .containsOnly(new FilmDtoStub("id1", "Unique"));
    }

    @Test
    void givenTwoFilmsWithSameNameExist_thenFilmsReturned() {
        assertThat(useCase.search("token", "Non unique"))
                .containsOnly(
                        new FilmDtoStub("id2", "Non unique"),
                        new FilmDtoStub("id3", "Non unique")
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
                .containsOnly(new FilmDtoStub("id4", "Won Oscar", true));
    }

    @Test
    void givenNameOfFilmWhichHas10BoxOffice_thenFilmWhichHas10BoxOfficeReturned() {
        assertThat(useCase.search("token", "Has 10 box office"))
                .containsOnly(
                        new FilmDtoStub("id5", "Has 10 box office", false, 0, 10)
                );
    }
}
