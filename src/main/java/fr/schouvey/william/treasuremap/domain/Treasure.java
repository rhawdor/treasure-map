package fr.schouvey.william.treasuremap.domain;

public class Treasure extends WorldElement {

    private Integer number;

    public Treasure(Integer number, Integer positionX, Integer positionY) {
        super(positionX, positionY);
        this.number = number;
    }

    public Integer getNumber() {
        return number;
    }

    public boolean acquireTreasure() {
        if (this.number > 0) {
            this.number -= 1;
            return true;
        }
        return false;
    }
}
