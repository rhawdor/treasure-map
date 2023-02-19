package fr.schouvey.william.treasuremap.domain;

import java.util.List;

public class WorldMap {

    private Size size;

    private List<Mountain> mountains;

    private List<Treasure> treasures;

    public Size getSize() {
        return size;
    }

    public WorldMap setSize(Size size) {
        this.size = size;
        return this;
    }

    public List<Mountain> getMountains() {
        return mountains;
    }

    public WorldMap setMountains(List<Mountain> mountains) {
        this.mountains = mountains;
        return this;
    }

    public List<Treasure> getTreasures() {
        return treasures;
    }

    public WorldMap setTreasures(List<Treasure> treasures) {
        this.treasures = treasures;
        return this;
    }

    public static class Size {

        private Size() {
        }

        public static Size of(Integer width, Integer height) {
            var size = new Size();
            size.height = height;
            size.width = width;
            return size;
        }

        private Integer width;

        private Integer height;

        public Integer getWidth() {
            return width;
        }

        public Integer getHeight() {
            return height;
        }
    }
}
