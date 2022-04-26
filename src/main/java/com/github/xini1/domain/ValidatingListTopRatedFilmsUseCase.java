package com.github.xini1.domain;

import com.github.xini1.exception.ApiTokenRequired;
import com.github.xini1.exception.ElementsOnPageLessThanZero;
import com.github.xini1.exception.PageNumberLessThanZero;
import com.github.xini1.port.usecase.ListTopRatedFilmsUseCase;
import com.github.xini1.port.usecase.Page;

/**
 * @author Maxim Tereshchenko
 */
final class ValidatingListTopRatedFilmsUseCase implements ListTopRatedFilmsUseCase {

    private final ListTopRatedFilmsUseCase original;

    ValidatingListTopRatedFilmsUseCase(ListTopRatedFilmsUseCase original) {
        this.original = original;
    }

    @Override
    public Page topRatedSortedByBoxOffice(String apiToken, int page, int elementsOnPage) {
        if (apiToken == null || apiToken.isBlank()) {
            throw new ApiTokenRequired();
        }
        if (page < 0) {
            throw new PageNumberLessThanZero();
        }
        if (elementsOnPage < 0) {
            throw new ElementsOnPageLessThanZero();
        }
        return original.topRatedSortedByBoxOffice(apiToken, page, elementsOnPage);
    }
}
