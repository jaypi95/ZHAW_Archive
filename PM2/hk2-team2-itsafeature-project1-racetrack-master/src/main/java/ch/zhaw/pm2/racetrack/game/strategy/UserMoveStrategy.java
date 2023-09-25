package ch.zhaw.pm2.racetrack.game.strategy;

import ch.zhaw.pm2.racetrack.exceptions.QuitGameException;
import ch.zhaw.pm2.racetrack.game.TextInteraction;
import ch.zhaw.pm2.racetrack.game.logic.PositionVector.Direction;

/**
 * Let the user decide the next move.
 */
public class UserMoveStrategy extends Strategy implements MoveStrategy {

    private final TextInteraction textInteraction;

    /**
     * Constructor for this Class
     */
    public UserMoveStrategy() {
        textInteraction = TextInteraction.getInstance();
    }

    /**
     * @return a Direction in which the Car should move next
     * @throws QuitGameException if the user wishes to quit the game
     */
    @Override
    public Direction nextMove() throws RuntimeException, QuitGameException {

        String userInput = textInteraction.askPlayerForNextMove();
        Direction direction;
        int userInputNr = 0;

        try {
            userInputNr = Integer.parseInt(userInput);
        } catch (NumberFormatException e) {
            //Just ignore
        }

        //Matches the Numbers to the existing Enum
        if (userInputNr > 0) {

            switch (userInputNr) {
                case 1:
                    direction = Direction.valueOf("DOWN_LEFT");
                    break;
                case 2:
                    direction = Direction.valueOf("DOWN");
                    break;
                case 3:
                    direction = Direction.valueOf("DOWN_RIGHT");
                    break;
                case 4:
                    direction = Direction.valueOf("LEFT");
                    break;
                case 5:
                    direction = Direction.valueOf("NONE");
                    break;
                case 6:
                    direction = Direction.valueOf("RIGHT");
                    break;
                case 7:
                    direction = Direction.valueOf("UP_LEFT");
                    break;
                case 8:
                    direction = Direction.valueOf("UP");
                    break;
                case 9:
                    direction = Direction.valueOf("UP_RIGHT");
                    break;
                default:
                    throw new RuntimeException();
            }
        } else {
            if ("Q".equals(userInput)) {
                throw new QuitGameException();
            }
            direction = Direction.valueOf(userInput);
        }

        return direction;
    }
}
