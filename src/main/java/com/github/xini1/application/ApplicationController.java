package com.github.xini1.application;

import com.github.xini1.application.dto.JsonPage;
import com.github.xini1.port.usecase.ListTopRatedFilmsUseCase;
import com.github.xini1.port.usecase.RateFilmUseCase;
import com.github.xini1.port.usecase.SearchFilmUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Maxim Tereshchenko
 */
@RestController
@RequestMapping("/films")
class ApplicationController {

    private final SearchFilmUseCase searchFilmUseCase;
    private final RateFilmUseCase rateFilmUseCase;
    private final ListTopRatedFilmsUseCase listTopRatedFilmsUseCase;

    ApplicationController(
            SearchFilmUseCase searchFilmUseCase,
            RateFilmUseCase rateFilmUseCase,
            ListTopRatedFilmsUseCase listTopRatedFilmsUseCase
    ) {
        this.searchFilmUseCase = searchFilmUseCase;
        this.rateFilmUseCase = rateFilmUseCase;
        this.listTopRatedFilmsUseCase = listTopRatedFilmsUseCase;
    }

    @GetMapping
    JsonPage search(
            @RequestHeader String apiToken,
            @RequestParam String search,
            @RequestParam(defaultValue = "0") int page
    ) {
        return new JsonPage(searchFilmUseCase.search(apiToken, search, page));
    }

    @PostMapping("/{imdbId}/ratings")
    void rate(@RequestHeader String apiToken, @PathVariable String imdbId, @RequestBody int rating) {
        rateFilmUseCase.rate(apiToken, imdbId, rating);
    }

    @GetMapping("/top")
    JsonPage top(
            @RequestHeader String apiToken,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int elements
    ) {
        return new JsonPage(listTopRatedFilmsUseCase.topRatedSortedByBoxOffice(apiToken, page, elements));
    }
}
