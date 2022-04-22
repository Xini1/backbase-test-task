package com.github.xini1;

import com.github.xini1.port.SearchFilmUseCase;

/**
 * @author Maxim Tereshchenko
 */
final class ResponseStub implements SearchFilmUseCase.Response {

    private final String imdbId;
    private final String name;
    private final boolean wonOscar;

    ResponseStub(String imdbId, String name) {
        this(imdbId, name, false);
    }

    ResponseStub(String imdbId, String name, boolean wonOscar) {
        this.imdbId = imdbId;
        this.name = name;
        this.wonOscar = wonOscar;
    }

    @Override
    public String imdbId() {
        return imdbId;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public boolean isWonOscar() {
        return wonOscar;
    }
}
