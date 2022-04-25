package com.github.xini1.port.usecase;

/**
 * @author Maxim Tereshchenko
 */
public interface ListTopRatedFilmsUseCase {

    Page topRatedSortedByBoxOffice(String apiToken, int page, int elementsOnPage);
}
