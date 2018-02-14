package com.dragons.castle.repository;

import com.dragons.castle.repository.model.GameLog;
import com.dragons.castle.services.game.model.Dragon;
import com.dragons.castle.services.game.model.Game;
import com.dragons.castle.services.game.model.Knight;
import com.dragons.castle.services.game.model.Result;
import com.dragons.castle.services.weather.model.WeatherReport;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Writes game log to text file
 */
@Slf4j
public class GameLogger {

    private final String repositoryFile;

    /**
     * Crates game logger
     * @param logFile where to log game results
     */
    public GameLogger(String logFile) {
        this.repositoryFile = logFile;
    }

    /**
     * Logs game results
     *
     * @param gameLog result to log
     */
    public void write(GameLog gameLog) {
        try {
            Path path = Paths.get(repositoryFile);
            if (!path.toFile().exists()) {
                Files.createFile(path);
            }
            String gameLogString = buildLog(gameLog);
            log.info(gameLogString);
            Files.write(
                    path,
                    gameLogString.getBytes(),
                    StandardOpenOption.APPEND);
        } catch (IOException exception) {
            log.error("Failed to write to game log repository", exception);
        }
    }

    private String buildLog(GameLog gameLog) {
        Game game = gameLog.getGame();
        StringBuilder builder = new StringBuilder();

        addGameId(builder, game);
        addKnight(builder, game.getKnight());
        addWeatherReport(builder, game.getWeatherReport());
        addDragon(builder, gameLog.getDragon());
        addResult(builder, gameLog.getResult());

        return builder.toString();
    }

    private void addGameId(StringBuilder builder, Game game) {
        builder.append(game.getGameId()).append(",");
    }

    private void addKnight(StringBuilder builder, Knight knight) {
        builder.append(knight.getAttack()).append(",")
                .append(knight.getArmor()).append(",")
                .append(knight.getAgility()).append(",")
                .append(knight.getEndurance()).append(",");
    }

    private void addWeatherReport(StringBuilder builder, WeatherReport weatherReport) {
        builder.append(weatherReport.getCode()).append(",");
    }

    private void addDragon(StringBuilder builder, Dragon dragon) {
        if (dragon != null) {
            builder.append(dragon.getScaleThickness()).append(",")
                    .append(dragon.getClawSharpness()).append(",")
                    .append(dragon.getWingStrength()).append(",")
                    .append(dragon.getFireBreath()).append(",");
        } else {
            builder.append("0,0,0,0,");
        }
    }

    private void addResult(StringBuilder builder, Result result) {
        builder.append(result.getStatus()).append("\n");
    }
}
