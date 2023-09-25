public class Salaerkonto extends Konto {

    private long limite;

    public Salaerkonto(String inhaber, long limite){
        super(inhaber);
        addLimite(limite);
    }
    public Salaerkonto(String inhaber, long ersteinlage, long limite){
        super(inhaber, ersteinlage);
        addLimite(limite);
    }
    private void addLimite(long limite){
        limite = limite * 100;
        if(limite >= 0 && limite <= 10000000){
            this.limite = limite * -1;
        }
    }
    public long getLimite(){
        return limite / 100 * -1;
    }
    public String abheben(long abheben){
        abheben = abheben * 100;
        if(getKontostand() - abheben >= limite){
            kontostand = kontostand - abheben;
            return "Sie haben erfolgreich " + abheben /100 + "Fr. abgehoben";
        } else {
            long auszuzahlenderRest = (kontostand - abheben) * -1;
            kontostand = kontostand - auszuzahlenderRest;
            return "Sorry, Sie sind pleite. Sie haben " + auszuzahlenderRest/100 + "Fr. abgehoben";
        }
    }
}
