package ch.zhaw.catan;

import ch.zhaw.catan.Config.Resource;

import java.util.HashMap;

/**
 * This class is a subclass of ResourceHolder and manages the stock of the Bank
 * <p>
 * For example, calling the method {@link Bank#tradeWithBank(Resource, Resource, Player)} will trade Resources with Players
 *
 * @author Badr Outiti
 */

public class Bank extends ResourceHolder {
    /**
     * Create a Bank object with the defined Resources
     */
    public Bank() {
        fillBankStockWithResources();
    }

    /**
     * Trade your Resources with the Bank four to one
     *
     * @param get    The Resource that is wanted from the Bank
     * @param give   The Resource that is offered to the Bank
     * @param player The Player that is trading
     * @return True if the trade was successful, and false if it wasn't
     */
    public boolean tradeWithBank(Resource get, Resource give, Player player) {
        boolean tradeSuccessful = false;
        if (player.hasEnoughResource(get, 4) && hasEnoughResource(give, 1)) {
            addResource(get, 4);
            removeResource(give, 1);
            player.addResource(give, 1);
            player.removeResource(get, 4);
            tradeSuccessful = true;
        }
        return tradeSuccessful;
    }

    /**
     * Fill the stock of the Bank with Resources at the start of the game
     */
    private void fillBankStockWithResources() {
        //Fill the HashMap with the Resources, that are defined in the Config class
        HashMap<Resource, Integer> initBankResources = new HashMap<>(Config.INITIAL_RESOURCE_CARDS_BANK);
        setResources(initBankResources);
    }
}

