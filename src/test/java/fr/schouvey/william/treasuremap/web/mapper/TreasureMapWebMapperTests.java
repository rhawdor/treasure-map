package fr.schouvey.william.treasuremap.web.mapper;

import fr.schouvey.william.treasuremap.domain.Adventurer;
import fr.schouvey.william.treasuremap.domain.Mountain;
import fr.schouvey.william.treasuremap.domain.OrientationEnum;
import fr.schouvey.william.treasuremap.domain.Treasure;
import fr.schouvey.william.treasuremap.domain.WorldMap;
import fr.schouvey.william.treasuremap.exception.InvalidInputException;
import fr.schouvey.william.treasuremap.exception.InvalidOrientationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TreasureMapWebMapperTests {

    private static final String SEPARATOR = " - ";
    private static final String NAME = "Lara";
    private static final String MOVEMENT_SEQUENCE = "ADADAGGA";
    private static final Integer ADVENTURER_POSITION_X = 0;
    private static final Integer ADVENTURER_POSITION_Y = 1;
    private static final OrientationEnum ADVENTURER_ORIENTATION = OrientationEnum.NORTH;
    private static final String ADVENTURER_LINE = String.join(SEPARATOR,
            "A", NAME,
            ADVENTURER_POSITION_X.toString(),
            ADVENTURER_POSITION_Y.toString(),
            ADVENTURER_ORIENTATION.getCode(),
            MOVEMENT_SEQUENCE);

    private static final String ADVENTURER_LINE_RESULT = String.join(SEPARATOR,
            "A", NAME,
            ADVENTURER_POSITION_X.toString(),
            ADVENTURER_POSITION_Y.toString(),
            ADVENTURER_ORIENTATION.getCode(), "0");
    private static final String COMMENT_LINE = "#";
    private static final Integer MAP_SIZE = 4;
    private static final String MAP_LINE = String.join(SEPARATOR,
            "C",
            MAP_SIZE.toString(),
            MAP_SIZE.toString());

    private static final Integer MOUTAIN_POSITION = 3;
    private static final String MOUNTAIN_LINE = String.join(SEPARATOR,
            "M",
            MOUTAIN_POSITION.toString(),
            MOUTAIN_POSITION.toString());

    private static final Integer TREASURE_POSITION = 2;
    private static final Integer TREASURE_NUMBER = 1;
    private static final String TREASURE_LINE = String.join(SEPARATOR,
            "T",
            TREASURE_POSITION.toString(),
            TREASURE_POSITION.toString(),
            TREASURE_NUMBER.toString());
    private static final List<String> LINES = List.of(ADVENTURER_LINE, COMMENT_LINE, "", MAP_LINE, MOUNTAIN_LINE, TREASURE_LINE);

    private WorldMap worldMap;

    private Adventurer adventurer;

    @BeforeEach
    void setUp() {
        var treasure = new Treasure().setNumber(TREASURE_NUMBER);
        treasure.setPositionX(TREASURE_POSITION);
        treasure.setPositionY(TREASURE_POSITION);
        var mountain = new Mountain();
        mountain.setPositionX(MOUTAIN_POSITION);
        mountain.setPositionY(MOUTAIN_POSITION);
        worldMap = new WorldMap()
                .setSize(WorldMap.Size.of(MAP_SIZE, MAP_SIZE))
                .setMountains(List.of(mountain))
                .setTreasures(List.of(treasure));
        adventurer = new Adventurer()
                .setName(NAME)
                .setOrientation(ADVENTURER_ORIENTATION);
        adventurer.setPositionX(ADVENTURER_POSITION_X);
        adventurer.setPositionY(ADVENTURER_POSITION_Y);
    }

    @Test
    void mapToAdventurers_withValidParams_returnAdventurersMap() {
        var result = TreasureMapWebMapper.mapToAdventurers(LINES);
        assertThat(result).hasSize(1);
        result.forEach((adventurer, movementSequence) -> {
            assertThat(adventurer.getName()).isEqualTo(NAME);
            assertThat(adventurer.getPositionX()).isEqualTo(ADVENTURER_POSITION_X);
            assertThat(adventurer.getPositionY()).isEqualTo(ADVENTURER_POSITION_Y);
            assertThat(adventurer.getOrientation()).isEqualTo(ADVENTURER_ORIENTATION);
            assertThat(adventurer.getTreasureNumber()).isZero();
            assertThat(movementSequence).hasSize(MOVEMENT_SEQUENCE.length());
        });
    }

    @Test
    void mapToAdventurers_withInvalidInput_throwsException() {
        assertThrows(InvalidInputException.class, () -> TreasureMapWebMapper.mapToAdventurers(List.of("A - Lara - iNvAlId")));
    }

    @Test
    void mapToAdventurers_withInvalidOrientation_throwsException() {
        assertThrows(InvalidInputException.class, () -> TreasureMapWebMapper.mapToAdventurers(List.of("A - Lara - 1 - 1 - V - AADADAGGA")));
    }

    @Test
    void mapToAdventurers_withInvalidMovement_throwsException() {
        assertThrows(InvalidInputException.class, () -> TreasureMapWebMapper.mapToAdventurers(List.of("A - Lara - 1 - 1 - S - AHADADAGGA")));
    }

    @Test
    void mapToWorldMap_withValidParams_returnWorldMap() {
        var result = TreasureMapWebMapper.mapToWorldMap(LINES);
        assertThat(result).isNotNull();
        assertThat(result.getSize().getWidth()).isEqualTo(MAP_SIZE);
        assertThat(result.getSize().getHeight()).isEqualTo(MAP_SIZE);
        assertThat(result.getMountains()).hasSize(1);
        assertThat(result.getTreasures()).hasSize(1);
    }

    @Test
    void mapToWorldMap_withMapInvalidInput_throwsException() {
        var lines = List.of("C - iNvAlId - 1", MOUNTAIN_LINE, TREASURE_LINE);
        assertThrows(InvalidInputException.class, () -> TreasureMapWebMapper.mapToWorldMap(lines));
    }

    @Test
    void mapToWorldMap_withMountainInvalidInput_throwsException() {
        var lines = List.of(MAP_LINE, "M - iNvAlId - 1", TREASURE_LINE);
        assertThrows(InvalidInputException.class, () -> TreasureMapWebMapper.mapToWorldMap(lines));
    }

    @Test
    void mapToWorldMap_withTreasureInvalidInput_throwsException() {
        var lines = List.of(MAP_LINE, MOUNTAIN_LINE, "T - 1 - 1 - iNvAlId");
        assertThrows(InvalidInputException.class, () -> TreasureMapWebMapper.mapToWorldMap(lines));
    }

    @Test
    void mapToString_withValidParams_returnListString() {
        var result = TreasureMapWebMapper.mapToString(worldMap, Set.of(adventurer));
        assertThat(result).hasSize(4);
        assertThat(result.get(0)).isEqualTo(MAP_LINE);
        assertThat(result.get(1)).isEqualTo(MOUNTAIN_LINE);
        assertThat(result.get(2)).isEqualTo(TREASURE_LINE);
        assertThat(result.get(3)).isEqualTo(ADVENTURER_LINE_RESULT);
    }

}
