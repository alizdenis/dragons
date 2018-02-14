package com.dragons.castle.services.game.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Result api dto
 */
@Getter
@RequiredArgsConstructor
public class Result {

    public static final String VICTORY = "Victory";

    private final String status;
    private final String message;

    @Override
    public String toString() {
        return "Result{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
