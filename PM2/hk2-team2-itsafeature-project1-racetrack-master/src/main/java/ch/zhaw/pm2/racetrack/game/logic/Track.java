package ch.zhaw.pm2.racetrack.game.logic;

import ch.zhaw.pm2.racetrack.exceptions.InvalidTrackFormatException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.CharBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * This class represents the racetrack board.
 *
 * <p>The racetrack board consists of a rectangular grid of 'width' columns and 'height' rows.
 * The zero point of he grid is at the top left. The x-axis points to the right and the y-axis points downwards.</p>
 * <p>Positions on the track grid are specified using {@link PositionVector} objects. These are vectors containing an
 * x/y coordinate pair, pointing from the zero-point (top-left) to the addressed space in the grid.</p>
 *
 * <p>Each position in the grid represents a space which can hold an enum object of type {@link Config.SpaceType}.<br>
 * Possible Space types are:
 * <ul>
 *  <li>WALL : road boundary or off track space</li>
 *  <li>TRACK: road or open track space</li>
 *  <li>FINISH_LEFT, FINISH_RIGHT, FINISH_UP, FINISH_DOWN :  finish line spaces which have to be crossed
 *      in the indicated direction to winn the race.</li>
 * </ul>
 * <p>Beside the board the track contains the list of cars, with their current state (position, velocity, crashed,...)</p>
 *
 * <p>At initialization the track grid data is read from the given track file. The track data must be a
 * rectangular block of text. Empty lines at the start are ignored. Processing stops at the first empty line
 * following a non-empty line, or at the end of the file.</p>
 * <p>Characters in the line represent SpaceTypes. The mapping of the Characters is as follows:</p>
 * <ul>
 *   <li>WALL : '#'</li>
 *   <li>TRACK: ' '</li>
 *   <li>FINISH_LEFT : '&lt;'</li>
 *   <li>FINISH_RIGHT: '&gt;'</li>
 *   <li>FINISH_UP   : '^;'</li>
 *   <li>FINISH_DOWN: 'v'</li>
 *   <li>Any other character indicates the starting position of a car.<br>
 *       The character acts as the id for the car and must be unique.<br>
 *       There are 1 to {@link Config#MAX_CARS} allowed. </li>
 * </ul>
 *
 * <p>All lines must have the same length, used to initialize the grid width).
 * Beginning empty lines are skipped.
 * The the tracks ends with the first empty line or the file end.<br>
 * An {@link InvalidTrackFormatException} is thrown, if
 * <ul>
 *   <li>not all track lines have the same length</li>
 *   <li>the file contains no track lines (grid height is 0)</li>
 *   <li>the file contains more than {@link Config#MAX_CARS} cars</li>
 * </ul>
 *
 * <p>The Track can return a String representing the current state of the race (including car positons)</p>
 *
 * @author defiljon
 * @date 26.03.2021
 */
public class Track {

    public static final char CRASH_INDICATOR = 'X';
    private static File trackFile;
    private final char[][] trackData;
    private final ArrayList<Car> cars = new ArrayList<>();
    private final Config config = new Config();
    private int currentCarIndex = 0;

    /**
     * Initialize a Track from the given track file.
     *
     * @param trackFile Reference to a file containing the track data
     * @throws FileNotFoundException       if the given track file could not be found
     * @throws InvalidTrackFormatException if the track file contains invalid data (no tracklines, ...)
     */
    public Track(File trackFile) throws FileNotFoundException, InvalidTrackFormatException {
        Track.trackFile = trackFile;  // save trackFile in a static var
        trackData = getTrackData();
        initTrack();
    }

    public static File getTrackFile() {
        return trackFile;
    }

    /**
     * Read the file saved in a static var and returns a 2 dimensional array with the data
     *
     * @return track data
     * @throws FileNotFoundException if file was not found
     */
    public char[][] getTrackData() throws FileNotFoundException {
        String fileContent;
        try {
            fileContent = Files.readString(Paths.get(config.getTrackDirectory().getName(), Track.trackFile.getName())).strip();
        } catch (IOException e) {
            throw new FileNotFoundException();
        }
        char[][] data = new char[fileContent.split("\n").length][];

        int lineIndex = 0;
        for (String line : fileContent.split("\n")) {
            data[lineIndex] = line.strip().toCharArray();
            lineIndex++;
        }
        return data;
    }

    private void initTrack() throws InvalidTrackFormatException {
        // if the lines in the track file don't all have the same size
        if (!Arrays.stream(trackData).allMatch(arrayLine -> arrayLine.length == trackData[0].length)) {
            throw new InvalidTrackFormatException("Every line of the track must have the same size.");
        }
        // if track doesn't contain a finish line
        if (!Arrays.stream(trackData).anyMatch(arrayLine -> CharBuffer.wrap(arrayLine).chars().anyMatch(character ->
            character == Config.SpaceType.FINISH_UP.value ||
                character == Config.SpaceType.FINISH_LEFT.value ||
                character == Config.SpaceType.FINISH_DOWN.value ||
                character == Config.SpaceType.FINISH_RIGHT.value))) {
            throw new InvalidTrackFormatException("Track doesn't contain a finish line.");
        }

        // instantiate the cars
        for (int lineIndex = 0; lineIndex < trackData.length; lineIndex++) {
            for (int charIndex = 0; charIndex < trackData[lineIndex].length; charIndex++) {
                // if character is not in the SpaceType values -> create car
                if (!Arrays.stream(Config.SpaceType.values()).map(e -> e.value).collect(Collectors.toList()).contains(trackData[lineIndex][charIndex])) {
                    cars.add(new Car(trackData[lineIndex][charIndex], new PositionVector(charIndex, lineIndex)));
                    trackData[lineIndex][charIndex] = Config.SpaceType.TRACK.value;  // we need to "detach" the car from the track
                }
            }
        }
        if (cars.isEmpty() || cars.size() > Config.MAX_CARS) {
            throw new InvalidTrackFormatException("Track must contain at least one car and not more than 9.");
        }
    }

    /**
     * Return the type of space at the given position.
     * If the location is outside the track bounds, it is considered a wall.
     *
     * @param position The coordinates of the position to examine
     * @return The type of track position at the given location
     */
    public Config.SpaceType getSpaceType(PositionVector position) {
        Config.SpaceType spaceType = Config.SpaceType.WALL;
        try {
            char ch = trackData[position.getY()][position.getX()];
            Optional<Config.SpaceType> candidate = Arrays.stream(Config.SpaceType.values()).filter(e -> e.value == ch).findFirst();
            spaceType = candidate.orElse(spaceType);
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }
        return spaceType;
    }

    /**
     * Return the number of cars.
     *
     * @return Number of cars
     */
    public int getCarCount() {
        return cars.size();
    }

    /**
     * Get instance of specified car.
     *
     * @param carIndex The zero-based carIndex number
     * @return The car instance at the given index
     */
    public Car getCar(int carIndex) throws IllegalArgumentException {
        try {
            return cars.get(carIndex);
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Get the id of the specified car.
     *
     * @param carIndex The zero-based carIndex number
     * @return A char containing the id of the car
     */
    public char getCarId(int carIndex) throws IllegalArgumentException {
        try {
            return cars.get(carIndex).getId();
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Get the position of the specified car.
     *
     * @param carIndex The zero-based carIndex number
     * @return A PositionVector containing the car's current position
     */
    public PositionVector getCarPos(int carIndex) throws IllegalArgumentException {
        try {
            return cars.get(carIndex).getPosition();
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Get the velocity of the specified car.
     *
     * @param carIndex The zero-based carIndex number
     * @return A PositionVector containing the car's current velocity
     */
    public PositionVector getCarVelocity(int carIndex) throws IllegalArgumentException {
        try {
            return cars.get(carIndex).getVelocity();
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Gets character at the given position.
     * If there is a crashed car at the position, {@link #CRASH_INDICATOR} is returned.
     *
     * @param y            position Y-value
     * @param x            position X-vlaue
     * @param currentSpace char to return if no car is at position (x,y)
     * @return character representing position (x,y) on the track
     */
    public char getCharAtPosition(int x, int y, Config.SpaceType currentSpace) {
        Optional<Car> carAtPos = cars.stream().filter(Car -> Car.getPosition().equals(new PositionVector(x, y))).findFirst();
        // carAtPos can be null | if crashed return CRASH else getId | if carAtPos is null return currentSpace value
        return carAtPos.map(car -> car.isCrashed() ? CRASH_INDICATOR : car.getId()).orElse(currentSpace.value);
    }

    /**
     * Return a String representation of the track, including the car locations.
     *
     * @return A String representation of the track
     */
    public String toString() {
        StringBuilder finalString = new StringBuilder();

        int yCoord = 0;
        for (char[] line : trackData) {
            StringBuilder builder = new StringBuilder(line.length);

            int xCoord = 0;
            for (char ch : line) {
                int finalX = xCoord; // needed for lambda
                int finalY = yCoord;
                Optional<Car> carAtPos = cars.stream().filter(Car -> Car.getPosition().equals(new PositionVector(finalX, finalY))).findFirst();
                if (carAtPos.isPresent()) {
                    // if car is not crashed, append the car id
                    if (!carAtPos.get().isCrashed()) {
                        builder.append(carAtPos.get().getId());
                    } else {
                        builder.append(CRASH_INDICATOR);
                    }
                } else {
                    builder.append(ch);
                }
                xCoord++;
            }
            yCoord++;
            finalString.append(builder.toString()).append("\n");
        }
        return finalString.append("\n").toString();
    }

    /**
     * @return an ArrayList containing all Cars
     */
    public ArrayList<Car> getAllCars() {
        return cars;
    }

    /**
     * @return the carIndex of the car which currently is moving
     */
    public int getCurrentCarIndex() {
        return this.currentCarIndex;
    }

    /**
     * Sets the currentCarIndex to the next, still active, car
     * Checks if the next car is not crashed
     */
    public void switchToNextActiveCar() {
        boolean carNotCrashed = false;
        while (!carNotCrashed) {
            if (currentCarIndex == getCarCount() - 1) {
                currentCarIndex = 0;
            } else {
                currentCarIndex++;
            }
            if (!getCar(currentCarIndex).isCrashed()) {
                carNotCrashed = true;
            }
        }
    }
}

