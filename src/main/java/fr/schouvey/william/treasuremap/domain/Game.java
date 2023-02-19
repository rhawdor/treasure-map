package fr.schouvey.william.treasuremap.domain;

import java.util.Set;

public class Game {

    private WorldMap worldMap;

    private Set<Adventurer> adventurers;

    public WorldMap getWorldMap() {
        return worldMap;
    }

    public Game setWorldMap(WorldMap worldMap) {
        this.worldMap = worldMap;
        return this;
    }

    public Set<Adventurer> getAdventurers() {
        return adventurers;
    }

    public Game setAdventurers(Set<Adventurer> adventurers) {
        this.adventurers = adventurers;
        return this;
    }
}
