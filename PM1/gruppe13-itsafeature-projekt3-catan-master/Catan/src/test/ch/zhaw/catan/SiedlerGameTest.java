package test.ch.zhaw.catan;

import ch.zhaw.catan.Config;
import ch.zhaw.catan.Config.Faction;
import ch.zhaw.catan.Config.Land;
import ch.zhaw.catan.Config.Resource;
import ch.zhaw.catan.SiedlerGame;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;


/**
 * This class contains test for the class SiedlerGame.
 * Some tests were provided by the lecturers which remained untouched.
 * The rest was written by the mentioned authors.
 *
 * @author outitbad
 * @author peterju1
 */
public class SiedlerGameTest {
    private SiedlerGame model;
    private static final int WIN_POINTS_5 = 5;
    private static final int PLAYERS = 3;
    private static final int MAX_DICE_VALUE = 12;
    private static final int MIN_DICE_VALUE = 2;

    private static final Map<Faction, Tuple<Point, Point>> first = Map.of(Faction.values()[0],
            new Tuple<>(new Point(5, 7), new Point(6, 6)), Faction.values()[1],
            new Tuple<>(new Point(11, 13), new Point(12, 12)), Faction.values()[2],
            new Tuple<>(new Point(2, 12), new Point(2, 10)));
    private static final Map<Faction, Tuple<Point, Point>> second = Map.of(Faction.values()[0],
            new Tuple<>(new Point(10, 16), new Point(9, 15)), Faction.values()[1],
            new Tuple<>(new Point(5, 15), new Point(5, 13)), Faction.values()[2],
            new Tuple<>(new Point(7, 19), new Point(8, 18)));

    private Map<Integer, Map<Faction, List<Resource>>> expectedDiceThrowPayout = Map.of(
            2, Map.of(
                    Faction.values()[0], List.of(Resource.GRAIN),
                    Faction.values()[1], List.of(),
                    Faction.values()[2], List.of()),
            3, Map.of(
                    Faction.values()[0], List.of(),
                    Faction.values()[1], List.of(),
                    Faction.values()[2], List.of()),
            4, Map.of(
                    Faction.values()[0], List.of(Resource.STONE),
                    Faction.values()[1], List.of(),
                    Faction.values()[2], List.of(Resource.CLAY)),
            5, Map.of(
                    Faction.values()[0], List.of(),
                    Faction.values()[1], List.of(),
                    Faction.values()[2], List.of(Resource.WOOD)),
            6, Map.of(
                    Faction.values()[0], List.of(Resource.WOOD),
                    Faction.values()[1], List.of(),
                    Faction.values()[2], List.of()),
            8, Map.of(
                    Faction.values()[0], List.of(),
                    Faction.values()[1], List.of(Resource.WOOL),
                    Faction.values()[2], List.of()),
            9, Map.of(
                    Faction.values()[0], List.of(),
                    Faction.values()[1], List.of(Resource.GRAIN),
                    Faction.values()[2], List.of()),
            10, Map.of(
                    Faction.values()[0], List.of(),
                    Faction.values()[1], List.of(Resource.GRAIN),
                    Faction.values()[2], List.of()),
            11, Map.of(
                    Faction.values()[0], List.of(Resource.CLAY),
                    Faction.values()[1], List.of(Resource.STONE),
                    Faction.values()[2], List.of()),
            12, Map.of(
                    Faction.values()[0], List.of(Resource.WOOL),
                    Faction.values()[1], List.of(Resource.WOOL),
                    Faction.values()[2], List.of()));

    private Map<Faction, Map<Resource, Integer>>
            initialResourceStockThreePlayerBoardStandard = Map.of(
            Faction.values()[0], Map.of(Resource.WOOL, 1, Resource.CLAY, 1),
            Faction.values()[1], Map.of(Resource.GRAIN, 1, Resource.WOOL, 1, Resource.STONE, 1),
            Faction.values()[2], Map.of(Resource.CLAY, 1));

    @Test
    public void requirementPlayerSwitching() {
        for (int players : List.of(2, 3, 4)) {
            initializeSiedlerGame(WIN_POINTS_5, players);
            assertTrue(players == model.getPlayerFactions().size(),
                    "Wrong number of players returned by getPlayers()");
            //Switching forward and testing method "getPlayerFaction"
            for (int i = 0; i < players; i++) {
                assertEquals(Config.Faction.values()[i], model.getCurrentPlayerFaction(),
                        "Player order does not match order of Faction.values()");
                model.switchToNextPlayer();
            }
            assertEquals(Config.Faction.values()[0], model.getCurrentPlayerFaction(),
                    "Player wrap-around from last player to first player did not work.");
            //Switching backward
            for (int i = players - 1; i >= 0; i--) {
                model.switchToPreviousPlayer();
                assertEquals(Config.Faction.values()[i], model.getCurrentPlayerFaction(),
                        "Switching players in reverse order does not work as expected.");
            }
        }
    }

    @Test
    public void requirementSetupTestBoardUsedWithTheTests() {
        requirementPlayerSwitching();
        bootstrapTestBoardForThreePlayersStandard(WIN_POINTS_5);

        //Land placement ok?
        assertTrue(Config.getStandardLandPlacement().size() == model.getBoard().getFields().size(),
                "Check if explicit init must be done (violates spec): "
                        + "modify initializeSiedlerGame accordingly.");
        for (Map.Entry<Point, Land> e : Config.getStandardLandPlacement().entrySet()) {
            assertEquals(e.getValue(), model.getBoard().getField(e.getKey()),
                    "Land placement does not match default placement.");
        }

        //Initial settlements/roads placed?
        assertTrue(model.getPlayerFactions().size() == PLAYERS);
        for (Faction f : model.getPlayerFactions()) {
            assertTrue(model.getBoard().getCorner(first.get(f).first) != null);
            assertTrue(model.getBoard().getEdge(first.get(f).first, first.get(f).second) != null);
            assertTrue(model.getBoard().getCorner(second.get(f).first) != null);
            assertTrue(model.getBoard().getEdge(second.get(f).first, second.get(f).second) != null);
        }
    }

    @Test
    public void requirementResourcePayoutAndReturnValueForDiceThrow() {
        requirementSetupTestBoardUsedWithTheTests();
        //Return value
        for (int i = MIN_DICE_VALUE; i <= MAX_DICE_VALUE; i++) {
            Map<Faction, List<Resource>> result = model.throwDice(i);
            Map<Faction, List<Resource>> expected = expectedDiceThrowPayout.get(i);
            if (expected == null) {
                for (Map.Entry<Faction, List<Resource>> e : result.entrySet()) {
                    assertTrue(e.getValue() == null || e.getValue().isEmpty());
                }
            } else {
                for (Map.Entry<Faction, List<Resource>> e : expected.entrySet()) {
                    if (result.get(e.getKey()) != null) {
                        assertTrue(compareLists(e.getValue(), result.get(e.getKey())),
                                "Expected resources: " + e.getValue() + ". Got: " + result.get(e.getKey()));
                    } else {
                        assertTrue(e.getValue() == null || e.getValue().isEmpty());
                    }
                }
            }
        }

        //Resource payout
        Map<Faction, Map<Resource, Integer>> expected = Map.of(
                Faction.values()[0], Map.of(Resource.GRAIN, 1, Resource.WOOL, 1,
                        Resource.CLAY, 1, Resource.STONE, 1, Resource.WOOD, 1),
                Faction.values()[1],
                Map.of(Resource.GRAIN, 2, Resource.WOOL, 2, Resource.CLAY, 0,
                        Resource.STONE, 1, Resource.WOOD, 0),
                Faction.values()[2],
                Map.of(Resource.GRAIN, 0, Resource.WOOL, 0, Resource.CLAY, 1,
                        Resource.STONE, 0, Resource.WOOD, 1));

        for (int i = 0; i < model.getPlayerFactions().size(); i++) {
            Faction f = model.getCurrentPlayerFaction();
            for (Resource r : Resource.values()) {
                int hasAlready = initialResourceStockThreePlayerBoardStandard.get(f).getOrDefault(r, 0);
                assertEquals(expected.get(f).get(r) + hasAlready,
                        model.getCurrentPlayerResourceStock(r),
                        "Payout not equal for player " + i + " and resource " + r);
            }
            model.switchToNextPlayer();
        }
    }

    /**
     * Test getCurrentPlayerResourceStock when the players have no resources
     */
    @Test
    public void testGetCurrentPlayerResourceStockNoResources() {
        //SetUp no players has Resources
        initializeSiedlerGame(WIN_POINTS_5, 3);

        //Test no player has Resources
        for (Resource resource : Config.Resource.values()) {
            assertEquals(0, model.getCurrentPlayerResourceStock(resource));
        }
    }

    /**
     * Test getCurrentPlayerResourceStock when the players have enough resources
     */
    @Test
    public void testGetCurrentPlayerResourceStockHaveResources() {
        //SetUp players have Resources (initialResourceStockThreePlayerBoardStandard)
        requirementSetupTestBoardUsedWithTheTests();

        //Test player 1 has 1WOOL and 1CLAY
        assertEquals(1, model.getCurrentPlayerResourceStock(Resource.WOOL));
        assertEquals(1, model.getCurrentPlayerResourceStock(Resource.CLAY));
        assertEquals(0, model.getCurrentPlayerResourceStock(Resource.GRAIN));
        assertEquals(0, model.getCurrentPlayerResourceStock(Resource.WOOD));
        assertEquals(0, model.getCurrentPlayerResourceStock(Resource.STONE));

        model.switchToNextPlayer();//Switch to player 2

        //Test player 2 has 1WOOL, 1GRAIN and 1STONE
        assertEquals(1, model.getCurrentPlayerResourceStock(Resource.WOOL));
        assertEquals(0, model.getCurrentPlayerResourceStock(Resource.CLAY));
        assertEquals(1, model.getCurrentPlayerResourceStock(Resource.GRAIN));
        assertEquals(0, model.getCurrentPlayerResourceStock(Resource.WOOD));
        assertEquals(1, model.getCurrentPlayerResourceStock(Resource.STONE));

        model.switchToPreviousPlayer();//Switch to player 1

        model.throwDice(2); //ADD 1 GRAIN

        //Test player 1 has 1 GRAIN
        assertEquals(1, model.getCurrentPlayerResourceStock(Resource.GRAIN));

        model.switchToNextPlayer(); //Switch to player 2

        //Give player 2, 1 WOOL
        model.throwDice(8); //ADD 1 WOOL

        //Test player 2 has 2 WOOL
        assertEquals(2, model.getCurrentPlayerResourceStock(Resource.WOOL));

        model.switchToPreviousPlayer(); //Switch to player 1

        //Test player 1 has 1 WOOL
        assertEquals(1, model.getCurrentPlayerResourceStock(Resource.WOOL));

        model.throwDice(12); //ADD 1 WOOL for both players

        //Test player 1 has 2 WOOL
        assertEquals(2, model.getCurrentPlayerResourceStock(Resource.WOOL));

        model.switchToNextPlayer(); //Switch to player 2

        //Test player 2 has 3 WOOL
        assertEquals(3, model.getCurrentPlayerResourceStock(Resource.WOOL));
    }

    /**
     * Test tradeWithBank when the players have no resources
     */
    @Test
    public void testTradeWithBankPlayerHasNoResources() {
        //SetUp no players has Resources
        initializeSiedlerGame(WIN_POINTS_5, 3);

        //Test player has not enough Resources
        assertFalse(model.tradeWithBankFourToOne(Resource.WOOL, Resource.GRAIN));
    }

    /**
     * Test tradeWithBank when the players have not enough resources
     */
    @Test
    public void testTradeWithBankPlayerHasNotEnoughResources() {
        //SetUp players have Resources (initialResourceStockThreePlayerBoardStandard)
        requirementSetupTestBoardUsedWithTheTests();

        //Player 1 add, 3GRAIN
        for (int i = 0; i < 3; i++) {
            model.throwDice(2);
        }

        //Test player has 3 GRAIN
        assertFalse(model.tradeWithBankFourToOne(Resource.GRAIN, Resource.WOOL));
    }

    /**
     * Test tradeWithBank when the players have enough resources
     */
    @Test
    public void testTradeWithBankEnoughResources() {
        //SetUp players have Resources (initialResourceStockThreePlayerBoardStandard)
        requirementSetupTestBoardUsedWithTheTests();

        //Player 1 add, 4GRAIN
        for (int i = 0; i < 4; i++) {
            model.throwDice(2);
        }
        //Test player has enough Resources
        assertTrue(model.tradeWithBankFourToOne(Resource.GRAIN, Resource.WOOL));
    }

    /**
     * Test tradeWithBank when the bank has not enough resources
     */
    @Test
    public void testTradeWithBankNotEnoughResources() {
        //SetUp players have Resources (initialResourceStockThreePlayerBoardStandard)
        requirementSetupTestBoardUsedWithTheTests();

        //Player 1 add 4 GRAIN so he can trade
        for (int i = 0; i < 4; i++) {
            model.throwDice(2);
        }

        //Get WOOL to 0 for the Bank, 2 players have 1 WOOL at the moment
        for (int i = 0; i < Config.INITIAL_RESOURCE_CARDS_BANK.get(Resource.WOOL) - 2; i++) {
            model.throwDice(8);
        }
        assertFalse(model.tradeWithBankFourToOne(Resource.GRAIN, Resource.WOOL));

    }

    /**
     * Test if the player can't build a road without resources
     */
    @Test
    public void testBuildRoadNoResources() {
        //SetUp
        setUpTestBuildRoadResources();
        Faction f1 = model.getCurrentPlayerFaction();

        //Check if you can place a Road without Resources
        assertFalse(model.buildRoad(first.get(f1).first, first.get(f1).second));

    }

    /**
     * Test build a road if the player has enough resources
     * Test if the player pays the resources after he built the road
     */
    @Test
    public void testBuildRoadWithEnoughResources() {
        //SetUp
        setUpTestBuildRoadResources();
        Faction f1 = model.getCurrentPlayerFaction();
        model.throwDice(11);
        model.throwDice(6);

        //Build the road and check if the Player lost his resources
        assertTrue(model.buildRoad(first.get(f1).first, first.get(f1).second));
        for (Resource resource : Resource.values()) {
            assertEquals(0, model.getCurrentPlayerResourceStock(resource));
        }
    }

    /**
     * Test positive cases for placeInitialRoad
     */
    @Test
    public void testPlaceInitialRoadPositive() {
        //SetUp
        initializeSiedlerGame(WIN_POINTS_5, PLAYERS);
        Faction f1 = model.getCurrentPlayerFaction();
        model.placeInitialSettlement(first.get(f1).first, false);

        //Check if there is no road at the specific Coordinate
        assertNull(model.getBoard().getEdge(first.get(f1).first, first.get(f1).second));

        //Check if you can place a road and if it is placed at the expected location
        assertTrue(model.placeInitialRoad(first.get(f1).first, first.get(f1).second));
        assertNotNull(model.getBoard().getEdge(first.get(f1).first, first.get(f1).second));

        //SetUp
        model.switchToNextPlayer();
        Faction f2 = model.getCurrentPlayerFaction();

        //Check if roads can only be built next to a settlement or road
        assertFalse(model.placeInitialRoad(first.get(f2).first, first.get(f2).second));//Next to nothing
        model.placeInitialSettlement(first.get(f2).first, false);
        assertTrue(model.placeInitialRoad(first.get(f2).first, first.get(f2).second));//Next to a settlement
        assertTrue(model.placeInitialRoad(first.get(f2).first, new Point(10, 12)));//Next to a road
    }

    /**
     * Test negative cases for placeInitialRoad
     */
    @Test
    public void testPlaceInitialRoadNegative() {
        //SetUp
        initializeSiedlerGame(WIN_POINTS_5, PLAYERS);
        Faction f1 = model.getCurrentPlayerFaction();
        model.placeInitialSettlement(first.get(f1).first, false);
        model.placeInitialRoad(first.get(f1).first, first.get(f1).second);

        //Check if the Road can be placed on top of another Road from the same player
        assertFalse(model.placeInitialRoad(first.get(f1).first, first.get(f1).second));

        //Check if a road can be placed in the Water
        assertFalse(model.placeInitialRoad(new Point(3, 3), new Point(2, 4)));

        //Check if a player can place a road on top of a road from another player
        model.switchToNextPlayer();
        Faction f2 = model.getCurrentPlayerFaction();
        assertFalse(model.placeInitialRoad(first.get(f1).first, first.get(f1).second));

        //Check if a road can be placed with false Coordinates
        assertFalse(model.placeInitialRoad(first.get(f2).first, first.get(f1).first));
    }

    /**
     * This method prepares the player with a board and resources to build settlements.
     */
    private void requirementBuildSettlementsWithResources() {
        requirementSetupTestBoardUsedWithTheTests();

        model.throwDice(2);//Grain
        model.throwDice(6); //Wood
    }

    /**
     * Tests if building a settlement fails if the player doesn't have enough resources
     */
    @Test
    public void testBuildSettlementNoResources() {
        requirementSetupTestBoardUsedWithTheTests();
        Point settlementValid = new Point(4, 4);
        assertFalse(model.buildSettlement(settlementValid));
    }

    /**
     * Tests if one can build a settlement over an existing settlement
     */
    @Test
    public void testBuildSettlementOverOtherSettlement() {
        requirementBuildSettlementsWithResources();

        for (Faction f : model.getPlayerFactions()) {
            assertFalse(model.buildSettlement(first.get(f).first));
        }

    }

    /**
     * Tests if settlements can be built in water
     */
    @Test
    public void testBuildSettlementInWater() {
        requirementBuildSettlementsWithResources();
        Point settlementWater = new Point(3, 3);
        assertFalse(model.buildSettlement(settlementWater));
    }

    /**
     * Tests if settlements can be built out of bounds
     */
    @Test
    public void testBuildSettlementOutOfBounds() {
        requirementBuildSettlementsWithResources();
        Point settlementOutOfBounds = new Point(100, 100);
        assertFalse(model.buildSettlement(settlementOutOfBounds));

    }

    /**
     * Tests if settlements can be built with negative coordinates
     */
    @Test
    public void testBuildSettlementNegativeCoordinates() {
        requirementBuildSettlementsWithResources();
        Point settlementNegative = new Point(-1, -5);

        assertFalse(model.buildSettlement(settlementNegative));

    }

    /**
     * Tests if settlement gets built when player has enough resources and if resources get removed afterwards.
     */
    @Test
    public void testBuildSettlementValid() {
        requirementBuildSettlementsWithResources();
        Point settlementValid = new Point(4, 4);
        assertTrue(model.buildSettlement(settlementValid));

        //Test if resources have been subtracted
        assertEquals(0, model.getCurrentPlayerResourceStock(Resource.GRAIN));
        assertEquals(0, model.getCurrentPlayerResourceStock(Resource.WOOD));
        assertEquals(0, model.getCurrentPlayerResourceStock(Resource.CLAY));
        assertEquals(0, model.getCurrentPlayerResourceStock(Resource.WOOL));


    }

    /**
     * Tests if Settlement can be placed initially
     * This method does not duplicate the test cases from before but rather tests the unique properties of the initial settlementPlace method
     * Initial settlements that get placed through requirementSetupTestBoardUsedWithTheTests() get already checked by this method.
     */
    @Test
    public void testPlaceInitialSettlement() {
        requirementSetupTestBoardUsedWithTheTests();

        //Test if player got resources from second initializing round
        assertEquals(1, model.getCurrentPlayerResourceStock(Resource.CLAY));
        assertEquals(1, model.getCurrentPlayerResourceStock(Resource.WOOL));
    }

    /**
     * This method gives the player enough resources to build a city.
     */
    private void requirementBuildCitiesWithResources() {
        for (int i = 0; i < 2; i++) {
            model.throwDice(2); //Grain
        }
        for (int i = 0; i < 3; i++) {
            model.throwDice(4); //Stone
        }
    }

    /**
     * This method places a settlement first to be able to place a city afterwards
     */
    private void requirementBuildSettlementsForCities(Point point) {
        model.throwDice(2); //Grain
        model.throwDice(6); //Wood
        model.throwDice(11); //Clay
        model.throwDice(12); //Wool

        model.buildSettlement(point);
    }

    /**
     * Tests if building a city fails if the player doesn't have enough resources
     */
    @Test
    public void testBuildCityNoResources() {
        requirementSetupTestBoardUsedWithTheTests();
        Point cityValid = new Point(4, 4);

        requirementBuildSettlementsForCities(cityValid);
        assertFalse(model.buildCity(cityValid));
    }

    /**
     * Test if building a city fails if there is no settlement yet
     */
    @Test
    public void testBuildCityNoSettlement() {
        requirementSetupTestBoardUsedWithTheTests();
        requirementBuildCitiesWithResources();

        Point cityValid = new Point(4, 4);
        assertFalse(model.buildCity(cityValid));
    }

    /**
     * Tests if one can build a city over an existing city
     */
    @Test
    public void testBuildCityOverOtherSettlement() {
        requirementSetupTestBoardUsedWithTheTests();
        requirementBuildCitiesWithResources();
        Point cityValid = new Point(4, 4);

        //switches to other player who places a city to make sure you cannot build over an existing city
        model.switchToNextPlayer();
        requirementBuildSettlementsForCities(cityValid);
        model.buildCity(cityValid);

        model.switchToPreviousPlayer();
        assertFalse(model.buildCity(cityValid));
    }

    /**
     * Tests if cities can be built in water
     */
    @Test
    public void testBuildCityInWater() {
        requirementSetupTestBoardUsedWithTheTests();
        requirementBuildCitiesWithResources();
        Point cityWater = new Point(3, 3);

        requirementBuildSettlementsForCities(cityWater);
        assertFalse(model.buildCity(cityWater));
    }

    /**
     * Tests if cities can be built out of bounds
     */
    @Test
    public void testBuildCityOutOfBounds() {
        requirementSetupTestBoardUsedWithTheTests();
        requirementBuildCitiesWithResources();
        Point cityOutOfBounds = new Point(100, 100);

        requirementBuildSettlementsForCities(cityOutOfBounds);
        assertFalse(model.buildCity(cityOutOfBounds));
    }

    /**
     * Tests if settlements can be built with negative coordinates
     */
    @Test
    public void testBuildCityNegativeCoordinates() {
        requirementSetupTestBoardUsedWithTheTests();
        requirementBuildCitiesWithResources();
        Point cityNegative = new Point(-1, -5);

        requirementBuildSettlementsForCities(cityNegative);
        assertFalse(model.buildCity(cityNegative));


        assertFalse(model.buildCity(cityNegative));

    }

    /**
     * Tests if settlement gets built when player has enough resources and if resources get removed afterwards.
     */
    @Test
    public void testBuildCityValid() {
        requirementSetupTestBoardUsedWithTheTests();
        requirementBuildCitiesWithResources();
        Point cityValid = new Point(4, 4);

        requirementBuildSettlementsForCities(cityValid);
        assertTrue(model.buildCity(cityValid));

        //Test if resources have been subtracted
        assertEquals(0, model.getCurrentPlayerResourceStock(Resource.STONE));
        assertEquals(0, model.getCurrentPlayerResourceStock(Resource.GRAIN));


    }

    /**
     * Tests if the get Winner method returns a winner even though no one has enough win points
     */
    @Test
    public void testWinnerWithoutWinPoints() {
        requirementSetupTestBoardUsedWithTheTests();

        assertNull(model.getWinner());
    }

    /**
     * Tests winner method if player one (Faction RED) has enough points to win
     */
    @Test
    public void testWinnerWithEnoughWinPoints() {
        requirementSetupTestBoardUsedWithTheTests();

        //Give additional settlements to player
        Point settlement1 = new Point(4, 4);
        Point settlement2 = new Point(7, 7);
        Point settlement3 = new Point(7, 13);
        model.placeInitialSettlement(settlement1, false);
        model.placeInitialSettlement(settlement2, false);
        model.placeInitialSettlement(settlement3, false);

        assertEquals(Faction.RED, model.getWinner());

    }

    /**
     * Tests if board gets not returned when game hasn't been initialized
     */
    @Test
    public void testGetBoardWithoutInitializing() {
        Assertions.assertThrows(NullPointerException.class, () -> model.getBoard());
    }

    /**
     * Tests if board gets returned after game has been initialized
     */
    @Test
    public void testGetBoardAfterInitializing() {
        requirementSetupTestBoardUsedWithTheTests();
        assertNotNull(model.getBoard());
    }

    /**
     * Tests if a faction gets returned when no player is playing
     */
    @Test
    public void testGetCurrentPlayerFactionWithoutPlayers() {
        Assertions.assertThrows(NullPointerException.class, () -> model.getCurrentPlayerFaction());
    }

    /**
     * Tests if correct faction gets returned right after game start
     */
    @Test
    public void testGetCurrentPlayerFactionAfterGameStart() {
        requirementSetupTestBoardUsedWithTheTests();
        assertEquals(Faction.RED, model.getCurrentPlayerFaction());
    }

    /**
     * Test if correct faction gets returned after player switching
     */
    @Test
    public void testGetCurrentPlayerFactionPlayerSwitch() {
        requirementSetupTestBoardUsedWithTheTests();
        model.switchToNextPlayer();
        assertEquals(Faction.BLUE, model.getCurrentPlayerFaction());
    }

    /**
     * Test if thief can be placed on map.
     */
    @Test
    public void testPlaceThief() {
        requirementSetupTestBoardUsedWithTheTests();
        Point thiefPlacement = new Point(4, 8);
        assertTrue(model.placeThiefAndStealCard(thiefPlacement));
    }

    /**
     * Test if thief can be moved after he has been placed initially
     */
    @Test
    public void testMoveThief() {
        requirementSetupTestBoardUsedWithTheTests();
        Point thiefInitialPlace = new Point(4, 8);
        Point thiefMovedPlace = new Point(8, 8);

        model.placeThiefAndStealCard(thiefInitialPlace);
        assertTrue(model.placeThiefAndStealCard(thiefMovedPlace));
    }

    /**
     * Test if thief can be placed on water
     */
    @Test
    public void testPlaceThiefOnWater() {
        requirementSetupTestBoardUsedWithTheTests();
        Point thiefWater = new Point(8, 2);

        assertFalse(model.placeThiefAndStealCard(thiefWater));
    }

    /**
     * Test if thief can be placed on road or settlement coordinates
     */
    @Test
    public void testPlaceThiefWrongCoordinate() {
        requirementSetupTestBoardUsedWithTheTests();
        Point thiefStructure = new Point(6, 10);

        assertFalse(model.placeThiefAndStealCard(thiefStructure));
    }

    /**
     * Test if thief can be placed out of bounds
     */
    @Test
    public void testPlaceThiefOutOfBounds() {
        requirementSetupTestBoardUsedWithTheTests();
        Point thiefOutOfBounds = new Point(100, 100);

        assertFalse(model.placeThiefAndStealCard(thiefOutOfBounds));
    }

    /**
     * Test if thief can be placed with invalid coordinates
     */
    @Test
    public void testPlaceThiefNegative() {
        requirementSetupTestBoardUsedWithTheTests();
        Point thiefNegative = new Point(-1, -1);

        assertFalse(model.placeThiefAndStealCard(thiefNegative));
    }

    /**
     * Test if thief actually robs players
     */
    @Test
    public void testThiefRobsPlayers() {
        requirementSetupTestBoardUsedWithTheTests();

        //Give resources and test if players actually received their resources prior to robbing
        for (int i = 0; i < 8; i++) {
            model.throwDice(2);
        }
        assertEquals(8, model.getCurrentPlayerResourceStock(Resource.GRAIN));
        model.switchToNextPlayer();

        for (int i = 0; i < 8; i++) {
            model.throwDice(8);
        }
        assertEquals(9, model.getCurrentPlayerResourceStock(Resource.WOOL));
        model.switchToNextPlayer();

        for (int i = 0; i < 9; i++) {
            model.throwDice(5);
        }
        assertEquals(9, model.getCurrentPlayerResourceStock(Resource.WOOD));


        //Place thief on field where there is no settlement nearby
        Point thiefAwaySettlement = new Point(7, 11);
        model.placeThiefAndStealCard(thiefAwaySettlement);

        //Test if players had to give away half of their resources and if it correctly rounded down in favour of the player
        assertEquals(9, model.getCurrentPlayerResourceStock(Resource.WOOD));
        model.switchToPreviousPlayer();
        assertEquals(9, model.getCurrentPlayerResourceStock(Resource.WOOL));
        model.switchToPreviousPlayer();
        assertEquals(8, model.getCurrentPlayerResourceStock(Resource.GRAIN));
    }

    /**
     * Prepares a default board for three players.
     * Method has been given by the project lecturers.
     *
     * @param winpoints How many points you need to win
     */
    private void bootstrapTestBoardForThreePlayersStandard(int winpoints) {
        initializeSiedlerGame(winpoints, PLAYERS);

        for (int i = 0; i < model.getPlayerFactions().size(); i++) {
            Faction f = model.getCurrentPlayerFaction();
            assertTrue(model.placeInitialSettlement(first.get(f).first, false));
            assertTrue(model.placeInitialRoad(first.get(f).first, first.get(f).second));
            model.switchToNextPlayer();
        }
        for (int i = 0; i < model.getPlayerFactions().size(); i++) {
            model.switchToPreviousPlayer();
            Faction f = model.getCurrentPlayerFaction();
            assertTrue(model.placeInitialSettlement(second.get(f).first, true));
            assertTrue(model.placeInitialRoad(second.get(f).first, second.get(f).second));
        }
    }


    private void initializeSiedlerGame(int winpoints, int players) {
        model = new SiedlerGame(winpoints, players);
    }

    private <T> boolean compareLists(List<T> list1, List<T> list2) {
        if (list1 == list2
                || list1 == null && list2.isEmpty()
                || list2 == null && list1.isEmpty()
        ) {
            return true;
        } else if (
                list1 == null
                        || list2 == null
                        || list1.size() != list2.size()) {
            return false;
        }
        List<T> tmp = new LinkedList<>(list2);
        for (T item : list1) {
            if (tmp.contains(item)) {
                tmp.remove(item);
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * setUp for the buildRoad Test with Resources
     */
    private void setUpTestBuildRoadResources() {
        //SetUp
        initializeSiedlerGame(WIN_POINTS_5, 3);
        Faction f1 = model.getCurrentPlayerFaction();

        //Check if there is no road at the specific Coordinate
        assertNull(model.getBoard().getEdge(first.get(f1).first, first.get(f1).second));

        //Place a Settlement so you can place roads
        model.placeInitialSettlement(first.get(f1).first, false);

        //Place a Settlement so you get CLAY from diceThrow
        model.placeInitialSettlement(new Point(9, 19), false);
    }
}
