package com.github.xini1.port.usecase;

/**
 * @author Maxim Tereshchenko
 */
public interface SearchFilmUseCase {

    Page search(String apiToken, String name, int page);
}
