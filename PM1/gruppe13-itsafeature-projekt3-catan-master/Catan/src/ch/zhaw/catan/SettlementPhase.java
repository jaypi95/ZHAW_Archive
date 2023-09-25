package ch.zhaw.catan;

import org.beryx.textio.IntInputReader;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextTerminal;

import java.awt.Point;

/**
 * This class runs the settlement phase of a game
 * Every Player can build their initial roads and settlements in an specific order
 *
 * @author baachsil
 */
public class SettlementPhase {

    private final SiedlerGame siedlerGame;
    private final SiedlerBoardTextView boardPrinter;
    private final TextTerminal<?> textTerminal;
    private final IntInputReader intInputReader;
    private final int playerCount;

    /**
     * Inits the settlement phase object
     *
     * @param siedlerGame siedlerGame object in which the game is running
     * @param playerCount how many players are playing the game
     * @param textIO      the textIO object to get an instance of the terminal
     */
    public SettlementPhase(SiedlerGame siedlerGame, int playerCount, TextIO textIO) {
        this.siedlerGame = siedlerGame;
        this.playerCount = playerCount;

        boardPrinter = siedlerGame.getBoard().getView();

        textTerminal = textIO.getTextTerminal();
        intInputReader = textIO.newIntInputReader();

    }

    /**
     * This methods starts and runs the whole settlement phase
     */
    public void runSettlementPhase() {
        //Prints an empty gameBoard
        textTerminal.println("Starting settlement phase...");
        boardPrinter.printGameBoard();

        for (int firstRun = 1; firstRun <= playerCount; firstRun++) {
            playerTurn(false);
            siedlerGame.switchToNextPlayer();
        }

        siedlerGame.switchToPreviousPlayer();

        for (int secondRun = playerCount; secondRun >= 1; secondRun--) {
            playerTurn(true);
            siedlerGame.switchToPreviousPlayer();
        }

        textTerminal.println("Settlement phase completed! Main game has started.");
    }

    /**
     * This method handles one player turn
     * A player can build one settlement and one road
     * If wished the player gets the payout
     *
     * @param payout Should the player get Resources when creating a settlement?
     */
    private void playerTurn(boolean payout) {
        placeSettlement(payout);
        boardPrinter.printGameBoard();
        placeRoad();
        boardPrinter.printGameBoard();
    }

    /**
     * This method lets the player place a settlement
     *
     * @param payout Should the player get resources when creating a settlement?
     */
    private void placeSettlement(boolean payout) {
        boolean validInput = false;

        while (!validInput) {
            textTerminal.println("Player " + siedlerGame.getCurrentPlayerFaction().name() + " can build a settlement. Please enter the desired coordinates!");

            textTerminal.println("X-Coordinate:");
            int xCoord = intInputReader.read();

            textTerminal.println(("Y-Coordinate:"));
            int yCoord = intInputReader.read();

            validInput = siedlerGame.placeInitialSettlement(new Point(xCoord, yCoord), payout);

            if (!validInput) {
                textTerminal.println("Invalid parameters! Action aborted - try again");
            } else {
                textTerminal.println("Settlement founded successfully!");
            }
        }
    }

    /**
     * This method lets the player build a road
     */
    private void placeRoad() {
        boolean validInput = false;

        while (!validInput) {
            textTerminal.println("Player " + siedlerGame.getCurrentPlayerFaction().name() + " can build a road. Please enter the desired coordinates!");

            textTerminal.println("Start X-Coordinate:");
            int xCoordStart = intInputReader.read();

            textTerminal.println(("Start Y-Coordinate:"));
            int yCoordStart = intInputReader.read();

            textTerminal.println("End X-Coordinate:");
            int xCoordEnd = intInputReader.read();

            textTerminal.println(("End Y-Coordinate:"));
            int yCoordEnd = intInputReader.read();

            validInput = siedlerGame.placeInitialRoad(new Point(xCoordStart, yCoordStart), new Point(xCoordEnd, yCoordEnd));

            if (!validInput) {
                textTerminal.println("Invalid parameters! Action aborted - try again");
            } else {
                textTerminal.println("Road placed successfully!");
            }
        }
    }
}
