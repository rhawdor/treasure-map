package fr.schouvey.william.treasuremap.domain.actions;

import fr.schouvey.william.treasuremap.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MoveForwardTests {

    private WorldMap worldMap;
    private Adventurer adventurer;

    private final MoveForward moveForward = new MoveForward();

    @BeforeEach
    void setUp() {
        var treasure1 = new Treasure(2, 0, 3);

        var treasure2 = new Treasure(3, 1, 3);

        var mountain1 = new Mountain(1, 0);

        var mountain2 = new Mountain(2, 1);

        adventurer = new Adventurer("Lara", OrientationEnum.SOUTH, 1, 1);

        worldMap = new WorldMap(WorldMap.Size.of(3, 4), List.of(mountain1, mountain2), List.of(treasure1, treasure2));
    }

    @Test
    void moveForward_clearAhead_doForward() {
        moveForward.execute(worldMap, adventurer);
        assertThat(adventurer.getPositionX()).isEqualTo(1);
        assertThat(adventurer.getPositionY()).isEqualTo(2);
    }

    @Test
    void moveForward_mountainAhead_doNotForward() {
        adventurer.turnLeft();
        moveForward.execute(worldMap, adventurer);
        assertThat(adventurer.getPositionX()).isEqualTo(1);
        assertThat(adventurer.getPositionY()).isEqualTo(1);
    }

    @Test
    void moveForward_mapLimitAhead_doNotForward() {
        worldMap = new WorldMap(WorldMap.Size.of(2, 2), List.of(), List.of());
        moveForward.execute(worldMap, adventurer);
        assertThat(adventurer.getPositionX()).isEqualTo(1);
        assertThat(adventurer.getPositionY()).isEqualTo(1);
    }

    @Test
    void moveForward_treasureAhead_getOneTreasure() {
        moveForward.execute(worldMap, adventurer);
        moveForward.execute(worldMap, adventurer);
        assertThat(adventurer.getPositionX()).isEqualTo(1);
        assertThat(adventurer.getPositionY()).isEqualTo(3);
        assertThat(adventurer.getTreasureNumber()).isEqualTo(1);
    }

}
