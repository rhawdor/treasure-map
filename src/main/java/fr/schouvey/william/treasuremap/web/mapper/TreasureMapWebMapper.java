package fr.schouvey.william.treasuremap.web.mapper;

import fr.schouvey.william.treasuremap.domain.Adventurer;
import fr.schouvey.william.treasuremap.domain.Mountain;
import fr.schouvey.william.treasuremap.domain.MovementEnum;
import fr.schouvey.william.treasuremap.domain.OrientationEnum;
import fr.schouvey.william.treasuremap.domain.Treasure;
import fr.schouvey.william.treasuremap.domain.WorldMap;
import fr.schouvey.william.treasuremap.exception.InvalidInputException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TreasureMapWebMapper {

    private static final String MOUNTAIN = "M";
    private static final String TREASURE = "T";
    private static final String MAP = "C";
    private static final String ADVENTURER = "A";

    private static final String SEPARATOR = " - ";

    private TreasureMapWebMapper() {}

    public static Map<Adventurer, List<MovementEnum>> mapToAdventurers(List<String> treasureMapLines) {
        Map<Adventurer, List<MovementEnum>> adventurersMap = new java.util.HashMap<>(Map.of());
        treasureMapLines.stream()
                .filter(line -> !line.isEmpty())
                .forEach(line -> {
                    if (line.startsWith(ADVENTURER)) {
                        var adventurerEntry = mapToAdventurer(line);
                        adventurersMap.put(adventurerEntry.getKey(), adventurerEntry.getValue());
                    }
                });
        return adventurersMap;
    }

    private static Map.Entry<Adventurer, List<MovementEnum>> mapToAdventurer(String line) {
        Adventurer adventurer = new Adventurer();
        List<MovementEnum> movementSequence;
        var lineElements = line.split(SEPARATOR);
        try {
            adventurer.setName(lineElements[1]);
            adventurer.setPositionX(Integer.valueOf(lineElements[2]));
            adventurer.setPositionY(Integer.valueOf(lineElements[3]));
            adventurer.setOrientation(OrientationEnum.getOrientationByCode(lineElements[4]));
            movementSequence = new ArrayList<>(mapToMovementSequence(lineElements[5]));
        } catch (RuntimeException e) {
            throw new InvalidInputException(line, e);
        }
        return Map.entry(adventurer, movementSequence);
    }

    private static List<MovementEnum> mapToMovementSequence(String line) {
        return Arrays.stream(line.split(""))
                .map(MovementEnum::getMovementByCode)
                .toList();
    }

    public static WorldMap mapToWorldMap(List<String> treasureMapLines) {
        WorldMap worldMap = new WorldMap();
        List<Mountain> mountains = new ArrayList<>();
        List<Treasure> treasures = new ArrayList<>();

        treasureMapLines.stream()
                .filter(line -> !line.isEmpty())
                .forEach(line -> {
                    if (line.startsWith(MOUNTAIN)) {
                        mountains.add(mapToMountain(line));
                    }
                    else if (line.startsWith(TREASURE)) {
                        treasures.add(mapToTreasure(line));
                    } else if (line.startsWith(MAP)) {
                        worldMap.setSize(mapToMapSize(line));
                    }
                });

        worldMap.setMountains(mountains);
        worldMap.setTreasures(treasures);
        return worldMap;
    }

    private static Mountain mapToMountain(String line) {
        var mountain = new Mountain();
        var lineElements = line.split(SEPARATOR);
        try {
            mountain.setPositionX(Integer.valueOf(lineElements[1]));
            mountain.setPositionY(Integer.valueOf(lineElements[2]));
        } catch (RuntimeException e) {
            throw new InvalidInputException(line, e);
        }
        return mountain;
    }

    private static Treasure mapToTreasure(String line) {
        var treasure = new Treasure();
        var lineElements = line.split(SEPARATOR);
        try {
            treasure.setPositionX(Integer.valueOf(lineElements[1]));
            treasure.setPositionY(Integer.valueOf(lineElements[2]));
            treasure.setNumber(Integer.valueOf(lineElements[3]));
        } catch (RuntimeException e) {
            throw new InvalidInputException(line, e);
        }
        return treasure;
    }

    private static WorldMap.Size mapToMapSize(String line) {
        int width;
        int height;
        try {
            width = Integer.parseInt(line.split(SEPARATOR)[1]);
            height = Integer.parseInt(line.split(SEPARATOR)[2]);
        } catch (RuntimeException e) {
            throw new InvalidInputException(line, e);
        }
        return WorldMap.Size.of(width, height);
    }

    public static List<String> mapToString(WorldMap worldMap, Set<Adventurer> adventurers) {
        List<String> outputList = new ArrayList<>();
        outputList.add(mapToMapString(worldMap));
        outputList.addAll(mapToMountainsStrings(worldMap));
        outputList.addAll(mapToTreasureStrings(worldMap));
        outputList.addAll(mapToAdventurersString(adventurers));
        return outputList;
    }

    private static String mapToMapString(WorldMap worldMap) {
        return MAP + SEPARATOR + worldMap.getSize().getWidth() + SEPARATOR + worldMap.getSize().getHeight();
    }

    private static List<String> mapToMountainsStrings(WorldMap worldMap) {
        return worldMap.getMountains()
                .stream()
                .map(m -> String.join(SEPARATOR, MOUNTAIN, m.getPositionX().toString(),
                        m.getPositionY().toString()))
                .toList();
    }

    private static List<String> mapToTreasureStrings(WorldMap worldMap) {
        return worldMap.getTreasures()
                .stream()
                .map(t -> String.join(SEPARATOR, TREASURE, t.getPositionX().toString(),
                        t.getPositionY().toString(), t.getNumber().toString()))
                .toList();
    }

    private static List<String> mapToAdventurersString(Set<Adventurer> adventurers) {
        return adventurers.stream()
                .map(a -> String.join(SEPARATOR, ADVENTURER, a.getName(), a.getPositionX().toString(), a.getPositionY().toString(),
                        a.getOrientation().getCode(), a.getTreasureNumber().toString()))
                .toList();
    }

}
