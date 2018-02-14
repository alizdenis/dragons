package com.dragons.castle.repository.config;

import com.dragons.castle.repository.GameLogger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfig {

    @Bean
    public GameLogger gameRepository() {
        return new GameLogger("gameLog.txt");
    }

}
