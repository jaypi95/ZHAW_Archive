package ch.zhaw.getraenkezubereiter.aufgabe4;

/**
 * Diese Klasse bietet die Funktionalitaet, um einen Tee
 * zu kochen.
 * 
 * @author tebe
 */
public class Tee extends KoffeinGetraenk{



  /**
   * Bereitet einen Tee zu.
   */
  public void bereiteTeeZu() {
    bereiteZu();
    System.out.println("Tee fertig");
  }


  // Weitere Methoden


  protected void braue(){
    System.out.println("tauche......");
  }

  protected void fuegeZutatenHinzu(){
    System.out.println("zitrone.....");
  }
}