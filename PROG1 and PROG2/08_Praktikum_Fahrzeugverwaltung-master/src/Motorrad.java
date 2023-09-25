import java.util.ArrayList;
import java.util.List;

/**
 * Diese Klasse speichert Informationen eines Motorrads.
 * @author Marc Rennhard
 */
public class Motorrad extends Motorfahrzeug{

  private boolean abs;
  private List<Verkauf> verkaeufe;

  /**
   * Konstruktor, um ein Motorrad zu erzeugen.
   *
   * @param modell     Das Modell
   * @param preis      Der Preis
   * @param stueckzahl Die verfuegbare Stueckzahl
   * @param leistung   Die Leistung in PS
   * @param abs        Ob das Motorrad ABS hat
   */
  public Motorrad(String modell, int preis, int stueckzahl, int leistung, boolean abs) {
    super(modell, preis, stueckzahl, leistung);
    this.abs = abs;
    verkaeufe = new ArrayList<Verkauf>();
  }


  /**
   * Liefert ob das Motorrad ABS hat.
   *
   * @return Ob das Motorrad ABS hat (true) oder nicht
   */
  public boolean gibAbs() {
    return abs;
  }
}