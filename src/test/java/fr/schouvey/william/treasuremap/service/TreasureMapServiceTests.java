package fr.schouvey.william.treasuremap.service;

import fr.schouvey.william.treasuremap.domain.*;
import fr.schouvey.william.treasuremap.domain.actions.MoveForward;
import fr.schouvey.william.treasuremap.domain.actions.Movement;
import fr.schouvey.william.treasuremap.domain.actions.TurnLeft;
import fr.schouvey.william.treasuremap.domain.actions.TurnRight;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class TreasureMapServiceTests {

    private WorldMap worldMap;
    private Adventurer adventurer;
    private List<Movement> movementSequence;

    @BeforeEach
    void setUp() {
        var treasure1 = new Treasure(2, 0, 3);

        var treasure2 = new Treasure(3, 1, 3);

        var mountain1 = new Mountain(1, 0);

        var mountain2 = new Mountain(2, 1);

        adventurer = new Adventurer("Lara", OrientationEnum.SOUTH, 1, 1);

        movementSequence = List.of(new MoveForward(),
                new MoveForward(),
                new TurnRight(),
                new MoveForward(),
                new TurnRight(),
                new MoveForward(),
                new TurnLeft(),
                new TurnLeft(),
                new MoveForward()
        );

        worldMap = new WorldMap(WorldMap.Size.of(3, 4), List.of(mountain1, mountain2), List.of(treasure1, treasure2));
    }

    @Test
    void processTreasureMap_withValidParams_returnGame() {
        var result = TreasureMapService.processTreasureMap(worldMap, Map.of(adventurer, movementSequence));
        assertThat(result.adventurers().stream().findFirst().isPresent()).isTrue();
        var adventurerResult = result.adventurers().stream().findFirst().get();
        assertThat(adventurerResult.getTreasureNumber()).isEqualTo(3);
        assertThat(adventurerResult.getOrientation()).isEqualTo(OrientationEnum.SOUTH);
        assertThat(adventurerResult.getPositionX()).isZero();
        assertThat(adventurerResult.getPositionY()).isEqualTo(3);
    }

}
