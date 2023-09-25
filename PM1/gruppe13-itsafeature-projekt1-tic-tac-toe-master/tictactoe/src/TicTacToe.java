/**
 * This is the main-class which starts the whole game.
 * In this case the main class also automates the input.
 *
 * @author peterju1
 * @version 2020100902
 */
public class TicTacToe {
    /**
     * Initializes the GameField and thus starts the game.
     */
    GameField gamefield = new GameField();

    /**
     * Creates the needed objects so a static method can call dynamic methods
     */
    public static void main(String[] args) {
        TicTacToe autoGame1 = new TicTacToe();
        TicTacToe autoGame2 = new TicTacToe();
        TicTacToe autoGame3 = new TicTacToe();

        System.out.println("Starting Autogame 1");
        autoGame1.game1();

        System.out.println("Starting Autogame 2");
        autoGame2.game2();

        System.out.println("Starting Autogame 3");
        autoGame3.game3();
    }

    /**
     * Automates the game. 
     * Usually this would be done via user input.
     */
    private void game1() {
        gamefield.init();
        gamefield.makeAMove(7, false);
        gamefield.makeAMove(1, false);
        gamefield.makeAMove(9, true);
        gamefield.makeAMove(3, false);
        gamefield.makeAMove(8, false);
    }

    private void game2() {
        gamefield.init();
        gamefield.makeAMove(3, false);
        gamefield.makeAMove(9, true);
        gamefield.makeAMove(1, false);
        gamefield.makeAMove(2, false);
        gamefield.makeAMove(20, false);
        gamefield.makeAMove(8, false);
        gamefield.makeAMove(7, false);
        gamefield.makeAMove(5, true);
        gamefield.makeAMove(4, false);
        gamefield.makeAMove(6, false);
    }

    private void game3() {
        gamefield.init();
        gamefield.makeAMove(6, false);
        gamefield.makeAMove(1, false);
        gamefield.makeAMove(1, false);
        gamefield.makeAMove(8, false);
        gamefield.makeAMove(7, false);
        gamefield.makeAMove(3, false);
        gamefield.makeAMove(4, false);
    }
}
