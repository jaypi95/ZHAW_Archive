# Testdocument for the Car  class

# Method: setPosition(final PositionVector) and getPosition()

Equivalence classes:

1. Smaller than 0
1. Bigger than 0 or 0

| Test case     |   Test value   |  Covered Equivalence Class |  Expected Test Results |
|	----------	|:-------------:	|:------:							|:------:|
| 1 |    PositionVector(3, 5)    | 2          |          PositionVector(3, 5)                  |     
| 2 |     PositionVector(Integer.MAX_VALUE, Integer.MAX_VALUE)      |    2           |           PositionVector(Integer.MAX_VALUE, Integer.MAX_VALUE)                 |
| 3 |    PositionVector(Integer.MIN_VALUE, Integer.MIN_VALUE)       |    1            |           PositionVector(Integer.MIN_VALUE, Integer.MIN_VALUE)                 |              

# Method: nextPosition()

Equivalence classes:

1. No acceleration
1. After 1 acceleration
1. After more than 1 acceleration

| Test case     |   Test value   |  Covered Equivalence Class |  Expected Test Results |
|	----------	|:-------------:	|:------:							|:------:|
| 1 |   PositionVector(0, 0)    | 1          |          PositionVector(0, 0)|     
| 2 |    PositionVector.Direction.UP      |    2            | PositionVector(0, -1)                 |
| 3 |    PositionVector.Direction.UP       |    3            | PositionVector(0, -9) |    

# Method: accelerate(PositionVector.Direction)

Equivalence classes:

1. accelerate in one direction
1. accelerate in various directions
1. accelerate in no direction

| Test case     |   Test value   |  Covered Equivalence Class |  Expected Test Results |
|	----------	|:-------------:	|:------:							|:------:|
| 1 |   PositionVector.Direction.UP    | 1          | PositionVector(0, -1)|     
| 2 |    PositionVector.Direction.UP      |    1            | PositionVector(0, -10)    |
| 3 |    PositionVector.Direction.UP and PositionVector.Direction.DOWN      |    2           | PositionVector(0, 0) |
| 4 |    PositionVector.Direction.UP       |    1            | new PositionVector(0, -1) | 
| 5 |    PositionVector.Direction.NONE       |    3            | PositionVector(0, 0) |
| 6 |    PositionVector.Direction.NONE and PositionVector.Direction.UP       |    1            | PositionVector(0, -2) | 

# Method: move()

Equivalence classes:

1. With velocity
1. Without velocity

| Test case     |   Test value   |  Covered Equivalence Class |  Expected Test Results |
|	----------	|:-------------:	|:------:							|:------:|
| 1 |   -   | 2         | PositionVector(0, 0)|     
| 2 |    PositionVector.Direction.UP      |    1            | PositionVector(0, -6)   |

# Method: crash() and isCrashed()

Equivalence classes:

1. Car is crashed
1. Car is not crashed

| Test case     |   Test value   |  Covered Equivalence Class |  Expected Test Results |
|	----------	|:-------------:	|:------:							|:------:|
| 1 |   Initial car object   | 2         | false|     
| 2 |    Initial car object     |    1            | true   |

# Method: setStrategyType(Config.StrategyType) and getStrategyType()

Equivalence classes:

1. StrategyObject is init
1. StrategyObject is not init

| Test case     |   Test value   |  Covered Equivalence Class |  Expected Test Results |
|	----------	|:-------------:	|:------:							|:------:|
| 1 |   Config.StrategyType.DO_NOT_MOVE  | 1         | true|     
| 2 |   Config.StrategyType.USER     |    1            | true   |
| 3 |   Config.StrategyType.MOVE_LIST    |    1            | true   |
| 4 |   Config.StrategyType.PATH_FOLLOWER   |    1            | true
| 5 |   Initial car object    |    2            | null   |


# Method: getId()

Equivalence classes:

1. ID is a letter
1. ID is a number
1. ID is a special char

| Test case     |   Test value   |  Covered Equivalence Class |  Expected Test Results |
|	----------	|:-------------:	|:------:							|:------:|
| 1 |   Initial car object  | 1         | 't'|     
| 2 |   Car object with ID '5'     |    2            | '5'  |
| 3 |   Car object with ID ' '    |    3            | ' '  |
| 4 |   Car object with ID '#'    |    3            | '#'   |

# Method: getVelocity()

Equivalence classes:

1. No velocity

| Test case     |   Test value   |  Covered Equivalence Class |  Expected Test Results |
|	----------	|:-------------:	|:------:							|:------:|
| 1 |   Initial car object  | 1         | PositionVector(0, 0)|   

# Method: carIsWinner() and isWinner()

Equivalence classes:

1. There is a winner
1. There is no winner

| Test case     |   Test value   |  Covered Equivalence Class |  Expected Test Results |
|	----------	|:-------------:	|:------:							|:------:|
| 1 |   Initial car object  | 2         | false|  
| 1 |   Initial car object  | 2         | false|   
| 1 |   Initial car object  | 1         | true|   
