/**
 * Die Klasse Buch repraesentiert ein Buch und beinhaltet 
 * Buchtitel, ISBN-Nummer (ISBN-13, z.B. 978-3868949070) 
 * und den Lagerbestand. 
 * @author Marc Rennhard
 */
public class Buch {
	private String titel;
	private int isbn;
	// Der aktuelle Bestand
	private int bestand;
	
	/**
	 * Erzeugt ein Buch mit den spezifizierten Werten fuer 
	 * Titel und ISBN-Nummer und setzt den Bestand auf 0.
	 * @param buchTitel Der Buchtitel.
	 */
	public Buch(String buchTitel,int buchISBN) {
		titel = buchTitel;
		isbn = buchISBN;
		bestand = 0;
	}
	
	/**
	 * Liefert den aktuellen Bestand.
	 * @return Der Bestand.
	 */
	private int getBestand() {
		return bestand;
	}
	
	/**
	 * Veraendert den Bestand um den angegebenen Wert.
	 * @param veraenderung Die Veraenderung des Bestands.
	 */
	public int veraendereBestand(int veraenderung) {
		bestand = bestand + veraenderung;
		return bestand;
	}
	
	/**
	 * Gibt die Informationen eines Buches aus.
	 */
	public void output() {
		System.out.println("Titel: " + titel);
		System.out.println("ISBN-13: " + isbn);
		System.out.println("Bestand: " + bestand);
		
	} 
	
}