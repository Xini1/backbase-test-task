package com.github.xini1.application;

import com.github.xini1.exception.CouldNotDetermineOscarWinner;
import com.github.xini1.port.OscarWinners;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * @author Maxim Tereshchenko
 */
final class OscarWinnersFromFile implements OscarWinners {

    private final Path path;

    OscarWinnersFromFile(Path path) {
        this.path = path;
    }

    OscarWinnersFromFile(String fileName) throws URISyntaxException {
        this(
                Paths.get(
                        Objects.requireNonNull(
                                        Thread.currentThread()
                                                .getContextClassLoader()
                                                .getResource(fileName)
                                )
                                .toURI()
                )
        );
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
        return new BufferedReader(new InputStreamReader(Files.newInputStream(path), StandardCharsets.UTF_8));
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
}
