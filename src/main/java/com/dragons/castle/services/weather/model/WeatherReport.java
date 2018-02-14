package com.dragons.castle.services.weather.model;

import lombok.Getter;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Weather report service DTO
 */
@Root(strict = false)
@Getter
public class WeatherReport {

    /**
     * Weather getCode
     */
    @Element
    private final String code;

    public WeatherReport(@Element(name = "code") String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "WeatherReport{" +
                "getCode='" + code + '\'' +
                '}';
    }
}
