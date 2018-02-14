package com.dragons.castle.services.weather;

import com.dragons.castle.services.weather.model.WeatherReport;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Weather forecast service for the game
 */
public interface WeatherService {

    /**
     * Retrieves weather report for the game
     *
     * @param gameId id of the game
     * @return weather report for passed game
     */
    @GET("weather/api/report/{gameId}")
    Single<WeatherReport> getWeatherReportFor(@Path("gameId") Integer gameId);

}
