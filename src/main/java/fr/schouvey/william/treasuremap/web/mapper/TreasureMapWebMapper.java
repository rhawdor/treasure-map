package fr.schouvey.william.treasuremap.web.mapper;

import fr.schouvey.william.treasuremap.domain.*;
import fr.schouvey.william.treasuremap.domain.actions.Movement;
import fr.schouvey.william.treasuremap.exception.InvalidInputException;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class TreasureMapWebMapper {

    private static final String MOUNTAIN = "M";
    private static final String TREASURE = "T";
    private static final String MAP = "C";
    private static final String ADVENTURER = "A";

    private static final String SEPARATOR = " - ";

    private TreasureMapWebMapper() {}

    public static Map<Adventurer, List<Movement>> mapToAdventurers(List<String> treasureMapLines) {
        Map<Adventurer, List<Movement>> adventurersMap = new HashMap<>(Map.of());
        treasureMapLines.stream()
                .filter(line -> !line.isEmpty())
                .forEach(line -> {
                    if (line.startsWith(ADVENTURER)) {
                        var lineElements = line.split(SEPARATOR);
                        var adventurer = mapToAdventurer(lineElements);
                        var movementSequence = mapToMovementSequence(lineElements);
                        adventurersMap.put(adventurer, movementSequence);
                    }
                });
        return adventurersMap;
    }

    private static Adventurer mapToAdventurer(String[] lineElements) {
        OrientationEnum orientation;
        String name;
        int positionX;
        int positionY;
        try {
            name = lineElements[1];
            positionX = Integer.parseInt(lineElements[2]);
            positionY = Integer.parseInt(lineElements[3]);
            orientation = OrientationEnum.getOrientationByCode(lineElements[4]);
        } catch (RuntimeException e) {
            throw new InvalidInputException(Arrays.toString(lineElements), e);
        }
        return new Adventurer(name, orientation, positionX, positionY);
    }

    private static List<Movement> mapToMovementSequence(String[] lineElements) {
        var movementSequenceCharacters = lineElements[5];
        try {
            return Arrays.stream(movementSequenceCharacters.split(""))
                    .map(MovementEnum::getMovementByCode)
                    .toList();
        } catch (RuntimeException e) {
            throw new InvalidInputException(Arrays.toString(lineElements), e);
        }
    }

    public static WorldMap mapToWorldMap(List<String> treasureMapLines) {
        List<Mountain> mountains = new ArrayList<>();
        List<Treasure> treasures = new ArrayList<>();
        AtomicReference<WorldMap.Size> size = new AtomicReference<>();

        treasureMapLines.stream()
                .filter(line -> !line.isEmpty())
                .forEach(line -> {
                    if (line.startsWith(MOUNTAIN)) {
                        mountains.add(mapToMountain(line));
                    }
                    else if (line.startsWith(TREASURE)) {
                        treasures.add(mapToTreasure(line));
                    } else if (line.startsWith(MAP)) {
                        size.set(mapToMapSize(line));
                    }
                });

        return new WorldMap(size.get(), mountains, treasures);
    }

    private static Mountain mapToMountain(String line) {
        var lineElements = line.split(SEPARATOR);
        int positionX;
        int positionY;
        try {
            positionX = Integer.parseInt(lineElements[1]);
            positionY = Integer.parseInt(lineElements[2]);
        } catch (RuntimeException e) {
            throw new InvalidInputException(line, e);
        }
        return new Mountain(positionX, positionY);
    }

    private static Treasure mapToTreasure(String line) {
        var lineElements = line.split(SEPARATOR);
        int positionX;
        int positionY;
        int number;
        try {
            positionX = Integer.parseInt(lineElements[1]);
            positionY = Integer.parseInt(lineElements[2]);
            number = Integer.parseInt(lineElements[3]);
        } catch (RuntimeException e) {
            throw new InvalidInputException(line, e);
        }
        return new Treasure(number, positionX, positionY);
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
