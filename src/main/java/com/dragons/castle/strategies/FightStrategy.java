package com.dragons.castle.strategies;

import com.dragons.castle.services.game.model.Dragon;
import com.dragons.castle.services.game.model.Knight;
import io.reactivex.Maybe;

/**
 * Represents fight strategy, by which dragon will be created
 */
public interface FightStrategy {

    /**
     * Represents strategy code
     *
     * @return code of the strategy
     */
    String getCode();

    /**
     * Creates the dragon for the fight
     *
     * @param forKnight that would beat it
     * @return the supreme dragon!
     */
    Maybe<Dragon> createDragonForBattle(Knight forKnight);
}
