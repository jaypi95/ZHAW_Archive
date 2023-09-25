
/**
 * Die Klassen Uhrenanzeige implementiert die Anzeige einer Digitaluhr.
 * Die Anzeige zeigt Stunden und Minuten. Der Anzeigebereich reicht von
 * 00:00 (Mitternacht) bis 23:59 (eine Minute vor Mitternacht).
 *
 * Eine Uhrenanzeige sollte minütlich "Taktsignale" (über die Operation
 * "taktsignalGeben") erhalten, damit sie die Anzeige aktualisieren 
 * kann. Dies geschieht, wie man es bei einer Uhr erwartet: Die 
 * Stunden erhöhen sich, wenn das Minutenlimit einer Stunde erreicht
 * ist.
 * 
 * @author Michael Kölling und David J. Barnes
 * @version 31.07.2011
 */
public class Uhrenanzeige
{
  private Nummernanzeige stunden;
  private Nummernanzeige minuten;
  private Nummernanzeige sekunden;
  private String zeitanzeige;    // simuliert die tatsächliche Anzeige

  /**
   * Konstruktor für ein Exemplar von Uhrenanzeige.
   * Mit diesem Konstruktor wird die Anzeige auf 00:00 initialisiert.
   */
  public Uhrenanzeige()
  {
    stunden = new Nummernanzeige(24);
    minuten = new Nummernanzeige(60);
    sekunden = new Nummernanzeige(60);
    anzeigeAktualisieren();
  }

  /**
   * Konstruktor für ein Exemplar von Uhrenanzeige.
   * Mit diesem Konstruktor wird die Anzeige auf den Wert
   * initialisiert, der durch 'stunde', 'minute' und 'sekunde'
   * definiert ist.
   */
  public Uhrenanzeige(int stunde, int minute, int sekunde)
  {
    stunden = new Nummernanzeige(24);
    minuten = new Nummernanzeige(60);
    sekunden = new Nummernanzeige(60);
    setzeUhrzeit(stunde, minute, sekunde);
  }

  /**
   * Diese Operation sollte einmal pro Sekunde aufgerufen werden -
   * sie sorgt dafür, dass diese Uhrenanzeige um eine Sekunde
   * weiter gestellt wird.
   */
  public void taktsignalGeben()
  {
    sekunden.erhoehen();
    if(sekunden.gibWert() == 0) {  // Limit wurde erreicht!
      minuten.erhoehen();
      if(minuten.gibWert() == 0) {
          stunden.erhoehen();
        }
    }
    anzeigeAktualisieren();
  }

  /**
   * Setze die Uhrzeit dieser Anzeige auf die gegebene 'stunde', sekunde und
   * 'minute'.
   */
  public void setzeUhrzeit(int stunde, int minute, int sekunde)
  {
    stunden.setzeWert(stunde);
    minuten.setzeWert(minute);
    sekunden.setzeWert(sekunde);
    anzeigeAktualisieren();
  }

  /**
   * Liefere die aktuelle Uhrzeit dieser Uhrenanzeige im Format SS:MM:ss.
   */
  public String gibUhrzeit()
  {
    return zeitanzeige;
  }

  /**
   * Aktualisiere die interne Zeichenkette, die die Zeitanzeige h�lt.
   */
  private void anzeigeAktualisieren()
  {
    zeitanzeige = stunden.gibAnzeigewert() + ":"
        + minuten.gibAnzeigewert() + ":" + sekunden.gibAnzeigewert();
  }
}
