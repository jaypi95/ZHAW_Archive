package ch.zhaw.catan.structures;

import ch.zhaw.catan.*;

import java.awt.Point;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This ist the superclass of Buildings e.g. city and settlement
 *
 * @author Silvan
 */
public abstract class Building extends Structure {

    private final SiedlerBoard board;
    private final Bank bank;
    private Point positionOfBuilding;

    public static final int WIN_POINTS = 1;

    /**
     * Creates a building object
     *
     * @param siedlerGame object
     * @param bank        object
     */
    public Building(SiedlerGame siedlerGame, Bank bank) {
        super(bank);
        this.bank = bank;
        this.board = siedlerGame.getBoard();
    }

    /**
     * This method builds a building at the desired point
     *
     * @param position on which the building should be placed
     * @param player   of the settlement
     * @return if the action was successful
     */
    public boolean buildBuilding(Point position, Player player, Config.Structure structure, boolean isFree) {
        boolean builtBuilding = false;

        if (playerHasEnoughResourcesForStructure(player, structure) | isFree) {
            try {

                if (structure == Config.Structure.SETTLEMENT) {
                    builtBuilding = board.buildSettlement(position, player.getFaction());
                } else {
                    builtBuilding = board.buildCity(position, player.getFaction());
                    player.addStructureToStock(Config.Structure.SETTLEMENT);
                }

                if (!isFree) {
                    resourceExchangeAfterBuilding(player, structure);
                }
                positionOfBuilding = position;
                player.removeStructureFromStock(structure);
                player.addPoints(WIN_POINTS);

            } catch (IllegalArgumentException illegalArgumentException) {
                builtBuilding = false;
            }
        }
        return builtBuilding;
    }

    /**
     * Get the Resources of all lands that are adjacent to your building
     *
     * @return a Map with the Resources
     */
    private Map<Config.Resource, Long> getResourceForBuildingNearLand() {
        Map<Config.Resource, Long> resources = new HashMap<>();

        //Get a List of the adjacent lands
        List<Config.Land> listOfAdjacentLands = board.getFields(positionOfBuilding);

        //Iterate trough the lands and put the Resources in the Map
        for (Config.Land land : listOfAdjacentLands) {
            Config.Resource resource = land.getResource();
            //Skip to the next land, if the Resource of the land is null
            if (resource == null) {
                continue;
            }
            resources.put(resource, (long) 1);

        }

        return resources;
    }

    /**
     * Pays out the resources for this building
     *
     * @param player which gets the resources
     */
    public void payOutPlayer(Player player) {
        Map<Config.Resource, Long> resourcesToAdd = getResourceForBuildingNearLand();
        bank.removeResourceWithMap(resourcesToAdd);
        player.addResourceWithMap(resourcesToAdd);
    }
}
