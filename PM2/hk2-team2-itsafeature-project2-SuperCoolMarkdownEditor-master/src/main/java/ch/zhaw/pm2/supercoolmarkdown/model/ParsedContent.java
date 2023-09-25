package ch.zhaw.pm2.supercoolmarkdown.model;

import javax.swing.text.AttributeSet;

/**
 * This class contains information about how the content should be displayed on the screen.
 *
 * @author Peterju1
 * @version 11.95.2021
 */
public class ParsedContent {

    private final String content;
    private int start;
    private int length;
    private AttributeSet format;
    private boolean replace;

    /**
     * The Constructor of this class
     *
     * @param content The content
     * @param start   The start index of the content
     * @param length  The length of the content
     * @param format  The Set to format to
     * @param replace True if the format should be replaced
     */
    public ParsedContent(String content, int start, int length, AttributeSet format, boolean replace) {
        this.content = content;
        this.start = start;
        this.length = length;
        this.format = format;
        this.replace = replace;
    }

    /**
     * This method returns the start index of where the content should be formatted
     *
     * @return The start index
     */
    public int getStart() {
        return start;
    }

    /**
     * This method returns the length of the section that should be formatted
     *
     * @return The length of the content
     */
    public int getLength() {
        return length;
    }

    /**
     * This method returns the Set that the content should be formatted with
     *
     * @return The format as a Set
     */
    public AttributeSet getFormat() {
        return format;
    }

    /**
     * This method returns the content
     *
     * @return The content
     */
    public String getContent() {
        return content;
    }

    /**
     * This method returns true if already existing formatting should be replaced with this formatting or false if this should only be added
     *
     * @return True if the format should be replaced
     */
    public boolean getReplace() {
        return replace;
    }
}
