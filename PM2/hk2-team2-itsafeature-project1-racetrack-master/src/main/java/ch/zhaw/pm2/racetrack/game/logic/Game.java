package ch.zhaw.pm2.racetrack.game.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ch.zhaw.pm2.racetrack.game.logic.PositionVector.Direction;

/**
 * Game controller class, performing all actions to modify the game state.
 * It contains the logic to move the cars, detect if they are crashed
 * and if we have a winner.
 *
 * @author baachsil
 * @date 26.03.2021
 */
public class Game {

    public static final int NO_WINNER = -1;
    private static Track track;
    private int currentCarIndex;


    /**
     * Init of the Game Object
     *
     * @param track the track on which this object operates
     */
    public Game(Track track) {
        Game.track = track;
        currentCarIndex = 0;
    }

    /**
     * Returns all of the grid positions in the path between two positions, for use in determining line of sight.
     * Determine the 'pixels/positions' on a raster/grid using Bresenham's line algorithm.
     * (https://de.wikipedia.org/wiki/Bresenham-Algorithmus)
     * Basic steps are
     * - Detect which axis of the distance vector is longer (faster movement)
     * - for each pixel on the 'faster' axis calculate the position on the 'slower' axis.
     * Direction of the movement has to correctly considered
     *
     * @param startPosition Starting position as a PositionVector
     * @param endPosition   Ending position as a PositionVector
     * @return Intervening grid positions as a List of PositionVector's, including the starting and ending positions.
     */
    public static List<PositionVector> calculatePath(PositionVector startPosition, PositionVector endPosition) {
        int startX = startPosition.getX();
        int startY = startPosition.getY();

        int endX = endPosition.getX();
        int endY = endPosition.getY();

        int dx = Math.abs(startX - endX);
        int dy = Math.abs(startY - endY);

        int sx = startX < endX ? 1 : -1;
        int sy = startY < endY ? 1 : -1;

        int err = dx - dy;
        int e2;

        List<PositionVector> positionVectors = new ArrayList<>();

        boolean run = true;
        while (run) {
            positionVectors.add(new PositionVector(startX, startY));

            if (startX == endX && startY == endY) {
                run = false;
            }

            e2 = 2 * err;
            if (e2 > -dy) {
                err = err - dy;
                startX = startX + sx;
            }

            if (e2 < dx) {
                err = err + dx;
                startY = startY + sy;
            }
        }

        return positionVectors;
    }

    /**
     * Return the index of the current active car.
     * Car indexes are zero-based, so the first car is 0, and the last car is getCarCount() - 1.
     *
     * @return The zero-based number of the current car
     */
    public int getCurrentCarIndex() {
        return currentCarIndex;
    }

    /**
     * Get the id of the specified car.
     *
     * @param carIndex The zero-based carIndex number
     * @return A char containing the id of the car
     */
    public char getCarId(int carIndex) {
        return track.getCarId(carIndex);
    }

    /**
     * Get the position of the specified car.
     *
     * @param carIndex The zero-based carIndex number
     * @return A PositionVector containing the car's current position
     */
    public PositionVector getCarPosition(int carIndex) {
        return track.getCarPos(carIndex);
    }

    /**
     * Get the velocity of the specified car.
     *
     * @param carIndex The zero-based carIndex number
     * @return A PositionVector containing the car's current velocity
     */
    public PositionVector getCarVelocity(int carIndex) {
        return track.getCarVelocity(carIndex);
    }

    /**
     * Return the winner of the game. If the game is still in progress, returns NO_WINNER.
     *
     * @return The winning car's index (zero-based, see getCurrentCar()), or NO_WINNER if the game is still in progress
     */
    public int getWinner() {
        int winner = NO_WINNER;
        // amount of cars still rollin'
        int activeCars = (int) track.getAllCars().stream().filter(car -> !car.isCrashed()).count();

        if (activeCars != 1) {
            Optional<Car> winnerCar = track.getAllCars().stream().filter(Car::isWinner).findAny();
            winner = winnerCar.map(car -> track.getAllCars().indexOf(car)).orElse(winner);
        } else { // if there's only one car left, it won
            track.switchToNextActiveCar();
            winner = track.getCurrentCarIndex();
        }
        return winner;
    }

    /**
     * Execute the next turn for the current active car.
     * <p>This method changes the current car's velocity and checks on the path to the next position,
     * if it crashes (car state to crashed) or passes the finish line in the right direction (set winner state).</p>
     * <p>The steps are as follows</p>
     * <ol>
     *   <li>Accelerate the current car</li>
     *   <li>Calculate the path from current (start) to next (end) position
     *       (see game#calculatePath(PositionVector, PositionVector)})</li>
     *   <li>Verify for each step what space type it hits:
     *      <ul>
     *          <li>TRACK: check for collision with other car (crashed &amp; don't continue), otherwise do nothing</li>
     *          <li>WALL: car did collide with the wall - crashed &amp; don't continue</li>
     *          <li>FINISH_*: car hits the finish line - wins only if it crosses the line in the correct direction</li>
     *      </ul>
     *   </li>
     *   <li>If the car crashed or wins, set its position to the crash/win coordinates</li>
     *   <li>If the car crashed, also detect if there is only one car remaining, remaining car is the winner</li>
     *   <li>Otherwise move the car to the end position</li>
     * </ol>
     * <p>The calling method must check the winner state and decide how to go on. If the winner is different
     * than NO_WINNER, or the current car is already marked as crashed the method returns immediately.</p>
     *
     * @param acceleration A Direction containing the current cars acceleration vector (-1,0,1) in x and y direction
     *                     for this turn
     */
    public void doCarTurn(Direction acceleration) {
        //Step 1: accelerate car
        Car currentCar = track.getCar(currentCarIndex);
        currentCar.accelerate(acceleration);

        //Step 2: get the path from A to B
        ArrayList<PositionVector> positionList = (ArrayList<PositionVector>) calculatePath(currentCar.getPosition(), currentCar.nextPosition());

        // make sure that every position in positionList is clear
        // we need to start at 1 because index 0 is the car's position, and we need to skip it because it cannot crash against itself
        for (int counter = 1; counter < positionList.size(); counter++) {
            PositionVector currentPos = positionList.get(counter);
            PositionVector previousPosition = positionList.get(counter - 1);

            // if there's something at the position
            if (willCarCrash(currentCarIndex, currentPos)) {
                currentCar.crash();
                currentCar.setPosition(previousPosition); // set the crash icon at the position right before the crash
                counter = positionList.size();  // break;
            }
            // check if there is a finish line in the car's path | make sure it's crossing it from the right direction | if the previous pos Y + 1 is equal to the current pos
            // ! NEED TO CROSS AND NOT ONLY BE ON IT                                       IF CAR IS PAST THE FINISH LINE
            if ((track.getSpaceType(currentPos) == Config.SpaceType.FINISH_DOWN && currentPos.getY() - 1 == previousPosition.getY()) ||
                (track.getSpaceType(currentPos) == Config.SpaceType.FINISH_UP && currentPos.getY() + 1 == previousPosition.getY()) ||
                (track.getSpaceType(currentPos) == Config.SpaceType.FINISH_LEFT && currentPos.getX() + 1 == previousPosition.getX()) ||
                (track.getSpaceType(currentPos) == Config.SpaceType.FINISH_RIGHT && currentPos.getX() - 1 == previousPosition.getX())) {
                currentCar.incrementLoopCount();
            }
            // if car crosses finish line backwards
            if ((track.getSpaceType(currentPos) == Config.SpaceType.FINISH_DOWN && currentPos.getY() + 1 == previousPosition.getY()) ||
                (track.getSpaceType(currentPos) == Config.SpaceType.FINISH_UP && currentPos.getY() - 1 == previousPosition.getY()) ||
                (track.getSpaceType(currentPos) == Config.SpaceType.FINISH_LEFT && currentPos.getX() - 1 == previousPosition.getX()) ||
                (track.getSpaceType(currentPos) == Config.SpaceType.FINISH_RIGHT && currentPos.getX() + 1 == previousPosition.getX())) {
                currentCar.decrementLoopCount();
            }
        }
        //Step 3: Move the car if it didn't crash
        if (!currentCar.isCrashed()) {
            currentCar.move();
        }
    }

    /**
     * Switches to the next car which is still in the game. Skips crashed cars.
     */
    public void switchToNextActiveCar() {
        track.switchToNextActiveCar();
        currentCarIndex = track.getCurrentCarIndex();
    }

    /**
     * Does indicate if a car would have a crash with a WALL space or another car at the given position.
     *
     * @param carIndex The zero-based carIndex number
     * @param position A PositionVector of the possible crash position
     * @return A boolean indicator if the car would crash with a WALL or another car.
     */
    public boolean willCarCrash(int carIndex, PositionVector position) {
        // ! carIndex param is unused
        boolean willCrash = false;

        //If it's a wall => crash
        Config.SpaceType spaceType = track.getSpaceType(position);
        if (spaceType == Config.SpaceType.WALL) {
            willCrash = true;
        }

        //If it's another car => crash
        char specificPos = track.getCharAtPosition(position.getX(), position.getY(), spaceType);
        if (specificPos != spaceType.value) {
            willCrash = true;
        }

        return willCrash;
    }
}
