package ch.zhaw.pm2.racetrack;

import ch.zhaw.pm2.racetrack.exceptions.QuitGameException;
import ch.zhaw.pm2.racetrack.game.MainGamePhase;
import ch.zhaw.pm2.racetrack.game.SetupPhase;
import ch.zhaw.pm2.racetrack.game.TextInteraction;

/**
 * This is the MainClass
 * It can start multiple games and inits the SetupPhase and the MainGamePhase
 *
 * @author baachsil
 * @date 26.03.2021
 */
public class RaceTrack {

    /**
     * Main Method which runs the whole game
     *
     * @param args not used
     */
    public static void main(String[] args) {
        boolean quit = false;
        TextInteraction textInteraction = TextInteraction.getInstance();

        while (!quit) {
            //Start the SetupPhase
            SetupPhase setupPhase = new SetupPhase();
            setupPhase.runSetupPhase();

            //Start the MainGamePhase
            MainGamePhase mainGamePhase = new MainGamePhase(setupPhase.getTrack());

            try {
                mainGamePhase.startMainGame();
            } catch (QuitGameException e) {
                quit = true;
            }

            if (!quit) {
                boolean validInput = false;
                while (!validInput) {
                    String oneMoreGame = textInteraction.askPlayerOneMoreGame();

                    if ("y".equalsIgnoreCase(oneMoreGame)) {
                        validInput = true;
                    } else if ("n".equalsIgnoreCase(oneMoreGame)) {
                        validInput = true;
                        quit = true;
                        textInteraction.quit();
                    }
                }
            }
        }
    }
}
