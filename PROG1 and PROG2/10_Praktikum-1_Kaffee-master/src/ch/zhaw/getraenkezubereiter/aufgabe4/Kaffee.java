package ch.zhaw.getraenkezubereiter.aufgabe4;

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
  public void bereiteKaffeeZu() {
    bereiteZu();
    System.out.println("Kaffee fertig");
  }



  // Weitere Methoden

  protected void braue(){
    System.out.println("braue kaffee.....");
  }

  protected void fuegeZutatenHinzu(){
    System.out.println("zuckere......milche......");
  }
}
