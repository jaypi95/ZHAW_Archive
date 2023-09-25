package ch.zhaw.pm2.racetrack.game.strategy.Dijkstra;

import ch.zhaw.pm2.racetrack.exceptions.InvalidTrackFormatException;
import ch.zhaw.pm2.racetrack.game.logic.Config;
import ch.zhaw.pm2.racetrack.game.logic.PositionVector;
import ch.zhaw.pm2.racetrack.game.logic.Track;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Dijkstra {

    /**
     * Finds the shortest path from the startPosition to the finish line. The finish line is
     * automatically chosen based on the length of the path
     *
     * @param startPosition starting car position usually, but can be any position
     * @return path from startPosition to the finish line. /!\ Path does not contain startPosition and finish line.
     */
    public static ArrayList<PositionVector> getShortestPath(PositionVector startPosition) {
        // create the nodes and connect them
        ArrayList<Node> nodes = createNodes();
        connectNodes(nodes);

        // set the start node
        Node startNode = nodes.stream().filter(node -> node.getPosition().equals(startPosition)).findFirst().get();
        calculateShortestPathFromSource(startNode);

        // set end node
        List<Node> finishLineNodes = nodes.stream().filter(node ->
            node.getName() == Config.SpaceType.FINISH_DOWN.value ||
                node.getName() == Config.SpaceType.FINISH_UP.value ||
                node.getName() == Config.SpaceType.FINISH_LEFT.value ||
                node.getName() == Config.SpaceType.FINISH_RIGHT.value).collect(Collectors.toList());
        // find which finish line node has the shortest path
        Node shortestFinish = finishLineNodes.stream().min(Comparator.comparingInt(Node::getDistance)).get();
        List<Node> path = shortestFinish.getShortestPath();
        path.add(finishLineNodes.get(finishLineNodes.indexOf(shortestFinish))); // we need to add the finish line node to the path

        // convert ArrayList<Node> to ArrayList<PositionVector>
        return (ArrayList<PositionVector>) path.stream().map(Node::getPosition).collect(Collectors.toList());
    }

    /**
     * Connect all nodes. The finish line must not be connected to the nodes right after it. Example:
     * ##### The finish line must be connected to Pos(1,1) but not Pos(3,1)
     * # > # to prevent the cars from reversing in to the finish line at the beginning
     * #####
     *
     * @param nodes the graph of nodes
     */
    private static void connectNodes(ArrayList<Node> nodes) {
        for (Node currentNode : nodes) {

            // if node is a normal track node
            if (currentNode.getName() == Config.SpaceType.TRACK.value) {
                //                                                    we need to filter the nodes array here because we only wanna connect the track nodes here
                ArrayList<Node> adjacentNodes = getAdjacentNodes((ArrayList<Node>) nodes.stream().filter(node -> node.getName() == Config.SpaceType.TRACK.value).collect(Collectors.toList()), currentNode);
                // no need to connect the adjacent nodes to current node because
                // we are already iterating over all the nodes, so it's gonna be connected in a next iteration
                for (Node adjNode : adjacentNodes) {
                    // if node is not diagonally adjacent, distance is 1
                    if (isAdjacentNonDiag(currentNode, adjNode)) {
                        currentNode.addDestination(adjNode, 1);
                    } else { // if diagonal -> distance longer
                        currentNode.addDestination(adjNode, 2);
                    }
                }
            } else { // * if current node is a finish line node, connect the finish line, but only in 1 direction
                ArrayList<Node> finish_line_nodes = (ArrayList<Node>) nodes.stream().filter(node -> node.getName() != Config.SpaceType.TRACK.value).collect(Collectors.toList());

                for (Node finish_node : finish_line_nodes) {

                    Stream<Node> adjacentNodes = getAdjacentNodes(nodes, finish_node).stream();
                    // filter out the adjacent nodes that are after the finish line
                    if (finish_node.getName() == Config.SpaceType.FINISH_RIGHT.value) {
                        adjacentNodes = adjacentNodes.filter(node -> finish_node.getPosition().getX() == node.getPosition().getX() + 1);
                    } else if (finish_node.getName() == Config.SpaceType.FINISH_LEFT.value) {
                        adjacentNodes = adjacentNodes.filter(node -> finish_node.getPosition().getX() == node.getPosition().getX() - 1);
                    } else if (finish_node.getName() == Config.SpaceType.FINISH_UP.value) {
                        adjacentNodes = adjacentNodes.filter(node -> finish_node.getPosition().getY() == node.getPosition().getY() - 1);
                    } else if (finish_node.getName() == Config.SpaceType.FINISH_DOWN.value) {
                        adjacentNodes = adjacentNodes.filter(node -> finish_node.getPosition().getY() == node.getPosition().getY() + 1);
                    }
                    for (Node adjNode : adjacentNodes.collect(Collectors.toList())) {
                        finish_node.addDestination(adjNode, 1); // we need a two way connection here
                        adjNode.addDestination(finish_node, 1);
                    }
                }
            }
        }
    }

    /**
     * Returns all the adjacent nodes to the current node (including diagonals and finish line)
     *
     * @param nodes       nodes
     * @param currentNode currentNode
     * @return true or false
     */
    private static ArrayList<Node> getAdjacentNodes(ArrayList<Node> nodes, Node currentNode) {
        return (ArrayList<Node>) nodes.stream().filter(node ->
            isAdjacentNonDiag(currentNode, node) || isAdjacentDiag(currentNode, node)
        ).collect(Collectors.toList());
    }

    /**
     * Checks if the primary node is adjacent to the secondary node (without the diagonals)
     *
     * @param primary   primary node
     * @param secondary secondary node
     * @return adjacent true or false
     */
    private static boolean isAdjacentNonDiag(Node primary, Node secondary) {
        return (primary.getPosition().getX() == secondary.getPosition().getX() + 1 && primary.getPosition().getY() == secondary.getPosition().getY()) ||
            (primary.getPosition().getX() == secondary.getPosition().getX() - 1 && primary.getPosition().getY() == secondary.getPosition().getY()) ||
            (primary.getPosition().getY() == secondary.getPosition().getY() - 1 && primary.getPosition().getX() == secondary.getPosition().getX()) ||
            (primary.getPosition().getY() == secondary.getPosition().getY() + 1 && primary.getPosition().getX() == secondary.getPosition().getX());
    }

    /**
     * Checks if the primary node is adjacent to the secondary node (only checks diagonal adjacency)
     *
     * @param primary   primary node
     * @param secondary secondary node
     * @return adjacent true or false
     */
    private static boolean isAdjacentDiag(Node primary, Node secondary) {
        return (primary.getPosition().getX() == secondary.getPosition().getX() + 1 && primary.getPosition().getY() == secondary.getPosition().getY() + 1) ||
            (primary.getPosition().getX() == secondary.getPosition().getX() + 1 && primary.getPosition().getY() == secondary.getPosition().getY() - 1) ||
            (primary.getPosition().getX() == secondary.getPosition().getX() - 1 && primary.getPosition().getY() == secondary.getPosition().getY() - 1) ||
            (primary.getPosition().getX() == secondary.getPosition().getX() - 1 && primary.getPosition().getY() == secondary.getPosition().getY() + 1);
    }

    /**
     * Reads the track file and creates a node for everything that isn't an obstacle (aka track and finish line).
     * Each node contains its char and its position
     *
     * @return array of the nodes (the graph)
     */
    private static ArrayList<Node> createNodes() {
        char[][] data;

        try {
            data = new Track(Track.getTrackFile()).getTrackData();
        } catch (FileNotFoundException | InvalidTrackFormatException ignored) {
            // this should not happen, unless the file was modified during gameplay
            // because the file has been checked previously
            data = new char[0][0];
        }
        // remove any cars from the track data because they mess up the algorithm
        for (int lineIndex = 0; lineIndex < data.length; lineIndex++) {
            for (int charIndex = 0; charIndex < data[lineIndex].length; charIndex++) {
                // if character is not in the SpaceType values then it's a car
                if (!Arrays.stream(Config.SpaceType.values()).map(e -> e.value).collect(Collectors.toList()).contains(data[lineIndex][charIndex])) {
                    data[lineIndex][charIndex] = Config.SpaceType.TRACK.value;
                }
            }
        }
        ArrayList<Node> finalArray = new ArrayList<>();

        for (int line = 0; line < data.length; line++) {
            for (int ch = 0; ch < data[line].length; ch++) {
                if (data[line][ch] != Config.SpaceType.WALL.value) {
                    finalArray.add(new Node(data[line][ch], new PositionVector(ch, line)));
                }
            }
        }
        return finalArray;
    }

    /**
     * Sets the distance from the source for every node in the graph
     *
     * @param source start node
     */
    private static void calculateShortestPathFromSource(Node source) {
        source.setDistance(0);

        Set<Node> settledNodes = new HashSet<>();
        Set<Node> unsettledNodes = new HashSet<>();
        unsettledNodes.add(source);

        while (unsettledNodes.size() != 0) {
            Node currentNode = getLowestDistanceNode(unsettledNodes);
            unsettledNodes.remove(currentNode);
            for (Map.Entry<Node, Integer> adjacencyPair : currentNode.getAdjacentNodes().entrySet()) {
                Node adjacentNode = adjacencyPair.getKey();
                Integer edgeWeigh = adjacencyPair.getValue();

                if (!settledNodes.contains(adjacentNode)) {
                    CalculateMinimumDistance(adjacentNode, edgeWeigh, currentNode);
                    unsettledNodes.add(adjacentNode);
                }
            }
            settledNodes.add(currentNode);
        }
    }

    /**
     * Compares the actual distance with the newly calculated node while following the newly explored path
     *
     * @param evaluationNode evaluationNode
     * @param edgeWeigh      edge weigh
     * @param sourceNode     source node
     */
    private static void CalculateMinimumDistance(Node evaluationNode, Integer edgeWeigh, Node sourceNode) {
        Integer sourceDistance = sourceNode.getDistance();
        if (sourceDistance + edgeWeigh < evaluationNode.getDistance()) {
            evaluationNode.setDistance(sourceDistance + edgeWeigh);
            LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
            shortestPath.add(sourceNode);
            evaluationNode.setShortestPath(shortestPath);
        }
    }

    /**
     * Returns the node with the lowest distance from the unsettled nodes set
     *
     * @param unsettledNodes unsettled nodes
     * @return node with the lowest distance from the source
     */
    private static Node getLowestDistanceNode(Set<Node> unsettledNodes) {
        Node lowestDistanceNode = null;
        int lowestDistance = Integer.MAX_VALUE;
        for (Node node : unsettledNodes) {
            int nodeDistance = node.getDistance();
            if (nodeDistance < lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }
        return lowestDistanceNode;
    }
}
