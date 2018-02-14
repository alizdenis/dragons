package com.dragons.castle.strategies;

import com.dragons.castle.services.game.model.Dragon;
import com.dragons.castle.services.game.model.Knight;
import io.reactivex.Maybe;
import org.junit.Test;

import java.util.Arrays;

import static com.dragons.castle.strategies.StrategiesTestUtils.assertDragon;
import static org.junit.Assert.assertEquals;

public class HeavyRainStrategyTest {

    private HeavyRainStrategy testObject = new HeavyRainStrategy();

    @Test
    public void shouldReturnCorrectCode() {
        assertEquals("HVA", testObject.getCode());
    }

    @Test
    public void shouldSimplyMoveStats() {
        Maybe<Dragon> dragonMaybe = testObject.createDragonForBattle(
            new Knight(
                "", 4, 5, 6, 5
            )
        );

        Dragon dragon = dragonMaybe.blockingGet();

        assertDragon(Arrays.asList(4, 10, 6, 0), dragon);
    }

    @Test
    public void shouldMoveAndDistributeStats() {
        Maybe<Dragon> dragonMaybe = testObject.createDragonForBattle(
            new Knight(
                "", 4, 3, 5, 8
            )
        );

        Dragon dragon = dragonMaybe.blockingGet();

        assertDragon(Arrays.asList(4, 10, 6, 0), dragon);
    }

    @Test
    public void shouldMoveAndDistributeComplexStats() {
        Maybe<Dragon> dragonMaybe = testObject.createDragonForBattle(
            new Knight(
                "", 4, 5, 3, 8
            )
        );

        Dragon dragon = dragonMaybe.blockingGet();

        assertDragon(Arrays.asList(5, 10, 5, 0), dragon);
    }
}