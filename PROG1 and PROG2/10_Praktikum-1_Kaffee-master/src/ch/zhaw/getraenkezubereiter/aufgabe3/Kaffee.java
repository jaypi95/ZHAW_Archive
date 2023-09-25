package ch.zhaw.getraenkezubereiter.aufgabe3;

/**
 * Diese Klasse bietet die Funktionalitaet, um einen Kaffee
 * zu kochen.
 * 
 * @author tebe
 */
public class Kaffee extends KoffeinGetraenk {


  /**
   * Bereitet einen Kaffee zu.
   */
  public void bereiteZu() {
    kocheWasser();
    braueFilterKaffee();
    giesseInTasse();
    fuegeZuckerUndMilchHinzu();
    System.out.println("Kaffee fertig");
  }



  // Weitere Methoden

  private void braueFilterKaffee(){
    System.out.println("brew.....");
  }

  private void fuegeZuckerUndMilchHinzu(){
    System.out.println("zuckere......milche......");
  }
}
