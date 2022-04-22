package com.github.xini1;

import com.github.xini1.port.SearchFilmUseCase;

/**
 * @author Maxim Tereshchenko
 */
final class ResponseStub implements SearchFilmUseCase.Response {

    private final String imdbId;
    private final String name;

    ResponseStub(String imdbId, String name) {
        this.imdbId = imdbId;
        this.name = name;
    }

    @Override
    public String imdbId() {
        return imdbId;
    }

    @Override
    public String name() {
        return name;
    }
}
