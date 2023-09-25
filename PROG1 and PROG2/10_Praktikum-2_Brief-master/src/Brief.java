/**
 * Diese Klasse verwaltet einen Brief.
 * 
 * @author tebe
 */
public class Brief {
  private Adresse sender;
  private Adresse empfaenger;
  private Inhalt inhalt;

  /**
   * Konstruktor. Erzeugt einen Brief.
   * @param sender Der Absender des Briefs
   * @param empfaenger Der Empfaenger des Briefs
   * @param inhalt Der Inhalt des Briefs
   */
  public Brief(Adresse sender, Adresse empfaenger, Inhalt inhalt) {
    this.sender = sender;
    this.empfaenger = empfaenger;
    this.inhalt = inhalt;
  }

  /**
   * Liefert den Sender des Briefs.
   * @return Der Sender des Briefs
   */
  public Adresse getSender() {
    return sender;
  }

  /**
   * Liefert den Empfänger des Briefs.
   * @return Der Empfänger des Briefs
   */
  public Adresse getEmpfaenger() {
    return empfaenger;
  }

  /**
   * Liefert den Inhalt des Briefs.
   * @return Der Inhalt des Briefs
   */
  public Inhalt getInhalt() {
    return inhalt;
  }
}
