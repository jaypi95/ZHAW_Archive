package ch.zhaw.catan;

import java.awt.Point;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * This class specifies the most important and basic parameters of the game
 * Catan.
 * <p>
 * The class provides definitions such as for the type and number of resource
 * cards or the number of available road elements per player. Furthermore, it
 * provides a dice number to field and a field to land type mapping for the
 * standard setup detailed <a href=
 * "https://www.catan.de/files/downloads/4002051693602_catan_-_das_spiel_0.pdf">here</a>
 * </p>
 *
 * @author tebe
 */
public class Config {
    // Minimum number of players
    // Note: The max. number is equal to the number of factions (see Faction enum)
    public static final int MIN_NUMBER_OF_PLAYERS = 2;

    // Initial thief position (on the desert field)
    public static final Point INITIAL_THIEF_POSITION = new Point(7, 11);

    // Available factions
    public enum Faction {
        RED("rr"), BLUE("bb"), GREEN("gg"), YELLOW("yy");

        private String name;

        private Faction(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    // RESOURCE CARD DECK
    public static final Map<Resource, Integer> INITIAL_RESOURCE_CARDS_BANK = Map.of(Resource.WOOD, 19,
            Resource.CLAY, 19, Resource.WOOL, 19, Resource.GRAIN, 19, Resource.STONE, 19);

    // SPECIFICATION OF AVAILABLE RESOURCE TYPES

    /**
     * This {@link Enum} specifies the available resource types in the game.
     *
     * @author tebe
     */
    public enum Resource {
        GRAIN("GR"), WOOL("WL"), WOOD("WD"), STONE("ST"), CLAY("CL");

        private String name;

        private Resource(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    // SPECIFICATION OF AVAILABLE LAND TYPES

    /**
     * This {@link Enum} specifies the available lands in the game. Some land types
     * provide a resource (e.g., {@value Land#FOREST}, others do not (e.g.,
     * {@value Land#WATER}.
     *
     * @author tebe
     */
    public enum Land {
        FOREST(Resource.WOOD), MEADOW(Resource.WOOL), GRAINFIELD(Resource.GRAIN),
        MOUNTAIN(Resource.STONE), CLAYSOIL(Resource.CLAY), WATER("~~"), DESERT("--");

        private Resource resource = null;
        private String name;

        private Land(Resource resource) {
            this(resource.toString());
            this.resource = resource;
        }

        private Land(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }

        /**
         * Returns the {@link Resource} that this land provides or null,
         * if it does not provide any.
         *
         * @return the {@link Resource} or null
         */
        public Resource getResource() {
            return resource;
        }
    }

    // STRUCTURES (with costs)
    private static final int NUMBER_OF_ROADS_PER_PLAYER = 15;
    private static final int NUMBER_OF_SETTLEMENTS_PER_PLAYER = 5;
    private static final int NUMBER_OF_CITIES_PER_PLAYER = 4;
    public static final int MAX_CARDS_IN_HAND_NO_DROP = 7;

    /**
     * This enum models the different structures that can be built.
     * <p>
     * The enum provides information about the cost of a structure and how many of
     * these structures are available per player.
     * </p>
     */
    public enum Structure {
        SETTLEMENT(List.of(Resource.WOOD, Resource.CLAY, Resource.WOOL, Resource.GRAIN),
                NUMBER_OF_SETTLEMENTS_PER_PLAYER),
        CITY(List.of(Resource.STONE, Resource.STONE, Resource.STONE, Resource.GRAIN, Resource.GRAIN),
                NUMBER_OF_CITIES_PER_PLAYER),
        ROAD(List.of(Resource.WOOD, Resource.CLAY), NUMBER_OF_ROADS_PER_PLAYER);

        private List<Resource> costs;
        private int stockPerPlayer;

        private Structure(List<Resource> costs, int stockPerPlayer) {
            this.costs = costs;
            this.stockPerPlayer = stockPerPlayer;
        }

        /**
         * Returns a list with the resources needed to build this structure.
         * <p>
         * If multiple resources of one resource type (e.g., {@link Resource#WOOD} are
         * needed for a specific structure, this resource appears the respective number
         * of times in the list of resources.
         * </p>
         *
         * @return the list of resources
         */
        public List<Resource> getCosts() {
            return costs;
        }

        /**
         * Returns a map with the resource types and their number needed to build this
         * structure.
         *
         * @return the map of the required resource types and their number
         */
        public Map<Resource, Long> getCostsAsMap() {
            return costs.stream()
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        }

        /**
         * Returns the number of pieces that are available of a certain structure (per
         * player). For example, there are {@link Config#NUMBER_OF_ROADS_PER_PLAYER}
         * pieces of the structure {@link ROAD} per player.
         *
         * @return the stock per player
         */
        public int getStockPerPlayer() {
            return stockPerPlayer;
        }
    }

    // STANDARD FIXED DICE NUMBER TO FIELD SETUP

    /**
     * Returns a mapping of the dice values per field.
     *
     * @return the dice values per field
     */
    public static final Map<Point, Integer> getStandardDiceNumberPlacement() {
        Map<Point, Integer> assignment = new HashMap<>();
        assignment.put(new Point(4, 8), 2);
        assignment.put(new Point(7, 5), 3);
        assignment.put(new Point(8, 14), 3);
        assignment.put(new Point(6, 8), 4);
        assignment.put(new Point(7, 17), 4);

        assignment.put(new Point(3, 11), 5);
        assignment.put(new Point(8, 8), 5);
        assignment.put(new Point(5, 5), 6);
        assignment.put(new Point(9, 11), 6);

        assignment.put(new Point(7, 11), 7);
        assignment.put(new Point(9, 5), 8);
        assignment.put(new Point(5, 17), 8);
        assignment.put(new Point(5, 11), 9);
        assignment.put(new Point(11, 11), 9);
        assignment.put(new Point(4, 14), 10);
        assignment.put(new Point(10, 8), 10);
        assignment.put(new Point(6, 14), 11);
        assignment.put(new Point(9, 17), 11);
        assignment.put(new Point(10, 14), 12);
        return Collections.unmodifiableMap(assignment);
    }

    // STANDARD FIXED LAND SETUP

    /**
     * Returns the field (coordinate) to {@link Land} mapping for the <a href=
     * "https://www.catan.de/files/downloads/4002051693602_catan_-_das_spiel_0.pdf">standard
     * setup</a> of the game Catan..
     *
     * @return the field to {@link Land} mapping for the standard setup
     */
    public static final Map<Point, Land> getStandardLandPlacement() {
        Map<Point, Land> assignment = new HashMap<>();
        Point[] water = {new Point(4, 2), new Point(6, 2), new Point(8, 2), new Point(10, 2),
                new Point(3, 5), new Point(11, 5), new Point(2, 8), new Point(12, 8), new Point(1, 11),
                new Point(13, 11), new Point(2, 14), new Point(12, 14), new Point(3, 17), new Point(11, 17),
                new Point(4, 20), new Point(6, 20), new Point(8, 20), new Point(10, 20)};

        for (Point p : water) {
            assignment.put(p, Land.WATER);
        }

        assignment.put(new Point(5, 5), Land.FOREST);
        assignment.put(new Point(7, 5), Land.MEADOW);
        assignment.put(new Point(9, 5), Land.MEADOW);

        assignment.put(new Point(4, 8), Land.GRAINFIELD);
        assignment.put(new Point(6, 8), Land.MOUNTAIN);
        assignment.put(new Point(8, 8), Land.GRAINFIELD);
        assignment.put(new Point(10, 8), Land.FOREST);

        assignment.put(new Point(3, 11), Land.FOREST);
        assignment.put(new Point(5, 11), Land.CLAYSOIL);
        assignment.put(new Point(7, 11), Land.DESERT);
        assignment.put(new Point(9, 11), Land.MOUNTAIN);
        assignment.put(new Point(11, 11), Land.GRAINFIELD);

        assignment.put(new Point(4, 14), Land.GRAINFIELD);
        assignment.put(new Point(6, 14), Land.MOUNTAIN);
        assignment.put(new Point(8, 14), Land.FOREST);
        assignment.put(new Point(10, 14), Land.MEADOW);

        assignment.put(new Point(5, 17), Land.MEADOW);
        assignment.put(new Point(7, 17), Land.CLAYSOIL);
        assignment.put(new Point(9, 17), Land.CLAYSOIL);

        return Collections.unmodifiableMap(assignment);
    }

}
