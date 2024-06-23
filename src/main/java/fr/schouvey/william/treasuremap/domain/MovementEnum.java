package fr.schouvey.william.treasuremap.domain;

import fr.schouvey.william.treasuremap.domain.actions.MoveForward;
import fr.schouvey.william.treasuremap.domain.actions.Movement;
import fr.schouvey.william.treasuremap.domain.actions.TurnLeft;
import fr.schouvey.william.treasuremap.domain.actions.TurnRight;
import fr.schouvey.william.treasuremap.exception.InvalidMovementException;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public enum MovementEnum {

    MOVE_FORWARD("A", MoveForward.class),
    TURN_LEFT("G", TurnLeft.class),
    TURN_RIGHT("D", TurnRight.class);

    private final String code;

    private final Class<? extends Movement> movement;

    MovementEnum(String code, Class<? extends Movement> movement) {
        this.code = code;
        this.movement = movement;
    }

    public Movement getMovementImplementation() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return movement.getConstructor().newInstance();
    }

    public static Movement getMovementByCode(String code) {
        var movement = Arrays.stream(values()).filter(m -> m.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new InvalidMovementException(code));
        try {
            return movement.getMovementImplementation();
        } catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
