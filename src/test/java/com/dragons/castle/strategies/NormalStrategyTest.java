package com.dragons.castle.strategies;

import com.dragons.castle.services.game.model.Dragon;
import com.dragons.castle.services.game.model.Knight;
import io.reactivex.Maybe;
import org.junit.Test;

import java.util.Arrays;

import static com.dragons.castle.strategies.StrategiesTestUtils.assertDragon;
import static org.junit.Assert.assertEquals;

public class NormalStrategyTest {

    private NormalStrategy testObject = new NormalStrategy();

    @Test
    public void shouldReturnCorrectCode() {
        assertEquals("NMR", testObject.getCode());
    }

    @Test
    public void shouldWinWithSingleHighValue() {
        Maybe<Dragon> dragonMaybe = testObject.createDragonForBattle(
            new Knight(
                "", 3, 6, 7, 4
            )
        );

        Dragon dragon = dragonMaybe.blockingGet();

        assertDragon(Arrays.asList(2, 5, 9, 4), dragon);
    }

    @Test
    public void shouldWinWithTwoHighValue() {
        Maybe<Dragon> dragonMaybe = testObject.createDragonForBattle(
            new Knight(
                "", 7, 1, 7, 5
            )
        );

        Dragon dragon = dragonMaybe.blockingGet();

        assertDragon(Arrays.asList(9, 1, 6, 4), dragon);
    }

    @Test
    public void shouldWinWhenStatsSumNeedsTrimming() {
        Maybe<Dragon> dragonMaybe = testObject.createDragonForBattle(
            new Knight(
                "", 0, 6, 8, 6
            )
        );

        Dragon dragon = dragonMaybe.blockingGet();

        assertDragon(Arrays.asList(0, 5, 10, 5), dragon);
    }

    @Test
    public void shouldWinWhenStatsSumNeedsAddition() {
        Maybe<Dragon> dragonMaybe = testObject.createDragonForBattle(
            new Knight(
                "", 4, 4, 7, 5
            )
        );

        Dragon dragon = dragonMaybe.blockingGet();

        assertDragon(Arrays.asList(3, 3, 9, 5), dragon);
    }


}