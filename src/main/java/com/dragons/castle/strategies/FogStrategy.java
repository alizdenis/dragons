package com.dragons.castle.strategies;

import com.dragons.castle.services.game.model.Dragon;
import com.dragons.castle.services.game.model.Knight;
import io.reactivex.Maybe;

/**
 * Strategy for the fog
 * <br>
 * Dragon always wins!
 */
public class FogStrategy implements FightStrategy {

    @Override
    public String getCode() {
        return "FUNDEFINEDG";
    }

    @Override
    public Maybe<Dragon> createDragonForBattle(Knight forKnight) {
        return Maybe.defer(() -> Maybe.just(
                new Dragon(1,9,1,9)
        ));
    }
}
