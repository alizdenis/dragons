package com.dragons.castle.strategies;

import com.dragons.castle.services.game.model.Dragon;
import com.dragons.castle.services.game.model.Knight;
import io.reactivex.Maybe;
import org.junit.Test;

import java.util.Arrays;

import static com.dragons.castle.strategies.StrategiesTestUtils.assertDragon;
import static org.junit.Assert.assertEquals;

public class DryHeatStrategyTest {

    private DryHeatStrategy testObject = new DryHeatStrategy();

    @Test
    public void shouldReturnCorrectCode() {
        assertEquals("T E", testObject.getCode());
    }

    @Test
    public void shouldBuildBalancedDragon() {
        Maybe<Dragon> dragonMaybe = testObject.createDragonForBattle(
            new Knight(
                "", 1, 3, 8, 8
            )
        );

        Dragon dragon = dragonMaybe.blockingGet();

        assertDragon(Arrays.asList(5, 5, 5, 5), dragon);
    }

}