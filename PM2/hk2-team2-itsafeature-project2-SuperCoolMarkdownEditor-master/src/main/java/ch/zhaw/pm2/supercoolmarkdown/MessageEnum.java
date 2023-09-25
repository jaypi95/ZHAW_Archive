package ch.zhaw.pm2.supercoolmarkdown;

/**
 * This Enum stores all text which gets displayed for the user
 *
 * @author Peterju1
 * @version 12.05.2021
 */
public enum MessageEnum {

    //Error messages
    FILE_NOT_FOUND("Die angegebene Datei konnte nicht gefunden werden!"),
    DAMAGED_FILE("Die angegebene Datei ist beschädigt und kann daher nicht geöffnet werden!"),
    FILE_IO_ERROR("Die angegebene Datei konnte nicht gelesen werden. Haben Sie genügend Rechte?"),

    //Filechooser
    FILECHOOSER_ASK_FOR_FILE("Wählen Sie eine Datei"),
    FILECHOOSER_ASK_FOR_DIR("Wählen Sie einen Speicherort"),

    //Alert
    ALERT_TITLE("Warnung"),
    ALERT_WANT_TO_SAVE("Möchten Sie die Änderungen speichern?"),
    ALERT_COULD_NOT_WRITE("Die Datei konnte nicht geschrieben werden. Haben Sie genügend Rechte?"),
    ALERT_INVALID_FONT_SIZE("Die eingegebene Schriftgrösse scheint ungültig zu sein! Wählen Sie bitte eine andere."),
    ALERT_MARKDOWN_INVALID("Das eingegebene Markdown scheint ungültig zu sein! Bitte überprüfen Sie die Syntax."),
    ALERT_MARKDOWN_IS_SHOWN("Aktuell wird der Markdowntext angezeigt! Bitte wechseln Sie die Ansicht, bevor Sie speichern können!"),

    //Button text
    BUTTON_TEXT_SHOW_MARKDOWN("Markdown anzeigen"),
    BUTTON_TEXT_SHOW_FORMATTED_TEXT("Formatierter Text anzeigen");

    private final String message;

    /**
     * Constructor of this enum
     *
     * @param message the message which gets stored
     */
    MessageEnum(String message) {
        this.message = message;
    }

    /**
     * @return the String value of the tag
     */
    @Override
    public String toString() {
        return message;
    }
}
