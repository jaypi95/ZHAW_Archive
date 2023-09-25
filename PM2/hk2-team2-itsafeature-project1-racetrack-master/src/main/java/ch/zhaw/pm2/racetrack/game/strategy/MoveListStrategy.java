package ch.zhaw.pm2.racetrack.game.strategy;

import ch.zhaw.pm2.racetrack.exceptions.QuitGameException;
import ch.zhaw.pm2.racetrack.game.TextInteraction;
import ch.zhaw.pm2.racetrack.game.logic.Config;
import ch.zhaw.pm2.racetrack.game.logic.PositionVector.Direction;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Accelerate based on a file with commands
 *
 * @author peterju1
 * @date 26.03.2021
 */
public class MoveListStrategy extends Strategy implements MoveStrategy {

    private final Config config;
    private final ArrayList<String> fileList;
    private final TextInteraction textInteraction;
    private int listPosition;

    /**
     * Constructor of this class
     */
    public MoveListStrategy() {
        config = new Config();
        listPosition = 0;
        fileList = new ArrayList<>();
        textInteraction = TextInteraction.getInstance();

        readFile(askForFile(moveList()));
    }

    /**
     * @return a Direction in which the Car should move next
     * @throws QuitGameException if the user wishes to quit the game
     */
    @Override
    public Direction nextMove() {
        try {
            Direction direction = Direction.valueOf(fileList.get(listPosition));
            listPosition++;

            //Sleep so you can watch the game
            Thread.sleep(1000);
            return direction;
        } catch (IllegalArgumentException | InterruptedException e) {
            throw new RuntimeException();
        }
    }

    /**
     * Reads the instruction File
     *
     * @param filename the filename
     */
    private void readFile(String filename) {
        try {
            File file = new File(config.getMoveDirectory() + "/" + filename);

            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {

                String line = scanner.nextLine();
                if (!line.isBlank()) {
                    fileList.add(line);
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        }
    }

    /**
     * Asks the user for a moveFile
     *
     * @param availableMoves a List with all available move Files
     * @return the name of the picked File
     */
    private String askForFile(ArrayList<File> availableMoves) {
        boolean fileFound = false;
        String move = "";
        while (!fileFound) {
            try {
                int numberOfMove = textInteraction.askThePlayerWhichMove(availableMoves);
                move = availableMoves.get(numberOfMove - 1).getName();
                validateFile(move);
                fileFound = true;
            } catch (IndexOutOfBoundsException e) {
                textInteraction.tellPlayerMoveNotFound();
            }
        }
        return move;
    }

    /**
     * Validates if the contents of the file is valid before starting the game
     *
     * @param filename filename
     */
    private void validateFile(String filename) {
        File file = new File(config.getMoveDirectory() + "/" + filename);
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                //Check if file contents are valid
                try {
                    String line = scanner.nextLine();
                    if (!line.isBlank()) {
                        Direction test = Direction.valueOf(line);
                    }
                } catch (RuntimeException e) {
                    throw new RuntimeException();
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        }
    }

    /**
     * Generates a List with all Files in the MoveDirectory
     * Ignores temporary Files
     *
     * @return a List with all Filenames
     */
    private ArrayList<File> moveList() {
        ArrayList<File> moveList = new ArrayList<>();
        File[] availableMoves = config.getMoveDirectory().listFiles();
        if (availableMoves == null) {
            availableMoves = new File[]{};
        }

        for (File move : availableMoves) {
            if (!move.getName().startsWith(".")) {
                moveList.add(move);
            }
        }
        return moveList;
    }
}


