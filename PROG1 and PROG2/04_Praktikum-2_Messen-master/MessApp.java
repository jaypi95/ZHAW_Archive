/**
 * Die Klasse MessApp steuert einen Messablauf, um die Performance des
 * Zufallszahlengenerators zu messen.
 */
public class MessApp {
  private Messkonduktor messkonduktor;
  private int[][] laufzeiten;
  private int anzahlZufallszahlen;
  private int anzahlMessungen;
  /**
   * Fuehrt eine Messung durch.
   */
  public void messen(int anzahlZufallszahlen,int anzahlMessungen) {
    initialisieren(anzahlMessungen);
    analyseDurchfuehren(anzahlZufallszahlen);
    berechneUndDruckeMittelwerteMessreihe();
    berechneUndDruckeMittelwerteMessung();
  }

  private void initialisieren(int anzahlMessungen) {
    // TODO Objektsammlung und Messkonduktor erzeugen
    laufzeiten = new int[10][anzahlMessungen];
    
  }

  private void analyseDurchfuehren(int anzahlZufallszahlen) {
    // TODO Benutzen Sie 'messkonduktor' um die Messungen
    // durchzufuehren und in der Objektsammlung zu speichern.
    messkonduktor = new Messkonduktor(anzahlZufallszahlen);
    for (int i = 0; i < laufzeiten.length; i++){
            laufzeiten[i] = messkonduktor.messungenDurchfuehren(laufzeiten[i]);
         
            
        }
        
  }

  private void berechneUndDruckeMittelwerteMessreihe() {
    // TODO Implementieren Sie die Methode.
    
    for(int i = 0; i < laufzeiten.length; i++){
        double mittelwert = 0;
        for (int p = 0; p < laufzeiten[i].length; p++){
           mittelwert = mittelwert + laufzeiten[i][p];
        }
        mittelwert = mittelwert / laufzeiten[i].length;
        System.out.print("Mittelwert Messreihe Nr.");
        System.out.print(i + 1);
        System.out.print(": " + mittelwert + "ms");
        System.out.println("");
    }
  }

  private void berechneUndDruckeMittelwerteMessung() {
    // TODO Implementieren Sie die Methode.
    for(int i = 0; i < laufzeiten[0].length; i++){
        double mittelwert = 0;
        for (int p = 0; p < laufzeiten.length; p++){
           mittelwert = mittelwert + laufzeiten[p][i];
        }
        mittelwert = mittelwert / laufzeiten.length;
        System.out.print("Mittelwert alle ");
        System.out.print(i + 1);
        System.out.print(". Messungen");
        System.out.print(": " + mittelwert + "ms");
        System.out.println("");
    }
  }
}