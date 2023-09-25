/**
 * Diese Klasse speichert Informationen eines Kunden.
 * @author Marc Rennhard
 */
public class Kunde 
{
  private String name;

  /**
   * Konstruktor, um einen Kunden zu erzeugen.
   * @param name Der Name des Kunden
   */
  public Kunde(String name)
  {
    this.name = name;
  }
  
  /**
   * Liefert Informationen des Kunden.
   * @return Infirmationen des Kunden
   */
  public String gibInfo()
  {
    return name;
  }
}
