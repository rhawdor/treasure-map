package fr.schouvey.william.treasuremap.domain;

import fr.schouvey.william.treasuremap.helper.PositionHelper;
import io.vavr.Tuple;

public class Adventurer extends WorldElement {

    private String name;

    private Integer treasureNumber = 0;

    private OrientationEnum orientation;

    public String getName() {
        return name;
    }

    public Adventurer setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getTreasureNumber() {
        return treasureNumber;
    }

    public Adventurer setTreasureNumber(Integer treasureNumber) {
        this.treasureNumber = treasureNumber;
        return this;
    }

    public Adventurer acquireTreasure() {
        this.treasureNumber += 1;
        return this;
    }

    public OrientationEnum getOrientation() {
        return orientation;
    }

    public Adventurer setOrientation(OrientationEnum orientation) {
        this.orientation = orientation;
        return this;
    }

    public Adventurer turnRight() {
        this.orientation = this.orientation.getRight();
        return this;
    }

    public Adventurer turnLeft() {
        this.orientation = this.orientation.getLeft();
        return this;
    }

    public Adventurer moveForward() {
        var positionTuple = PositionHelper.getNewPositionFromOrientation(orientation, Tuple.of(getPositionX(), getPositionY()));
        this.setPositionX(positionTuple._1);
        this.setPositionY(positionTuple._2);
        return this;
    }

}
