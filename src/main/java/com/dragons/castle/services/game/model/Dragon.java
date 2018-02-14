package com.dragons.castle.services.game.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Dragon api dto
 */
@Getter
@RequiredArgsConstructor
public class Dragon {

    private final Integer scaleThickness;
    private final Integer clawSharpness;
    private final Integer wingStrength;
    private final Integer fireBreath;

    @Override
    public String toString() {
        return "Dragon{" +
                "scaleThickness=" + scaleThickness +
                ", clawSharpness=" + clawSharpness +
                ", wingStrength=" + wingStrength +
                ", fireBreath=" + fireBreath +
                '}';
    }
}
