package com.dragons.castle.strategies.config;

import com.dragons.castle.strategies.DryHeatStrategy;
import com.dragons.castle.strategies.FightStrategy;
import com.dragons.castle.strategies.FightStrategyRegistry;
import com.dragons.castle.strategies.FogStrategy;
import com.dragons.castle.strategies.HeavyRainStrategy;
import com.dragons.castle.strategies.NormalStrategy;
import com.dragons.castle.strategies.StormStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
public class StrategiesConfig {

    @Bean
    public FightStrategy dryHeatStrategy() {
        return new DryHeatStrategy();
    }

    @Bean
    public FightStrategy fogStrategy() {
        return new FogStrategy();
    }

    @Bean
    public FightStrategy heavyRainStrategy() {
        return new HeavyRainStrategy();
    }

    @Bean
    public FightStrategy normalStrategy() {
        return new NormalStrategy();
    }

    @Bean
    public FightStrategy stormStrategy() {
        return new StormStrategy();
    }

    @Bean
    public FightStrategyRegistry fightStrategyRegistry(List<FightStrategy> strategies) {
        Map<String, FightStrategy> strategyMap = strategies.stream()
                .collect(
                        Collectors.toMap(
                                FightStrategy::getCode,
                                Function.identity()));

        return new FightStrategyRegistry(strategyMap, normalStrategy());
    }

}
