/**
 * Diese Klasse modeliert einen Gegenstand
 * mit seinem Namen und seiner Beschreibung sowie 
 * seinem Gewicht.
 *  
 * @author tebe
 * @version 1.0
 *
 */
public class Gegenstand {
	private final String name;
	private final String beschreibung;
	private final int gewicht;
	
	/**
	 * Erzeugt einen Gegenstand mit Namen und 
	 * Gewicht aber ohne genauere Beschreibung.
	 * 
	 * Der Name darf nicht null sein und das
	 * Gewicht muss groesser als 0 sein.
	 *  
	 * @param name Der Name des Gegenstands
	 * @param gewicht Das Gewicht des Gegenstands
	 */
	public Gegenstand(String name, int gewicht) {
		this(name, "", gewicht);
	}

	/**
	 * Erzeugt einen Gegenstand mit Namen und 
	 * Gewicht und genauere Beschreibung.
	 * 
	 * Name und Beschreibung duerfen nicht null sein und das
	 * Gewicht muss groesser als 0 sein.
	 * 
	 * @param name Der Name des Gegenstands
	 * @param gewicht Das Gewicht des Gegenstands
	 */
	public Gegenstand(String name, String beschreibung, int gewicht){		
		this.name = name;
		this.gewicht = gewicht;
		this.beschreibung = beschreibung;
		if(! istDefinitionGueltig()) {
			throw new IllegalArgumentException(
					"Gegenstandsdefinition ist ungueltig.");
		}
	}
	
	private boolean istDefinitionGueltig(){
		return !(name == null || beschreibung == null || gewicht <= 0);
	}
	
	/**
	 * @return Der Name des Gegenstands
	 */
	public String gibName() {
		return name;
	}

	/**
	 * Gibt die Beschreibung des Gegenstandes zurueck.
	 * Falls der Gegenstand ueber keine Beschreibung
	 * verfuegt, wird ein leerer String zurueckgegeben.
	 * @return Die Beschreibung des Gegenstands
	 */
	public String gibBeschreibung() {
		return beschreibung;
	}

	/**
	 * @return Das Gewicht des Gegenstands
	 */
	public int gibGewicht() {
		return gewicht;
	}
}
