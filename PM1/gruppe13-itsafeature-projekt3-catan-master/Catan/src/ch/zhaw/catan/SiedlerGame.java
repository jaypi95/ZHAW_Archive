package ch.zhaw.catan;

import ch.zhaw.catan.Config.Faction;
import ch.zhaw.catan.Config.Resource;
import ch.zhaw.catan.Config.Structure;
import ch.zhaw.catan.structures.City;
import ch.zhaw.catan.structures.Road;
import ch.zhaw.catan.structures.Settlement;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;


/**
 * This class performs all actions related to modifying the game state.
 *
 * <p>For example, calling the method {@link SiedlerGame#throwDice(int)}
 * will do the payout of the resources to the players according to
 * the payout rules of the game which take into account factors like
 * the amount of resources requested of a certain type, the number of players
 * requesting it, the amount of resources available in the bank and
 * the settlement types.</p>
 *
 * @author Badr Outiti
 */
public class SiedlerGame {

    private final SiedlerBoard board;
    private final ArrayList<Player> listOfPlayers;
    private final int numberOfPlayers;
    private Player currentPlayer;
    private final int winPoints;
    private final Bank bank;

    /**
     * Constructs a SiedlerGame game state object.
     *
     * @param winPoints the number of points required to win the game
     * @param players   the number of players
     * @throws IllegalArgumentException if winPoints is lower than
     *                                  three or players is not between two and four
     */
    public SiedlerGame(int winPoints, int players) {
        //If winPoints is lower than 3 and players is in between MIN_NUMBER_OF_PLAYERS and 4
        if (winPoints >= 3 && players >= Config.MIN_NUMBER_OF_PLAYERS && players <= Faction.values().length) {
            this.winPoints = winPoints;
            this.numberOfPlayers = players;
            this.bank = new Bank();

            listOfPlayers = new ArrayList<>();
            this.board = new SiedlerBoard();
            //Init the Players
            initPlayers();

        } else {
            throw new IllegalArgumentException("Invalid configuration: The player count needs to be between "
                    + Config.MIN_NUMBER_OF_PLAYERS + " and 4 and the minimum points to win must be higher then 2");
        }
    }

    /**
     * Switches to the next player in the defined sequence of players.
     */
    public void switchToNextPlayer() {
        //If the current Player is not the last Player in the list, change the current Player to the next Player in the list
        if (listOfPlayers.indexOf(currentPlayer) < listOfPlayers.size() - 1) {
            int index = listOfPlayers.indexOf(currentPlayer) + 1;
            currentPlayer = listOfPlayers.get(index);
        } else {
            //If the current Player is the last Player in the list, change the current Player to the first Player in the list
            currentPlayer = listOfPlayers.get(0);
        }
    }

    /**
     * Switches to the previous player in the defined sequence of players.
     */
    public void switchToPreviousPlayer() {
        //If the current Player is not the first one in the list, change the current Player to the previous Player in the list
        if (listOfPlayers.indexOf(currentPlayer) != 0) {
            int index = listOfPlayers.indexOf(currentPlayer) - 1;
            currentPlayer = listOfPlayers.get(index);
        } else {
            //If the current Player is the first Player in the list, change the current Player to the last Player in the list
            int index = listOfPlayers.size() - 1;
            currentPlayer = listOfPlayers.get(index);
        }
    }

    /**
     * Returns the {@link Faction}s of the active players.
     *
     * <p>The order of the player's factions in the list must
     * correspond to the order in which they play.
     * Hence, the player that sets the first settlement must be
     * at position 0 in the list etc.
     *
     * <strong>Important note:</strong> The list must contain the
     * factions of active players only.</p>
     *
     * @return the list with player's factions
     */
    public List<Faction> getPlayerFactions() {
        //Create a temporary List for the Factions
        List<Faction> listOfFactions = new ArrayList<>();
        //Add the Factions from each Player to the List in the defined order of the Game
        for (Player player : listOfPlayers) {
            listOfFactions.add(player.getFaction());
        }
        return listOfFactions;
    }


    /**
     * Returns the game board.
     *
     * @return the game board
     */
    public SiedlerBoard getBoard() {
        return board;
    }

    /**
     * Returns the {@link Faction} of the current player.
     *
     * @return the faction of the current player
     */
    public Faction getCurrentPlayerFaction() {
        return currentPlayer.getFaction();
    }

    /**
     * Gets the current amount of resources of the specified type
     * in the current players' stock (hand).
     *
     * @param resource the resource type
     * @return the amount of resources of this type
     */
    public int getCurrentPlayerResourceStock(Resource resource) {
        return currentPlayer.getResourceStock(resource);
    }

    /**
     * Places a settlement in the founder's phase (phase II) of the game.
     *
     * <p>The placement does not cost any resources. If payout is
     * set to true, one resource per adjacent field is taken from the
     * bank and added to the players' stock of resources.</p>
     *
     * @param position the position of the settlement
     * @param payout   true, if the player shall get the resources of the surrounding fields
     * @return true, if the placement was successful
     */
    public boolean placeInitialSettlement(Point position, boolean payout) {
        Settlement settlement = new Settlement(this, bank);
        return settlement.buildSettlement(position, currentPlayer, payout, true);
    }

    /**
     * Places a road in the founder's phase (phase II) of the game.
     * The placement does not cost any resources.
     *
     * @param roadStart position of the start of the road
     * @param roadEnd   position of the end of the road
     * @return true, if the placement was successful
     */
    public boolean placeInitialRoad(Point roadStart, Point roadEnd) {
        Road road = new Road(this, bank);
        return road.buildRoad(roadStart, roadEnd, currentPlayer, true);
    }

    /**
     * This method takes care of the payout of the resources to the players
     * according to the payout rules of the game. If a player does not get resources,
     * the list for this players' faction is empty.
     *
     * <p>The payout rules of the game take into account factors like
     * the amount of resources of a certain type, the number of players
     * requesting resources of this type, the amount of resources available
     * in the bank and the settlement types.</p>
     *
     * @param dicethrow the result of the dice throw
     * @return the resources that have been paid to the players
     */
    public Map<Faction, List<Resource>> throwDice(int dicethrow) {
        Map<Faction, List<Resource>> distributedResources = new HashMap<>();
        for (Player player : listOfPlayers) {
            Map<Resource, Long> resourceForPlayerNearNumber = board.getResourceForPlayerNearNumber(dicethrow, player.getFaction());
            player.addResourceWithMap(resourceForPlayerNearNumber);
            bank.removeResourceWithMap(resourceForPlayerNearNumber);

            // save resources given to player to a list
            List<Resource> distributedResourcesForPlayer = new ArrayList<>();
            // iterate over the resources (Wood, 2), (Stone, 3), etc...
            for (Map.Entry<Resource, Long> resourceGiven : resourceForPlayerNearNumber.entrySet()) {
                // if a resource appears multiple times, give it that many times. Ex: (Wood, 2)
                for (int i = 0; i < resourceGiven.getValue(); i++) {
                    distributedResourcesForPlayer.add(resourceGiven.getKey());
                }

            }
            distributedResources.put(player.getFaction(), distributedResourcesForPlayer);
        }
        return distributedResources;

    }


    /**
     * Builds a settlement at the specified position on the board.
     *
     * <p>The settlement can be built if:
     * <ul>
     * <li> the player has the resource cards required</li>
     * <li> a settlement to place on the board</li>
     * <li> the specified position meets the build rules for settlements</li>
     * </ul>
     *
     * @param position the position of the settlement
     * @return true, if the placement was successful
     */
    public boolean buildSettlement(Point position) {
        boolean builtSettlement = false;
        Settlement settlement = new Settlement(this, bank);

        if (currentPlayer.getStructureStock(Structure.SETTLEMENT) > 0) {
            builtSettlement = settlement.buildSettlement(position, currentPlayer, false, false);
        }
        return builtSettlement;
    }


    /**
     * Builds a city at the specified position on the board.
     *
     * <p>The city can be built if:
     * <ul>
     * <li> the player has the resource cards required</li>
     * <li> a city to place on the board</li>
     * <li> the specified position meets the build rules for cities</li>
     * </ul>
     *
     * @param position the position of the city
     * @return true, if the placement was successful
     */
    public boolean buildCity(Point position) {
        boolean builtCity = false;
        City city = new City(this, bank);

        if (currentPlayer.getStructureStock(Structure.CITY) > 0) {
            builtCity = city.buildCity(position, currentPlayer);
        }
        return builtCity;
    }

    /**
     * Builds a road at the specified position on the board.
     *
     * <p>The road can be built if:
     * <ul>
     * <li> the player has the resource cards required</li>
     * <li> a road to place on the board</li>
     * <li> the specified position meets the build rules for roads</li>
     * </ul>
     *
     * @param roadStart the position of the start of the road
     * @param roadEnd   the position of the end of the road
     * @return true, if the placement was successful
     */
    public boolean buildRoad(Point roadStart, Point roadEnd) {
        boolean builtRoad = false;
        Road road = new Road(this, bank);

        if (currentPlayer.getStructureStock(Structure.ROAD) > 0) {
            builtRoad = road.buildRoad(roadStart, roadEnd, currentPlayer, false);
        }
        return builtRoad;
    }


    /**
     * Trades in {@value #//FOUR_TO_ONE_TRADE_OFFER} resources of the
     * offered type for {@value #//FOUR_TO_ONE_TRADE_WANT} resource of the wanted type.
     *
     * @param offer offered type
     * @param want  wanted type
     * @return true, if player and bank had enough resources and the trade was successful
     */
    public boolean tradeWithBankFourToOne(Resource offer, Resource want) {
        return bank.tradeWithBank(offer, want, currentPlayer);
    }


    /**
     * Returns the winner of the game, if any.
     *
     * @return the winner of the game or null, if there is no winner (yet)
     */
    public Faction getWinner() {
        Faction winnerFaction = null;
        if (currentPlayer.getPoints() >= winPoints) {
            winnerFaction = currentPlayer.getFaction();
        }

        return winnerFaction;
    }

    /**
     * Places the thief on the specified field and steals a random resource card (if
     * the player has such cards) from a random player with a settlement at that
     * field (if there is a settlement) and adds it to the resource cards of the
     * current player.
     *
     * @param field the field on which to place the thief
     * @return false, if the specified field is not a field or the thief cannot be
     * placed there (e.g., on water)
     */
    public boolean placeThiefAndStealCard(Point field) {
        boolean validField = board.canPlaceThief(field);
        if (validField) {

            robAllPlayers();

            board.placeThief(field);

            //rob one random player
            ArrayList<Player> listWithPlayersThatCanBeRobbed = playersWithCityOrSettlementAtField(field);
            if (listWithPlayersThatCanBeRobbed.size()>0) {
                Random randomGenerator = new Random();
                int randomNumber = randomGenerator.nextInt(listWithPlayersThatCanBeRobbed.size()) + 1;
                Player playerToBeRobbed = listWithPlayersThatCanBeRobbed.get(randomNumber - 1);
                Resource stolenResource = playerToBeRobbed.stealAResource();
                currentPlayer.addResource(stolenResource, 1);
            }
        }
        return validField;
    }

    /**
     * This method takes 50 % of the cards from all players with more than 7
     */
    private void robAllPlayers() {

        for (Player player : listOfPlayers) {
            int cardCount = player.getAllCardsInHand();

            if (cardCount > Config.MAX_CARDS_IN_HAND_NO_DROP) {
                int cardsToBeRobbed = cardCount / 2;

                //Give the deleted resources back to the bank
                HashMap<Resource, Long> deletedResources = player.deleteResources(cardsToBeRobbed);
                bank.addResourceWithMap(deletedResources);
            }
        }
    }

    /**
     * Initialise all Player Objects and the listOfPlayers at the start of the game
     */
    //Initialise the Players and the list of Players.
    private void initPlayers() {
        Faction[] factionValues = Config.Faction.values();
        for (int i = 0; i < numberOfPlayers; i++) {
            Player newPlayer = new Player(factionValues[i]);
            listOfPlayers.add(newPlayer);
        }
        initCurrentPlayer();
    }

    /**
     * Initialise the currentPlayer at the start of the game
     */
    private void initCurrentPlayer() {
        //If the currentPlayer isn't init. and the listOfPlayers already has player objects, the currentPlayer init as Player at place 0
        if (listOfPlayers != null && currentPlayer == null) {
            currentPlayer = listOfPlayers.get(0);
        }
    }

    /**
     * Return a list with players that have a settlement or a city at a field. If there are none return null.
     *
     * @param field The point of the field
     * @return a list with players that have a settlement or a city at the field.
     */
    private ArrayList<Player> playersWithCityOrSettlementAtField(Point field) {
        ArrayList<Player> playersAtField = new ArrayList<>();
        ArrayList<String> factionsWithSettlementAtField = board.checkWhichPlayersHaveACityOrSettlementAtField(field);
        for (Player player : listOfPlayers) {
            for (String factionsAtField : factionsWithSettlementAtField) {
                if (player.getFaction().toString().equalsIgnoreCase(factionsAtField)) {
                    playersAtField.add(player);
                }
            }
        }
        return playersAtField;
    }
}

