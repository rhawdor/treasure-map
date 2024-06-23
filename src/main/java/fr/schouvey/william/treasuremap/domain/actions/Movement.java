package fr.schouvey.william.treasuremap.domain.actions;

import fr.schouvey.william.treasuremap.domain.Adventurer;
import fr.schouvey.william.treasuremap.domain.WorldMap;

public interface Movement {

    void execute(final WorldMap worldMap, final Adventurer adventurer);

}
