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
    UNBEKANNT("?"), GEHE("gehe"), HILFE("hilfe"), BEENDEN("beenden"), UMSEHEN("umsehen"), UEBERNIMM("uebernimm"), NIMM("nimm");
    private String befehlswort;

    Befehlswort(String befehlswort) {
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
        StringBuilder text = new StringBuilder();
        for (Befehlswort befehl : Befehlswort.values()) {
            if (!befehl.equals(Befehlswort.UNBEKANNT)) {
                text.append(befehl.gibBefehl()).append(" ");
            }
        }
        return text.toString();
    }

}
