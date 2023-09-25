package ch.zhaw.catan;

import ch.zhaw.catan.Config.Land;
import ch.zhaw.hexboard.HexBoard;
import ch.zhaw.hexboard.Label;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility methods for creating and modifying the GameField
 *
 * @author Jon Defilla
 */
public class SiedlerBoard extends HexBoard<Land, String, String, Annotation> {
    private final SiedlerBoardTextView view;
    private Point positionOfThief;

    /**
     * Initialises the board with the numbers and fields
     */
    public SiedlerBoard() {
        view = new SiedlerBoardTextView(this);

        // build the board
        for (Map.Entry<Point, Land> landPoint : Config.getStandardLandPlacement().entrySet()) {
            addField(landPoint.getKey(), landPoint.getValue());
        }

        // add the numbers to the board view
        for (Map.Entry<Point, Integer> landValue : Config.getStandardDiceNumberPlacement().entrySet()) {
            if (landValue.getValue() < 10) {
                view.setLowerFieldLabel(landValue.getKey(), new Label(' ', landValue.getValue().toString().toCharArray()[0]));
            } else {
                view.setLowerFieldLabel(landValue.getKey(), new Label('1', landValue.getValue().toString().toCharArray()[1]));
            }
        }
        placeThief(new Point(7, 11));
    }

    /**
     * Get the board view
     *
     * @return the board view
     */
    public SiedlerBoardTextView getView() {
        return view;
    }

    /**
     * This method checks if the road is permitted to be placed at a certain Point
     * It checks for example that there isn't already another road on the Point or that there is a Settlement or City next to the road
     * If there is no error, place the road
     *
     * @param start start position of the road
     * @param end   end position of the road
     * @return true if successful, otherwise false
     */
    public boolean buildRoad(Point start, Point end, Config.Faction faction) {
        boolean canBuild = true;
        // check if there is already a road at position
        if (getEdge(start, end) != null) {
            canBuild = false;
        }
        // check if trying to build on water
        // if building on water, position only has 2 neighbour fields
        if (getFields(start).size() < 3 || getFields(end).size() < 3) {
            canBuild = false;
        }
        // there must be a city or a settlement near the road of the same faction
        if (!faction.toString().equals(getCorner(start)) && !faction.toString().equals(getCorner(end))) {
            // if there are no other roads next to the road to be built
            if (!getAdjacentEdges(start).contains(faction.toString()) && !getAdjacentEdges(end).contains(faction.toString())) {
                canBuild = false;
            }
        }
        if (canBuild) {
            setEdge(start, end, faction.toString());
        }
        return canBuild;
    }

    /**
     * This method checks if the Settlement is permitted to be placed at a certain Point
     * If the position is busy, returns false. If not, places the settlement
     * and returns true
     *
     * @param position position
     * @param faction  faction of the settlement
     * @return true if successful
     */
    public boolean buildSettlement(Point position, Config.Faction faction) {
        boolean canBuild = true;
        // check if there is already a settlement at position
        if (getCorner(position) != null) {
            canBuild = false;
        }
        // distance rules - there must be at least 2 roads between settlements and cities
        // no matter the faction
        for (Point adjacentCorner : getAdjacentCorners(position)) {
            String corner = getCorner(adjacentCorner);
            if (corner != null) {
                canBuild = false;
            }
        }
        // check if the settlement is in the water
        // if building on water, position only has 2 neighbour fields
        if (getFields(position).size() < 3) {
            canBuild = false;
        }
        if (canBuild) {
            setCorner(position, faction.toString());
        }
        return canBuild;
    }

    /**
     * This method checks if the City is permitted to be placed at a certain Point and if it is
     * it builds a city at position for the faction
     *
     * @param position Point to build the city at (corner)
     * @param faction  faction
     * @return true of successful, otherwise false
     */
    public boolean buildCity(Point position, Config.Faction faction) {
        boolean canBuild = false;

        // check if there is already a settlement at position
        if (getCorner(position) != null && getCorner(position).equals(faction.toString())) {
            setCorner(position, faction.toString().toUpperCase());
            canBuild = true;
        }
        return canBuild;
    }

    /**
     * Return the number of resources that need to be distributed to each faction after a dice throw
     *
     * @param diceThrow the number that was thrown with the dice
     * @param faction faction
     */
    public Map<Config.Resource, Long> getResourceForPlayerNearNumber(int diceThrow, Config.Faction faction) {
        Map<Config.Resource, Long> resources = new HashMap<>();

        // this hashmap contains the center of a field with the corners next to that field (only for one faction)
        // example: new Point(4,4), ["rr", "RR", "rr"]
        HashMap<Point, List<String>> centerOfFieldWithCorners = new HashMap<>();

        Map<Point, Integer> fieldsWithNumber = new HashMap<>(Config.getStandardDiceNumberPlacement());
        // remove all fields that don't have the diceThrow number
        fieldsWithNumber.entrySet().removeIf(entry -> entry.getValue() != diceThrow);
        //remove field with thief
        fieldsWithNumber.remove(positionOfThief);

        for (Map.Entry<Point, Integer> fieldWithNumber : fieldsWithNumber.entrySet()) {
            List<String> cornersOfField = getCornersOfField(fieldWithNumber.getKey());
            // remove all corners that are not the player's faction
            cornersOfField.removeIf(entries -> !entries.equals(faction.toString()));
            // save the center of the field with the faction's structures
            centerOfFieldWithCorners.put(fieldWithNumber.getKey(), cornersOfField);
        }

        for (Map.Entry<Point, List<String>> centerFieldCorners : centerOfFieldWithCorners.entrySet()) {
            // convert field point to land enum and get land's resource
            Config.Resource resourceOfField = getField(centerFieldCorners.getKey()).getResource();
            List<String> structuresOnField = centerFieldCorners.getValue();

            if (structuresOnField.isEmpty()) {
                continue; // continue because faction doesn't have any structures on that field
            }
            int numberOfSettlementsOnField = structuresOnField.stream().filter(structure -> structure.equals(faction.toString().toLowerCase())).toArray().length;
            int numberOfCitiesOnField = structuresOnField.stream().filter(structure -> structure.equals(faction.toString().toUpperCase())).toArray().length;

            resources.put(resourceOfField, (long) (numberOfSettlementsOnField + numberOfCitiesOnField * 2));
        }

        return resources;
    }

    /**
     * This method checks if the thief can be placed at a specific position
     *
     * @param field the field on which the thief should be placed
     */
    public boolean canPlaceThief(Point field) {
        boolean validPosition = true;

        try {
            Land land = getField(field);
            if (land == Land.WATER | land == Land.DESERT) {
                validPosition = false;
            }
        } catch (IllegalArgumentException illegalArgumentException) {
            validPosition = false;
        }

        return validPosition;
    }

    /**
     * This methods places the thief at a specific position
     *
     * @param field the position on which the thief should be placed
     */
    public void placeThief(Point field) {
        List annotationList = getAdjacentCorners(field);
        try {
            addFieldAnnotation(field, (Point) annotationList.get(0), new Annotation("TT"));
        } catch (IllegalArgumentException illegalArgumentException) {
            Annotation thief = getFieldAnnotation(field, (Point) annotationList.get(0));
            thief.setAnnotationContent("TT");
        }
        if (positionOfThief != null) {
            annotationList = getAdjacentCorners(positionOfThief);
            Annotation thief = getFieldAnnotation(positionOfThief, (Point) annotationList.get(0));
            thief.setAnnotationContent("  ");
        }
        positionOfThief = field;

    }

    /**
     * Check which players have a settlement or City at the specific Field
     *
     * @param field The point of the field
     * @return An ArrayList with the factions that have a settlement or City at the field
     */
    public ArrayList<String> checkWhichPlayersHaveACityOrSettlementAtField(Point field) {
        ArrayList<String> factionsWithSettlementAtField = new ArrayList<>();
        for (String faction : getCornersOfField(field)) {
            for (Config.Faction factions : Config.Faction.values()) {
                if (faction.equalsIgnoreCase(factions.toString())) {
                    factionsWithSettlementAtField.add(faction);
                }
            }
        }
        return factionsWithSettlementAtField;
    }
}

