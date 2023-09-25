/**
 * This class changes between English and German
 *
 * @author peterju1
 * @version 2020100202
 */
public class LanguageHandler {
    //0 Deutsch, 1 English
    private int language;

    /**
     * Initializing variable with default language.
     */
    public LanguageHandler() {
        this.language = 0;
    }

    /**
     * Initializing variable with language value
     * This also validates if the value is 0 or 1
     *
     * @param pLanguage start language
     */
    public LanguageHandler(int pLanguage) {
        if (pLanguage == 0 || pLanguage == 1) {
            language = pLanguage;
        } else {
            language = -1;
        }
    }

    /**
     * Changes language if user wishes so
     */
    public void changeLanguage() {
        if (language == 0) {
            language = 1;
            displayLanguagedChaned();
        } else if (language == 1) {
            language = 0;
            displayLanguagedChaned();
        } else {
            System.out.println("Invalid language input");
        }
    }

    /**
     * Shows if a user tried an illegal turn
     */
    public void displayLanguagedChaned() {
        if (language == 0) {
            System.out.println("Sprache wurde erfolgreich umgestellt!");
        } else if (language == 1) {
            System.out.println("Language changed successfully!");
        } else {
            System.out.println("Invalid language input");
        }
    }

    /**
     * Shows if a user tried an illegal turn
     */
    public void displayIllegalTurn() {
        if (language == 0) {
            System.out.println("Dieser Zug ist nicht erlaubt. Machen Sie einen anderen Zug.");
        } else if (language == 1) {
            System.out.println("This turn is not allowed. Please make another turn.");
        } else {
            System.out.println("Invalid language input");
        }
    }

    /**
     * Informs user which players turn it is
     *
     * @param pPlayer Number of player whose turn it is
     */
    public void displayTurnMessage(int pPlayer) {
        if (language == 0) {
            System.out.println("Spieler " + pPlayer + " ist am Zug.");
        } else if (language == 1) {
            System.out.println("It's the turn of Player " + pPlayer);
        } else {
            System.out.println("Invalid language input");
        }
    }

    /**
     * Informs player if he won
     *
     * @param pPlayer Number of player who won
     */
    public void displayWinMessage(int pPlayer) {
        if (language == 0) {
            System.out.println("Spieler " + pPlayer + " hat gewonnen!");
        } else if (language == 1) {
            System.out.println("Player " + pPlayer + " has won!");
        } else {
            System.out.println("Invalid language input");
        }
    }

    /**
     * If the game is a draw (nobody won)
     */
    public void displayDrawMessage() {
        if (language == 0) {
            System.out.println("Das Spiel ist unentschieden.");
        } else if (language == 1) {
            System.out.println("It's a draw.");
        } else {
            System.out.println("Invalid language input");
        }
    }

    /**
     * Asks user if he wants to change the language
     */
    public void displayChangeLangQuestion() {
        if (language == 0) {
            System.out.println("Möchten Sie die Sprache umstellen? y/n");
        } else if (language == 1) {
            System.out.println("Do you want to change the language? y/n");
        } else {
            System.out.println("Invalid language input");
        }
    }

    /**
     * Informs about language change
     */
    public void displayLangSuccessChange() {
        if (language == 0) {
            System.out.println("Sprache erfolgreich umgestellt.");
        } else if (language == 1) {
            System.out.println("Language successfully changed.");
        } else {
            System.out.println("Invalid language input");
        }
    }

    /**
     * Informs user that he has to answer the change language question with y or n
     */
    public void displayChangeLangError() {
        if (language == 0) {
            System.out.println("Bitte beantworten Sie die Frage mit y oder n");
        } else if (language == 1) {
            System.out.println("Please answer the question with y or n");
        } else {
            System.out.println("Invalid language input");
        }
    }

    /**
     * Informs User that the input for his turn is not valid
     */
    public void displayIllegalInput() {
        if (language == 0) {
            System.out.println("Diese Eingabe ist nicht erlaubt. Bitte geben Sie die Nummer eines Feldes ein.");
        } else if (language == 1) {
            System.out.println("This input is not allowed. Please input the number of a field.");
        } else {
            System.out.println("Invalid language input");
        }
    }

    /**
     * Displays the welcome message
     */
    public void displayWelcomeMessage() {
        if (language == 0) {
            System.out.println("Willkommen bei Tic-Tac-Toe");
        } else if (language == 1) {
            System.out.println("Welcome to Tic-Tac-Toe");
        } else {
            System.out.println("Invalid language input");
        }
    }
}
