package ch.zhaw.pm2.racetrack.exceptions;

/**
 * Exception which gets thrown when the user wants to quit the game
 *
 * @author baachsil
 * @date 26.03.2021
 */
public class QuitGameException extends Exception {

    /**
     * Constructor with errormessage
     * Currently not used but maybe useful in the future
     *
     * @param errorMessage the errorMessage which can be read
     */
    public QuitGameException(String errorMessage) {
        super(errorMessage);
    }

    /**
     * Default Constructor
     */
    public QuitGameException() {
    }

}
