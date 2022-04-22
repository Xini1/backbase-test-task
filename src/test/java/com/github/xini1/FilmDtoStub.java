package com.github.xini1;

import com.github.xini1.port.FilmDto;

/**
 * @author Maxim Tereshchenko
 */
final class FilmDtoStub implements FilmDto {

    private final String imdbId;
    private final String name;
    private final boolean wonOscar;

    FilmDtoStub(String imdbId, String name) {
        this(imdbId, name, false);
    }

    FilmDtoStub(String imdbId, String name, boolean wonOscar) {
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
