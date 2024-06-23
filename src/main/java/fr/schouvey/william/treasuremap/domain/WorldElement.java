package fr.schouvey.william.treasuremap.domain;

public abstract class WorldElement {

    private Integer positionX;

    private Integer positionY;

    public WorldElement(Integer positionX, Integer positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public Integer getPositionX() {
        return positionX;
    }

    public Integer getPositionY() {
        return positionY;
    }

    public boolean isPositionMatching(WorldElement worldElement) {
        return isPositionMatching(worldElement.getPositionX(), worldElement.getPositionY());
    }

    public boolean isPositionMatching(Integer positionX, Integer positionY) {
        return this.positionX.equals(positionX) && this.positionY.equals(positionY);
    }

    protected void moveTo(Integer positionX, Integer positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }
}
