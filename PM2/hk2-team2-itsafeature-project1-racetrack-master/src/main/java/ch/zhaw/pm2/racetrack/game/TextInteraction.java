package ch.zhaw.pm2.racetrack.game;

import ch.zhaw.pm2.racetrack.game.logic.Config;
import ch.zhaw.pm2.racetrack.game.logic.Track;
import org.beryx.textio.IntInputReader;
import org.beryx.textio.PropertiesConstants;
import org.beryx.textio.StringInputReader;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

import java.io.File;
import java.util.ArrayList;

/**
 * This class handles all the Interaction with the Player
 * This includes reading and writing on the textTerminal
 *
 * @author outitbad
 * @date 26.03.2021
 */
public class TextInteraction {
    private static TextInteraction instance;
    private final TextTerminal<?> textTerminal;
    private final IntInputReader intInputReader;
    private final StringInputReader stringInputReader;

    /**
     * Inits the text interaction object and adjusts the text terminal
     */
    public TextInteraction() {
        TextIO textIO = TextIoFactory.getTextIO();
        textTerminal = textIO.getTextTerminal();
        intInputReader = textIO.newIntInputReader();
        stringInputReader = textIO.newStringInputReader();
        // configure terminal
        textTerminal.getProperties().setPaneWidth(1500);
        textTerminal.getProperties().setPaneHeight(1000);
        textTerminal.getProperties().put(PropertiesConstants.PROP_PROMPT_COLOR, "white");
        textTerminal.getProperties().put(PropertiesConstants.PROP_INPUT_COLOR, "cyan");
        textTerminal.getProperties().put(PropertiesConstants.PROP_PROMPT_FONT_FAMILY, "Courier");
        textTerminal.setBookmark("MAIN");
    }

    /**
     * Implements the Singleton Pattern
     * Every class can get an Instance at any time
     *
     * @return the one and only TextInteraction Instance
     */
    public static TextInteraction getInstance() {
        if (TextInteraction.instance == null) {
            TextInteraction.instance = new TextInteraction();
        }
        return TextInteraction.instance;
    }

    /**
     * Ask the player which track he wants to play on
     *
     * @param availableTracks a list with the available tracks
     * @return the number of the track
     */
    public int askThePlayerWhichTrack(ArrayList<File> availableTracks) {
        textTerminal.println("Type the number of the Track that you wanna use:");
        for (int numberOfTrack = 1; numberOfTrack <= availableTracks.size(); numberOfTrack++) {
            textTerminal.println(numberOfTrack + ". " + availableTracks.get(numberOfTrack - 1));
        }
        return intInputReader.read();
    }

    /**
     * Tell the player that the file of the track was not found
     */
    public void tellPlayerFileNotFound() {
        textTerminal.println("The File of your Track was not found, please try again or use another Track!");
        textTerminal.println();
    }

    /**
     * Tell the player that the picked strategy was not found
     */
    public void tellPlayerStrategyNotFound() {
        textTerminal.println("The strategy was not found, please try again or use another strategy!");
        textTerminal.println();
    }

    /**
     * Tell the player that the picked move was not found
     */
    public void tellPlayerMoveNotFound() {
        textTerminal.println("The move was not found, please try again or use another move!");
        textTerminal.println();
    }

    /**
     * Ask the player which strategy he wants to use for his car
     *
     * @param strategyTypes a list with the available strategys
     * @param carId         the id of the car
     * @return the number of the strategy
     */
    public int askPlayerWhichStrategy(ArrayList<Config.StrategyType> strategyTypes, char carId) {
        textTerminal.println("Type the number of the Strategy that you wanna use for your car: " + carId);
        for (int numberOfStrategy = 1; numberOfStrategy <= strategyTypes.size(); numberOfStrategy++) {
            textTerminal.println(numberOfStrategy + ". " + strategyTypes.get(numberOfStrategy - 1));
        }

        return intInputReader.read();
    }

    /**
     * This Method asks the player for the next move and displays the grid with the possibilities.
     *
     * @return the name of the direction the player wants to go
     * @author peterju1
     */
    public String askPlayerForNextMove() {
        String ask = "Where do you want to go?";
        String grid = " _______________________________________________\n|               |              |                |\n| UP_LEFT   (7) | UP       (8) | UP_RIGHT   (9) |" +
            "\n|_______________|______________|________________|\n|               |              |                |\n| LEFT      (4) | NONE     (5) | RIGHT      (6) |" +
            "\n|_______________|______________|________________|\n|               |              |                |\n| DOWN_LEFT (1) | DOWN     (2) | DOWN_RIGHT (3) |" +
            "\n|_______________|______________|________________|" +
            "\nPress 'Q' to quit the game";

        textTerminal.println(ask + "\n\n" + grid);
        String directionString = stringInputReader.read();

        return directionString.toUpperCase();

    }

    /**
     * Tell the player that the given MoveInput was invalid
     */
    public void tellPlayerInvalidMoveInput() {
        textTerminal.println("This Input is not valid! Try again...");
        textTerminal.println();
    }

    /**
     * Prints the whole track
     *
     * @param track the track which should get printed
     */
    public void printTrack(Track track) {
        textTerminal.resetToBookmark("MAIN");
        textTerminal.print(track.toString());
    }

    /**
     * Displays the win message
     *
     * @param carID which car has won?
     */
    public void tellPlayerHeHasWon(char carID) {
        textTerminal.println("Congratulation, the car: " + carID + " has won the Game!");
    }

    /**
     * Asks the player which move should be used
     *
     * @param availableMoves List of all available moves
     * @return the chosen move
     */
    public int askThePlayerWhichMove(ArrayList<File> availableMoves) {
        textTerminal.println("Type the number of the move that you wanna use:");
        for (int numberOfMove = 1; numberOfMove <= availableMoves.size(); numberOfMove++) {
            textTerminal.println(numberOfMove + ". " + availableMoves.get(numberOfMove - 1));
        }
        return intInputReader.read();
    }


    /**
     * Tell the player that the given track files is not formatted properly
     */
    public void tellPlayerInvalidTrackFormat() {
        textTerminal.println("There was an error with the File that you choose, these could be the problems");
        textTerminal.println("1. Not all track lines have the same length\n2. The file contains no track lines (grid height is 0)\n" +
            "3. The file contains more than " + Config.MAX_CARS + " cars");
    }

    /**
     * Print which car can move now
     *
     * @param carName which car can move now?
     */
    public void printCurrentCar(char carName) {
        textTerminal.println("It's car " + carName + "'s turn! ");
    }

    /**
     * Quits the terminal
     */
    public void quit() {
        textTerminal.dispose();
    }

    /**
     * Ask the player if he wants to play another game
     *
     * @return if he wants to play another game
     */
    public String askPlayerOneMoreGame() {
        textTerminal.println("Do you want to play another game? y / n");
        return stringInputReader.read();
    }

}
