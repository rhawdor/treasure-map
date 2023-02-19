package fr.schouvey.william.treasuremap.service;

import fr.schouvey.william.treasuremap.domain.Adventurer;
import fr.schouvey.william.treasuremap.domain.Mountain;
import fr.schouvey.william.treasuremap.domain.MovementEnum;
import fr.schouvey.william.treasuremap.domain.OrientationEnum;
import fr.schouvey.william.treasuremap.domain.Treasure;
import fr.schouvey.william.treasuremap.domain.WorldMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class TreasureMapServiceTests {

    private WorldMap worldMap;
    private Adventurer adventurer;
    private List<MovementEnum> movementSequence;

    @BeforeEach
    void setUp() {
        var treasure1 = new Treasure().setNumber(2);
        treasure1.setPositionX(0);
        treasure1.setPositionY(3);

        var treasure2 = new Treasure().setNumber(3);
        treasure2.setPositionX(1);
        treasure2.setPositionY(3);

        var mountain1 = new Mountain();
        mountain1.setPositionX(1);
        mountain1.setPositionY(0);

        var mountain2 = new Mountain();
        mountain2.setPositionX(2);
        mountain2.setPositionY(1);

        adventurer = new Adventurer()
                .setName("Lara")
                .setOrientation(OrientationEnum.SOUTH);
        adventurer.setPositionX(1);
        adventurer.setPositionY(1);

        movementSequence = List.of(MovementEnum.MOVE_FORWARD,
                MovementEnum.MOVE_FORWARD,
                MovementEnum.TURN_RIGHT,
                MovementEnum.MOVE_FORWARD,
                MovementEnum.TURN_RIGHT,
                MovementEnum.MOVE_FORWARD,
                MovementEnum.TURN_LEFT,
                MovementEnum.TURN_LEFT,
                MovementEnum.MOVE_FORWARD
        );

        worldMap = new WorldMap()
                .setSize(WorldMap.Size.of(3, 4))
                .setTreasures(List.of(treasure1, treasure2))
                .setMountains(List.of(mountain1, mountain2));

    }

    @Test
    void processTreasureMap_withValidParams_returnGame() {
        var result = TreasureMapService.processTreasureMap(worldMap, Map.of(adventurer, movementSequence));
        assertThat(result.getAdventurers().stream().findFirst().isPresent()).isTrue();
        var adventurerResult = result.getAdventurers().stream().findFirst().get();
        assertThat(adventurerResult.getTreasureNumber()).isEqualTo(3);
        assertThat(adventurerResult.getOrientation()).isEqualTo(OrientationEnum.SOUTH);
        assertThat(adventurerResult.getPositionX()).isZero();
        assertThat(adventurerResult.getPositionY()).isEqualTo(3);
    }

}
