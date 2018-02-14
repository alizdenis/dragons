package com.dragons.castle.services.game.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Knight api dto
 */
@Getter
@RequiredArgsConstructor
public class Knight {

    private final String name;
    private final Integer attack;
    private final Integer armor;
    private final Integer agility;
    private final Integer endurance;

    @Override
    public String toString() {
        return "Knight{" +
                "name='" + name + '\'' +
                ", attack=" + attack +
                ", armor=" + armor +
                ", agility=" + agility +
                ", endurance=" + endurance +
                '}';
    }
}
