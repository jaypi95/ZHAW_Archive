public class Nummernkonto extends Konto{

    static int kontoNummer = 999;

    public Nummernkonto(String inhaber){
        super(inhaber);
        kontoNummer = kontoNummer + 1;
    }
    public Nummernkonto(String inhaber, long ersteinlage){
        super(inhaber, ersteinlage);
        kontoNummer = kontoNummer + 1;
    }
    public String getInhaber(){
        return String.valueOf(kontoNummer);
    }
}
