package fr.schouvey.william.treasuremap.service;

import fr.schouvey.william.treasuremap.domain.Adventurer;
import fr.schouvey.william.treasuremap.domain.GameResult;
import fr.schouvey.william.treasuremap.domain.actions.Movement;
import fr.schouvey.william.treasuremap.domain.WorldMap;

import java.util.List;
import java.util.Map;

public class TreasureMapService {

    private TreasureMapService() {}

    public static GameResult processTreasureMap(final WorldMap worldMap, final Map<Adventurer, List<Movement>> adventurersMap) {
        adventurersMap.forEach((adventurer, movementSequence) -> processAdventurerMovements(worldMap, adventurer, movementSequence));
        return new GameResult(worldMap, adventurersMap.keySet());
    }

    private static void processAdventurerMovements(final WorldMap worldMap, final Adventurer adventurer, final List<Movement> movementSequence) {
        movementSequence.forEach(movement -> movement.execute(worldMap, adventurer));
    }

}
