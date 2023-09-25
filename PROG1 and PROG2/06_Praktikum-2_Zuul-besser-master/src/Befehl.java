/**
 * Objekte dieser Klasse halten Informationen ueber Befehle, die der Benutzer
 * eingegeben hat. Ein Befehl besteht momentan aus zwei Zeichenketten: einem
 * Befehlswort und einem zweiten Wort. Beim Befehl "nimm karte" beispielsweise
 * sind die beiden Zeichenketten "nimm" und "karte".
 *
 * Wenn der Befehl nur aus einem Wort bestand, dann ist das zweite Wort <null>.
 *
 * @author tebe
 * @version 20.10.2013
 */

class Befehl {
    private String befehlswort;
    private String zweitesWort;

    /**
     * Erzeuge ein Befehlsobjekt. Beide Woerter muessen angegeben werden, aber
     * jedes oder beide duerfen 'null' sein. Ist das erste Wort 'null', wird der
     * Befehl als nicht verstanden betrachtet. Ist das zweite Wort 'null' wurde
     * kein zweites Wort angegeben.
     *
     * @param befehlswort
     *            Das erste Wort des Befehls.
     * @param zweitesWort
     *            Das zweite Wort des Befehls.
     */
    public Befehl(String befehlswort, String zweitesWort) {
        this.befehlswort = befehlswort;
        this.zweitesWort = zweitesWort;
    }

    /**
     * Liefere das Befehlswort (das erste Wort) dieses Befehls.
     *
     * @return Das Befehlswort.
     */
    public String gibBefehlswort() {
        return befehlswort;
    }

    public Befehlswort gibBefehl(){
        return Befehlswort.gibBefehlswort(this.befehlswort);
    }

    /**
     * Liefere das zweite Wort dieses Befehls.
     * Liefere 'null', wenn es kein zweites Wort gab.
     * @return Das zweite Wort dieses Befehls.
     */
    public String gibZweitesWort() {
        return zweitesWort;
    }

    /**
     * Testet, ob dieser Befehl ein zweites Wort hat.
     * @return 'true', wenn dieser Befehl ein zweites Wort hat.
     */
    public boolean hatZweitesWort() {
        return (zweitesWort != null);
    }
}
