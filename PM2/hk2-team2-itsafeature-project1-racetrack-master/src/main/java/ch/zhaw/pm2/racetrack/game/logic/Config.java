package ch.zhaw.pm2.racetrack.game.logic;

import java.io.File;
import java.util.Objects;

public class Config {
    public static final int MAX_CARS = 9;

    // Directory containing the track files
    private File trackDirectory = new File("tracks");

    // Directory containing the track files
    private File moveDirectory = new File("moves");

    // Directory containing the follower files
    private File followerDirectory = new File("follower");

    public File getMoveDirectory() {
        return moveDirectory;
    }

    public void setMoveDirectory(File moveDirectory) {
        Objects.requireNonNull(moveDirectory);
        this.moveDirectory = moveDirectory;
    }

    public File getFollowerDirectory() {
        return followerDirectory;
    }

    public void setFollowerDirectory(File followerDirectory) {
        Objects.requireNonNull(followerDirectory);
        this.followerDirectory = followerDirectory;
    }

    public File getTrackDirectory() {
        return trackDirectory;
    }

    public void setTrackDirectory(File trackDirectory) {
        Objects.requireNonNull(trackDirectory);
        this.trackDirectory = trackDirectory;
    }

    public enum StrategyType {
        DO_NOT_MOVE, USER, MOVE_LIST, PATH_FOLLOWER, PATH_FINDER
    }

    /**
     * Enum representing possible space types of the grid.
     * The char value is used to parse from the track file and represents
     * the space type in the text representation created by toString().
     */
    public enum SpaceType {
        WALL('#'),
        TRACK(' '),
        FINISH_UP('^'),
        FINISH_DOWN('v'),
        FINISH_LEFT('<'),
        FINISH_RIGHT('>');

        public final char value;

        SpaceType(final char c) {
            value = c;
        }
    }


}
