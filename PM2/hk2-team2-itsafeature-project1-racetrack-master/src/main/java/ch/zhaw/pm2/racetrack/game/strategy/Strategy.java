package ch.zhaw.pm2.racetrack.game.strategy;


import ch.zhaw.pm2.racetrack.exceptions.QuitGameException;
import ch.zhaw.pm2.racetrack.game.logic.PositionVector;

/**
 * Abstract SuperClass for all Strategies
 */
public abstract class Strategy {

    /**
     * @return a Direction in which the Car should move next
     * @throws QuitGameException if the user wishes to quit the game
     */
    public abstract PositionVector.Direction nextMove() throws QuitGameException;

}
