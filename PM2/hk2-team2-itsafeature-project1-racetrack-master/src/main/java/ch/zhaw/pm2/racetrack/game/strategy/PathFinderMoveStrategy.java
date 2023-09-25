package ch.zhaw.pm2.racetrack.game.strategy;

import ch.zhaw.pm2.racetrack.exceptions.QuitGameException;
import ch.zhaw.pm2.racetrack.game.logic.PositionVector;
import ch.zhaw.pm2.racetrack.game.strategy.Dijkstra.Dijkstra;

import java.util.List;

import static ch.zhaw.pm2.racetrack.game.strategy.PathFollowerMoveStrategy.calculateFullPath;
import static ch.zhaw.pm2.racetrack.game.strategy.PathFollowerMoveStrategy.generateDirections;

/**
 * Accelerate based on calculating the track for the shortest path
 *
 * @author defiljon
 * @date 26.03.2021
 */
public class PathFinderMoveStrategy extends Strategy implements MoveStrategy {
    private final List<PositionVector.Direction> directionList;
    private int listPosition;

    /**
     * Constructor for this Class
     *
     * @param start
     */
    public PathFinderMoveStrategy(PositionVector start) {
        List<PositionVector> waypointList = Dijkstra.getShortestPath(start);
        directionList = generateDirections(calculateFullPath(waypointList, start), start);
    }

    /**
     * @return a Direction in which the Car should move next
     * @throws QuitGameException if the user wishes to quit the game
     */
    @Override
    public PositionVector.Direction nextMove() {
        PositionVector.Direction direction = null;
        try {
            direction = directionList.get(listPosition);
            listPosition++;

            Thread.sleep(200);

        } catch (IllegalArgumentException | InterruptedException e) {
            throw new RuntimeException();
        }
        return direction;
    }
}
