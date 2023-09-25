# Testdocument for the Game class

## Method: getCurrentCarIndex()

Equivalence classes:

1. Returns correct ID

| Test case     |   Test value   |  Covered Equivalence Class |  Expected Test Results |
|	----------	|:-------------:	|:------:							|:------:|
| Get Index of the first car |    -    | 1          |          0                  |     
| Get Index of the second car |     -      | 1           |           1                 |

## Method: getCarId(int carIndex)

Equivalence classes:

1. Returns correct ID
1. Does not return wrong ID

| Test case     |   Test value   |  Covered Equivalence Class |  Expected Test Results |
|	----------	|:-------------:	|:------:							|:------:|
| First car has ID a |    int carIndex = 0    | 1          |          97 (corresponds to a in ASCII)                 |     
| Second car does not have same ID |     int carIndex = 1     | 2           |           !97                 |

## Method: getCarPosition(int carIndex)

Equivalence classes:

1. Returns correct starting position
1. Does not return starting position of car 1 when querying for car 2

| Test case     |   Test value   |  Covered Equivalence Class |  Expected Test Results |
|	----------	|:-------------:	|:------:							|:------:|
| First car has correct position |    PositionVector(24, 22)    | 1          |          PositionVector(24, 22)                 |     
| Second car does not return position of first car |     PositionVector(24, 22)     | 2           |           !PositionVector(24, 22)                |

## Method: getWinner()

Equivalence classes:

1. Returns correct winner

| Test case     |   Test value   |  Covered Equivalence Class |  Expected Test Results |
|	----------	|:-------------:	|:------:							|:------:|
| Correct Winner after first car crashes |    Car 1 crashes into wall   | 1          |          Second car equals winner car                 |     
| Second car does not return position of first car |     Car 1 finishes     | 1          |           First car equals winner car                |

## Method: doCarTurn(Direction acceleration)

Equivalence classes:

1. Moves car to correct position
1. Does correctly not move car

| Test case     |   Test value   |  Covered Equivalence Class |  Expected Test Results |
|	----------	|:-------------:	|:------:							|:------:|
| Move car |    PositionVector.Direction.valueOf("RIGHT")   | 1          |          PositionVector(oldPosition.getX() + 1, oldPosition.getY())                 |     
| Stop car |     PositionVector.Direction.valueOf("RIGHT") && PositionVector.Direction.valueOf("LEFT")    | 1          |           oldPosition equals newPosition               |
| Accelerate | 2x PositionVector.Direction.valueOf("RIGHT") | 1 | PositionVector(oldPosition.getX() + 3, oldPosition.getY()) |
| Turn | 2x PositionVector.Direction.valueOf("RIGHT") && PositionVector.Direction.valueOf("DOWN") | 1 | PositionVector(oldPosition.getX() + 5, oldPosition.getY() + 1) |
| Stay still | PositionVector.Direction.valueOf("NONE") | 2 | oldPosition equals newPosition |
| Not move after wall crash | PositionVector.Direction.valueOf("UP") | 2 | oldPosition equals newPosition |
| Not move after crash with other car | 3x PositionVector.Direction.valueOf("DOWN") | 2 | position during crash is position after crash |

## Method: switchToNextActiveCar()

Equivalence classes:

1. Switches to next car

| Test case     |   Test value   |  Covered Equivalence Class |  Expected Test Results |
|	----------	|:-------------:	|:------:							|:------:|
| Switches to next car |    -   | 1          |          Active car not equal to starter car                 |     
| Switches back to first car |     -     | 1          |           Active car equals starter car                |

## Method: calculatePath(PositionVector startPosition, PositionVector endPosition)

It doesn't seem to be possible to create negative tests here since the method won't accept values higher than
Integer.MAX_VALUE

Equivalence classes:

1. Calculates correct vector

| Test case     |   Test value   |  Covered Equivalence Class |  Expected Test Results |
|	----------	|:-------------:	|:------:							|:------:|
| Calculate vertical vector |    Start: PositionVector(1, 1), End: PositionVector(1, 20)  | 1          |          List of correct position vectors                 |     
| Calculate horizontal vector |     Start: PositionVector(1, 1), End: PositionVector(20, 1)     | 1          |           List of correct position vectors                |
| Calculate diagonal vector |    Start: PositionVector(1, 1), End: PositionVector(20, 20)   | 1          |          List of correct position vectors                 |     
| Calculate random vector |    Start: PositionVector(37, 3), End: PositionVector(48, 11)    | 1          |          List of correct position vectors                 |     
| Calculate negative vector |    Start: PositionVector(-1, -5), End:  PositionVector(3, 6)  | 1          |          List of correct position vectors                 |     
| Calculate 0-vector | Start: PositionVector(2, 2), End: PositionVector(2, 2) | 1 | Single correct position vector |
| Calculate vector at integer maximum | Start: PositionVector(Integer.MAX_VALUE - 1, 2), End: PositionVector(Integer.MAX_VALUE, 2) | 1 | List of correct position vectors |

## Method: willCarCrash(int carIndex, PositionVector position)

Equivalence classes:

1. Will car crash
1. Will car not crash

| Test case     |   Test value   |  Covered Equivalence Class |  Expected Test Results |
|	----------	|:-------------:	|:------:							|:------:|
| Car crashes into wall |    Current car position + wall position   | 1          |          true                 |     
| Car crashes into car |     Current car position + other car position      | 1          |           true|
| Car does not crash onto track | Current car position + drivable track position | 2 | false |
