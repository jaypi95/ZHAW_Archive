package ch.zhaw.pm2.racetrack.game.strategy;

import ch.zhaw.pm2.racetrack.exceptions.QuitGameException;

import static ch.zhaw.pm2.racetrack.game.logic.PositionVector.Direction;

/**
 * Do not accelerate in any direction.
 *
 * @author peterju1
 * @date 26.03.2021
 */
public class DoNotMoveStrategy extends Strategy implements MoveStrategy {

    /**
     * @return a Direction in which the Car should move next
     * @throws QuitGameException if the user wishes to quit the game
     */
    @Override
    public Direction nextMove() {
        try {
            return Direction.NONE;
        } catch (RuntimeException e) {
            throw new RuntimeException();
        }
    }
}
