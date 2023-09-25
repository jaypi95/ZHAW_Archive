package ch.zhaw.catan.structures;

import ch.zhaw.catan.Bank;
import ch.zhaw.catan.Config;
import ch.zhaw.catan.Player;
import ch.zhaw.catan.SiedlerGame;

import java.awt.Point;

/**
 * This class represents the Structure city
 *
 * @author Silvan
 */
public class City extends Building {

    /**
     * Create a city object
     *
     * @param siedlerGame object
     * @param bank        object
     */
    public City(SiedlerGame siedlerGame, Bank bank) {
        super(siedlerGame, bank);
    }

    /**
     * Builds a city on a specific location
     *
     * @param position on which the city should be built
     * @param player   which wants to build the city
     * @return if the action was successful
     */
    public boolean buildCity(Point position, Player player) {
        return buildBuilding(position, player, Config.Structure.CITY, false);
    }
}
