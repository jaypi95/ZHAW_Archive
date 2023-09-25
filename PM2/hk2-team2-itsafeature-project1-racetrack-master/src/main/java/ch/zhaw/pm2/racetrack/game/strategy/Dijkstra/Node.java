package ch.zhaw.pm2.racetrack.game.strategy.Dijkstra;

import ch.zhaw.pm2.racetrack.game.logic.PositionVector;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Node {

    private final char name;
    private final PositionVector position;
    private LinkedList<Node> shortestPath = new LinkedList<>();
    private Integer distance = Integer.MAX_VALUE;
    private Map<Node, Integer> adjacentNodes = new HashMap<>();

    public Node(char name, PositionVector position) {
        this.name = name;
        this.position = position;
    }

    /**
     * @return the node's position in the graph
     */
    public PositionVector getPosition() {
        return position;
    }

    /**
     * Adds individual adjacent nodes to this node
     *
     * @param destination adjacent node
     * @param distance    distance between the two
     */
    public void addDestination(Node destination, int distance) {
        adjacentNodes.put(destination, distance);
    }

    /**
     * @return node name (is identical to the char on the track that it represents)
     */
    public char getName() {
        return name;
    }

    /**
     * @return the adjacent nodes
     */
    public Map<Node, Integer> getAdjacentNodes() {
        return adjacentNodes;
    }

    /**
     * @param adjacentNodes adjacent nodes
     */
    public void setAdjacentNodes(Map<Node, Integer> adjacentNodes) {
        this.adjacentNodes = adjacentNodes;
    }

    /**
     * @return the distance from the start node
     */
    public Integer getDistance() {
        return distance;
    }

    /**
     * Set the distance of the node from the start node
     *
     * @param distance distance
     */
    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    /**
     * @return the path from the start node
     */
    public List<Node> getShortestPath() {
        return shortestPath;
    }

    /**
     * Sets the shortest path from start node
     *
     * @param shortestPath path from start node
     */
    public void setShortestPath(LinkedList<Node> shortestPath) {
        this.shortestPath = shortestPath;
    }

}
