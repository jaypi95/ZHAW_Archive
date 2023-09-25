import java.util.Calendar;

/**
 * Diese Klasse verwaltet einen Inhalt bestehend aus Datum,
 * Titel, Anrede und Text 
 * @author rema
 *
 */
public class Inhalt {
  private Calendar datum;
  private String titel;
  private String anrede;
  private String text;

	/**
	 * Konstruktor. Erzeugt einen Inhalt.
	 * @param datum Das Datum des Inhalts
	 * @param titel Der Titel des Inhalts
	 * @param anrede Die Anrede des Inhalts
	 * @param text Der Text des Inhalts
	 */
	public Inhalt(Calendar datum, String titel, String anrede, String text) {
		this.datum = datum;
		this.titel = titel;
		this.anrede = anrede;
		this.text = text;
	}

  /**
   * Liefert das Datum zurueck.
   * @return Das Datum des Inhalts
   */
  public Calendar getDatum() {
    return datum;
  }

   /**
   * Liefert den Titel zurueck.
   * @return Der Titel des Inhalts
   */
  public String getTitel() {
    return titel;
  }

	/**
	 * Liefert die Anrede zurueck.
	 * @return Die Anrede des Inhalts
	 */
	public String getAnrede() {
		return anrede;
	}

  /**
  * Liefert den Text zurueck.
  * @return Der Text des Inhalts
  */
	public String getText() {
		return text;
	}
}
