package ch.zhaw.hexboard;

/**
 * This class defines a label composed of two characters.
 *
 * @author tebe
 */
public final class Label {
    public static final char DEFAULT_CHARACTER = ' ';
    private final char first;
    private final char second;

    /**
     * Creates a label from two characters.
     *
     * @param firstChar  first character
     * @param secondChar second character
     */
    public Label(char firstChar, char secondChar) {
        first = firstChar;
        second = secondChar;
    }

    /**
     * Creates a label using the default character {@link #DEFAULT_CHARACTER}.
     */
    public Label() {
        first = ' ';
        second = ' ';
    }

    public char getFirst() {
        return first;
    }

    public char getSecond() {
        return second;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "" + first + second;
    }
}
