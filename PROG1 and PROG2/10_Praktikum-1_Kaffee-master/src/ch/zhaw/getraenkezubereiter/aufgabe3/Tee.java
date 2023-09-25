package ch.zhaw.getraenkezubereiter.aufgabe3;

/**
 * Diese Klasse bietet die Funktionalitaet, um einen Tee
 * zu kochen.
 * 
 * @author tebe
 */
public class Tee extends KoffeinGetraenk {



  /**
   * Bereitet einen Tee zu.
   */
  public void bereiteZu() {

    kocheWasser();
    taucheTeebeutel();
    giesseInTasse();
    fuegeZitroneHinzu();
    System.out.println("Tee fertig");
  }


  // Weitere Methoden

  private void taucheTeebeutel(){
    System.out.println("tauch.....");
  }

  private void fuegeZitroneHinzu(){
    System.out.println("zitrone......");
  }
}