

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Bietet Funktionalitaeten zum Speichern von Pruefungsergebnissen von einer
 * Vielzahl von Studenten. Aus den Gespeicherten Ergebnissen lassen sich
 * personalisierte Antworttexte generieren.
 * 
 */
public class Pruefungsverwaltung {
	private List<Pruefungsergebnis> ergebnisse = new ArrayList<>();
	private Map<Double, String> noteVonZahlZuText = new HashMap<>();

	public Pruefungsverwaltung() {
		init();
	}

	private void init() {
		noteVonZahlZuText.put(1.0, "eins punkt null");
		noteVonZahlZuText.put(1.5, "eins punkt fuenf");
		noteVonZahlZuText.put(2.0, "zwei punkt null");
		noteVonZahlZuText.put(2.5, "zwei punkt fuenf");
		noteVonZahlZuText.put(3.0, "drei punkt null");
		noteVonZahlZuText.put(3.5, "drei punkt fuenf");
		noteVonZahlZuText.put(4.0, "vier punkt null");
		noteVonZahlZuText.put(4.5, "vier punkt fuenf");
		noteVonZahlZuText.put(5.0, "fuenf punkt null");
		noteVonZahlZuText.put(5.5, "fuenf punkt fuenf");
		noteVonZahlZuText.put(6.0, "sechs punkt null");
	}

	public void speichereErgebnis(Pruefungsergebnis ergebnis) {
		ergebnisse.add(ergebnis);
	}

	/**
	 * Gibt pro gespeichertem Ergebnis einen Text auf die Konsole aus. <br />
	 * <br />
	 * Je nachdem ob der Kandidate die Pruefung bestanden (>= 4.0) oder nicht
	 * bestanden (< 4.0) hat, wird ein Text in folgendem Format ausgegeben:
	 * 
	 * <ul>
	 * <li>
	 * &lt;Name&gt;, Sie haben an der Pruefung eine &lt;Note&gt; (&lt;Note als
	 * Text&gt;) erzielt und sind somit durchgefallen!</li>
	 * <li>
	 * Herzliche Gratulation &lt;Name&gt;! Sie haben an der Pruefung eine
	 * &lt;Note&gt; (&lt;Note als Text&gt;) erzielt und somit bestanden!</li>
	 * </ul>
	 */
	public void druckeAntworttexte() {
		for (Pruefungsergebnis ergebnis : ergebnisse) {
			String student = ergebnis.getStudent();
			double note = ergebnis.getNote();

			double noteGerundet = rundeAufHalbeNote(note);
			String text = generiereText(student, noteGerundet);

			System.out.println(text);
		}
	}

	public double rundeAufHalbeNote(double note) {
		return Math.round(note * 2.0) / 2.0;
	}

	private String generiereText(String student, double note) {
		String noteAlsText = noteVonZahlZuText.get(note);

		if (note < 4) {
			return student + ", Sie haben an der Pruefung eine " + note + " ("
					+ noteAlsText + ") erzielt und sind somit durchgefallen!";
		} else {
			return "Herzliche Gratulation " + student
					+ "! Sie haben an der Pruefung eine " + note + " ("
					+ noteAlsText + ") erzielt und somit bestanden!";
		}
	}
}
