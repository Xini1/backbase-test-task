package com.github.xini1.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.xini1.port.OscarWinners;
import org.junit.jupiter.api.Test;

/**
 * @author Maxim Tereshchenko
 */
final class OscarWinnersFromFileTest {

    private final OscarWinners oscarWinners = new OscarWinnersFromFile("academy_awards.csv");

    @Test
    void givenUpIsNotOscarWinner_thenFalse() {
        assertThat(oscarWinners.isWinner("Up")).isFalse();
    }

    @Test
    void givenSlumdogMillionaireIsOscarWinner_thenTrue() {
        assertThat(oscarWinners.isWinner("Slumdog Millionaire")).isTrue();
    }
}