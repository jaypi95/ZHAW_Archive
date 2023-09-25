package ch.zhaw.hexboard;

import java.awt.Point;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/***
 * <p>
 * This class represents a simple generic hexagonal game board.
 * </p>
 * <p>
 * The game board uses a fixed coordinate system which is structured as follows:
 *
 * <pre>
 *         0    1    2    3    4    5    6    7    8 
 *         |    |    |    |    |    |    |    |    |   ...
 *
 *  0----  C         C         C         C         C
 *            \   /     \   /     \   /     \   /     \ 
 *  1----       C         C         C         C         C
 *
 *  2----  F    |    F    |    F    |    F    |    F    |   ...
 *
 *  3----       C         C         C         C         C
 *           /     \   /     \   /     \   /     \   / 
 *  4----  C         C         C         C         C    
 *
 *  5----  |    F    |    F    |    F    |    F    |    F   ...
 *
 *  6----  C         C         C         C         C    
 *           \     /   \     /   \     /   \     /   \     
 *  7----       C         C         C         C         C    
 *
 *    ...
 * </pre>
 * </p>
 *
 * <p>
 * Fields <strong>F</strong> and corners <strong>C</strong> can be retrieved
 * using their coordinates ({@link java.awt.Point}) on the board. Edges can be
 * retrieved using the coordinates of the two corners they connect.
 * </p>
 *
 * <p>
 * When created, the board is empty (no fields added). To add fields, the
 * #{@link #addField(Point, Object)} function can be used. Edges and corners are
 * automatically created when adding a field. They cannot be created/removed
 * individually. When adding a field, edges and corners that were already
 * created, e.g., because adding an adjacent field already created them, are
 * left untouched.
 * </p>
 *
 * <p>
 * Fields, edges and corners can store an object of the type of the
 * corresponding type parameter each.
 * </p>
 *
 * <p>
 * Furthermore, the hexagonal game board can store six additional objects, so
 * called annotations, for each field. These objects are identified by the
 * coordinates of the field and the corner. Hence, they can be thought of being
 * located between the center and the respective corner. Or in other words,
 * their positions correspond to the positions N, NW, SW, NE, NW, SE and NE in
 * the below visualization of a field.
 * </p>
 *
 * <pre>
 *       SW (C) SE
 *    /      N      \
 *  (C) NE       NW (C)
 *   |       F       |
 *   |               |
 *  (C) SE       SW (C)
 *    \      S      /
 *       NW (C) NE
 * </pre>
 *
 * @param <F> Data type for the field data objects
 * @param <C> Data type for the corner data objects
 * @param <E> Data type for the edge data objects
 * @param <A> Data type for the annotation data objects
 *
 * @author tebe
 *
 */
public class HexBoard<F, C, E, A> {
    private int maxCoordinateX = 0;
    private int maxCoordinateY = 0;
    private final Map<Point, F> field;
    private final Map<Point, C> corner;
    private final Map<Edge, E> edge;
    private final Map<FieldAnnotationPosition, A> annotation;

    /**
     * Constructs an empty hexagonal board.
     */
    public HexBoard() {
        field = new HashMap<>();
        corner = new HashMap<>();
        edge = new HashMap<>();
        annotation = new HashMap<>();
    }

    /**
     * Adds a field to the board and creates the surrounding (empty) corners and
     * edges if they do not yet exist Note: Corners and edges of a field might
     * already have been created while creating adjacent fields.
     *
     * @param center  Coordinate of the center of a field on the unit grid
     * @param element Data element to be stored for this field
     * @throws IllegalArgumentException if center is not the center of a field, the
     *                                  field already exists or data is null
     */
    public void addField(Point center, F element) {
        if (isFieldCoordinate(center) && !field.containsKey(center)) {
            field.put(center, element);
            maxCoordinateX = Math.max(center.x + 1, maxCoordinateX);
            maxCoordinateY = Math.max(center.y + 2, maxCoordinateY);
            // add (empty) edge, if they do not yet exist
            for (Edge e : constructEdgesOfField(center)) {
                if (!edge.containsKey(e)) {
                    edge.put(e, null);
                }
            }
            // add (empty) corners, if they do not yet exist
            for (Point p : getCornerCoordinatesOfField(center)) {
                if (!corner.containsKey(p)) {
                    corner.put(p, null);
                }
            }
        } else {
            throw new IllegalArgumentException(
                    "Coordinates are not the center of a field, the field already exists or data is null - ("
                            + center.x + ", " + center.y + ")");
        }
    }

    /**
     * Add an annotation for the specified field and corner.
     *
     * @param center the center of the field
     * @param corner the corner of the field
     * @param data   the annotation
     * @throws IllegalArgumentException if the field does not exists or when the
     *                                  annotation already exists
     */
    public void addFieldAnnotation(Point center, Point corner, A data) {
        FieldAnnotationPosition annotationPosition = new FieldAnnotationPosition(center, corner);
        if (!annotation.containsKey(annotationPosition)) {
            annotation.put(annotationPosition, data);
        } else {
            throw new IllegalArgumentException("Annotation: " + annotation + " already exists for field "
                    + center + " and position " + corner);
        }
    }

    /**
     * Get an annotation for the specified field and corner.
     *
     * @param center the center of the field
     * @param corner the corner of the field
     * @return the annotation
     * @throws IllegalArgumentException if coordinates are not a field and
     *                                  corresponding corner coordinate
     */
    public A getFieldAnnotation(Point center, Point corner) {
        return annotation.get(new FieldAnnotationPosition(center, corner));
    }

    /**
     * Get field annotation whose position information includes the specified corner.
     *
     * @param corner the corner
     * @return a list with the annotations that are not null
     * @throws IllegalArgumentException if corner is not a corner
     */
    public List<A> getFieldAnnotationsForCorner(Point corner) {
        List<A> list = new LinkedList<>();
        for (Entry<FieldAnnotationPosition, A> entry : annotation.entrySet()) {
            if (entry.getKey().isCorner(corner) && entry.getValue() != null) {
                list.add(entry.getValue());
            }
        }
        return list;
    }

    /**
     * Get all field annotation of the specified field.
     *
     * @param center the field
     * @return a list with the annotations that are not null
     * @throws IllegalArgumentException if center is not a field
     */
    public List<A> getFieldAnnotationsForField(Point center) {
        List<A> list = new LinkedList<>();
        for (Entry<FieldAnnotationPosition, A> entry : annotation.entrySet()) {
            if (entry.getKey().isField(center) && entry.getValue() != null) {
                list.add(entry.getValue());
            }
        }
        return list;
    }

    /**
     * Determines whether the field at the specified position exists.
     *
     * @param center the field
     * @return false, if the field does not exist or the position is not a field
     */
    public boolean hasField(Point center) {
        if (!HexBoard.isFieldCoordinate(center)) {
            return false;
        }
        return field.containsKey(center);
    }

    static boolean isFieldCoordinate(Point position) {
        boolean isYFieldCoordinateEven = (position.y - 2) % 6 == 0;
        boolean isYFieldCoordinateOdd = (position.y - 5) % 6 == 0;
        boolean isXFieldCoordinateEven = position.x % 2 == 0;
        boolean isXFieldCoordinateOdd = (position.x - 1) % 2 == 0;

        return (position.y >= 2 && position.x >= 1)
                && (isYFieldCoordinateEven && isXFieldCoordinateEven)
                || (isYFieldCoordinateOdd && isXFieldCoordinateOdd);
    }

    static boolean isCornerCoordinate(Point p) {
        // On the horizontal center lines, no edge points exist
        boolean isOnFieldCenterLineHorizontal = (p.y - 2) % 3 == 0;

        // On the vertical center lines, edge points exist
        boolean isOnFieldCenterLineVerticalOdd = (p.x - 1) % 3 == 0 && p.x % 2 == 0;
        boolean isOnFieldCenterLineVerticalEven = (p.x - 1) % 3 == 0 && (p.x - 1) % 2 == 0;
        boolean isNotAnEdgePointOnFieldCentralVerticalLine = isOnFieldCenterLineVerticalOdd
                && !(p.y % 6 == 0 || (p.y + 2) % 6 == 0)
                || isOnFieldCenterLineVerticalEven && !((p.y + 5) % 6 == 0 || (p.y + 3) % 6 == 0);

        return !(isOnFieldCenterLineHorizontal || isNotAnEdgePointOnFieldCentralVerticalLine);
    }

    private List<Edge> constructEdgesOfField(Point position) {
        Edge[] e = new Edge[6];
        e[0] = new Edge(new Point(position.x, position.y - 2),
                new Point(position.x + 1, position.y - 1));
        e[1] = new Edge(new Point(position.x + 1, position.y - 1),
                new Point(position.x + 1, position.y + 1));
        e[2] = new Edge(new Point(position.x + 1, position.y + 1),
                new Point(position.x, position.y + 2));
        e[3] = new Edge(new Point(position.x, position.y + 2),
                new Point(position.x - 1, position.y + 1));
        e[4] = new Edge(new Point(position.x - 1, position.y + 1),
                new Point(position.x - 1, position.y - 1));
        e[5] = new Edge(new Point(position.x - 1, position.y - 1),
                new Point(position.x, position.y - 2));
        return Arrays.asList(e);
    }

    private static List<Point> getCornerCoordinatesOfField(Point position) {
        Point[] corner = new Point[6];
        corner[0] = new Point(position.x, position.y - 2);
        corner[1] = new Point(position.x + 1, position.y - 1);
        corner[2] = new Point(position.x + 1, position.y + 1);
        corner[3] = new Point(position.x, position.y + 2);
        corner[4] = new Point(position.x - 1, position.y - 1);
        corner[5] = new Point(position.x - 1, position.y + 1);
        return Collections.unmodifiableList(Arrays.asList(corner));
    }

    protected static List<Point> getAdjacentCorners(Point position) {
        Point[] corner = new Point[3];
        if (position.y % 3 == 0) {
            corner[0] = new Point(position.x, position.y - 2);
            corner[1] = new Point(position.x + 1, position.y + 1);
            corner[2] = new Point(position.x - 1, position.y + 1);
        } else {
            corner[0] = new Point(position.x, position.y + 2);
            corner[1] = new Point(position.x + 1, position.y - 1);
            corner[2] = new Point(position.x - 1, position.y - 1);
        }
        return Collections.unmodifiableList(Arrays.asList(corner));
    }

    /**
     * Returns all non-null corner data elements.
     *
     * @return the non-null corner data elements
     */
    public List<C> getCorners() {
        List<C> result = new LinkedList<>();
        for (C c : this.corner.values()) {
            if (c != null) {
                result.add(c);
            }
        }
        return Collections.unmodifiableList(result);
    }

    protected Set<Point> getCornerCoordinates() {
        return Collections.unmodifiableSet(this.corner.keySet());
    }

    private static List<Point> getAdjacentFields(Point corner) {
        Point[] field = new Point[3];
        if (corner.y % 3 == 0) {
            field[0] = new Point(corner.x, corner.y + 2);
            field[1] = new Point(corner.x + 1, corner.y - 1);
            field[2] = new Point(corner.x - 1, corner.y - 1);
        } else {
            field[0] = new Point(corner.x, corner.y - 2);
            field[1] = new Point(corner.x + 1, corner.y + 1);
            field[2] = new Point(corner.x - 1, corner.y + 1);
        }
        return Collections.unmodifiableList(Arrays.asList(field));
    }

    /**
     * Returns the data for the field denoted by the point.
     *
     * @param center the location of the field
     * @return the stored data (or null)
     * @throws IllegalArgumentException if the requested field does not exist
     */
    public F getField(Point center) {
        if (field.containsKey(center)) {
            return field.get(center);
        } else {
            throw new IllegalArgumentException("No field exists at these coordinates: " + center);
        }
    }

    /**
     * Returns the fields with non-null data elements.
     *
     * @return the list with the (non-null) field data
     */
    public List<Point> getFields() {
        List<Point> result = new LinkedList<>();
        for (Entry<Point, F> e : field.entrySet()) {
            if (e.getValue() != null) {
                result.add(e.getKey());
            }
        }
        return Collections.unmodifiableList(result);
    }

    /**
     * Returns the field data of the fields that touch this corner.
     * <p>
     * If the specified corner is not a corner or none of the fields that touch this
     * corner have a non-null data element, an empty list is returned.
     * </p>
     *
     * @param corner the location of the corner
     * @return the list with the (non-null) field data
     */
    public List<F> getFields(Point corner) {
        List<F> result = new LinkedList<>();
        if (isCornerCoordinate(corner)) {
            for (Point f : getAdjacentFields(corner)) {
                if (field.get(f) != null) {
                    result.add(field.get(f));
                }
            }
        }
        return Collections.unmodifiableList(result);
    }

    /**
     * Returns the data for the edge denoted by the two points.
     *
     * @param p1 first point
     * @param p2 second point
     * @return the stored data (or null)
     */
    public E getEdge(Point p1, Point p2) {
        Edge e = new Edge(p1, p2);
        if (edge.containsKey(e)) {
            return edge.get(e);
        } else {
            return null;
        }
    }

    /**
     * Stores the data for the edge denoted by the two points.
     *
     * @param p1   first point
     * @param p2   second point
     * @param data the data to be stored
     * @throws IllegalArgumentException if the two points do not identify an
     *                                  EXISTING edge of the field
     */
    public void setEdge(Point p1, Point p2, E data) {
        Edge e = new Edge(p1, p2);
        if (edge.containsKey(e)) {
            edge.put(e, data);
        } else {
            throw new IllegalArgumentException("Edge does not exist => no data can be stored: " + e);
        }
    }

    /**
     * Returns the data for the corner denoted by the point.
     *
     * @param location the location of the corner
     * @return the data stored for this node (or null)
     * @throws IllegalArgumentException if the requested corner does not exist
     */
    public C getCorner(Point location) {
        if (corner.containsKey(location)) {
            return corner.get(location);
        } else {
            throw new IllegalArgumentException("No corner exists at the coordinates: " + location);
        }
    }

    /**
     * Stores the data for the edge denoted by the two points.
     *
     * @param location the location of the corner
     * @param data     the data to be stored
     * @return the old data entry (or null)
     * @throws IllegalArgumentException if there is no corner at this location
     */
    public C setCorner(Point location, C data) {
        C old = corner.get(location);
        if (corner.containsKey(location)) {
            corner.put(location, data);
            return old;
        } else {
            throw new IllegalArgumentException(
                    "Corner does not exist => no data can be stored: " + location);
        }
    }

    /**
     * Returns the (non-null) corner data elements of the corners that are direct
     * neighbors of the specified corner.
     * <p>
     * Each corner has three direct neighbors, except corners that are located at
     * the border of the game board.
     * </p>
     *
     * @param center the location of the corner for which to return the direct
     *               neighbors
     * @return list with non-null corner data elements
     */
    public List<C> getNeighboursOfCorner(Point center) {
        List<C> result = new LinkedList<>();
        for (Point c : HexBoard.getAdjacentCorners(center)) {
            C temp = corner.get(c);
            if (temp != null) {
                result.add(temp);
            }
        }
        return result;
    }

    /**
     * Returns the (non-null) edge data elements of the edges that directly connect
     * to that corner.
     * <p>
     * Each corner has three edges connecting to it, except edges that are located
     * at the border of the game board.
     * </p>
     *
     * @param corner corner for which to get the edges
     * @return list with non-null edge data elements of edges connecting to the
     * specified edge
     */
    public List<E> getAdjacentEdges(Point corner) {
        List<E> result = new LinkedList<>();
        for (Entry<Edge, E> e : this.edge.entrySet()) {
            if (e.getKey().isEdgePoint(corner)
                    && e.getValue() != null) {
                result.add(e.getValue());
            }
        }
        return result;
    }

    /**
     * Returns the (non-null) data elements of the corners of the specified field.
     *
     * @param center the location of the field
     * @return list with non-null corner data elements
     */
    public List<C> getCornersOfField(Point center) {
        List<C> result = new LinkedList<>();
        for (Point c : getCornerCoordinatesOfField(center)) {
            C temp = getCorner(c);
            if (temp != null) {
                result.add(temp);
            }
        }
        return result;
    }

    int getMaxCoordinateX() {
        return maxCoordinateX;
    }

    int getMaxCoordinateY() {
        return maxCoordinateY;
    }

    /**
     * Checks whether there is a corner at that specified location.
     *
     * @param location the location to check
     * @return true, if there is a corner at this location
     */
    public boolean hasCorner(Point location) {
        if (!HexBoard.isCornerCoordinate(location)) {
            return false;
        }
        return corner.containsKey(location);
    }

    /**
     * Checks whether there is an edge between the two points.
     *
     * @param p1 first point
     * @param p2 second point
     * @return true, if there is an edge between the two points
     */
    public boolean hasEdge(Point p1, Point p2) {
        if (Edge.isEdge(p1, p2)) {
            return edge.containsKey(new Edge(p1, p2));
        } else {
            return false;
        }
    }

    static boolean isCorner(Point field, Point corner) {
        return HexBoard.isFieldCoordinate(field)
                && HexBoard.getCornerCoordinatesOfField(field).contains(corner);
    }

}
