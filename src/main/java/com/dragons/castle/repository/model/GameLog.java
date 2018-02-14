package com.dragons.castle.repository.model;

import com.dragons.castle.services.game.model.Dragon;
import com.dragons.castle.services.game.model.Game;
import com.dragons.castle.services.game.model.Result;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Game logging DTO
 */
@Getter
@RequiredArgsConstructor
public class GameLog {

    private final Game game;
    private final Dragon dragon;
    private final Result result;

    @Override
    public String toString() {
        return "GameLog{" +
                "game=" + game +
                ", dragon=" + dragon +
                ", result=" + result +
                '}';
    }
}
