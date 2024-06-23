package fr.schouvey.william.treasuremap.domain;

import fr.schouvey.william.treasuremap.helper.PositionHelper;
import io.vavr.Tuple;

public class Adventurer extends WorldElement {

    private final String name;

    private Integer treasureNumber = 0;

    private OrientationEnum orientation;

    public Adventurer(String name, OrientationEnum orientation, Integer positionX, Integer positionY) {
        super(positionX, positionY);
        this.name = name;
        this.orientation = orientation;
    }

    public String getName() {
        return name;
    }

    public Integer getTreasureNumber() {
        return treasureNumber;
    }

    public void acquireTreasure() {
        this.treasureNumber += 1;
    }

    public OrientationEnum getOrientation() {
        return orientation;
    }

    public void turnRight() {
        this.orientation = this.orientation.getRight();
    }

    public void turnLeft() {
        this.orientation = this.orientation.getLeft();
    }

    public void moveForward() {
        var positionTuple = PositionHelper.getNewPositionFromOrientation(orientation, Tuple.of(getPositionX(), getPositionY()));
        this.moveTo(positionTuple._1, positionTuple._2);
    }

}
