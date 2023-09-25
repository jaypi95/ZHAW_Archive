public class Motorfahrzeug extends Fahrzeuge{
    private int leistung;

    protected Motorfahrzeug(String modell, int preis, int stueckzahl, int leistung){
        super(modell, preis,stueckzahl);
        this.leistung = leistung;
    }
    /**
     * Liefert die Leistung des fahrzeugs.
     * @return Die Leistung
     */
    public int gibLeistung()
    {
        return leistung;
    }


}
