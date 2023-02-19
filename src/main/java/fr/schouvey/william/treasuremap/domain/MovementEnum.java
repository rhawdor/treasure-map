package fr.schouvey.william.treasuremap.domain;

import fr.schouvey.william.treasuremap.exception.InvalidMovementException;

import java.util.Arrays;

public enum MovementEnum {

    MOVE_FORWARD("A"),
    TURN_LEFT("G"),
    TURN_RIGHT("D");

    private final String code;

    MovementEnum(String code) {
        this.code = code;
    }

    public static MovementEnum getMovementByCode(String code) {
        return Arrays.stream(values()).filter(m -> m.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new InvalidMovementException(code));
    }

}
