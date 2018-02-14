package com.dragons.castle.services.game.model;

import com.dragons.castle.services.weather.model.WeatherReport;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Game api dto
 */
@Getter
@RequiredArgsConstructor
public class Game {

    private final Integer gameId;
    private final Knight knight;

    private WeatherReport weatherReport;

    public Game(Integer gameId, Knight knight, WeatherReport weatherReport) {
        this(gameId, knight);
        this.weatherReport = weatherReport;
    }

    public Game addWeatherReport(WeatherReport weatherReport) {
        return new Game(getGameId(), getKnight(), weatherReport);
    }

    @Override
    public String toString() {
        return "Game{" +
                "gameId=" + gameId +
                ", knight=" + knight +
                ", weatherReport=" + weatherReport +
                '}';
    }
}
