/**
 * Diese Klasse speichert Informationen eines Verkaufs.
 * @author Marc Rennhard
 *
 */
public class Verkauf 
{
  private Kunde kunde;
  private int stueckzahl;
  private int gesamtpreis;
  
  /**
   * Konstruktor, erzeugt einen Verkauf.
   * @param kunde Der Kunde
   * @param stueckzahl Die gekaufte Stueckzahl
   * @param gesamtpreis Der Gesamtpreis
   */
  public Verkauf(Kunde kunde, int stueckzahl, int gesamtpreis)
  {
    this.kunde = kunde;
    this.stueckzahl = stueckzahl;
    this.gesamtpreis = gesamtpreis;
  }
  
  /**
   * Gibt Informationen des Verkaufs zurueck.
   * @return Informationen des Verkaufs
   */
  public String gibInfo() {
    return String.format("Kunde: %s: %d Stueck zu total CHF", kunde.gibInfo(), stueckzahl, gesamtpreis);
  }
}
