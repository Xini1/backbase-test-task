package com.github.xini1.domain;

import com.github.xini1.port.usecase.FilmDto;
import com.github.xini1.port.usecase.Page;

import java.util.Collection;
import java.util.List;

/**
 * @author Maxim Tereshchenko
 */
final class EmptyPage extends Page {

    @Override
    public Collection<FilmDto> films() {
        return List.of();
    }

    @Override
    public int page() {
        return 0;
    }

    @Override
    public int totalPages() {
        return 0;
    }
}
