import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Dies ist die Hauptklasse der Anwendung "Die Welt von Zuul".
 * "Die Welt von Zuul" ist ein sehr einfaches, textbasiertes Adventure-Game. Ein
 * Spieler kann sich in einer Umgebung bewegen, die Kontrolle von anderen Personen
 * im Raum uebernehmen und Gegenstaende einpacken, sofern seine Tragkraft ausreicht.
 * Das Spiel sollte auf jeden Fall noch weiter ausgebaut werden, damit es interessanter wird!
 * 
 * 
 * Diese Instanz dieser Klasse erzeugt und initialisiert alle anderen Objekte
 * der Anwendung: Sie legt alle Raeume und einen Parser an und startet das
 * Spiel. Sie wertet auch die Befehle aus, die der Parser liefert und sorgt fuer
 * ihre Ausfuehrung.
 * 
 * @author tebe (Original: Michael Koelling und David J. Barnes)
 * @version 1.0
 */

public class Spiel {
	private Parser parser;
	private Raum aktuellerRaum;
	private Person spieler;
	private static Scanner scanner;
	private static boolean run;

	/**
	 * Erzeuge ein Spiel und initialisiere die Spielwelt.
	 */
	public Spiel() {
		spielweltErzeugen();
		parser = new Parser();
		scanner = new Scanner(System.in);
		run = true;
	}
	public static void main(String[] args){

		Spiel anwendung = new Spiel();
		while (run) {
			String input = scanner.nextLine();
			anwendung.spielen();
		}
	}
	/**
	 * Baut die Spielewelt auf. Erzeugt die Raeume mit Verbindungen und fuellt
	 * diese mit Personen und Gegenstaenden.
	 */
	private void spielweltErzeugen() {
		spieler = new Person("Captain Kirk", 20);
		ArrayList<Raum> raeume = raeumeAnlegen();
		fuellenMitPersonen(raeume);
		fuellenMitGegenstaenden(raeume);
	}

	/**
	 * Erzeuge alle Raeume, verbinde ihre Ausgaenge miteinander.
	 * 
	 * @return Die angelegten Raeume
	 */
	private ArrayList<Raum> raeumeAnlegen() {
		HashMap<String, Raum> raum = new HashMap<String, Raum>();
		// die Raeume erzeugen
		raum.put("draussen", new Raum("vor dem Haupteingang der Universitaet"));
		raum.put("hoersaal", new Raum("in einem Vorlesungssaal"));
		raum.put("cafeteria", new Raum("in der Cafeteria der Uni"));
		raum.put("labor", new Raum("in einem Rechnerraum"));
		raum.put("buero", new Raum("im Verwaltungsbuero der Informatik"));
		// die Ausgaenge initialisieren
		raum.get("draussen").setzeAusgang("osten", raum.get("hoersaal"));
		raum.get("draussen").setzeAusgang("sueden", raum.get("labor"));
		raum.get("draussen").setzeAusgang("westen", raum.get("cafeteria"));
		raum.get("hoersaal").setzeAusgang("westen", raum.get("draussen"));
		raum.get("cafeteria").setzeAusgang("osten", raum.get("draussen"));
		raum.get("labor").setzeAusgang("norden", raum.get("draussen"));
		raum.get("labor").setzeAusgang("osten", raum.get("buero"));
		raum.get("buero").setzeAusgang("westen", raum.get("labor"));

		// Startraum
		aktuellerRaum = raum.get("draussen");
		ArrayList<Raum> raumliste = new ArrayList<Raum>();
		for (Raum r : raum.values()) {
			raumliste.add(r);
		}
		return raumliste;
	}

	/**
	 * Verteilt eine Anzahl Personen auf eine Liste von Raeumen. Die Zuteilung
	 * erfolgt auf Basis einer Zufallsstrategie.
	 * 
	 * @param raum
	 *            Liste der Raeume
	 */
	private void fuellenMitPersonen(ArrayList<Raum> raum) {
		ArrayList<Person> person = new ArrayList<Person>();
		person.add(new Person("Dr. Hans Muster", 40));
		person.add(new Person("Peter Stark", 80));
		person.add(new Person("Anna Pfister", 45));
		person.add(new Person("Prof. Dr. Luna Berger", 35));
		int counter = 0;
		while (person.size() > 0) {
			if (Math.random() > 0.5) {
				raum.get(counter).betreten(person.get(0));
				person.remove(0);
			}
			counter = (counter + 1) % raum.size();
		}
	}

	/**
	 * Verteilt eine Anzahl Gegenstaende auf eine Liste von Raeumen. Die
	 * Zuteilung erfolgt auf Basis einer Zufallsstrategie.
	 * 
	 * @param raum
	 *            Liste der Raeume
	 */
	private void fuellenMitGegenstaenden(ArrayList<Raum> raum) {
		ArrayList<Gegenstand> gegenstand = new ArrayList<Gegenstand>();
		gegenstand.add(new Gegenstand("Sehr schwerer Laserpointer", 1));
		gegenstand.add(new Gegenstand("Beamer", 12));
		gegenstand.add(new Gegenstand("Workstation", 10));
		gegenstand.add(new Gegenstand("Wandtafel", 250));
		gegenstand.add(new Gegenstand("Mineralwasser (6x1.5L)", 9));
		gegenstand.add(new Gegenstand("Laptoptasche mit Laptop", 5));
		gegenstand.add(new Gegenstand("Flipchart", 11));
		gegenstand.add(new Gegenstand("Whiteboard", 8));
		gegenstand.add(new Gegenstand("Toeggelikasten", 30));
		int counter = 0;
		while (gegenstand.size() > 0) {
			if (Math.random() > 0.5) {
				raum.get(counter).hineinlegen(gegenstand.get(0));
				gegenstand.remove(0);
			}
			counter = (counter + 1) % raum.size();
		}
	}

	/**
	 * Die Hauptmethode zum Spielen. Laeuft bis zum Ende des Spiels in einer
	 * Schleife.
	 */
	private void spielen() {
		willkommenstextAusgeben();

		// Die Hauptschleife. Hier lesen wir wiederholt Befehle ein
		// und fuehren sie aus, bis das Spiel beendet wird.
		boolean beendet = false;
		while (!beendet) {
			Befehl befehl = parser.liefereBefehl();
			beendet = verarbeiteBefehl(befehl);
		}
		System.out.println("Danke fuer dieses Spiel. Auf Wiedersehen.");
	}

	/**
	 * Einen Begruessungstext fuer den Spieler ausgeben.
	 */
	private void willkommenstextAusgeben() {
		System.out.println();
		System.out.println("Willkommen zu Zuul!");
		System.out
				.println("Zuul ist ein neues, unglaublich langweiliges Spiel.");
		System.out.println("Tippen sie '" + Befehlswort.HILFE
				+ "', wenn Sie Hilfe brauchen.");
		System.out.println();
		System.out.println(aktuellerRaum.gibLangeBeschreibung());
	}

	/**
	 * Verarbeite einen gegebenen Befehl (fuehre ihn aus).
	 * 
	 * @param befehl
	 *            Der zu verarbeitende Befehl.
	 * @return 'true', wenn der Befehl das Spiel beendet, 'false' sonst.
	 */
	private boolean verarbeiteBefehl(Befehl befehl) {
		boolean moechteBeenden = false;

		Befehlswort befehlswort = befehl.gibBefehl();

		switch (befehlswort) {
		case UNBEKANNT:
			System.out.println("Ich weiss nicht, was Sie meinen...");
			break;
		case UMSEHEN:
			umsehen();
			break;
		case HILFE:
			hilfstextAusgeben();
			break;
		case GEHE:
			wechsleRaum(befehl);
			break;
		case BEENDEN:
			moechteBeenden = beenden(befehl);
			break;
		case UEBERNIMM:
			uebernimm(befehl);
			break;
		case NIMM:
			nimm(befehl);
			break;
		default:
			System.out.println("Befehlswort ohne zugehoerige Aktion.");
			break;
		}
		return moechteBeenden;
	}

	/**
	 * 
	 */
	private void umsehen() {
		System.out.println("Sie sind: " + spieler.gibName());
		System.out.println(aktuellerRaum.gibLangeBeschreibung());
	}

	/**
	 * Packt den spezifizierten Gegenstand in den Rucksack
	 * des Spielers und entfernt ihn aus dem aktuellen Raum.
	 * 
	 * Falls der bezeichnete Gegenstand nicht vorhanden ist, aendert sich nichts.
	 * 
	 * @param befehl Der auszufuehrende Befehl
	 */
	private void nimm(Befehl befehl) {
		if (befehl.hatZweitesWort()) {
			int kennummer = Integer.parseInt(befehl.gibZweitesWort());
			gegenstandEinpacken(kennummer);
		} else {
			System.out.println("Geben Sie die Nummer des Gegenstands an.");
		}
	}

	/**
	 * Packt den Gegenstand mit der gegebenen 
	 * Nummer, falls vorhanden, in den Rucksack 
	 * des Spielers und entfernt ihn aus dem aktuellen Raum.
	 * 
	 * @param nummer Nummer des Gegenstands
	 */
	private void gegenstandEinpacken(int nummer) {
		Gegenstand gegenstand = aktuellerRaum.herausnehmen(nummer);
		if (gegenstand == null) {
			System.out.println("Es gibt keinen Gegenstand mit dieser Nummer: "
					+ nummer);
		} else {
			if (spieler.gibTragkraft() >= berechneGewicht(spieler.getRucksack()) + gegenstand.gibGewicht()) {
				System.out.println("Gegenstand eingepackt: " + gegenstand.gibName());
				spieler.getRucksack().add(gegenstand);
			} else {
				System.out
						.println("Gegenstand konnte nicht eingepackt werden.");
				aktuellerRaum.hineinlegen(gegenstand);
			}
		}
	}

	/**
	 * Berechnet das Gewicht der Gegenstaende in dieser Liste
	 * @param rucksack Die Liste mit Gegenstaenden
	 * @return Das Gewicht der Gegenstaende
	 */
	private int berechneGewicht(ArrayList<Gegenstand> rucksack) {
		int gewicht = 0;
		for(Gegenstand gegenstand : rucksack) {
			gewicht += gegenstand.gibGewicht();
		}
		return gewicht;
	}
	
	/**
	 * Uebernimmt die Kontrolle der spezifizierten Person. Der Spieler steuert
	 * anschliessend neu diese Person.
	 * 
	 * Falls die bezeichnete Person nicht vorhanden ist, aendert sich nichts.
	 * 
	 * @param befehl
	 *            Der auszufuehrende Befehl
	 */
	private void uebernimm(Befehl befehl) {
		if (befehl.hatZweitesWort()) {
			int nummer = Integer.parseInt(befehl.gibZweitesWort());
			personUebernehmen( nummer);			
		} else
		{
			System.out.println("Geben Sie die Nummer der Person an.");
		}
	}

	/**
	 * Uebernimmt, falls vorhanden, die Kontrolle der
	 * Person mit der spezifizierten Nummer.
	 * 
	 * @param nummer Nummer der Person
	 */
	private void personUebernehmen(int nummer) {
		Person person = aktuellerRaum.verlassen(nummer);
		if (person == null) {
			System.out.println("Es gibt keine Person mit Nummer " + nummer);
		} else {
			aktuellerRaum.betreten(spieler);
			spieler = person;
			System.out.println("Sie kontrollieren nun " + spieler.gibName());
		}
	}

	/**
	 * Gib Hilfsinformationen aus. Hier geben wir eine etwas alberne und unklare
	 * Beschreibung aus, sowie eine Liste der Befehlswoerter.
	 */
	private void hilfstextAusgeben() {
		System.out.println("Sie haben sich verlaufen. Sie sind allein.");
		System.out.println("Sie irren auf dem Unigelaende herum.");
		System.out.println();
		befehleAusgeben();
	}

	/**
	 * Gibt eine Liste der vorhandenen Befehlswoerter aus.
	 */
	private void befehleAusgeben() {
		System.out.println("Ihnen stehen folgende Befehle zur Verfuegung:");
		System.out.println(Befehlswort.gibBefehlsworteAlsText());
	}

	/**
	 * Versuche, in eine Richtung zu gehen. Wenn es einen Ausgang gibt, wechsele
	 * in den neuen Raum, ansonsten gib eine Fehlermeldung aus.
	 */
	private void wechsleRaum(Befehl befehl) {
		if (!befehl.hatZweitesWort()) {
			// Gibt es kein zweites Wort, wissen wir nicht, wohin...
			System.out.println("Wohin moechten Sie gehen?");
			return;
		}

		String richtung = befehl.gibZweitesWort();
		// Wir versuchen, den Raum zu verlassen.
		Raum naechsterRaum = aktuellerRaum.gibAusgang(richtung);
		if (naechsterRaum == null) {
			System.out.println("Dort ist keine Tuer!");
		} else {
			aktuellerRaum = naechsterRaum;
			System.out.println(aktuellerRaum.gibLangeBeschreibung());
		}
	}

	/**
	 * Der Befehl zum Beenden wurde eingegeben. Ueberpruefe den Rest des
	 * Befehls, ob das Spiel wirklich beendet werden soll.
	 * 
	 * @return 'true', wenn der Befehl das Spiel beendet, 'false' sonst.
	 */
	private boolean beenden(Befehl befehl) {
		if (befehl.hatZweitesWort()) {
			System.out.println("Was soll beendet werden?");
			return false;
		} else {
			return true;
		}
	}

}
