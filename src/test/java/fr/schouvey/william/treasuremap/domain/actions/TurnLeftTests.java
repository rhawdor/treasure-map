package fr.schouvey.william.treasuremap.domain.actions;

import fr.schouvey.william.treasuremap.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TurnLeftTests {

    private WorldMap worldMap;
    private Adventurer adventurer;

    private final TurnLeft turnLeft = new TurnLeft();

    @BeforeEach
    void setUp() {
        adventurer = new Adventurer("Lara", OrientationEnum.SOUTH, 1, 1);
        worldMap = new WorldMap(WorldMap.Size.of(3, 4), List.of(), List.of());
    }

    @Test
    void turnLeft_doTurnLeft() {
        turnLeft.execute(worldMap, adventurer);
        assertThat(adventurer.getOrientation()).isEqualTo(OrientationEnum.EAST);
    }

}
