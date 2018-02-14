package com.dragons.castle.strategies;

import com.dragons.castle.services.game.model.Dragon;
import com.dragons.castle.services.game.model.Knight;
import io.reactivex.Maybe;

/**
 * Storm fight strategy
 * </br>
 * Everybody dies, so lets keep dragon at home
 */
public class StormStrategy implements FightStrategy {

    @Override
    public String getCode() {
        return "SRO";
    }

    @Override
    public Maybe<Dragon> createDragonForBattle(Knight forKnight) {
        return Maybe.empty();
    }
}
