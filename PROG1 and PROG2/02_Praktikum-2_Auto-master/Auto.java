
/**
 * Write a description of class Auto here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Auto
{
 
    private String marke;
    private String typ;
    private double hubraum;
    private boolean turbo;
    private int bestand;
    private String markenError;
    private String typenError;
    private String hubraumError;
    private String turboError;

    /**
     * Initialisere Variablen
     */
    public Auto()
    {
        marke = "";
        typ = "";
        hubraum = 0.0;
        turbo = false;
        bestand = 0;
        markenError = "Der Markenname muss mindestens 3 und darf höchstens 10 Zeichen lang sein";
        typenError = "Der Typenname muss mindestens 3 und darf höchstens 10 Zeichen lang sein";
        hubraumError = "Der Hubraum muss mindestens 0.5 und höchstens 8 Liter betragen";
        turboError = "Geben Sie mit true oder false an, ob das Auto einen Turbo hat oder nicht.";
        
        
    }

    /**
     * Erzeuge ein neues auto
     *
     * @param  createMarke Automarke
     * @param  createTyp   Der Typ des Autos
     * @param  createHubraum Der Hubraum
     * @param  createTurbo    Hat das Auto einen Turbo?
     * 
     */
    public void createCar(String createMarke, String createTyp, double createHubraum, boolean createTurbo)
    {
      if(createMarke.length() < 3 || createMarke.length() > 10) {
         createMarke = "___";
         System.out.println(markenError);
        }
      if(createTyp.length() < 3 || createTyp.length() > 10) {
         
        createTyp = "___";
        System.out.println(typenError);
         
        }
      if(createHubraum < 0.5 || createHubraum > 8.0) {
         createHubraum = 0;
         System.out.println(hubraumError);
        }
        
      marke = createMarke;
      typ = createTyp;
      hubraum = createHubraum;
      turbo = createTurbo;
    }
    public void setMarke(String newMarke)
    {
      if(newMarke.length() < 3 || newMarke.length() > 10) {
         
         System.out.println(markenError);
        }
      else {
         marke = newMarke; 
        }
    }
    public void setTyp(String newTyp)
    {
      if(newTyp.length() < 3 || newTyp.length() > 10) {
         
         System.out.println(typenError);
        }
      else {
         typ = newTyp; 
        }
    }
    public void setHubraum(double newHubraum)
    {
      if(newHubraum < 0.5 || newHubraum > 8.0) {
         
         System.out.println(hubraumError);
        }
      else {
         hubraum = newHubraum; 
        }
    }
    public void setTurbo(boolean newTurbo)
    {
      if(newTurbo == true || newTurbo == false) {
         turbo = newTurbo;
         }
      else {          
         System.out.println(turboError);
        }
    }
    public void setBestand(int newBestand)
    {
     if(newBestand > 10 || newBestand < -10){
         System.out.println("Der Bestand darf nicht um mehr als 10 steigen oder sinken.");
        }
     else if(bestand + newBestand < 0){
        System.out.println("Wait, that's illegal."); 
        }
     else {
        System.out.println("Alter Bestand " + bestand);
        bestand += newBestand;
        System.out.println("Neuer Bestand " + bestand);
        }
    }
    public void getAuto()
    {
     String ausgabeTurboCode = "";
     String ausgabeTurboName = "";
     if(turbo == true){
         ausgabeTurboCode = "-t";
         ausgabeTurboName = "turbo";
        }
     else 
     { 
       ausgabeTurboCode = ""; 
       ausgabeTurboName = "";
     }
        System.out.println(marke + " " + typ + ", " + hubraum + " Liter" + " " + ausgabeTurboName);
        System.out.print("Code: " + marke.substring(0,3));
        System.out.print("-");
        System.out.print(typ.substring(0,3));
        System.out.print("-");
        System.out.print(hubraum);
        System.out.println(ausgabeTurboCode);
        System.out.println("Lagerbestand: " + bestand);
    }
}
