package ch.zhaw.catan.structures;

import ch.zhaw.catan.*;

import java.awt.Point;

/**
 * This class represents the structure Settlement
 *
 * @author Silvan
 */
public class Settlement extends Building {

    /**
     * Creates a settlement object
     *
     * @param siedlerGame object
     * @param bank        object
     */
    public Settlement(SiedlerGame siedlerGame, Bank bank) {
        super(siedlerGame, bank);
    }

    /**
     * This methods builds a settlement at a specific location
     *
     * @param position on which the settlement should be built
     * @param player   which builds the settlement
     * @param payout   should the player get resources (just for the SettlementPhase)
     * @param isFree   is this building free to build?
     * @return if the action was successful
     */
    public boolean buildSettlement(Point position, Player player, boolean payout, boolean isFree) {
        boolean builtSettlement = buildBuilding(position, player, Config.Structure.SETTLEMENT, isFree);

        if (payout) {
            payOutPlayer(player);
        }

        return builtSettlement;
    }
}
