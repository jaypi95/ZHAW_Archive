package ch.zhaw.catan.structures;

import ch.zhaw.catan.*;

import java.util.Map;

/**
 * This class is the superclass of all Structures e.g Road, Settlement usw...
 *
 * @author Silvan
 */
public abstract class Structure {

    private final Bank bank;

    /**
     * Creates a new Structure object
     *
     * @param bank the bank object
     */
    public Structure(Bank bank) {
        this.bank = bank;
    }

    /**
     * Exchange the Resources after Building a Structure
     */
    public void resourceExchangeAfterBuilding(Player player, Config.Structure structure) {
        player.removeStructureFromStock(structure);
        player.removeResourceWithMap(structure.getCostsAsMap());
        bank.addResourceWithMap(structure.getCostsAsMap());
    }

    /**
     * Check if the Player has enough Resources to build a specific Structure
     *
     * @return true if the Player has enough Resources, false if he hasn't
     */
    public boolean playerHasEnoughResourcesForStructure(Player player, Config.Structure structure) {
        boolean hasEnough = false;
        Map<Config.Resource, Long> costs = structure.getCostsAsMap();
        for (Config.Resource resource : costs.keySet()) {
            if (player.getResourceStock(resource) >= costs.get(resource)) {
                hasEnough = true;
            } else {
                hasEnough = false;
                break;
            }
        }
        return hasEnough;
    }

}
