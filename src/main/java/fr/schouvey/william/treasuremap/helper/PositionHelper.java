package fr.schouvey.william.treasuremap.helper;

import fr.schouvey.william.treasuremap.domain.OrientationEnum;
import io.vavr.Tuple;
import io.vavr.Tuple2;

public class PositionHelper {

    private PositionHelper() {}

    public static Tuple2<Integer, Integer> getNewPositionFromOrientation(OrientationEnum orientation, Tuple2<Integer, Integer> positions) {
        var positionX = positions._1;
        var positionY = positions._2;

        switch (orientation) {
            case NORTH -> positionY -= 1;
            case SOUTH -> positionY += 1;
            case EAST -> positionX += 1;
            case WEST -> positionX -= 1;
        }
        return Tuple.of(positionX, positionY);
    }
}
