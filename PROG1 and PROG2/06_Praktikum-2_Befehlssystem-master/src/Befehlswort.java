import java.util.HashSet;
import java.util.HashMap;

/**
 * Diese Klasse haelt eine Aufzaehlung der akzeptierten Befehlswoerter.
 * Mit ihrer Hilfe werden eingetippte Befehle erkannt.
 *
 * @author  tebe
 * @version 1.0
 */

public enum Befehlswort {
	UNBEKANNT("?"), GEHE("gehe"), HILFE("hilfe"), BEENDEN("beenden");
	private String befehlswort;

	private Befehlswort(String befehlswort) {
		this.befehlswort = befehlswort;
	}
	public String gibBefehl(){
		return befehlswort;
	}
	public static Befehlswort gibBefehlswort(String befehl){
		for(Befehlswort befehlswort: Befehlswort.values()){
			if (befehlswort.gibBefehl().equals(befehl)) {
				return befehlswort;
			}
		}
		return Befehlswort.UNBEKANNT;
	}
	public static String gibBefehlsworteAlsText() {
		String text = "";
		for (Befehlswort befehl : Befehlswort.values()) {
			if (!befehl.equals(Befehlswort.UNBEKANNT)) {
				text = text + befehl.gibBefehl() + " ";
			}
		}
		return text;
	}

}
