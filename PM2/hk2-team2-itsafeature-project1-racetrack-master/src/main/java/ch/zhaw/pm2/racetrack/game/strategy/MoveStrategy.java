package ch.zhaw.pm2.racetrack.game.strategy;

import ch.zhaw.pm2.racetrack.exceptions.QuitGameException;

import static ch.zhaw.pm2.racetrack.game.logic.PositionVector.Direction;

/**
 * Interface for all strategys
 */
public interface MoveStrategy {

    /**
     * @return a Direction in which the Car should move next
     * @throws QuitGameException if the user wishes to quit the game
     */
    Direction nextMove() throws QuitGameException;

}
