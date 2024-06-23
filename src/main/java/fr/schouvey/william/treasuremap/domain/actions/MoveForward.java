package fr.schouvey.william.treasuremap.domain.actions;

import fr.schouvey.william.treasuremap.domain.Adventurer;
import fr.schouvey.william.treasuremap.domain.WorldMap;
import fr.schouvey.william.treasuremap.helper.PositionHelper;
import io.vavr.Tuple;
import io.vavr.Tuple2;

public class MoveForward implements Movement {

    public void execute(final WorldMap worldMap, final Adventurer adventurer) {
        if (canAdventurerMoveForward(worldMap, adventurer)) {
            adventurer.moveForward();
            acquireTreasureIfApplicable(worldMap, adventurer);
        }
    }

    private static boolean canAdventurerMoveForward(final WorldMap worldMap, final Adventurer adventurer) {
        var nextPosition = PositionHelper.getNewPositionFromOrientation(adventurer.getOrientation(),
                Tuple.of(adventurer.getPositionX(), adventurer.getPositionY()));
        return !isAMountainFacing(worldMap, nextPosition) && !isLimitOfWorldMap(worldMap, nextPosition);
    }

    private static boolean isAMountainFacing(final WorldMap worldMap, final Tuple2<Integer, Integer> nextPositions) {
        return worldMap.getMountains()
                .stream()
                .anyMatch(m -> m.isPositionMatching(nextPositions._1, nextPositions._2));
    }

    private static boolean isLimitOfWorldMap(final WorldMap worldMap, final Tuple2<Integer, Integer> nextPositions) {
        var limitX = worldMap.getSize().getWidth() - 1;
        var limitY = worldMap.getSize().getHeight() - 1;

        return nextPositions._1 > limitX || nextPositions._2 > limitY;
    }

    private static void acquireTreasureIfApplicable(final WorldMap worldMap, final Adventurer adventurer) {
        worldMap.getTreasures()
                .stream()
                .filter(t -> t.isPositionMatching(adventurer))
                .forEach(t -> {
                    t.acquireTreasure();
                    adventurer.acquireTreasure();
                });
    }

}
