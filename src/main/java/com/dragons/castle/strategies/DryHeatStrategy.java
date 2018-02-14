package com.dragons.castle.strategies;

import com.dragons.castle.services.game.model.Dragon;
import com.dragons.castle.services.game.model.Knight;
import io.reactivex.Maybe;

/**
 * Strategy for the dry heat fight
 * </br>
 * Dragon should be balanced
 */
public class DryHeatStrategy implements FightStrategy {

    @Override
    public String getCode() {
        return "T E";
    }

    @Override
    public Maybe<Dragon> createDragonForBattle(Knight forKnight) {
        return Maybe.defer(() -> Maybe.just(
                new Dragon(5,5,5,5)
        ));
    }

}
