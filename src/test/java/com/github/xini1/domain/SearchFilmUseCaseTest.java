package com.github.xini1.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.github.xini1.InMemoryFilmDescriptions;
import com.github.xini1.exception.ApiTokenRequired;
import com.github.xini1.exception.FilmNameRequired;
import com.github.xini1.exception.IncorrectPageNumber;
import com.github.xini1.port.usecase.SearchFilmUseCase;
import org.junit.jupiter.api.Test;

/**
 * @author Maxim Tereshchenko
 */
final class SearchFilmUseCaseTest {

    private final SearchFilmUseCase useCase = new FilmsModuleConfiguration(
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
        assertThat(useCase.search("token", "Unique", 0))
                .isEqualTo(new PageStub(new FilmDtoStub("id1", "Unique")));
    }

    @Test
    void givenTwoFilmsWithSameNameExist_thenFilmsReturned() {
        assertThat(useCase.search("token", "Non unique", 0))
                .isEqualTo(
                        new PageStub(
                                new FilmDtoStub("id2", "Non unique"),
                                new FilmDtoStub("id3", "Non unique")
                        )
                );
    }

    @Test
    void givenNoFilmWithSuchNameExist_thenEmptyCollectionReturned() {
        assertThat(useCase.search("token", "Non existent", 0))
                .isEqualTo(new EmptyPage());
    }

    @Test
    void givenApiTokenIsNull_thenApiTokenMissingThrown() {
        assertThatThrownBy(() -> useCase.search(null, "Whatever", 0))
                .isInstanceOf(ApiTokenRequired.class);
    }

    @Test
    void givenApiTokenIsEmpty_thenApiTokenMissingThrown() {
        assertThatThrownBy(() -> useCase.search("", "Whatever", 0))
                .isInstanceOf(ApiTokenRequired.class);
    }

    @Test
    void givenFilmNameIsNull_thenFilmNameRequiredThrown() {
        assertThatThrownBy(() -> useCase.search("whatever", null, 0))
                .isInstanceOf(FilmNameRequired.class);
    }

    @Test
    void givenFilmNameIsEmpty_thenFilmNameRequiredThrown() {
        assertThatThrownBy(() -> useCase.search("whatever", "", 0))
                .isInstanceOf(FilmNameRequired.class);
    }

    @Test
    void givenNameOfFilmWhichWonOscar_thenFilmWhichWonOscarReturned() {
        assertThat(useCase.search("token", "Won Oscar", 0))
                .isEqualTo(new PageStub(new FilmDtoStub("id4", "Won Oscar", true)));
    }

    @Test
    void givenNameOfFilmWhichHas10BoxOffice_thenFilmWhichHas10BoxOfficeReturned() {
        assertThat(useCase.search("token", "Has 10 box office", 0))
                .isEqualTo(
                        new PageStub(
                                new FilmDtoStub(
                                        "id5",
                                        "Has 10 box office",
                                        false,
                                        0,
                                        10
                                )
                        )
                );
    }

    @Test
    void givenPageNumberIsLower0_thenIncorrectPageNumberThrown() {
        assertThatThrownBy(() -> useCase.search("whatever", "whatever", -1))
                .isInstanceOf(IncorrectPageNumber.class);
    }
}
