import java.util.ArrayList;
import java.util.List;

/**
 * Diese Klasse speichert Informationen eines Autos.
 * @author Marc Rennhard
 */
public class Auto extends Motorfahrzeug
{

  private int plaetze;
  private boolean vierradantrieb;

  
  /**
   * Konstruktor, um ein Auto zu erzeugen.
   * @param modell Das Modell 
   * @param preis Der Preis
   * @param stueckzahl Die verfuegbare Stueckzahl
   * @param leistung Die Leistung in PS
   * @param plaetze Die Sitzplaetze
   * @param vierradantrieb Ob das Auto Vierradantrieb hat
   */
  public Auto(String modell, int preis, int stueckzahl, int leistung, int plaetze, boolean vierradantrieb)
  {
    super(modell, preis, stueckzahl, leistung);
    this.plaetze = plaetze;
    this.vierradantrieb = vierradantrieb;

  }

  /**
   * Liefert die Anzahl Sitzplaetze des Autos.
   * @return Die Anzahl Plaetze
   */
  public int gibPlaetze()
  {
    return plaetze;
  }
  
  /**
   * Liefert ob das Auto Vierradantrieb hat.
   * @return Ob das Auto Vierradantrieb hat (true) oder nicht
   */
  public boolean gibVierradantrieb()
  {
    return vierradantrieb;
  }
  


}
