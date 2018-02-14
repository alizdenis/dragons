package com.dragons.castle.services.game.config;

import com.dragons.castle.repository.GameLogger;
import com.dragons.castle.services.game.GameEngine;
import com.dragons.castle.services.game.GameService;
import com.dragons.castle.services.weather.WeatherService;
import com.dragons.castle.strategies.FightStrategyRegistry;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Configuration
public class GameConfig {

    private static final String BASE_URL = "http://www.dragonsofmugloar.com/";

    @Bean
    public GameService gameService() {
        Logger logger = LoggerFactory.getLogger(GameService.class);
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(logger::info);
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        Retrofit jsonRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient.build())
                .build();

        return jsonRetrofit.create(GameService.class);
    }

    @Bean
    public GameEngine gameEngine(GameService gameService,
                                 WeatherService weatherService,
                                 GameLogger gameLogger,
                                 FightStrategyRegistry fightStrategyRegistry) {
        return new GameEngine(
                gameService,
                weatherService,
            gameLogger,
                fightStrategyRegistry);
    }
}
