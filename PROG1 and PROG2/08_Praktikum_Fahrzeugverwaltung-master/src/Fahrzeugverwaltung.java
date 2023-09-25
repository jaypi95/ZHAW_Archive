import java.util.ArrayList;

/**
 * Diese Klasse verwaltet die Fahrzeuge.
 * @author Marc Rennhard
 */
public class Fahrzeugverwaltung 
{
  private ArrayList<Fahrrad> fahrraeder;
  private ArrayList<Motorrad> motorraeder;
  private ArrayList<Auto> autos;
  
  /**
   * Konstruktor. Erzeugt eine Fahrzeugverwaltung.
   */
  public Fahrzeugverwaltung() 
  {
    motorraeder = new ArrayList<Motorrad>();
    autos = new ArrayList<Auto>();
    fahrraeder = new ArrayList<Fahrrad>();
  }
  
  /**
   * Erzeugt ein Fahrrad.
   * @param modell Das Modell
   * @param preis Der Preis
   * @param stueckzahl Die verfuegbare Stueckzahl
   * @param gaenge Die Anzahl gaenge
   */
  public void erzeugeFahrrad(String modell, int preis, int stueckzahl, int gaenge) {
    Fahrrad fahrrad = new Fahrrad(modell, preis, stueckzahl, gaenge);
    fahrraeder.add(fahrrad);
  }
  
  /**
   * Erzeugt ein Motorrad.
   * @param modell Das Modell
   * @param preis Der Preis
   * @param stueckzahl Die verfuegbare Stueckzahl
   * @param leistung Die Leistung
   * @param abs Ob das Motorrad ABS hat
   */
  public void erzeugeMotorrad(String modell, int preis, int stueckzahl, int leistung, boolean abs) {
    Motorrad motorrad = new Motorrad(modell, preis, stueckzahl, leistung, abs);
    motorraeder.add(motorrad);
  }
  
  /**
   * Erzeugt ein Auto.
   * @param modell Das Modell
   * @param preis Der Preis
   * @param stueckzahl Die verfuegbare Stueckzahl
   * @param leistung Die Leistung
   * @param plaetze Die Anzahl Sitzplaetze
   * @param vierradantrieb Ob das Auto Vierradantrieb hat
   */
  public void erzeugeAuto(String modell, int preis, int stueckzahl, int leistung, int plaetze, boolean vierradantrieb) {
    Auto auto = new Auto(modell, preis, stueckzahl, leistung, plaetze, vierradantrieb);
    autos.add(auto);
  } 

  /**
   * Kauft ein Fahrrad in der gewuenschten Stueckzahl und gibt Informationen über das Ergebnis auf die Konsole aus.
   * @param Modell Das gewuenschte Modell
   * @param stueckzahl Die gewuenschte Stueckzahl
   * @param kunde Der Kunde
   */
  public void kaufeFahrrad(String modell, int stueckzahl, Kunde kunde) {
    for (Fahrrad fahrrad : fahrraeder) {
      if (fahrrad.gibModell().equals(modell)) {
        String kaufergebnis = fahrrad.kaufen(stueckzahl, kunde);
        System.out.println(kaufergebnis);
        return;
      }
    }
    System.out.println("Das Modell " + modell + " konnte nicht gefunden werden");
  }

  /**
   * Kauft ein Motorrad in der gewuenschten Stueckzahl und gibt Informationen über das Ergebnis auf die Konsole aus.
   * @param Modell Das gewuenschte Modell
   * @param stueckzahl Die gewuenschte Stueckzahl
   * @param kunde Der Kunde
   */
  public void kaufeMotorrad(String modell, int stueckzahl, Kunde kunde) {
    for (Motorrad motorrad : motorraeder) {
      if (motorrad.gibModell().equals(modell)) {
        String kaufergebnis = motorrad.kaufen(stueckzahl, kunde);
        System.out.println(kaufergebnis);
        return;
      }
    }
    System.out.println("Das Modell " + modell + " konnte nicht gefunden werden");
  }

  /**
   * Kauft ein Auto in der gewuenschten Stueckzahl und gibt Informationen über das Ergebnis auf die Konsole aus..
   * @param Modell Das gewuenschte Modell
   * @param stueckzahl Die gewuenschte Stueckzahl
   * @param kunde Der Kunde
   */
  public void kaufeAuto(String modell, int stueckzahl, Kunde kunde) {
    for (Auto auto : autos) {
      if (auto.gibModell().equals(modell)) {
        String kaufergebnis = auto.kaufen(stueckzahl, kunde);
        System.out.println(kaufergebnis);
        return;
      }
    }
    System.out.println("Das Modell " + modell + " konnte nicht gefunden werden");
  }

  /**
   * Gibt Informationen aller Fahrzeuge aus
   */
  public void ausgeben() 
  {
    for (Fahrrad fahrrad : fahrraeder) {
      System.out.println(fahrrad.gibInfo());
    }
    for (Motorrad motorrad : motorraeder) {
      System.out.println(motorrad.gibInfo());
    }
    for (Auto auto : autos) {
      System.out.println(auto.gibInfo());
    }
  }
}
