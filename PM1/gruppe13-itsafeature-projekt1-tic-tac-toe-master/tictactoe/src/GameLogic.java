class GameLogic {
    GameField gameField;

    /**
     * Initialise GameLogic object.
     *
     * @param gameField GameField object
     */
    public GameLogic(GameField gameField) {
        this.gameField = gameField;
    }

    /**
     * @param cellIndex Cell index, such as 1,2,3,..,8,9
     * @return true if move valid at cell, otherwise false
     */
    public boolean isValidMove(int cellIndex) {
        boolean validTurn;
        if (cellIndex <= 9 && cellIndex > 0) {
            validTurn = gameField.getCell(cellIndex).equals(" ");
        } else {
            validTurn = false;
        }
        return validTurn;
    }

    /**
     * @return -1 if nobody won, 0 if player o won, 1 if player x won, 2 if it's a draw
     */
    public int checkWin() {
        int returnCode = -1;
        // check rows
        if (checkRowColWin(gameField.getCell(1), gameField.getCell(2), gameField.getCell(3))) {
            returnCode = getPlayerAtCell(gameField.getCell(1));
        } else if (checkRowColWin(gameField.getCell(4), gameField.getCell(5), gameField.getCell(6))) {
            returnCode = getPlayerAtCell(gameField.getCell(4));
        } else if (checkRowColWin(gameField.getCell(7), gameField.getCell(8), gameField.getCell(9))) {
            returnCode = getPlayerAtCell(gameField.getCell(7));
            // check columns
        } else if (checkRowColWin(gameField.getCell(1), gameField.getCell(4), gameField.getCell(7))) {
            returnCode = getPlayerAtCell(gameField.getCell(1));
        } else if (checkRowColWin(gameField.getCell(2), gameField.getCell(5), gameField.getCell(8))) {
            returnCode = getPlayerAtCell(gameField.getCell(2));
        } else if (checkRowColWin(gameField.getCell(3), gameField.getCell(6), gameField.getCell(9))) {
            returnCode = getPlayerAtCell(gameField.getCell(3));
            // check diagonals
        } else if (checkRowColWin(gameField.getCell(1), gameField.getCell(5), gameField.getCell(9))) {
            returnCode = getPlayerAtCell(gameField.getCell(1));
        } else if (checkRowColWin(gameField.getCell(3), gameField.getCell(5), gameField.getCell(7))) {
            returnCode = getPlayerAtCell(gameField.getCell(3));
            // check for draw
        } else if (!gameField.getCell(1).equals(" ") && !gameField.getCell(2).equals(" ") && !gameField.getCell(3).equals(" ") && !gameField.getCell(4).equals(" ") && !gameField.getCell(5).equals(" ") && !gameField.getCell(6).equals(" ") && !gameField.getCell(7).equals(" ") && !gameField.getCell(8).equals(" ") && !gameField.getCell(9).equals(" ")) {
            returnCode = 2;
        }
        return returnCode;
    }

    /**
     * @param cellContent (x | o | ' ')
     * @return the player at a cell.
     * -1 if there if the cell is empty
     * 0 if there is player o
     * 1 if there is player x
     */
    private int getPlayerAtCell(String cellContent) {
        int returnCode = -1;
        if (cellContent.equalsIgnoreCase("x")) {
            returnCode = 1;
        } else if (cellContent.equalsIgnoreCase("o")) {
            returnCode = 0;
        }
        return returnCode;
    }

    /**
     * @return true if the 3 values are the same (case ignored) and not " ", otherwise false
     */
    private boolean checkRowColWin(String value1, String value2, String value3) {
        return !value1.equalsIgnoreCase(" ") && value1.equalsIgnoreCase(value2) && value2.equalsIgnoreCase(value3);
    }
}
