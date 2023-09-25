package ch.zhaw.pm2.racetrack;

import ch.zhaw.pm2.racetrack.exceptions.InvalidTrackFormatException;
import ch.zhaw.pm2.racetrack.game.logic.Config;
import ch.zhaw.pm2.racetrack.game.logic.Game;
import ch.zhaw.pm2.racetrack.game.logic.PositionVector;
import ch.zhaw.pm2.racetrack.game.logic.Track;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for the Game class
 *
 * @author peterju1
 * @date 26.03.2021
 */
public class GameTest {

    private final Config config;
    private Track track = null;
    private File trackFile;
    private Game game;
    private PositionVector oldPosition;
    private ArrayList<String> fileList;

    public GameTest() {
        this.config = new Config();
    }


    @BeforeEach
    private void setUp() {

        trackFile = new File(config.getTrackDirectory() + "/challenge.txt");
        try {
            track = new Track(trackFile);
        } catch (FileNotFoundException | InvalidTrackFormatException e) {
            e.printStackTrace();
        }
        game = new Game(track);
        oldPosition = game.getCarPosition(game.getCurrentCarIndex());

    }

    /**
     * Test if the first car has Index 0
     */
    @Test
    public void testGetCurrentCarIndexFirstCar() {
        assertEquals(0, game.getCurrentCarIndex());
    }

    /**
     * Test if next car has Index 1
     */
    @Test
    public void testGetCurrentCarIndexSecondCar() {
        game.switchToNextActiveCar();
        assertEquals(1, game.getCurrentCarIndex());
    }

    /**
     * Test if first car in challenge track has ID corresponding to the letter a
     */
    @Test
    public void testGetCarIdValid() {
        assertEquals(97, game.getCarId(0));
    }

    /**
     * Test if second car in challenge track has NOT the ID corresponding to the letter a
     */
    @Test
    public void testGetCarIdInvalid() {
        assertNotEquals(97, game.getCarId(1));
    }

    /**
     * Test if getCarPosition returns the correct values for the start position of the first car
     */
    @Test
    public void testGetCarPositionValid() {
        PositionVector expectedPosition = new PositionVector(24, 22);

        assertEquals(expectedPosition, oldPosition);
    }

    /**
     * Test if getCarPosition does not return the values for the start position of the first car when switching to the second car.
     */
    @Test
    public void testGetCarPositionInvalid() {
        PositionVector expectedPosition = new PositionVector(24, 22);
        oldPosition = game.getCarPosition(1);

        assertNotEquals(expectedPosition, oldPosition);
    }

    /**
     * Tests if correct winner gets returned when all other cars crash
     */
    @Test
    public void testGetWinnerAfterCrash() {
        PositionVector.Direction direction = PositionVector.Direction.valueOf("UP");
        game.doCarTurn(direction);
        game.switchToNextActiveCar();
        assertEquals(game.getCurrentCarIndex(), game.getWinner());
    }

    /**
     * Tests if correct winner gets returned when it drives over the finishing line
     * Method runs the moves by itself because running the strategies without opening a terminal was not possible.
     */
    @Test
    public void testGetWinnerAfterFinishing() {
        fileList = new ArrayList<>();
        readFile("challenge-car-a.txt");
        for (String file : fileList) {
            try {
                if (!file.isEmpty()) {
                    PositionVector.Direction direction = PositionVector.Direction.valueOf(file);
                    game.doCarTurn(direction);
                }
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException();
            }
        }
        assertEquals(game.getCurrentCarIndex(), game.getWinner());
    }

    /**
     * Test if car was moved to correct new expected position
     */
    @Test
    public void testDoCarTurnMoveCar() {
        PositionVector.Direction direction = PositionVector.Direction.valueOf("RIGHT");
        game.doCarTurn(direction);
        PositionVector newPosition = game.getCarPosition(game.getCurrentCarIndex());

        PositionVector expectedPosition = new PositionVector(oldPosition.getX() + 1, oldPosition.getY());

        assertEquals(expectedPosition, newPosition);
    }

    /**
     * turn the car 180° in the opposite direction which should make it come to a stop
     */
    @Test
    public void testDoCarTurnStopCar() {
        PositionVector.Direction direction = PositionVector.Direction.valueOf("RIGHT");
        game.doCarTurn(direction);
        PositionVector expectedPosition = game.getCarPosition(game.getCurrentCarIndex());

        direction = PositionVector.Direction.valueOf("LEFT");
        game.doCarTurn(direction);
        PositionVector newPosition = game.getCarPosition(game.getCurrentCarIndex());

        assertEquals(expectedPosition, newPosition);
    }

    /**
     * Test acceleration (two subsequent moves in the same direction should result in a 3 times longer section moved compared to a single move)
     */
    @Test
    public void testDoCarTurnAccelerate() {
        PositionVector.Direction direction = PositionVector.Direction.valueOf("RIGHT");
        game.doCarTurn(direction);
        game.doCarTurn(direction);
        PositionVector newPosition = game.getCarPosition(game.getCurrentCarIndex());
        PositionVector expectedPosition = new PositionVector(oldPosition.getX() + 3, oldPosition.getY());

        assertEquals(expectedPosition, newPosition);
    }

    /**
     * Test turning into a curve while already travelling at high speed
     */
    @Test
    public void testDoCarTurnMakeActualTurn() {
        PositionVector.Direction direction = PositionVector.Direction.valueOf("RIGHT");
        game.doCarTurn(direction);
        game.doCarTurn(direction);

        direction = PositionVector.Direction.valueOf("DOWN");
        game.doCarTurn(direction);
        PositionVector newPosition = game.getCarPosition(game.getCurrentCarIndex());
        PositionVector expectedPosition = new PositionVector(oldPosition.getX() + 5, oldPosition.getY() + 1);

        assertEquals(expectedPosition, newPosition);
    }

    /**
     * Test if car does not crash when it stays at the same position (it cannot crash again itself)
     */
    @Test
    public void testDoCarTurnStandStill() {
        PositionVector.Direction direction = PositionVector.Direction.valueOf("NONE");
        game.doCarTurn(direction);
        PositionVector newPosition = game.getCarPosition(game.getCurrentCarIndex());

        assertEquals(oldPosition, newPosition);
    }

    /**
     * Test if car does not move anymore after it crashes into a wall
     * Since the first car starts right against up to a wall it should not move at all but instead crash immediately
     */
    @Test
    public void testDoCarTurnCrashIntoWall() {
        PositionVector.Direction direction = PositionVector.Direction.valueOf("UP");
        game.doCarTurn(direction);
        PositionVector newPosition = game.getCarPosition(game.getCurrentCarIndex());

        assertEquals(oldPosition, newPosition);
    }

    /**
     * Test if car does not move anymore after it crashes into another car
     */
    @Test
    public void testDoCarTurnCrashIntoOtherCar() {
        PositionVector.Direction direction = PositionVector.Direction.valueOf("DOWN");
        game.doCarTurn(direction);
        game.doCarTurn(direction);
        PositionVector newPosition = game.getCarPosition(game.getCurrentCarIndex());
        game.doCarTurn(direction);
        PositionVector expectedPosition = game.getCarPosition(game.getCurrentCarIndex());
        assertEquals(expectedPosition, newPosition);
    }

    /**
     * Tests if the switch method actually switches to the next car.
     */
    @Test
    public void testSwitchToNextActiveCar() {
        int oldCar = game.getCurrentCarIndex();
        game.switchToNextActiveCar();
        int newCar = game.getCurrentCarIndex();

        assertNotEquals(oldCar, newCar);
        assertEquals(0, oldCar);
        assertEquals(1, newCar);
    }

    /**
     * Tests if the switch method actually switches to the next car.
     */
    @Test
    public void testSwitchToBackToFirstCar() {
        int oldCar = game.getCurrentCarIndex();
        game.switchToNextActiveCar();
        game.switchToNextActiveCar();
        int newCar = game.getCurrentCarIndex();

        assertEquals(oldCar, newCar);

    }

    /**
     * This method compares a list of position vectors which together form a vertical path with the calculated path / list of position vectors.
     */
    @Test
    public void testCalculateVerticalVector() {
        PositionVector start = new PositionVector(1, 1);
        PositionVector end = new PositionVector(1, 20);

        List<PositionVector> comparisonList = new ArrayList<>(Arrays.asList(
            new PositionVector(1, 1),
            new PositionVector(1, 2),
            new PositionVector(1, 3),
            new PositionVector(1, 4),
            new PositionVector(1, 5),
            new PositionVector(1, 6),
            new PositionVector(1, 7),
            new PositionVector(1, 8),
            new PositionVector(1, 9),
            new PositionVector(1, 10),
            new PositionVector(1, 11),
            new PositionVector(1, 12),
            new PositionVector(1, 13),
            new PositionVector(1, 14),
            new PositionVector(1, 15),
            new PositionVector(1, 16),
            new PositionVector(1, 17),
            new PositionVector(1, 18),
            new PositionVector(1, 19),
            new PositionVector(1, 20)
        ));

        assertEquals(comparisonList, Game.calculatePath(start, end));
    }

    /**
     * A horizontal vector instead of a vertical one
     */
    @Test
    public void testCalculateHorizontalVector() {
        PositionVector start = new PositionVector(1, 1);
        PositionVector end = new PositionVector(20, 1);

        List<PositionVector> comparisonList = new ArrayList<>(Arrays.asList(
            new PositionVector(1, 1),
            new PositionVector(2, 1),
            new PositionVector(3, 1),
            new PositionVector(4, 1),
            new PositionVector(5, 1),
            new PositionVector(6, 1),
            new PositionVector(7, 1),
            new PositionVector(8, 1),
            new PositionVector(9, 1),
            new PositionVector(10, 1),
            new PositionVector(11, 1),
            new PositionVector(12, 1),
            new PositionVector(13, 1),
            new PositionVector(14, 1),
            new PositionVector(15, 1),
            new PositionVector(16, 1),
            new PositionVector(17, 1),
            new PositionVector(18, 1),
            new PositionVector(19, 1),
            new PositionVector(20, 1)
        ));

        assertEquals(comparisonList, Game.calculatePath(start, end));
    }

    /**
     * A Path that follow a 45° path
     */
    @Test
    public void testCalculateDiagonalVector() {
        PositionVector start = new PositionVector(1, 1);
        PositionVector end = new PositionVector(20, 20);

        List<PositionVector> comparisonList = new ArrayList<>(Arrays.asList(
            new PositionVector(1, 1),
            new PositionVector(2, 2),
            new PositionVector(3, 3),
            new PositionVector(4, 4),
            new PositionVector(5, 5),
            new PositionVector(6, 6),
            new PositionVector(7, 7),
            new PositionVector(8, 8),
            new PositionVector(9, 9),
            new PositionVector(10, 10),
            new PositionVector(11, 11),
            new PositionVector(12, 12),
            new PositionVector(13, 13),
            new PositionVector(14, 14),
            new PositionVector(15, 15),
            new PositionVector(16, 16),
            new PositionVector(17, 17),
            new PositionVector(18, 18),
            new PositionVector(19, 19),
            new PositionVector(20, 20)
        ));

        assertEquals(comparisonList, Game.calculatePath(start, end));
    }

    /**
     * This one tests a path that was chosen at random to see if the error correction actually works.
     */
    @Test
    public void testCalculateRandomVector() {
        PositionVector start = new PositionVector(37, 3);
        PositionVector end = new PositionVector(48, 11);

        List<PositionVector> comparisonList = new ArrayList<>(Arrays.asList(
            new PositionVector(37, 3),
            new PositionVector(38, 4),
            new PositionVector(39, 4),
            new PositionVector(40, 5),
            new PositionVector(41, 6),
            new PositionVector(42, 7),
            new PositionVector(43, 7),
            new PositionVector(44, 8),
            new PositionVector(45, 9),
            new PositionVector(46, 10),
            new PositionVector(47, 10),
            new PositionVector(48, 11)
        ));

        assertEquals(comparisonList, Game.calculatePath(start, end));
    }

    /**
     * Testing a negative value which also should be valid
     */
    @Test
    public void testCalculateNegativeVector() {
        PositionVector start = new PositionVector(-1, -5);
        PositionVector end = new PositionVector(3, 6);

        List<PositionVector> comparisonList = new ArrayList<>(Arrays.asList(
            new PositionVector(-1, -5),
            new PositionVector(-1, -4),
            new PositionVector(0, -3),
            new PositionVector(0, -2),
            new PositionVector(0, -1),
            new PositionVector(1, 0),
            new PositionVector(1, 1),
            new PositionVector(2, 2),
            new PositionVector(2, 3),
            new PositionVector(2, 4),
            new PositionVector(3, 5),
            new PositionVector(3, 6)
        ));

        assertEquals(comparisonList, Game.calculatePath(start, end));
    }

    /**
     * Testing a 0-Vector which should result in a list with a single entry.
     */
    @Test
    public void testCalculateZeroVector() {
        PositionVector start = new PositionVector(2, 2);
        PositionVector end = new PositionVector(2, 2);

        List<PositionVector> comparisonList = new ArrayList<>(Arrays.asList(
            new PositionVector(2, 2)
        ));

        assertEquals(comparisonList, Game.calculatePath(start, end));
    }

    /**
     * Testing Vectors at the Integer Maximum
     */
    @Test
    public void testCalculateMaxVector() {
        PositionVector start = new PositionVector(Integer.MAX_VALUE - 1, 2);
        PositionVector end = new PositionVector(Integer.MAX_VALUE, 2);

        List<PositionVector> comparisonList = new ArrayList<>(Arrays.asList(
            new PositionVector(Integer.MAX_VALUE - 1, 2),
            new PositionVector(Integer.MAX_VALUE, 2)
        ));

        assertEquals(comparisonList, Game.calculatePath(start, end));
    }

    /**
     * Tests if method returns true when car has crashed into wall.
     */
    @Test
    public void testWillCarCrashWall() {
        PositionVector wallPosition = new PositionVector(oldPosition.getX(), oldPosition.getY() - 1);

        assertTrue(game.willCarCrash(game.getCurrentCarIndex(), wallPosition));
    }

    /**
     * Tests if method returns true when car has crashed into another car.
     */
    @Test
    public void testWillCarCrashOtherCar() {
        PositionVector carPosition = new PositionVector(oldPosition.getX(), oldPosition.getY() + 2);

        assertTrue(game.willCarCrash(game.getCurrentCarIndex(), carPosition));
    }

    /**
     * Tests if method returns false when car drives on track.
     */
    @Test
    public void testWillCarCrashNotCrashOnTrack() {
        PositionVector trackPosition = new PositionVector(oldPosition.getX() + 1, oldPosition.getY());

        assertFalse(game.willCarCrash(game.getCurrentCarIndex(), trackPosition));
    }

    /**
     * Helper method to run the game automatically
     *
     * @param filename the filename
     */
    private void readFile(String filename) {
        try {
            File file = new File(config.getMoveDirectory() + "/" + filename);

            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {

                fileList.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        }
    }

    /**
     * Another helper method
     * Generates a List with all Files in the MoveDirectory
     * Ignores temporary Files
     *
     * @return a List with all Filenames
     */
    private ArrayList<File> moveList() {
        ArrayList<File> moveList = new ArrayList<>();
        File[] availableMoves = config.getMoveDirectory().listFiles();
        for (File move : availableMoves) {
            if (!move.getName().startsWith(".")) {
                moveList.add(move);
            }
        }
        return moveList;
    }
}
