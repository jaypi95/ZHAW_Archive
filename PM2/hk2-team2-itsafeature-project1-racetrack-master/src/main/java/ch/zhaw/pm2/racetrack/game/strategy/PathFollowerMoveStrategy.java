package ch.zhaw.pm2.racetrack.game.strategy;

import ch.zhaw.pm2.racetrack.exceptions.QuitGameException;
import ch.zhaw.pm2.racetrack.game.TextInteraction;
import ch.zhaw.pm2.racetrack.game.logic.Config;
import ch.zhaw.pm2.racetrack.game.logic.Game;
import ch.zhaw.pm2.racetrack.game.logic.PositionVector;
import ch.zhaw.pm2.racetrack.game.logic.PositionVector.Direction;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * The PathFollowerMoveStrategy class determines the next move based on a file containing points on a path.
 *
 * @author baachsil
 * @date 26.03.2021
 */
public class PathFollowerMoveStrategy extends Strategy implements MoveStrategy {
    private final Config config;
    private final TextInteraction textInteraction;
    private final List<PositionVector> waypointList;
    PositionVector lastVector;
    private List<PositionVector.Direction> directionList;
    private int listPosition;

    /**
     * Constructor for this Class
     */
    public PathFollowerMoveStrategy(PositionVector start) {
        config = new Config();
        listPosition = 0;
        textInteraction = TextInteraction.getInstance();
        waypointList = new ArrayList<>();
        directionList = new ArrayList<>();
        lastVector = new PositionVector();

        readFile(askForFile(waypointList()));
        directionList = generateDirections(calculateFullPath(waypointList, start), start);
    }

    /**
     * Generates all Directions which needs to be performed in order to get to the finish line
     *
     * @param fields all the fields which the car needs to go to
     * @param start  the start Point of the car
     * @return A list of directions
     */
    public static List<PositionVector.Direction> generateDirections(List<PositionVector> fields, PositionVector start) {
        List<PositionVector.Direction> directionsList = new ArrayList<>();
        List<PositionVector> fullPath = calculateFullPath(fields, start);
        PositionVector currentVelocity = Direction.NONE.vector;

        for (int counter = 1; counter < fullPath.size(); counter++) {
            PositionVector startPos = fullPath.get(counter - 1);
            PositionVector endPos = fullPath.get(counter);

            //in order to reach the endPos we need to calc the goalSpeed
            PositionVector goalSpeed = PositionVector.subtract(endPos, startPos);

            Optional<PositionVector.Direction> directionToStore = findMoveResultingInVector(currentVelocity, goalSpeed);

            // was a direction found?
            if (directionToStore.isPresent()) {
                directionsList.add(directionToStore.get());
            } else {
                // we need to stop at this position
                directionToStore = findMoveResultingInVector(currentVelocity, Direction.NONE.vector);
                if (directionToStore.isPresent()) {
                    directionsList.add(directionToStore.get());
                    directionToStore = findMoveResultingInVector(Direction.NONE.vector, goalSpeed);
                    if (directionToStore.isPresent()) {
                        directionsList.add(directionToStore.get());
                    } else {
                        throw new RuntimeException("Error while trying to generate the Direction. Can't reach a Field");
                    }
                } else {
                    throw new RuntimeException();
                }

            }
            currentVelocity = goalSpeed;
        }

        return directionsList;
    }

    /**
     * Find a direction that we can add to the current velocity to get the target velocity
     *
     * @param currentVelocity current velocity
     * @param targetVelocity  target velocity
     * @return target velocity if found, otherwise more than one move is necessary -> Optional is empty
     */
    private static Optional<PositionVector.Direction> findMoveResultingInVector(PositionVector currentVelocity, PositionVector targetVelocity) {
        return Arrays.stream(PositionVector.Direction.values())
            .filter(direction -> PositionVector.add(currentVelocity, direction.vector).equals(targetVelocity)).findFirst();
    }

    /**
     * Generates from the wayPoints a list with all Points in it
     *
     * @param waypointList the List with all waypoints
     * @param start        the start Position of the Car
     * @return list containing waypoints
     */
    public static List<PositionVector> calculateFullPath(List<PositionVector> waypointList, PositionVector start) {
        List<PositionVector> fullPath = new ArrayList<>();
        PositionVector from = start;
        for (PositionVector to : waypointList) {
            fullPath.addAll(Game.calculatePath(from, to));
            from = to;
        }

        return fullPath.stream().distinct().collect(Collectors.toList());
    }

    /**
     * @return a Direction in which the Car should move next
     * @throws RuntimeException if the user wishes to quit the game
     */
    @Override
    public Direction nextMove() {
        Direction direction;
        try {
            direction = directionList.get(listPosition);
            listPosition++;

            Thread.sleep(200);

        } catch (IllegalArgumentException | InterruptedException e) {
            throw new RuntimeException();
        }
        return direction;
    }

    /**
     * Reads the instruction File
     *
     * @param filename the filename
     */
    private void readFile(String filename) {
        try {
            File file = new File(config.getFollowerDirectory() + "/" + filename);

            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {

                String line = scanner.nextLine();
                if (!line.isBlank()) {
                    String numbers = line.replaceAll("[^\\d\\,]", "");
                    String[] coordinates = numbers.split(",");
                    int vectorX = Integer.parseInt(coordinates[0]);
                    int vectorY = Integer.parseInt(coordinates[1]);
                    PositionVector fileWaypoint = new PositionVector(vectorX, vectorY);
                    waypointList.add(fileWaypoint);
                }
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        }
    }

    /**
     * Asks the user for a moveFile
     *
     * @param availableWaypoints a List with all available move Files
     * @return the name of the picked File
     */
    private String askForFile(ArrayList<File> availableWaypoints) {
        boolean fileFound = false;
        String move = "";
        while (!fileFound) {
            try {
                int numberOfMove = textInteraction.askThePlayerWhichMove(availableWaypoints);
                move = availableWaypoints.get(numberOfMove - 1).getName();
                //validateFile(move);
                fileFound = true;
            } catch (IndexOutOfBoundsException e) {
                textInteraction.tellPlayerMoveNotFound();
            }
        }
        return move;
    }

    /**
     * Generates a List with all Files in the MoveDirectory
     * Ignores temporary Files
     *
     * @return a List with all Filenames
     */
    private ArrayList<File> waypointList() {
        ArrayList<File> waypointList = new ArrayList<>();
        File[] availableWaypoints = config.getFollowerDirectory().listFiles();
        if (availableWaypoints == null) {
            availableWaypoints = new File[]{};
        }

        for (File move : availableWaypoints) {
            if (!move.getName().startsWith(".")) {
                waypointList.add(move);
            }
        }
        return waypointList;
    }

}

