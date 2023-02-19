package fr.schouvey.william.treasuremap.domain;

public class Treasure extends WorldElement {

    private Integer number;

    public Integer getNumber() {
        return number;
    }

    public Treasure setNumber(Integer number) {
        this.number = number;
        return this;
    }

    public boolean acquireTreasure() {
        if (this.number > 0) {
            this.number -= 1;
            return true;
        }
        return false;
    }
}
