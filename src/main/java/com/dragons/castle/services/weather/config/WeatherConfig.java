package com.dragons.castle.services.weather.config;

import com.dragons.castle.services.weather.WeatherService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

@Configuration
public class WeatherConfig {

    private static final String BASE_URL = "http://www.dragonsofmugloar.com/";

    @Bean
    public WeatherService weatherService() {
        Logger logger = LoggerFactory.getLogger(WeatherService.class);
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(logger::info);
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        Retrofit xmlRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient.build())
                .build();

        return xmlRetrofit.create(WeatherService.class);
    }
}
