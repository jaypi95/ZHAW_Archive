class GameField {
    GameLogic gameLogic;
    GamePrinter gamePrinter;
    LanguageHandler languageHandler;
    // space if empty, x, or o
    String gameBoard; //Current state of the game
    int currentPlayer;

    /**
     * Initialize GameField Object
     */
    public GameField() {
        gameLogic = new GameLogic(this);
        gamePrinter = new GamePrinter(this);
        languageHandler = new LanguageHandler();
    }

    /**
     * Initialising new game
     * This method allows to restart the game or start a new game without creating a new object
     */
    public void init() {
        //Displays the language message
        languageHandler.displayWelcomeMessage();

        //Displays the empty gameField
        gamePrinter.printEmptyGameField();

        //Init the empty Gameboard
        gameBoard = " _ _ _ _ _ _ _ _ ";

        //Sets the starting Player
        currentPlayer = 1;
    }

    /**
     * @param cellNumber Cell index, such as 1,2,3,..,8,9
     * @return value stored in the cell "cellNumber"
     */
    public String getCell(int cellNumber) {
        int position = cellNumber + cellNumber - 2;
        return gameBoard.substring(position, position + 1);
    }

    /**
     * Changes the current player
     */
    private void changePlayer() {
        if (currentPlayer == 1) {
            currentPlayer = 2;
        } else {
            currentPlayer = 1;
        }
    }

    /**
     * Lets the player make a move
     *
     * @param cellNumber     Cell index, such as 1,2,3,..,8,9
     * @param changeLanguage Should the language be changed?
     */
    public void makeAMove(int cellNumber, boolean changeLanguage) {
        //Turn Message
        languageHandler.displayTurnMessage(currentPlayer);

        if (gameLogic.isValidMove(cellNumber)) {

            //Stores the Value in the gameboard
            setCell(cellNumber);

            //print the current gameField
            gamePrinter.printActiveGameField();

            //Check if a player has won or the game is in a draw state
            int checkWin = gameLogic.checkWin();
            if (checkWin == -1) {
                changePlayer();
                //Change language
                languageHandler.displayChangeLangQuestion();

                if (changeLanguage) {
                    languageHandler.changeLanguage();
                }

            } else if (checkWin == 0) {
                //Player  O Winmessage
                languageHandler.displayWinMessage(1);

            } else if (checkWin == 1) {
                //Player X Winmeesage
                languageHandler.displayWinMessage(2);

            } else {
                //Draw
                languageHandler.displayDrawMessage();

            }
        } else {
            languageHandler.displayIllegalTurn();
        }
    }

    /**
     * Stores a value in the gameboard
     *
     * @param cellNumber cellNumber such as 1,2,3, ... 9
     */
    private void setCell(int cellNumber) {
        //Var defs
        String character;
        String combined;
        int index = cellNumber + cellNumber - 2;

        //get String part up to index
        String start = gameBoard.substring(0, index + 1);

        //get String part after index
        String end = gameBoard.substring(index + 1, 17);
        if (this.currentPlayer == 2) {
            character = "O";
        } else {
            character = "X";
        }

        //Combine start part, the newly generated character and end part
        start = start.substring(0, start.lastIndexOf(" "));
        combined = start + character + end;
        this.gameBoard = combined;
    }
}
