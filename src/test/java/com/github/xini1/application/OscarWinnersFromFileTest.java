package com.github.xini1.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.xini1.port.OscarWinners;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;

/**
 * @author Maxim Tereshchenko
 */
final class OscarWinnersFromFileTest {

    private OscarWinners oscarWinners;

    @BeforeEach
    void setUp() throws URISyntaxException {
        oscarWinners = new OscarWinnersFromFile("academy_awards.csv");
    }

    @Test
    void givenUpIsNotOscarWinner_thenFalse() {
        assertThat(oscarWinners.isWinner("Up")).isFalse();
    }

    @Test
    void givenSlumdogMillionaireIsOScarWinner_thenTrue() {
        assertThat(oscarWinners.isWinner("Slumdog Millionaire")).isTrue();
    }
}