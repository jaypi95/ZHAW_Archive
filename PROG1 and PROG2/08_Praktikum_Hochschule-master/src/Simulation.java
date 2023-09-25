public class Simulation
{
  Betreuungsverhaeltnis betreuungsverhaeltnis;

  public Simulation()
  {
    Dozent dozent = new Dozent("Albert Einstein", "1234-5678", "TG210", "058-9347259");
    betreuungsverhaeltnis = new Betreuungsverhaeltnis(dozent);
  }

  private void simulieren()
  {
    betreuungsverhaeltnis.hinzufuegen(new Student("Adam Alder", "abcd-efgh"));
    betreuungsverhaeltnis.hinzufuegen(new Student("Bea Bingo", "ace-gikm"));
    betreuungsverhaeltnis.hinzufuegen(new Student("Clea Clever", "bdfh-jlnp"));
    betreuungsverhaeltnis.hinzufuegen(new Student("Dino Dasen", "qwer-tzui"));
    betreuungsverhaeltnis.hinzufuegen(new Student("Eva Ente", "asdf-ghjk"));
    betreuungsverhaeltnis.hinzufuegen(new Student("Fritz Floh", "yxcv-bnmm"));
    betreuungsverhaeltnis.hinzufuegen(new Student("Geri Gugger", "uvwa-xyzb"));
    betreuungsverhaeltnis.verteileCredits();
    betreuungsverhaeltnis.ausgeben();
  }

  public static void main(String[] args)
  {
    (new Simulation()).simulieren();
  }
}
