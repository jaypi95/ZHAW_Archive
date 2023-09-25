package ch.zhaw.catan;

import ch.zhaw.catan.Config.Faction;
import ch.zhaw.catan.Config.Resource;
import ch.zhaw.catan.Config.Structure;
import org.beryx.textio.IntInputReader;
import org.beryx.textio.StringInputReader;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextTerminal;

import java.awt.Point;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;


/**
 * This class runs the main part of the game after the board has been set up and after the settlement phase finished.
 * It basically receives the input from the main method and starts the third phase of the game.
 * With that input it either calls the appropriate methods to build something, to trade with the bank or to throw the dice.
 *
 * @author peterju1
 */
public class MainGamePhase {


    private boolean start;
    private boolean turn;
    private final SiedlerGame siedlerGame;
    private final SiedlerBoardTextView boardPrinter;
    private final TextTerminal<?> textTerminal;
    private final StringInputReader stringInputReader;
    private final IntInputReader intInputReader;
    private final TextIO textIO;
    private Faction winner;
    private String quit;

    /**
     * Inits the  MainGamePhase object
     *
     * @param textIO the textIO object to get an instance of the terminal
     * @param siedlerGame siedlerGame object in which the game is running
     */
    public MainGamePhase(TextIO textIO, SiedlerGame siedlerGame) {
        boardPrinter = new SiedlerBoardTextView(siedlerGame.getBoard());
        start = true;
        textTerminal = textIO.getTextTerminal();
        stringInputReader = textIO.newStringInputReader();
        intInputReader = textIO.newIntInputReader();
        winner = null;
        quit = "no";
        this.textIO = textIO;
        this.siedlerGame = siedlerGame;

    }

    /**
     * This {@link Enum} specifies the commands in the main action interface
     */
    public enum Commands {
        BUILD("BUILD"), TRADE("TRADE"), RESOURCE("SHOW RESOURCES"), FINISH("FINISH TURN"), QUIT("QUIT GAME");
        private final String name;

        Commands(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    /**
     * This {@link Enum} specifies the commands in the building action interface
     */
    public enum buildCommand {
        SETTLEMENT("BUILD SETTLEMENT"), CITY("BUILD CITY"), ROAD("BUILD ROAD"), COST("SHOW COSTS"), RETURN("RETURN TO MAIN MENU");
        private final String name;

        buildCommand(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    /**
     * This Method contains a loop + an action interface for the user. It is the main interface of the game.
     * It takes in an user input and runs each turn. At the end of the turn it switches to the next player.
     */
    public void gameRunning() {
        while (start) {
            proceedWithDiceThrow();
            turn = true;
            while (turn) {
                switch (getEnumValue(textIO, Commands.class)) {
                    case BUILD:
                        proceedWithBuilding();
                        break;
                    case TRADE:
                        proceedWithTrading();
                        break;
                    case RESOURCE:
                        proceedWithResource();
                        break;
                    case FINISH:
                        turn = false;
                        break;
                    case QUIT:
                        proceedWithExit();
                        break;
                    default:
                        throw new IllegalStateException("Internal error found - Command not implemented.");
                }
            }
            if (quit.equals("yes")) {
                start = false;
            }

            winner = siedlerGame.getWinner();
            if (start) {
                if (winner != null) {
                    textTerminal.println("Congratulations " + winner.name() + " you have won the game.");
                    proceedWithExit();
                } else {
                    siedlerGame.switchToNextPlayer();
                    boardPrinter.printGameBoard();
                    textTerminal.println("\nIt's " + siedlerGame.getCurrentPlayerFaction().name() + "'s turn.");
                }
            }
        }
    }


    /**
     * Calls the method to generate a new dice throw
     * Takes that number and calls the method to pay out the players in resources.
     */
    private void proceedWithDiceThrow() {
        int thrownNumber = generateDiceThrow();
        textTerminal.println("\nThrow dice...");

        textTerminal.println("\nResult: " + thrownNumber + "\n");

        if (thrownNumber != 7) {
            Map<Faction, List<Resource>> payout = siedlerGame.throwDice(thrownNumber); //The method "throwDice" actually pays out the resources, the name is just very poorly chosen.
            for (Map.Entry<Faction, List<Resource>> entry : payout.entrySet()) {

                if (entry.getValue().size() == 0) {
                    textTerminal.println("\nUnfortunately, no resources have been paid out to " + entry.getKey().name());
                } else {
                    textTerminal.println("\nThe following resources have been paid out to " + entry.getKey().name());
                    for (Resource entry1 : entry.getValue()) {
                        textTerminal.println(entry1.name());
                    }
                }
            }
        } else {
            proceedWithBandit();
        }
    }

    /**
     * Shows available building types, their cost and calls the appropriate methods to build it.
     * When the player chooses "Return" --> return to the previous menu
     */
    private void proceedWithBuilding() {
        boolean build = true;
        while (build) {
            switch (getEnumValue(textIO, buildCommand.class)) {
                case SETTLEMENT:
                    caseBuildSettlement();
                    break;
                case ROAD:
                    caseBuildRoad();
                    break;
                case COST:
                    casePrintStructureCosts();
                    break;
                case CITY:
                    caseBuildCity();
                case RETURN:
                    build = false;
                    break;
                default:
                    throw new IllegalStateException("Internal error found - Command not implemented.");
            }
        }
    }

    /**
     * This method asks for the cards that should be traded with the bank and calls the appropriate methods in SiedlerGame
     */
    private void proceedWithTrading() {
        proceedWithResource();

        textTerminal.println("\nResource to trade in");
        Config.Resource tradeInResource = getEnumValue(textIO, Config.Resource.class);

        textTerminal.println("\nResource you wish to receive");
        Config.Resource tradeOutResource = getEnumValue(textIO, Config.Resource.class);

        if (siedlerGame.tradeWithBankFourToOne(tradeInResource, tradeOutResource)) {
            textTerminal.println("Successfully traded with the bank.");
        } else {
            textTerminal.println("Your trade failed. Make sure you have enough resources to trade in.");
        }


    }

    /**
     * This method shows the Resources that the current Player owns.
     */
    private void proceedWithResource() {

        textTerminal.println("\nYou have the following resources in hand\n");
        for (Resource resource : Resource.values()) {
            int stock = siedlerGame.getCurrentPlayerResourceStock(resource);
            textTerminal.println(resource.name() + ": " + stock);
        }
        textTerminal.println("\n");
    }

    /**
     * This method exits the game with a confirmation.
     */
    private void proceedWithExit() {
        if (winner != null) {
            textTerminal.println("Thank you for playing Catan");
            textTerminal.println("Press Enter to quit");
            stringInputReader.read();
            start = false;
        } else {
            textTerminal.println("Are you sure?");
            textTerminal.println("yes / no");
            String sure = stringInputReader.read();

            if (sure.equals("yes")) {
                textTerminal.println("Bye!");
                quit = sure;
                turn = false;


            }
        }
    }

    /**
     * This method asks for the Settlement coordinates, and calls the place Settlement method.
     */
    private void caseBuildSettlement() {

        textTerminal.println("\nX-Coordinate:");
        int param1 = intInputReader.read();

        textTerminal.println("\nY-Coordinate");
        int param2 = intInputReader.read();

        Point point = new Point(param1, param2);

        if (siedlerGame.buildSettlement(point)) {
            boardPrinter.printGameBoard();
            textTerminal.println("\nSettlement successfully placed.");
        } else {
            textTerminal.println("\nSettlement could not be placed. Make sure you have enough resources and that your input was valid");
        }

    }

    /**
     * This method asks for the City coordinates, and calls the place City method.
     */
    private void caseBuildCity() {

        textTerminal.println("\nX-Coordinate:");
        int param1 = intInputReader.read();

        textTerminal.println("\nY-Coordinate");
        int param2 = intInputReader.read();

        Point point = new Point(param1, param2);

        if (siedlerGame.buildCity(point)) {
            boardPrinter.printGameBoard();
            textTerminal.println("\nCity successfully placed.");
        } else {
            textTerminal.println("\nCity could not be placed. Make sure you have enough resources and that your input was valid");
        }

    }

    /**
     * This Method asks for the coordinates one by one, and calls the place Road method
     */
    private void caseBuildRoad() {

        textTerminal.println("S\ntart X-Coordinate:");
        int param11 = intInputReader.read();

        textTerminal.println("\nStart Y-Coordinate");
        int param12 = intInputReader.read();

        textTerminal.println("\nEnd X-Coordinate:");
        int param21 = intInputReader.read();

        textTerminal.println("\nEnd Y-Coordinate");
        int param22 = intInputReader.read();

        Point point1 = new Point(param11, param12);
        Point point2 = new Point(param21, param22);

        if (siedlerGame.buildRoad(point1, point2)) {
            boardPrinter.printGameBoard();
            textTerminal.println("\nRoad successfully placed.\n");
        } else {
            textTerminal.println("\nRoad could not be placed. Make sure you have enough resources and that your input was valid\n");
        }


    }

    /**
     * This method prints out the cost of each available Structure in resources.
     */
    private void casePrintStructureCosts() {

        //Would be easier to extend with new buildings if we could use java.lang and reflection to loop through the methods
        Map<Config.Resource, Long> settlement = Structure.SETTLEMENT.getCostsAsMap();
        Map<Config.Resource, Long> road = Structure.ROAD.getCostsAsMap();
        Map<Config.Resource, Long> city = Structure.CITY.getCostsAsMap();

        //Code duplication could be prevented with reflection (java.lang) because otherwise it expects a map but gets an Object object
        textTerminal.println("\nSETTLEMENT");
        for (Map.Entry<Config.Resource, Long> entry : settlement.entrySet()) {
            textTerminal.println(entry.getKey().name() + " " + entry.getValue());
        }

        textTerminal.println("\nROAD");
        for (Map.Entry<Config.Resource, Long> entry : road.entrySet()) {
            textTerminal.println(entry.getKey().name() + " " + entry.getValue());
        }

        textTerminal.println("\nCITY");
        for (Map.Entry<Config.Resource, Long> entry : city.entrySet()) {
            textTerminal.println(entry.getKey().name() + " " + entry.getValue());
        }
        textTerminal.println("\n");
    }

    /**
     * This method throws a configurable number of dice and returns their combined value.
     *
     * @return int with sum of all dice
     */
    private int generateDiceThrow() {
        int numberOfDice = 2;
        int[] dieResult = new int[numberOfDice];
        for (int i = 0; i < numberOfDice; i++) {
            dieResult[i] = new Random().nextInt(6) + 1;
        }
        return IntStream.of(dieResult).sum();
    }

    /**
     * This Method takes the input of the user. It gets used in the switch cases to handle the Enums.
     *
     * @param textIO object
     * @param name   of the action
     * @param <T>    object type
     * @return Enum Value
     */
    private static <T extends Enum<T>> T getEnumValue(TextIO textIO, Class<T> name) {
        return textIO.newEnumInputReader(name).read("Choose action:");
    }

    /**
     * This method handles the user input for placing the bandit
     */
    private void proceedWithBandit() {
        textTerminal.println("Oh no, all players have been robbed!");
        textTerminal.println("Where should the thief go to?");

        boolean validInput = false;
        while (!validInput) {
            textTerminal.println("\nX-Coordinate:");
            int xCoord = intInputReader.read();

            textTerminal.println("\nY-Coordinate");
            int yCoord = intInputReader.read();

            validInput = siedlerGame.placeThiefAndStealCard(new Point(xCoord, yCoord));

            if (!validInput) {
                textTerminal.println("Invalid parameters! Action aborted - try again");
            } else {
                textTerminal.println("Thief successfully placed! Resource card has been stolen.");
            }
        }

    }
}
