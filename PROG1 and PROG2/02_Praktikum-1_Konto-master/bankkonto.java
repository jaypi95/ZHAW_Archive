
/**
 * Klasse um ein Bankkonto zu eröffnen und den Kontostand abzufragen
 *
 * @autor peterju1
 * @version 2020091701
 */
public class bankkonto
{
    // instance variables - replace the example below with your own
    private String inhaber;
    private int kontostand;
    private int zinssatz;

    /**
     * Constructor for objects of class bankkonto
     */
    public bankkonto()
    {
        // initialise instance variables
        inhaber = null;
        kontostand = 0;
        zinssatz = 0;
    }

    /**
     * Erzeugung Konto nur mit Angabe des Inhabers
     *
     * @param  inhaber
     *     
     */
    public void KontoMitInhaber(String kontoInhaber)
    {
        inhaber = kontoInhaber;
        kontostand = 0;
        zinssatz = 2;
    }
    
    /**
     * Erzeugung Konto mit Angabe des Inhabers und des Zinssatzes
     *
     * @param  inhaber, zinssatz
     *     
     */
    public void KontoMitZins(String kontoInhaber, int zins)
    {
        inhaber = kontoInhaber;
        kontostand = 0;
        zinssatz = zins;
    }
    /**
     * Zinssatz abfragen
     *
     * @return Zinssatz
     *     
     */
    public int getZins() {
            return zinssatz;
    }
    /**
     * Zinssatz neu setzen
     *
     * @param zinssatz
     *     
     */
    public int setZins(int zins)
    {
        zinssatz = zins;
        return zinssatz;
    }
    
    /**
     * Geld einzahlen
     *
     * @param difference
     *     
     */
    public int addToKontostand(int difference)
    {
        kontostand += difference;
        return kontostand;
    }
    /**
     * Geld abheben
     *
     * @param difference
     *     
     */
    public int removeFromKontostand(int difference)
    {
        kontostand -= difference;
        return kontostand;
    }
    /**
     * Jahreszins ausgeben
     *
     * @return Jahreszins
     *     
     */
    public int getJahreszins()
    {
        return (kontostand * zinssatz) / 100;
       
    }
    /**
     * Gibt den Zustand des Kontos aus   
     */
    public void printOut()
    {
        System.out.println("Informationen zum Konto:");
        System.out.println("Kontoinhaber: " + inhaber);
        System.out.println("Kontostand: " + kontostand);
        System.out.println("Zinssatz: " + zinssatz);
    }
}
