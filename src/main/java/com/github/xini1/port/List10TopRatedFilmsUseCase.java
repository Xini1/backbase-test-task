package com.github.xini1.port;

import java.util.List;

/**
 * @author Maxim Tereshchenko
 */
public interface List10TopRatedFilmsUseCase {

    List<FilmDto> byBoxOffice(String apiToken);
}
