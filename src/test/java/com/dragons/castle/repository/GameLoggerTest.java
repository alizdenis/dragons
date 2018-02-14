package com.dragons.castle.repository;

import com.dragons.castle.repository.model.GameLog;
import com.dragons.castle.services.game.model.Dragon;
import com.dragons.castle.services.game.model.Game;
import com.dragons.castle.services.game.model.Knight;
import com.dragons.castle.services.game.model.Result;
import com.dragons.castle.services.weather.model.WeatherReport;
import org.junit.After;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class GameLoggerTest {

    public static final String TEST_FILE = "test.txt";

    private GameLogger testObject = new GameLogger("test.txt");

    private Path file = Paths.get(TEST_FILE);

    @After
    public void tearDown() {
        file.toFile().delete();
    }

    @Test
    public void shouldWriteLogToFile() throws IOException {
        testObject.write(new GameLog(
            new Game(123,
                new Knight("KnightName", 1, 1, 1, 1),
                new WeatherReport("NMR")),
            new Dragon(2, 2, 2, 2),
            new Result("Victory", "Dragon Won!")
        ));

        String writtenValue = new String(Files.readAllBytes(file));
        assertEquals("Values should match", "123,1,1,1,1,NMR,2,2,2,2,Victory\n", writtenValue);
    }

    @Test
    public void shouldWriteLogToFileWhenDragonIsMissing() throws IOException {
        testObject.write(new GameLog(
            new Game(123,
                new Knight("KnightName", 1, 1, 1, 1),
                new WeatherReport("NMR")),
            null,
            new Result("Victory", "Dragon Won!")
        ));

        String writtenValue = new String(Files.readAllBytes(file));
        assertEquals("Values should match", "123,1,1,1,1,NMR,0,0,0,0,Victory\n", writtenValue);
    }

}