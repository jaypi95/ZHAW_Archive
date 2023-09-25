package ch.zhaw.pm2.racetrack;

import ch.zhaw.pm2.racetrack.game.logic.Car;
import ch.zhaw.pm2.racetrack.game.logic.Config;
import ch.zhaw.pm2.racetrack.game.logic.PositionVector;
import ch.zhaw.pm2.racetrack.game.strategy.DoNotMoveStrategy;
import ch.zhaw.pm2.racetrack.game.strategy.MoveListStrategy;
import ch.zhaw.pm2.racetrack.game.strategy.PathFollowerMoveStrategy;
import ch.zhaw.pm2.racetrack.game.strategy.UserMoveStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for the Car class
 *
 * @author outitbad
 * @date 26.03.2021
 */
public class CarTest {
    private Car car;

    /**
     * Init a car object with the start position (0, 0) and the char 't'
     */
    @BeforeEach
    void initCar() {
        this.car = new Car('t', new PositionVector(0, 0));
    }

    /**
     * Test if the positionvector is the same when we get and set it.
     */
    @Test
    void setAndGetPositionTest() {
        PositionVector a = new PositionVector(3, 5);
        car.setPosition(a);
        PositionVector b = car.getPosition();
        assertEquals(a, b);
    }

    /**
     * Test if we can set and get a positionvector with Integer.MAX_VALUE
     */
    @Test
    void setPositionMaxInt() {
        //Test if the Position Vectors are the same
        PositionVector a = new PositionVector(Integer.MAX_VALUE, Integer.MAX_VALUE);
        car.setPosition(a);
        PositionVector b = car.getPosition();
        assertEquals(a, b);
    }

    /**
     * Test if we can set and get a positionvector with Integer.MIN_VALUE
     */
    @Test
    void setPositionMinInt() {
        //Test if the Position Vectors are the same
        PositionVector a = new PositionVector(Integer.MIN_VALUE, Integer.MIN_VALUE);
        car.setPosition(a);
        PositionVector b = car.getPosition();
        assertEquals(a, b);
    }

    /**
     * Test if the nextPosition method returns the same positionvector at the start of the game (Velocity is 0)
     */
    @Test
    void nextPositionAtStartGameTest() {
        PositionVector b = car.nextPosition();
        PositionVector ExpectedPosition = new PositionVector(0, 0);
        assertEquals(ExpectedPosition, b);
    }

    /**
     * Test the nextPosition method after setting the acceleration
     */
    @Test
    void nextPositionAfter1AccelerationTest() {
        car.accelerate(PositionVector.Direction.UP);
        PositionVector b = car.nextPosition();
        PositionVector ExpectedPosition = new PositionVector(0, -1);
        assertEquals(ExpectedPosition, b);
    }

    /**
     * Test the nextPosition method after moving and accelerating 3 times
     */
    @Test
    void nextPositionAfter3AccelerationTest() {
        for (int i = 0; i < 3; i++) {
            car.accelerate(PositionVector.Direction.UP);
            car.move();
        }
        PositionVector b = car.nextPosition();
        PositionVector ExpectedPosition = new PositionVector(0, -9);
        assertEquals(ExpectedPosition, b);
    }

    /**
     * Test the accelerate method after accelerating 1 time
     */
    @Test
    void Accelerate1TimeTest() {
        car.accelerate(PositionVector.Direction.UP);
        car.move();
        PositionVector b = car.getPosition();
        PositionVector ExpectedPosition = new PositionVector(0, -1);
        assertEquals(ExpectedPosition, b);
    }

    /**
     * Test the accelerate method after accelerating 4 times
     */
    @Test
    void Accelerate4TimeTest() {
        for (int i = 0; i < 4; i++) {
            car.accelerate(PositionVector.Direction.UP);
            car.move();
        }
        PositionVector b = car.getPosition();
        PositionVector ExpectedPosition = new PositionVector(0, -10);
        assertEquals(ExpectedPosition, b);
    }

    /**
     * Test the accelerate method when you go up and down
     */
    @Test
    void AccelerateUpThenDownTest() {
        car.accelerate(PositionVector.Direction.UP);
        car.move();

        car.accelerate(PositionVector.Direction.DOWN);
        car.move();
        car.accelerate(PositionVector.Direction.DOWN);
        car.move();

        PositionVector b = car.getPosition();
        PositionVector ExpectedPosition = new PositionVector(0, 0);
        assertEquals(ExpectedPosition, b);
    }

    /**
     * Test the accelerate method, that it can't stack without moving
     */
    @Test
    void AccelerateStackingTest() {
        for (int i = 0; i < 3; i++) {
            car.accelerate(PositionVector.Direction.UP);
        }
        car.move();
        PositionVector b = car.getPosition();
        PositionVector ExpectedPosition = new PositionVector(0, -1);
        assertEquals(ExpectedPosition, b);
    }

    /**
     * Test the accelerate method when the direction is NONE and velocity is 0
     */
    @Test
    void AccelerateNoneTest() {
        car.accelerate(PositionVector.Direction.NONE);
        car.move();
        PositionVector b = car.getPosition();
        PositionVector ExpectedPosition = new PositionVector(0, 0);
        assertEquals(ExpectedPosition, b);
    }

    /**
     * Test the accelerate method when the direction is NONE and you have velocity
     */
    @Test
    void AccelerateNoneWithVelocityTest() {
        car.accelerate(PositionVector.Direction.UP);
        car.move();
        car.accelerate(PositionVector.Direction.NONE);
        car.move();
        PositionVector b = car.getPosition();
        PositionVector ExpectedPosition = new PositionVector(0, -2);
        assertEquals(ExpectedPosition, b);
    }

    /**
     * Test if the car moves without velocity
     */
    @Test
    void moveWithNoVelocity() {
        car.move();
        PositionVector b = car.getPosition();
        PositionVector ExpectedPosition = new PositionVector(0, 0);
        assertEquals(ExpectedPosition, b);
    }

    /**
     * Test the move method if you accelerate 3 times
     */
    @Test
    void moveWith3Velocity() {
        for (int i = 0; i < 3; i++) {
            car.accelerate(PositionVector.Direction.UP);
            car.move();
        }
        PositionVector b = car.getPosition();
        PositionVector ExpectedPosition = new PositionVector(0, -6);
        assertEquals(ExpectedPosition, b);
    }

    /**
     * Test if the car is crashed at the start of the game
     */
    @Test
    void IsCrashedAtStartTest() {
        assertEquals(false, car.isCrashed());
    }

    /**
     * Test if the car is crashed after using the crash method
     */
    @Test
    void crashAndIsCrashedTest() {
        car.crash();
        assertEquals(true, car.isCrashed());
    }

    /**
     * Test the setStrategyType and getStrategyType method for the useable strategys
     */
    @Test
    void setStrategyTypeAndGetStrategyTest() {
        car.setStrategyType(Config.StrategyType.DO_NOT_MOVE);
        assertTrue(car.getStrategy() instanceof DoNotMoveStrategy);

        car.setStrategyType(Config.StrategyType.USER);
        assertTrue(car.getStrategy() instanceof UserMoveStrategy);

        car.setStrategyType(Config.StrategyType.MOVE_LIST);
        assertTrue(car.getStrategy() instanceof MoveListStrategy);

        car.setStrategyType(Config.StrategyType.PATH_FOLLOWER);
        assertTrue(car.getStrategy() instanceof PathFollowerMoveStrategy);
    }

    /**
     * Test if getStrategy returns null at the start of the Game
     */
    @Test
    void getStrategyTypeStartOfGameTest() {
        Assertions.assertNull(car.getStrategy());
    }

    /**
     * Test if getID returns the correct char
     */
    @Test
    void getIDLetterTest() {
        assertEquals('t', car.getId());
    }


    /**
     * Test if getID can return a number as char
     */
    @Test
    void getIDNumberTest() {
        car = new Car('5', new PositionVector(0, 0));
        assertEquals('5', car.getId());
    }

    /**
     * Test if getID can return a space as char
     */
    @Test
    void getIDSpaceTest() {
        car = new Car(' ', new PositionVector(0, 0));
        assertEquals(' ', car.getId());
    }

    /**
     * Test if getID can return a number as char
     */
    @Test
    void getIDSpecialCharTest() {
        car = new Car('#', new PositionVector(0, 0));
        assertEquals('#', car.getId());
    }

    /**
     * Test if the velocity at the start is 0
     */
    @Test
    void getVelocityAtStartTest() {
        assertEquals(car.getVelocity(), new PositionVector(0, 0));
    }

    /**
     * Test if isWinner is set to true, when the loop count equals LOOP_COUNT_TO_WIN
     */
    @Test
    void isWinner() {
        car.decrementLoopCount();
        assertEquals(false, car.isWinner());
        car.incrementLoopCount();
        assertEquals(false, car.isWinner());
        car.incrementLoopCount();
        assertEquals(true, car.isWinner());

    }
}
