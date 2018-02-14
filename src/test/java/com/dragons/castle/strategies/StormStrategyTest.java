package com.dragons.castle.strategies;

import com.dragons.castle.services.game.model.Dragon;
import com.dragons.castle.services.game.model.Knight;
import io.reactivex.Maybe;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class StormStrategyTest {

    private StormStrategy testObject = new StormStrategy();

    @Test
    public void shouldReturnCorrectCode() {
        assertEquals("Codes should match", "SRO", testObject.getCode());
    }

    @Test
    public void shouldBuildBalancedDragon() {
        Maybe<Dragon> dragonMaybe = testObject.createDragonForBattle(
                new Knight(
                        "", 1,3,8,8
                )
        );

        Dragon dragon = dragonMaybe.blockingGet();
        assertNull("Dragon should be null", dragon);
    }
}