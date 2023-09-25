public class Bank {

    public Bank() {

    }
    public static void main(String[] args) {
        Konto konto1 = new Konto("Jolly Jumper", 30000);
        Salaerkonto salaerkonto1 = new Salaerkonto("Donald Ducker", -2000, 5000);
        Nummernkonto nummernkonto1 = new Nummernkonto("Unknown", 100000);

        System.out.println("Inhaber: " + konto1.getInhaber() + ", Kontostand: " + konto1.getKontostand());
        System.out.println("Inhaber: " + salaerkonto1.getInhaber() + ", Kontostand: " + salaerkonto1.getKontostand() + ", Überzugslimite: " + salaerkonto1.getLimite());
        System.out.println("Inhaber: " + nummernkonto1.getInhaber() + ", Kontostand: " + nummernkonto1.getKontostand());
    }
}
