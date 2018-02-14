package com.dragons.castle.strategies;

import com.dragons.castle.services.game.model.Dragon;
import com.dragons.castle.services.game.model.Knight;
import io.reactivex.Maybe;

/**
 * Heavy rain fight strategy
 * </br>
 * Destroy umbrellas with claws!
 */
public class HeavyRainStrategy implements FightStrategy {

    @Override
    public String getCode() {
        return "HVA";
    }

    @Override
    public Maybe<Dragon> createDragonForBattle(Knight forKnight) {
        return Maybe.defer(() -> Maybe.just(prepareTheDragonForRain(forKnight)));
    }

    private Dragon prepareTheDragonForRain(Knight knight) {
        int leftStats = (knight.getArmor() + knight.getEndurance()) - 10;
        int distribution = leftStats / 2;

        return new Dragon(
                knight.getAttack() + distribution,
                10,
                knight.getAgility() + (leftStats - distribution),
                0
        );
    }

}
