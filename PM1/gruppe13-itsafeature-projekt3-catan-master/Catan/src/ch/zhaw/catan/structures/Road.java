package ch.zhaw.catan.structures;

import ch.zhaw.catan.*;

import java.awt.Point;

/**
 * This class represents the road structure
 *
 * @author Silvan
 */
public class Road extends Structure {

    private final SiedlerBoard board;

    /**
     * Creates a road object
     *
     * @param siedlerGame object
     * @param bank        object
     */
    public Road(SiedlerGame siedlerGame, Bank bank) {
        super(bank);

        this.board = siedlerGame.getBoard();
    }

    /**
     * This method builds a road at a specific location
     *
     * @param roadStart start point of the road
     * @param roadEnd   end point of the road
     * @param player    which builds the road
     * @param isFree    is this structure free to build?
     * @return if the action was successful
     */
    public boolean buildRoad(Point roadStart, Point roadEnd, Player player, boolean isFree) {
        boolean builtRoad = false;
        if (playerHasEnoughResourcesForStructure(player, Config.Structure.ROAD) | isFree) {
            try {
                builtRoad = board.buildRoad(roadStart, roadEnd, player.getFaction());

                if (!isFree) {
                    resourceExchangeAfterBuilding(player, Config.Structure.ROAD);
                }

            } catch (IllegalArgumentException illegalArgumentException) {
                builtRoad = false;
            }
        }
        return builtRoad;
    }
}
