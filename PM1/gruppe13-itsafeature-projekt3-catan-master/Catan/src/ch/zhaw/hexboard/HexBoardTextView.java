package ch.zhaw.hexboard;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

/**
 * This class can be used to get a textual representation of a hex-grid modeled
 * by {@link ch.zhaw.hexboard.HexBoard}.
 * <p>
 * It creates a textual representation of the {@link ch.zhaw.hexboard.HexBoard}
 * that includes all defined fields, edges, corners and annotations.
 * </p>
 * The generation of the textual representation is basically working on a line
 * by line basis. Thereby, the two text lines needed for the diagonal edges are
 * treated like "one line" in that they are created in one step together.
 * <p>
 * The textual representation does not contain the hex-grid as such but only the
 * fields that actually exist on the hex-board. Note that if a field exists,
 * also its corners and edges exist and are therefore shown in the textual
 * representation.
 * </p>
 * <p>
 * This class defines how edges, corners and fields look like (their "label").
 * This is done as follows:
 * <ul>
 * <li>If there is no data object associated with an edge, corner or field,
 * their default representation is used. Note that the default representation of
 * an edge depends on its direction (see below).</li>
 * <li>If there is a data object associated with an edge, corner or field, the
 * {@link ch.zhaw.hexboard.Label} is determined by calling:
 * <ul>
 * <li>EL = {@link #getEdgeLabel(Object)}</li>
 * <li>CL = {@link #getCornerLabel(Object)}</li>
 * <li>UL = {@link #getFieldLabelUpper(Object)}</li>
 * </ul>
 * </li>
 * </ul>
 * In addition to edges, corners and field labels, the hex-board's field
 * annotations are included too. If an annotation exists for one of the corners
 * (N, NW, SW, S, SE, NE), which means that an associated data object exists, it
 * is turned into a {@link ch.zhaw.hexboard.Label} with
 * {@link #getAnnotationLabel(Object)}.
 * </p>
 * <p>
 * Two examples of how that looks like are shown below. The first example shows
 * a case with all edges, corners and the field with no data associated with
 * them. The second one has all edges corner and the upper field label defined
 * by calling the corresponding method for creating the Label for the associated
 * data object.
 *
 * <pre>
 *        DEFAULT                LABELS FROM DATA
 *
 *          (  )                       (CL)
 *        //    \\                   EL    EL
 *    //            \\           EL     N      EL
 * (  )              (  )     (CL) NE        NW (CL)
 *  ||                ||       EL       UL       EL
 *  ||                ||       EL                EL
 * (  )              (  )     (CL) SE        SW (CL)
 *    \\            //           EL     S      EL
 *        \\    //                   EL    EL
 *          (  )                       (CL)
 * </pre>
 * <p>
 * To override the default behavior, which creates a Label using the two first
 * characters of the string returned by the toString() method of the
 * edge/corner/field data object, you might override the respective methods.
 * </p>
 * <p>
 * Finally, a field can be labeled with a lower label (LL) by providing a map of
 * field coordinates and associated labels. An example of a representation with
 * all field annotations, corner labels and field labels defined but default
 * edges is the following:
 *
 * <pre>
 *          (CL)
 *        // N  \\
 *    //            \\
 * (CL) NE        NW (CL)
 *  ||       UL       ||
 *  ||       LL       ||
 * (CL) SE        SW (CL)
 *    \\     S      //
 *        \\    //
 *          (CL)
 * </pre>
 * </p>
 *
 * @param <F> See {@link ch.zhaw.hexboard.HexBoard}
 * @param <C> See {@link ch.zhaw.hexboard.HexBoard}
 * @param <E> See {@link ch.zhaw.hexboard.HexBoard}
 * @param <A> See {@link ch.zhaw.hexboard.HexBoard}
 * @author tebe
 */
public class HexBoardTextView<F, C, E, A> {

    private static final String ONE_SPACE = " ";
    private static final String THREE_SPACES = "   ";
    private static final String FOUR_SPACES = "    ";
    private static final String SIX_SPACES = "      ";
    private static final String SEVEN_SPACES = "       ";
    private static final String EIGHT_SPACES = "        ";
    private static final String NINE_SPACES = "         ";
    private final HexBoard<F, C, E, A> board;
    private final Label emptyLabel = new Label(' ', ' ');
    private final Label defaultDiagonalEdgeDownLabel = new Label('\\', '\\');
    private final Label defaultDiagonalEdgeUpLabel = new Label('/', '/');
    private final Label defaultVerticalEdgeLabel = new Label('|', '|');
    private Map<Point, Label> fixedLowerFieldLabels;

    /**
     * Creates a view for the specified board.
     *
     * @param board the board
     */
    public HexBoardTextView(HexBoard<F, C, E, A> board) {
        this.fixedLowerFieldLabels = new HashMap<>();
        this.board = board;
    }

    /**
     * Sets the lower field label for the specified field.
     *
     * @param field the field
     * @param label the label
     * @throws IllegalArgumentException if arguments are null or if the field does
     *                                  not exist
     */
    public void setLowerFieldLabel(Point field, Label label) {
        if (field == null || label == null || !board.hasField(field)) {
            throw new IllegalArgumentException("Argument(s) must not be null and field must exist.");
        }
        fixedLowerFieldLabels.put(field, label);
    }

    /**
     * Returns a label to be used as label for the edge. This method is called to
     * determine the label for this edge.
     *
     * @param e edge data object
     * @return the label
     */
    protected Label getEdgeLabel(E e) {
        return deriveLabelFromToStringRepresentation(e);
    }

    /**
     * Returns a label to be used as label for the corner. This method is called to
     * determine the label for this corner.
     *
     * @param c corner data object
     * @return the label
     */
    protected Label getCornerLabel(C c) {
        return deriveLabelFromToStringRepresentation(c);
    }

    /**
     * Returns a label to be used as upper label for the field. This method is
     * called to determine the upper label for this field.
     *
     * @param f field data object
     * @return the label
     */
    protected Label getFieldLabelUpper(F f) {
        return deriveLabelFromToStringRepresentation(f);
    }

    /**
     * Returns a label to be used as lower label for the field at this position.
     * This method is called to determine the lower label for this field.
     *
     * @param p location of the field
     * @return the label
     */
    private Label getFieldLabelLower(Point p) {
        Label l = this.fixedLowerFieldLabels.get(p);
        l = l == null ? emptyLabel : l;
        return l;
    }

    private Label deriveLabelFromToStringRepresentation(Object o) {
        Label label = emptyLabel;
        if (o.toString().length() > 0) {
            String s = o.toString();
            if (s.length() > 1) {
                return new Label(s.charAt(0), s.charAt(1));
            } else {
                return new Label(s.charAt(0), ' ');
            }
        }
        return label;
    }

    /**
     * <p>
     * This method returns a single-line string with all corners and field
     * annotations for a given y-coordinate. It produces the string by iterating
     * over corner positions and appending per corner:
     * </p>
     * <p>
     * "(CL) NE NW " for y%3==1 "(CL) SE SW " for y%3==0
     * </p>
     * <p>
     * Corners/labels that do not exist are replaced by spaces.
     * </p>
     */
    private String printCornerLine(int y) {
        StringBuilder cornerLine = new StringBuilder("");
        int offset = 0;
        if (y % 2 != 0) {
            cornerLine.append(NINE_SPACES);
            offset = 1;
        }
        for (int x = offset; x <= board.getMaxCoordinateX(); x = x + 2) {
            Point p = new Point(x, y);
            Label cornerLabel;

            // handle corner labels for corners other than north and south corners
            Point center;
            Label first = null;
            Label second = null;
            switch (y % 3) {
                case 0:
                    center = new Point(x + 1, y - 1);
                    first = this.getAnnotationLabel(
                            board.getFieldAnnotation(center, new Point(center.x - 1, center.y + 1)));
                    second = this.getAnnotationLabel(
                            board.getFieldAnnotation(center, new Point(center.x + 1, center.y + 1)));
                    break;
                case 1:
                    center = new Point(x + 1, y + 1);
                    first = this.getAnnotationLabel(
                            board.getFieldAnnotation(center, new Point(center.x - 1, center.y - 1)));
                    second = this.getAnnotationLabel(
                            board.getFieldAnnotation(center, new Point(center.x + 1, center.y - 1)));
                    break;
                default:
                    throw new IllegalArgumentException("Not a corner line");
            }

            if (board.hasCorner(p)) {
                cornerLabel = board.getCorner(p) != null ? getCornerLabel(board.getCorner(p)) : emptyLabel;
                cornerLine.append("(").append(cornerLabel.getFirst()).append(cornerLabel.getSecond()).append(")");
            } else {
                cornerLine.append(FOUR_SPACES);
            }
            cornerLine.append(ONE_SPACE).append(first.getFirst()).append(first.getSecond());
            cornerLine.append(EIGHT_SPACES).append(second.getFirst()).append(second.getSecond()).append(ONE_SPACE);
        }
        return cornerLine.toString();
    }

    private Label getAnnotationLabel(A annotation) {
        if (annotation == null) {
            return emptyLabel;
        } else {
            return deriveLabelFromToStringRepresentation(annotation);
        }
    }

    private String printMiddlePartOfField(int y) {
        boolean isOffsetRow = (y - 2) % 6 == 0;
        StringBuilder lower = new StringBuilder(isOffsetRow ? NINE_SPACES : "");
        StringBuilder upper = new StringBuilder(isOffsetRow ? NINE_SPACES : "");
        int xstart = isOffsetRow ? 2 : 1;

        for (int x = xstart; x <= board.getMaxCoordinateX() + 1; x = x + 2) {
            Point edgeStart = new Point(x - 1, y - 1);
            Point edgeEnd = new Point(x - 1, y + 1);
            Label l = this.emptyLabel;
            if (board.hasEdge(edgeStart, edgeEnd)) {
                E edge = board.getEdge(edgeStart, edgeEnd);
                if (edge != null) {
                    l = this.getEdgeLabel(edge);
                } else {
                    l = this.defaultVerticalEdgeLabel;
                }
            }
            Point center = new Point(x, y);
            boolean hasFieldWithData = board.hasField(center) && board.getField(center) != null;
            Label lowerFieldLabel = hasFieldWithData ? getFieldLabelLower(center) : emptyLabel;
            Label upperFieldLabel = hasFieldWithData ? getFieldLabelUpper(board.getField(center))
                    : emptyLabel;
            lower.append(ONE_SPACE).append(l.getFirst()).append(l.getSecond()).append(SEVEN_SPACES);
            lower.append(lowerFieldLabel.getFirst()).append(lowerFieldLabel.getSecond()).append(SIX_SPACES);

            upper.append(ONE_SPACE).append(l.getFirst()).append(l.getSecond()).append(SEVEN_SPACES);
            upper.append(upperFieldLabel.getFirst()).append(upperFieldLabel.getSecond()).append(SIX_SPACES);
        }
        return upper + System.lineSeparator() + lower;
    }

    private void printDiagonalEdge(StringBuilder upper, StringBuilder lower, boolean down, Label l,
                                   Label annotationLabel) {
        if (down) {
            upper.append(ONE_SPACE + l.getFirst() + l.getSecond() + "     " + annotationLabel.getFirst());
            lower.append(FOUR_SPACES + l.getFirst() + l.getSecond() + "  " + annotationLabel.getSecond());
        } else {
            upper.append(FOUR_SPACES + l.getFirst() + l.getSecond() + "  " + annotationLabel.getFirst());
            lower.append(ONE_SPACE + l.getFirst() + l.getSecond() + "     " + annotationLabel.getSecond());
        }
    }

    private String printDiagonalEdges(int y) {
        StringBuilder sbUpper = new StringBuilder();
        StringBuilder sbLower = new StringBuilder();
        Point edgeStart;
        Point edgeEnd;
        A annotation = null;
        Label l;
        boolean isDown = y % 6 == 0;

        sbUpper.append(THREE_SPACES);
        sbLower.append(THREE_SPACES);
        for (int x = 0; x <= board.getMaxCoordinateX(); x = x + 1) {
            if (isDown) {
                edgeStart = new Point(x, y);
                edgeEnd = new Point(x + 1, y + 1);
                annotation = board.getFieldAnnotation(new Point(x + 1, y - 1), new Point(x + 1, y + 1));
            } else {
                edgeStart = new Point(x, y + 1);
                edgeEnd = new Point(x + 1, y);
                annotation = board.getFieldAnnotation(new Point(x + 1, y + 2), new Point(x + 1, y));
            }
            l = determineEdgeLabel(isDown, edgeStart, edgeEnd);
            this.printDiagonalEdge(sbUpper, sbLower, isDown, l, this.getAnnotationLabel(annotation));
            isDown = !isDown;
        }
        return sbUpper.toString() + System.lineSeparator() + sbLower.toString();
    }

    private Label determineEdgeLabel(boolean isDown, Point edgeStart, Point edgeEnd) {
        Label l;
        if (board.hasEdge(edgeStart, edgeEnd)) {
            // does it have data associated with it?
            if (board.getEdge(edgeStart, edgeEnd) != null) {
                l = this.getEdgeLabel(board.getEdge(edgeStart, edgeEnd));
            } else {
                // default visualization
                l = isDown ? this.defaultDiagonalEdgeDownLabel : this.defaultDiagonalEdgeUpLabel;
            }
        } else {
            l = this.emptyLabel;
        }
        return l;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y <= board.getMaxCoordinateY(); y = y + 3) {
            sb.append(printCornerLine(y));
            sb.append(System.lineSeparator());
            sb.append(printDiagonalEdges(y));
            sb.append(System.lineSeparator());
            sb.append(printCornerLine(y + 1));
            sb.append(System.lineSeparator());
            sb.append(printMiddlePartOfField(y + 2));
            sb.append(System.lineSeparator());

        }
        return sb.toString();
    }

}
