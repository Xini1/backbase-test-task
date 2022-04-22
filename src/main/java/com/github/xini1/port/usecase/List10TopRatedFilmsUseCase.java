package com.github.xini1.port.usecase;

import java.util.List;

/**
 * @author Maxim Tereshchenko
 */
public interface List10TopRatedFilmsUseCase {

    List<FilmDto> top10RatedSortedByBoxOffice(String apiToken);
}
