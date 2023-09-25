/**
 * Diese Klasse verwaltet eine Adresse.
 * 
 * @author tebe
 */
public class Adresse {
  private String name;
  private String vorname;
  private String strasse;
  private int hausnummer;
  private String ort;
  private int plz;

  /**
   * Konstruktor. Erzeugt eine Adresse.
   * @param vorname Der Vorname
   * @param name Der Nachname
   * @param strasse Die Strasse
   * @param hausnummer Die Hausnummer
   * @param plz Die Postleitzahl
   * @param ort Der Ort
   */
  public Adresse(String vorname, String name, String strasse,
      int hausnummer, int plz, String ort) {
    this.name = name;
    this.vorname = vorname;
    this.strasse = strasse;
    this.hausnummer = hausnummer;
    this.ort = ort;
    this.plz = plz;
  }

  /**
   * Liefert den Nachnamen.
   * @return Der Nachname
   */
  public String getNachname() {
    return name;
  }

  /**
   * Liefert den Vornamen.
   * @return Der Vorname
   */
  public String getVorname() {
    return vorname;
  }

  /**
   * Liefert die Strasse
   * @return Die Strasse
   */
  public String getStrasse() {
    return strasse;
  }

  /**
   * Liefert die Hausnummer.
   * @return Die Hausnummer
   */  
  public int getHausnummer() {
    return hausnummer;
  }

  /**
   * Liefert den Ort.
   * @return Der Ort
   */
  public String getOrt() {
    return ort;
  }

  /**
   * Liefert die Postleitzahl.
   * @return Die Postleitzahl
   */
  public int getPlz() {
    return plz;
  }
}
