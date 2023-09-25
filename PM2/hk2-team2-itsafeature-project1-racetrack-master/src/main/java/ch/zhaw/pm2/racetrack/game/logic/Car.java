package ch.zhaw.pm2.racetrack.game.logic;

import ch.zhaw.pm2.racetrack.game.strategy.DoNotMoveStrategy;
import ch.zhaw.pm2.racetrack.game.strategy.MoveListStrategy;
import ch.zhaw.pm2.racetrack.game.strategy.PathFinderMoveStrategy;
import ch.zhaw.pm2.racetrack.game.strategy.PathFollowerMoveStrategy;
import ch.zhaw.pm2.racetrack.game.strategy.Strategy;
import ch.zhaw.pm2.racetrack.game.strategy.UserMoveStrategy;


/**
 * Class representing a car on the racetrack.
 * Uses {@link PositionVector} to store current position on the track grid and current velocity vector.
 * Each car has an identifier character which represents the car on the race track board.
 * Also keeps the state, if the car is crashed (not active anymore). The state can not be changed back to uncrashed.
 * The velocity is changed by providing an acceleration vector.
 * The car is able to calculate the endpoint of its next position and on request moves to it.
 *
 * @author defiljon
 * @date 26.03.2021
 */
public class Car {

    /**
     * Represents the amount of loops around the track which need to be done to win the game.
     */
    private static final int LOOP_COUNT_TO_WIN = 1;
    /**
     * Car identifier used to represent the car on the track
     */
    private final char id;
    /**
     * Current position of the car on the track grid using a {@link PositionVector}
     */
    private PositionVector position;
    /**
     * Current velocity of the car using a {@link PositionVector}
     */
    private PositionVector velocity = new PositionVector(0, 0);
    /**
     * Velocity from the last move
     */
    private PositionVector lastVelocity = new PositionVector(0, 0);
    /**
     * Represents the amount of loops around the track, which the car has done.
     * Must be 1 in order for the car to win the game.
     * Can be negative if the car crossed the finish line backwards.
     */
    private int carLoopCount = 0;

    /**
     * Indicator if the car has crashed
     */
    private boolean crashed = false;

    /**
     * Strategy which the car uses to decide where to move next
     */
    private Strategy strategy;

    /**
     * Constructor for this class
     *
     * @param id       What ID should the car use?
     * @param position Where is the startPosition for this car?
     */
    public Car(char id, PositionVector position) {
        this.id = id;
        setPosition(position);
    }

    /**
     * Return the position that will apply after the next move at the current velocity.
     * Does not complete the move, so the current position remains unchanged.
     *
     * @return Expected position after the next move
     */
    public PositionVector nextPosition() {
        return PositionVector.add(position, velocity);
    }

    /**
     * Add the specified amounts to this cars velocity.
     * The only acceleration values allowed are -1, 0 or 1 in both axis
     * There are 9 possible acceleration vectors, which are defined in {@link PositionVector.Direction}.
     * Changes only velocity, not position.
     *
     * @param acceleration A Direction vector containing the amounts to add to the velocity in x and y dimension
     */
    public void accelerate(PositionVector.Direction acceleration) {
        velocity = PositionVector.add(lastVelocity, acceleration.vector);
    }

    /**
     * Update this Car's position based on its current velocity.
     */
    public void move() {
        lastVelocity = velocity;
        position = PositionVector.add(position, velocity);
    }

    /**
     * Mark this Car as being crashed.
     */
    public void crash() {
        crashed = true;
    }

    /**
     * Returns whether this Car has been marked as crashed.
     *
     * @return Returns true if crash() has been called on this Car, false otherwise.
     */
    public boolean isCrashed() {
        return crashed;
    }

    /**
     * Sets the StrategyType for this car
     *
     * @param strategyType Which Strategy should be constructed?
     */
    public void setStrategyType(Config.StrategyType strategyType) {
        switch (strategyType) {
            case DO_NOT_MOVE:
                strategy = new DoNotMoveStrategy();
                break;
            case USER:
                strategy = new UserMoveStrategy();
                break;
            case MOVE_LIST:
                strategy = new MoveListStrategy();
                break;
            case PATH_FOLLOWER:
                strategy = new PathFollowerMoveStrategy(position);
                break;
            case PATH_FINDER:
                strategy = new PathFinderMoveStrategy(position);
                break;
        }
    }

    /**
     * Get the strategy of the car
     *
     * @return the strategy of the car
     */
    public Strategy getStrategy() {
        return strategy;
    }

    /**
     * @return the id from this car
     */
    public char getId() {
        return id;
    }

    /**
     * @return the position of this car
     */
    public PositionVector getPosition() {
        return position;
    }

    /**
     * Set this Car position directly, regardless of current position and velocity.
     * This should only be used by the game controller in rare cases to set the crash or winning position.
     * The next position is normally automatically calculated and set in the {@link #move()} method.
     *
     * @param position The new position to set the car directly to.
     */
    public void setPosition(final PositionVector position) {
        this.position = new PositionVector(position);
    }

    /**
     * @return the current Velocity of the car
     */
    public PositionVector getVelocity() {
        return velocity;
    }

    /**
     * Increase the loop count by 1
     */
    public void incrementLoopCount() {
        carLoopCount++;
    }

    /**
     * Decrease the loop count by 1
     */
    public void decrementLoopCount() {
        carLoopCount--;
    }

    /**
     * @return if the car has won
     */
    public boolean isWinner() {
        return carLoopCount == LOOP_COUNT_TO_WIN;
    }

}
