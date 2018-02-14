package com.dragons.castle.strategies;

import com.dragons.castle.services.game.model.Dragon;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class StrategiesTestUtils {

    /**
     * Asserts dragon stats on expected and actual results
     * @param expectedStats stats in ordered list
     * @param dragon with actual stats
     */
    public static void assertDragon(List expectedStats, Dragon dragon) {
        assertEquals("Scale Thickness should match", expectedStats.get(0), dragon.getScaleThickness());
        assertEquals("Claw Sharpness should match", expectedStats.get(1), dragon.getClawSharpness());
        assertEquals("Wings Strength should match", expectedStats.get(2), dragon.getWingStrength());
        assertEquals("Fire Breath should match", expectedStats.get(3), dragon.getFireBreath());
    }


}
