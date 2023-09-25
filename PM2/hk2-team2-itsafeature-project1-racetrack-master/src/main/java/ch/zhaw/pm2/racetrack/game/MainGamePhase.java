package ch.zhaw.pm2.racetrack.game;

import ch.zhaw.pm2.racetrack.exceptions.QuitGameException;
import ch.zhaw.pm2.racetrack.game.logic.Game;
import ch.zhaw.pm2.racetrack.game.logic.Track;

/**
 * This class contains the MainGame
 * It contains the main while loop which prints the game and make the moves for every car
 *
 * @author outitbad
 * @date 26.03.2021
 */
public class MainGamePhase {

    private final Game game;
    private final TextInteraction textInteraction;
    private final Track track;

    /**
     * Constructor of the MainGame
     *
     * @param track on which this MainGame should operate
     */
    public MainGamePhase(Track track) {
        this.track = track;
        game = new Game(track);
        textInteraction = TextInteraction.getInstance();
    }

    /**
     * Starts a Game
     *
     * @throws QuitGameException if the user wants to quit the Game
     */
    public void startMainGame() throws QuitGameException {
        boolean quit = false;
        int winner = -1;
        while (winner == -1 && !quit) {

            textInteraction.printTrack(track);

            boolean validUserInput = false;
            while (!validUserInput) {
                try {
                    textInteraction.printCurrentCar(track.getCar(game.getCurrentCarIndex()).getId());
                    game.doCarTurn(track.getCar(game.getCurrentCarIndex()).getStrategy().nextMove());
                    validUserInput = true;
                } catch (RuntimeException e) {
                    textInteraction.tellPlayerInvalidMoveInput();
                } catch (QuitGameException e) {
                    quit = true;
                    validUserInput = true;
                }
            }

            //Check if there is a Winner
            if (game.getWinner() != Game.NO_WINNER) {
                textInteraction.printTrack(track);
                textInteraction.tellPlayerHeHasWon(game.getCarId(track.getCurrentCarIndex()));
                winner = game.getWinner();
            } else {
                game.switchToNextActiveCar();
            }
        }

        if (quit) {
            textInteraction.quit();
            throw new QuitGameException();
        }
    }
}

