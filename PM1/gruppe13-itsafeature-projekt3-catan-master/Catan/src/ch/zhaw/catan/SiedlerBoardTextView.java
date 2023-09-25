package ch.zhaw.catan;

import ch.zhaw.catan.Config.Land;
import ch.zhaw.hexboard.HexBoardTextView;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class has utility methods to print the whole gameBoard to the textTerminal directly
 *
 * @author defiljon
 */
public class SiedlerBoardTextView extends HexBoardTextView<Land, String, String, Annotation> {

    public SiedlerBoardTextView(SiedlerBoard board) {
        super(board);
    }

    /**
     * initialise an object of the siedlerBoardTextView
     */
    public void printGameBoard() {
        String gameBoard = addCoordinates(toString());
        TextIO textIO = TextIoFactory.getTextIO();
        TextTerminal<?> textTerminal = textIO.getTextTerminal();
        textTerminal.print(gameBoard);
    }

    /**
     * Adds a coordinate system to the board
     *
     * @param board the siedler board
     * @return board with coordinates
     */
    protected String addCoordinates(String board) {
        final String SEVEN_SPACES = "       ";
        final String THREE_SPACES = "   ";
        final String TWO_SPACES = "  ";
        final String ONE_SPACE = " ";

        List<String> split = new ArrayList<>(Arrays.asList(board.split(System.lineSeparator())));
        // remove all lines that are empty (only contain spaces)
        split.removeIf(line -> line.trim().isEmpty());

        // * add yAxis
        // add the number on first line manually
        split.set(0, 0 + ONE_SPACE + split.get(0));
        // add numbers on the lines in between
        int number = 1;
        for (int lineIndex = 0; lineIndex < split.size() - 1; lineIndex++) {
            if (lineIndex % 3 == 0 && lineIndex % 6 != 0) {
                // iterate over every yAxis coord "block" (3lines)
                split.set(lineIndex, ((number < 10) ? (number + ONE_SPACE) : number) + split.get(lineIndex));
                number += 2; // +2 because we skip the line in the center of the fields
                split.set(lineIndex + 3, ((number < 10) ? (number + ONE_SPACE) : number) + split.get(lineIndex + 3));
                number++; // need to increment by one for next iteration
            } else if (lineIndex % 6 != 0) {
                // we don't wanna add a space to the lines that have index % 6 == 0 because we added a number there before
                split.set(lineIndex, TWO_SPACES + split.get(lineIndex));
            }
        }
        // add the number on the last line
        split.set(split.size() - 1, number + split.get(split.size() - 1));

        // * add xAxis
        StringBuilder xAxis = new StringBuilder();
        xAxis.append(THREE_SPACES); // we need to offset the numbers by 3 spaces (because of the y axis and to center them)
        for (int i = 0; i <= 14; i++) {
            xAxis.append(i).append((i < 10) ? (SEVEN_SPACES + ONE_SPACE) : SEVEN_SPACES);
        }
        split.add(0, xAxis.toString());

        return String.join(System.lineSeparator(), split) + System.lineSeparator() + System.lineSeparator();
    }
}
