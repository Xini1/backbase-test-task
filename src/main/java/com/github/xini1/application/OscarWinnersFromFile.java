package com.github.xini1.application;

import com.github.xini1.port.OscarWinners;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author Maxim Tereshchenko
 */
final class OscarWinnersFromFile implements OscarWinners {

    private final String fileName;

    OscarWinnersFromFile(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public boolean isWinner(String name) {
        try (var reader = reader()) {
            return reader.lines()
                    .map(OscarNomination::new)
                    .filter(OscarNomination::isBestPictureNomination)
                    .filter(line -> line.isNominated(name))
                    .anyMatch(OscarNomination::isWinner);
        } catch (IOException e) {
            throw new CouldNotDetermineOscarWinner(e);
        }
    }

    private BufferedReader reader() throws IOException {
        return new BufferedReader(new FileReader(fileName, StandardCharsets.UTF_8));
    }

    private static final class OscarNomination {

        private final String raw;

        private OscarNomination(String raw) {
            this.raw = raw;
        }

        boolean isBestPictureNomination() {
            return raw.contains("Best Picture");
        }

        boolean isNominated(String filmName) {
            return raw.contains(filmName);
        }

        boolean isWinner() {
            return raw.contains("YES");
        }
    }

    public static final class CouldNotDetermineOscarWinner extends RuntimeException {

        public CouldNotDetermineOscarWinner(Throwable cause) {
            super(cause);
        }
    }
}
