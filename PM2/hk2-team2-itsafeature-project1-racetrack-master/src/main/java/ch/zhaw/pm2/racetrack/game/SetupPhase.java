package ch.zhaw.pm2.racetrack.game;

import ch.zhaw.pm2.racetrack.exceptions.InvalidTrackFormatException;
import ch.zhaw.pm2.racetrack.game.logic.Car;
import ch.zhaw.pm2.racetrack.game.logic.Config;
import ch.zhaw.pm2.racetrack.game.logic.Track;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class runs the setup phase of a game
 * The players can decide which track will be used and how many cars there will be in the game
 *
 * @author outitbad
 * @date 26.03.2021
 */
public class SetupPhase {

    private final Config config;
    private final ArrayList<Config.StrategyType> carStrategyList;
    private Track track;
    private TextInteraction textInteraction;

    /**
     * Inits the setup phase object
     */
    public SetupPhase() {
        config = new Config();
        carStrategyList = new ArrayList<>();
        textInteraction = TextInteraction.getInstance();

        //Fill the carStrategyList
        carStrategyList.addAll(Arrays.asList(Config.StrategyType.values()));
        textInteraction = TextInteraction.getInstance();
    }

    /**
     * This method starts and runs the whole setup phase
     * <p>
     * The setup phase includes asking the player about the track and the number of players
     * Furthermore, a strategy is selected for each car
     * The track and the cars are also initialized here
     */
    public void runSetupPhase() {
        //Init track
        boolean trackGotInit = false;
        while (!trackGotInit) {
            try {
                int numbOfTrack = textInteraction.askThePlayerWhichTrack(trackList());
                this.track = new Track(getTrackFile(numbOfTrack));
                trackGotInit = true;
            } catch (FileNotFoundException | IndexOutOfBoundsException e) {
                textInteraction.tellPlayerFileNotFound();
            } catch (InvalidTrackFormatException e) {
                textInteraction.tellPlayerInvalidTrackFormat();
            }
        }

        //Decide which Strategy will be used
        for (Car car : track.getAllCars()) {
            boolean strategyFound = false;
            while (!strategyFound) {
                try {
                    int numbOfStrategy = textInteraction.askPlayerWhichStrategy(carStrategyList, car.getId());
                    car.setStrategyType(carStrategyList.get(numbOfStrategy - 1));
                    strategyFound = true;
                } catch (IndexOutOfBoundsException e) {
                    textInteraction.tellPlayerStrategyNotFound();
                }
            }
        }
    }

    /**
     * This method returns the file of a track
     *
     * @param numberOfTrack The number of the track in the ArrayList
     * @return The file of the track
     */
    private File getTrackFile(int numberOfTrack) {
        return trackList().get(numberOfTrack - 1);
    }

    /**
     * This method returns an ArrayList with the track files
     * The track files are taken from the directory which is saved in the config class
     *
     * @return An ArrayList with the track files
     */
    private ArrayList<File> trackList() {
        ArrayList<File> trackList = new ArrayList<>();

        File[] availableTracks = config.getTrackDirectory().listFiles();
        if (availableTracks == null) {
            availableTracks = new File[]{};
        }

        for (File track : availableTracks) {
            if (!track.getName().startsWith(".")) {
                trackList.add(track);
            }
        }
        return trackList;
    }

    /**
     * @return the track
     */
    public Track getTrack() {
        return track;
    }
}



