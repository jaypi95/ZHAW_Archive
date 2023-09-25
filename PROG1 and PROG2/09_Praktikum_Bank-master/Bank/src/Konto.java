public class Konto {

    long kontostand;
    String inhaber;

    public Konto(){

    }

    public Konto(String inhaber){
        this.inhaber = inhaber;
    }
    public Konto(String inhaber, double ersteinlage){
        this.inhaber = inhaber;
        einzahlen((long) ersteinlage); //converting Fr. to Rp.

    }

    private boolean checkMaxKontostand(long einzahlen){
        if(kontostand + einzahlen > 100000000){
            return false;
        } else {
            return true;
        }
    }
    public long getKontostand(){
        return kontostand / 100;
    }
    public String getInhaber(){
        return inhaber;
    }
    public String einzahlen(long einzahlen){
        einzahlen = einzahlen * 100; //Converting Fr. to Rp.
        if(checkMaxKontostand(einzahlen)) {

            kontostand = kontostand + einzahlen;
            return "Das Geld wurde erfolgreich einbezahlt.";
        } else {
            long einzuzahlenderRest = kontostand + einzahlen -100000000;
            kontostand = kontostand + einzuzahlenderRest;
            return "Das Konto hat den Maximalbetrag erreicht. Es konnten nur " + einzuzahlenderRest/100 + "Fr. eingezahlt werden.";
        }
    }
    public String abheben(long abheben){
        abheben = abheben * 100;
        if(getKontostand() - abheben >= 0){
            kontostand = kontostand - abheben;
            return "Sie haben erfolgreich " + abheben /100 + "Fr. abgehoben";
        } else {
            long auszuzahlenderRest = (kontostand - abheben) * -1;
            kontostand = kontostand - auszuzahlenderRest;
            return "Sorry, Sie sind pleite. Sie haben " + auszuzahlenderRest/100 + "Fr. abgehoben";
        }
    }
}
