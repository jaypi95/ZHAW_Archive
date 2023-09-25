import java.util.*;
/**
 * Diese Klasse modelliert Räume in der Welt von Zuul.
 * 
 * Ein "Raum" repräsentiert einen Ort in der virtuellen Landschaft des
 * Spiels. Ein Raum ist mit anderen Räumen über Ausgänge verbunden.
 * Mögliche Ausgänge liegen im Norden, Osten, Süden und Westen.
 * Für jede Richtung hält ein Raum eine Referenz auf den 
 * benachbarten Raum.
 * 
 * @author  Michael Kölling und David J. Barnes
 * @version 31.07.2011
 */
public class Raum 
{
    private String beschreibung;
    //public Raum nordausgang;
    //public Raum suedausgang;
    //public Raum ostausgang;
    //public Raum westausgang;
    private HashMap<String, Raum> ausgaenge = new HashMap<>();
    /**
     * Erzeuge einen Raum mit einer Beschreibung. Ein Raum
     * hat anfangs keine Ausgänge.
     * @param beschreibung enthält eine Beschreibung in der Form
     *        "in einer Küche" oder "auf einem Sportplatz".
     */
    public Raum(String beschreibung) 
    {
        this.beschreibung = beschreibung;
    }

    /**
     * Definiere die Ausgänge dieses Raums. Jede Richtung
     * führt entweder in einen anderen Raum oder ist 'null'
     * (kein Ausgang).
     * @param norden Der Nordausgang.
     * @param osten Der Ostausgang.
     * @param sueden Der Südausgang.
     * @param westen Der Westausgang.
     */
    public void setzeAusgaenge(String richtung, Raum raum) 
    {
        ausgaenge.put(richtung,raum);
    }
    public Raum gibAusgang(String richtung)
    {
        Raum raum = ausgaenge.get(richtung);
        return raum;
    }
    public String gibAusgaengeAlsString()
    {
        String ausgang = null;
        
        if (gibAusgang("norden") != null){
            ausgang = "norden";
        }
        if (gibAusgang("osten") != null){
            ausgang = "osten";
        }
        if (gibAusgang("westen") != null){
            ausgang = "westen";
        }
        if (gibAusgang("sueden") != null){
            ausgang = "sueden";
        }
        ausgang = "Sie sind " + beschreibung + "\nAusgaenge: " + ausgang;
        return ausgang;
    }

    /**
     * @return die Beschreibung dieses Raums.
     */
    public String gibBeschreibung()
    {
        return beschreibung;
    }
}
