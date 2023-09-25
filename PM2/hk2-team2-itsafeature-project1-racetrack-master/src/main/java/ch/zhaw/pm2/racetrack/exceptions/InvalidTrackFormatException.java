package ch.zhaw.pm2.racetrack.exceptions;

/**
 * Exception which gets thrown when the track file is not formatted correctly
 *
 * @author baachsil
 * @date 26.03.2021
 */
public class InvalidTrackFormatException extends Exception {

    /**
     * The Constructor of this Exception
     *
     * @param errorMessage the Errormessage which can be read
     */
    public InvalidTrackFormatException(String errorMessage) {
        super(errorMessage);
    }
}
