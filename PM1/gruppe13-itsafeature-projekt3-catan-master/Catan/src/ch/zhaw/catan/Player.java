package ch.zhaw.catan;

import java.util.HashMap;

import ch.zhaw.catan.Config.Faction;
import ch.zhaw.catan.Config.Resource;
import ch.zhaw.catan.Config.Structure;

/**
 * This class is a subclass of ResourceHolder and manages all Player data, for example their Faction, Resources, Structures and Points
 *
 * For example calling the method {@link Player#addPoints(int)}} will add points for the Player
 *
 * @author Badr Outiti
 */
public class Player extends ResourceHolder {

    private final Faction faction;
    private int points;
    private HashMap<Structure, Integer> structures;

    /**
     * Construct a Player state Object and bring it to the correct initial state
     *
     * @param faction the Faction that the Player represents
     */
    public Player(Faction faction) {
        this.faction = faction;
        this.points = 0;
        fillPlayerStockWithResources();
        fillPlayerStockWithStructures();
    }

    /**
     * Get the points that the Player currently has
     *
     * @return the points that the Player currently has
     */
    public int getPoints() {
        return points;
    }

    /**
     * Add a certain number of points to the Player
     *
     * @param points The amount of points that the Player gets additionally
     */
    public void addPoints(int points) {
        this.points += points;
    }

    /**
     * Get the Faction of the Player
     *
     * @return the Faction of the Player
     */
    public Faction getFaction() {
        return faction;
    }

    /**
     * Remove 1 Structure from the stock of the Player
     *
     * @param structure The Specific Structure that is targeted
     * @throws IllegalArgumentException if there are no Structures in the Players stock
     */
    public void removeStructureFromStock(Structure structure) {
        if (structures.get(structure) > 0) {
            int numberOfStructures = structures.get(structure);
            numberOfStructures--;
            structures.replace(structure, numberOfStructures);
        } else {
            throw new IllegalArgumentException("This action is not executable");

        }
    }

    /**
     * This method adds a structure back to the Structure stock of the Player
     *
     * @param structure The specific Structure that is targeted
     */
    public void addStructureToStock(Structure structure) {
        structures.replace(structure, structures.get(structure) + 1);
    }

    /**
     * Get the stock of the Player for a specific Structure
     *
     * @param structure The specific Structure that is targeted
     * @return The amount of the specific Structure that the Player has in his stock
     */
    public int getStructureStock(Structure structure) {
        return structures.get(structure);
    }

    /**
     * Fill the stock of the Player with Resources at the start of the game
     */
    private void fillPlayerStockWithResources() {
        HashMap<Resource, Integer> initResourceStock = new HashMap<>();
        //Put all Resources that are in the Game, in a Map with the Integer value 0
        for (Resource resource : Resource.values()) {
            initResourceStock.put(resource, 0);
        }
        setResources(initResourceStock);
    }

    /**
     * Fill the stock of the Player with Structures at the start ot the game
     * The amount of Structures that the Player gets are given in the Config class
     */
    private void fillPlayerStockWithStructures() {
        this.structures = new HashMap<>();
        Structure[] values = Structure.values();
        //Give the Player the Stock for the Structures like given in the Config class
        for (Structure structure : values) {
            structures.put(structure, structure.getStockPerPlayer());
        }
    }
}
