package com.dragons.castle.strategies;

import com.dragons.castle.services.weather.model.WeatherReport;

import java.util.Map;

/**
 * Holds all fight strategies and defaults if needed
 */
public class FightStrategyRegistry {

    private final Map<String, FightStrategy> strategies;
    private final FightStrategy defaultStrategy;

    public FightStrategyRegistry(Map<String, FightStrategy> strategies,
                                 FightStrategy defaultStrategy) {
        this.strategies = strategies;
        this.defaultStrategy = defaultStrategy;
    }

    /**
     * Retrieves strategy based on passed weather report
     * @param report of the weather for which to retrieve strategy
     * @return found strategy for weather or default value, if not found
     */
    public FightStrategy getFightStrategy(WeatherReport report) {
        return strategies.getOrDefault(report.getCode(), defaultStrategy);
    }

}
