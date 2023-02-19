package fr.schouvey.william.treasuremap.domain;

public abstract class WorldElement {

    private Integer positionX;

    private Integer positionY;

    public Integer getPositionX() {
        return positionX;
    }

    public WorldElement setPositionX(Integer positionX) {
        this.positionX = positionX;
        return this;
    }

    public Integer getPositionY() {
        return positionY;
    }

    public WorldElement setPositionY(Integer positionY) {
        this.positionY = positionY;
        return this;
    }

    public boolean isPositionMatching(WorldElement worldElement) {
        return isPositionMatching(worldElement.getPositionX(), worldElement.getPositionY());
    }

    public boolean isPositionMatching(Integer positionX, Integer positionY) {
        return this.positionX.equals(positionX) && this.positionY.equals(positionY);
    }
}
