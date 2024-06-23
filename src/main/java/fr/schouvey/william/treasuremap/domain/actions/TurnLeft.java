package fr.schouvey.william.treasuremap.domain.actions;

import fr.schouvey.william.treasuremap.domain.Adventurer;
import fr.schouvey.william.treasuremap.domain.WorldMap;

public class TurnLeft implements Movement {

    public void execute(WorldMap worldMap, Adventurer adventurer) {
        adventurer.turnLeft();
    }

}
