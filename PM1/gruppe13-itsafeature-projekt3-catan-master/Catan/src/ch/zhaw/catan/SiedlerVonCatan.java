package ch.zhaw.catan;

import org.beryx.textio.IntInputReader;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

/**
 * This class is the mainClass of this project
 * The main methods constructs the SiedlerGameClass, starts the SettlementPhase and runs the mainGame
 *
 * @author Silvan
 */
public class SiedlerVonCatan {

    /**
     * The main class of this project
     *
     * @param args of the main method
     */
    public static void main(String[] args) {
        int numPlayers = 0;
        int winPoints;
        SiedlerGame siedlerGame = null;

        //Creating TextIO Object & new InputReader
        TextIO textIO = TextIoFactory.getTextIO();
        TextTerminal<?> textTerminal = textIO.getTextTerminal();
        IntInputReader intInputReader = textIO.newIntInputReader();

        //Make the terminal bigger
        textTerminal.getProperties().setPaneWidth(2000);
        textTerminal.getProperties().setPaneHeight(1500);

        boolean validInput = false;
        while (!validInput) {

            //Ask the user for init values
            textTerminal.println("Enter number of players:");
            numPlayers = intInputReader.read();

            textTerminal.println("Enter required points to win:");
            winPoints = intInputReader.read();

            validInput = true;

            //Init the siedlerGameClass
            try {
                siedlerGame = new SiedlerGame(winPoints, numPlayers);
            } catch (IllegalArgumentException illegalArgumentException) {
                textTerminal.println(illegalArgumentException.getMessage());
                textTerminal.println("");
                validInput = false;
            }
        }

        //Start the settlementPhase
        SettlementPhase settlementPhase = new SettlementPhase(siedlerGame, numPlayers, textIO);
        settlementPhase.runSettlementPhase();

        //Start the mainGame
        MainGamePhase mainGamePhase = new MainGamePhase(textIO, siedlerGame);
        mainGamePhase.gameRunning();

        //close the game
        textTerminal.dispose();
    }
}
