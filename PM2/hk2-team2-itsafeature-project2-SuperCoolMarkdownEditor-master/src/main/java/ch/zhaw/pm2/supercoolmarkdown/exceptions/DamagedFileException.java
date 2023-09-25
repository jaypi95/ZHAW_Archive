package ch.zhaw.pm2.supercoolmarkdown.exceptions;

/**
 * An exception which gets thrown when a file is damaged and can not be read by
 * the text editor
 *
 * @author Baachsil
 * @version 11.05.2021
 */
public class DamagedFileException extends Exception {

    /**
     * Constructor fot the DamagedFileException
     *
     * @param reason the reason in which way the file is damaged
     */
    public DamagedFileException(String reason) {
        super(reason);
    }
}
