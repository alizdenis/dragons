package com.dragons.castle.strategies;

import com.dragons.castle.services.game.model.Dragon;
import com.dragons.castle.services.game.model.Knight;
import io.reactivex.Maybe;
import io.reactivex.Observable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Normals fight strategy
 */
public class NormalStrategy implements FightStrategy {

    private static final List<Integer> WEIGHTS = Arrays.asList(0, 2, 3, 4);

    private Map<String, int[]> strategy = new HashMap();

    public NormalStrategy() {
        strategy.put("0", new int[]{+2, 0, -1, -1});
        strategy.put("1", new int[]{0, +2, -1, -1});
        strategy.put("2", new int[]{-1, -1, +2, 0});
        strategy.put("3", new int[]{-1, -1, 0, +2});
    }

    @Override
    public String getCode() {
        return "NMR";
    }

    @Override
    public Maybe<Dragon> createDragonForBattle(Knight forKnight) {
        return Maybe.defer(() -> prepareTheDragon(forKnight));
    }

    private Maybe<Dragon> prepareTheDragon(Knight knight) {
        return Observable.just(knight)
                .map(this::extractStats)
                .flatMap(stats -> Observable.just(stats)
                                .map(this::buildStrategyKey)
                                .map(key -> key.length() > 1 ? reduceKey(key) : key)
                                .map(strategy::get)
                                .map(strategyStats -> Observable.just(applyStrategy(stats, strategyStats)))
                )
                .flatMap(observable -> observable)
                .map(this::createDragonFromStats)
                .firstElement();
    }

    private Dragon createDragonFromStats(int[] dragonStats) {
        return new Dragon(
                dragonStats[0],
                dragonStats[1],
                dragonStats[2],
                dragonStats[3]
        );
    }

    private int[] extractStats(Knight knight) {
        return new int[]{
                    knight.getAttack(),
                    knight.getArmor(),
                    knight.getAgility(),
                    knight.getEndurance()
            };
    }

    private String reduceKey(String key) {
        return WEIGHTS.stream()
                .map(String::valueOf)
                .filter(key::contains)
                .findFirst()
                .get();
    }

    private int[] applyStrategy(int[] stats, int[] strategyStats) {
        int[] result = new int[stats.length];

        for (int i = 0; i < stats.length; i++) {
            int sum = result[i] + stats[i] + strategyStats[i];
            result[i] = sum;
            if (sum < 0) {
                result[i] = 0;
                int zeroIndex = findZeroIndex(strategyStats);
                result[zeroIndex] = result[zeroIndex] - 1;
            }
        }
        return result;
    }

    private int findZeroIndex(int[] stats) {
        for (int i = 0; i < stats.length; i++) {
            if ((stats[i] == 0)) {
                return i;
            }
        }
        return -1;
    }

    private String buildStrategyKey(int[] stats) {
        return findMaxValueIndexes(stats).stream()
                .map(String::valueOf)
                .collect(Collectors.joining());
    }

    private List<Integer> findMaxValueIndexes(int[] stats) {
        int max = findMaxValueIndex(stats);
        List<Integer> result = new ArrayList();
        for (int i = 0; i < stats.length; i++) {
            if ((stats[max] == stats[i])) {
                result.add(i);
            }
        }
        return result;
    }

    private int findMaxValueIndex(int[] stats) {
        int max = 0;
        for (int i = 0; i < stats.length; i++) {
            if (stats[max] < stats[i]) {
                max = i;
            }
        }
        return max;
    }
}
