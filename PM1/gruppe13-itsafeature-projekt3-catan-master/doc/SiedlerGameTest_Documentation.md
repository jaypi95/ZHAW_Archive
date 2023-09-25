# SiedlerGameTest Documentation

The SiedlerGameTest class tests all of SiedlerGame's functions which can be executed.
The test results can be found in a separate file.

The following classes were tested:

## Testresultate:
* requirementPlayerSwitching
  * Tests player switching mechanism.
* requirementSetupTestBoardUsedWithTheTests
  * Sets up board used for the other tests.
* requirementResourcePayoutAndReturnValueForDiceThrow
  * Tests if throwing dice gives the players the correct amount of resources.
* testGetCurrentPlayerResourceStockNoResources
  * Tests the returned value of the player's resources when he doesn't have any.
* testGetCurrentPlayerResourceStockHaveResources
  * Tests the returned value of the player's resources.
* testTradeWithBankPlayerHasNoResources
  * Tests if player tries to trade with the bank when he doesn't have any resources.
* testTradeWithBankPlayerHasNotEnoughResources
  * Tests if player tries to trade with the bank when he doesn't have enough resources.
* testTradeWithBankEnoughResources
  * Tests if the player can trade with the bank when he does have enough resources.
* testTradeWithBankNotEnoughResources
  * Tests if player tries to trade with bank when the bank doesn't have enough resources.
* testBuildRoadNoResources
  * Tests if player tries to build a road with no resources
* testBuildRoadWithEnoughResources
  * Tests if player tries to build a road with enough resources and tests the player paid the resources.
* testPlaceInitialRoadPositive
  * Positive test for placing initial road.
* testPlaceInitialRoadNegative
  * Negative test for placing initial road.
* testBuildSettlementNoResources
  * Tests if building a settlement fails if the player doesn't have enough resources.
* testBuildSettlementOverOtherSettlement
  * Tests if one can build a settlement over an existing settlement.
* testBuildSettlementInWater
  * Tests if settlements can be built in water.
* testBuildSettlementOutOfBounds
  * Tests if settlements can be built out of bounds.
* testBuildSettlementNegativeCoordinates
  * Tests if settlements can be built with negative coordinates.
* testBuildSettlementValid
  * Tests if settlement gets built when player has enough resources and if resources get removed afterwards.
* testPlaceInitialSettlement
  * Tests if Settlement can be placed initially.
* testBuildCityNoResources
  * Tests if building a city fails if the player doesn't have enough resources.
* testBuildCityNoSettlement
  * Test if building a city fails if there is no settlement yet.
* testBuildCityOverOtherSettlement
  * Tests if one can build a city over an existing city.
* testBuildCityInWater
  * Tests if cities can be built in water.
* testBuildCityOutOfBounds
  * Tests if cities can be built out of bounds.
* testBuildCityNegativeCoordinates
  * Tests if settlements can be built with negative coordinates.
* testBuildCityValid
  * Tests if settlement gets built when player has enough resources and if resources get removed afterwards.
* testWinnerWithoutWinPoints
  * Tests if the get Winner method returns a winner even though no one has enough win points.
* testWinnerWithEnoughWinPoints
  * Tests winner method if player one (Faction RED) has enough points to win.
* testGetBoardWithoutInitializing
  * Tests if board gets not returned when game hasn't been initialized.
* testGetBoardAfterInitializing
  * Tests if board gets returned after game has been initialized.
* testGetCurrentPlayerFactionWithoutPlayers
  * Tests if a faction gets returned when no player is playing.
* testGetCurrentPlayerFactionAfterGameStart
  * Tests if correct faction gets returned right after game start.
* testGetCurrentPlayerFactionPlayerSwitch
  * Test if correct faction gets returned after player switching.
* testPlaceThief
  * Test if thief can be placed on map.
* testMoveThief
  * Test if thief can be moved after he has been placed initially.
* testPlaceThiefOnWater
  * Test if thief can be placed on water.
* testPlaceThiefWrongCoordinate
  * Test if thief can be placed on road or settlement coordinates.
* testPlaceThiefOutOfBounds
  * Test if thief can be placed out of bounds.
* testPlaceThiefNegative
  * Test if thief can be placed with invalid coordinates.
* testThiefRobsPlayers
  * Test if thief actually robs players.
  