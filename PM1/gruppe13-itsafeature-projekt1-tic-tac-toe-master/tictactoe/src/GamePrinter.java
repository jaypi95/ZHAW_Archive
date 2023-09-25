/**
 *  This class prints the gamefield to the Console.
 *
 *  @author outitbad
 *  @version 02102020
 */

public class GamePrinter {
    GameField gamefield;

    /**
     * The constructor creates an object of the GameField with the param pGamefield.
     */
    public GamePrinter(GameField gameField) {
        this.gamefield = gameField;

    }

    /**
     * Prints an empty gamefield.
     */
    public void printEmptyGameField() {

        System.out.println("----------");
        System.out.println("|  |  |  |");
        System.out.println("----------");
        System.out.println("|  |  |  |");
        System.out.println("----------");
        System.out.println("|  |  |  |");
        System.out.println("----------");

    }

    /**
     * Prints the active state of the gamefield.
     */
    public void printActiveGameField() {


        System.out.println("-------------");
        System.out.println("| " + getCellState(1) + " | " + getCellState(2) + " | " + getCellState(3) + " |");
        System.out.println("-------------");
        System.out.println("| " + getCellState(4) + " | " + getCellState(5) + " | " + getCellState(6) + " |");
        System.out.println("-------------");
        System.out.println("| " + getCellState(7) + " | " + getCellState(8) + " | " + getCellState(9) + " |");
        System.out.println("-------------");

    }

    /**
     * Returns the state of the cell "X" or "O" or " ".
     * @param cellNumber tells us which cell we are checking.
     */
    private String getCellState(int cellNumber) {
        String cellState;

        if (gamefield.getCell(cellNumber).equalsIgnoreCase("x")) {
            cellState = "X";

        } else if (gamefield.getCell(cellNumber).equalsIgnoreCase("O")) {
            cellState = "O";
        } else {

            cellState = " ";

        }

        return cellState;
    }


}