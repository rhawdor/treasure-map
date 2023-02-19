package fr.schouvey.william.treasuremap.service;

import fr.schouvey.william.treasuremap.domain.Adventurer;
import fr.schouvey.william.treasuremap.domain.Game;
import fr.schouvey.william.treasuremap.domain.MovementEnum;
import fr.schouvey.william.treasuremap.domain.WorldMap;
import fr.schouvey.william.treasuremap.helper.PositionHelper;
import io.vavr.Tuple;
import io.vavr.Tuple2;

import java.util.List;
import java.util.Map;

public class TreasureMapService {

    private TreasureMapService() {}

    public static Game processTreasureMap(final WorldMap worldMap, final Map<Adventurer, List<MovementEnum>> adventurersMap) {
        adventurersMap.forEach((adventurer, movementSequence) -> processAdventurerMovements(worldMap, adventurer, movementSequence));
        return new Game().setWorldMap(worldMap).setAdventurers(adventurersMap.keySet());
    }

    private static void processAdventurerMovements(WorldMap worldMap, Adventurer adventurer, List<MovementEnum> movementSequence) {
        movementSequence.forEach(movement -> {
            switch (movement) {
                case MOVE_FORWARD -> moveForward(worldMap, adventurer);
                case TURN_LEFT -> adventurer.turnLeft();
                case TURN_RIGHT -> adventurer.turnRight();
            }
        });
    }

    private static void moveForward(WorldMap worldMap, Adventurer adventurer) {
        if (canAdventurerMoveForward(worldMap, adventurer)) {
            adventurer.moveForward();
            acquireTreasureIfApplicable(worldMap, adventurer);
        }
    }

    private static boolean canAdventurerMoveForward(WorldMap worldMap, Adventurer adventurer) {
        var nextPosition = PositionHelper.getNewPositionFromOrientation(adventurer.getOrientation(),
                Tuple.of(adventurer.getPositionX(), adventurer.getPositionY()));
        return !isAMountainFacing(worldMap, nextPosition) && !isLimitOfWorldMap(worldMap, nextPosition);
    }

    private static boolean isAMountainFacing(WorldMap worldMap, Tuple2<Integer, Integer> nextPositions) {
        return worldMap.getMountains()
                .stream()
                .anyMatch(m -> m.isPositionMatching(nextPositions._1, nextPositions._2));
    }

    private static boolean isLimitOfWorldMap(WorldMap worldMap, Tuple2<Integer, Integer> nextPositions) {
        var limitX = worldMap.getSize().getWidth() - 1;
        var limitY = worldMap.getSize().getHeight() - 1;

        return nextPositions._1 > limitX || nextPositions._2 > limitY;
    }

    private static void acquireTreasureIfApplicable(WorldMap worldMap, Adventurer adventurer) {
        worldMap.getTreasures()
                .stream()
                .filter(t -> t.isPositionMatching(adventurer))
                .forEach(t -> {
                    t.acquireTreasure();
                    adventurer.acquireTreasure();
                });
    }

}
