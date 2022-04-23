package com.github.xini1.application;

import com.github.xini1.application.dto.JsonFilmDto;
import com.github.xini1.application.dto.JsonPage;
import com.github.xini1.port.usecase.List10TopRatedFilmsUseCase;
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

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Maxim Tereshchenko
 */
@RestController
@RequestMapping("/films")
class ApplicationController {

    private final SearchFilmUseCase searchFilmUseCase;
    private final RateFilmUseCase rateFilmUseCase;
    private final List10TopRatedFilmsUseCase list10TopRatedFilmsUseCase;

    ApplicationController(
            SearchFilmUseCase searchFilmUseCase,
            RateFilmUseCase rateFilmUseCase,
            List10TopRatedFilmsUseCase list10TopRatedFilmsUseCase
    ) {
        this.searchFilmUseCase = searchFilmUseCase;
        this.rateFilmUseCase = rateFilmUseCase;
        this.list10TopRatedFilmsUseCase = list10TopRatedFilmsUseCase;
    }

    @GetMapping
    JsonPage search(@RequestHeader String apiToken, @RequestParam String search, @RequestParam int page) {
        return new JsonPage(searchFilmUseCase.search(apiToken, search, page));
    }

    @PostMapping("/{imdbId}/ratings")
    void rate(@RequestHeader String apiToken, @PathVariable String imdbId, @RequestBody int rating) {
        rateFilmUseCase.rate(apiToken, imdbId, rating);
    }

    @GetMapping("/top")
    List<JsonFilmDto> top(@RequestHeader String apiToken) {
        return list10TopRatedFilmsUseCase.top10RatedSortedByBoxOffice(apiToken)
                .stream()
                .map(JsonFilmDto::new)
                .collect(Collectors.toList());
    }
}
