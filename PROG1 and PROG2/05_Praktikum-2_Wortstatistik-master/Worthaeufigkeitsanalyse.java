import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Zaehlt die Anzahl Vorkommnisse von Woertern ueber mehrere Zeichenketten.
 * Es lassen sich eine beliebige Anzahl an Zeichenketten uebergeben. Die
 * Statistik wird fortlaufend gefuehrt. Die Wortzaehlung laesst sich jederzeit
 * ausgeben. Die Satzeichen . , ? ! " : ; werden entfernt. Alle Buchstaben
 * werden in Kleinbuchstaben umgewandelt um beispielsweise das Wort 'die'
 * inmitten eines Satzes und das Wort 'Die' am Anfang eines Satzes als gleiches
 * Wort werten zu koennen.
 *
 * @version 1.0
 * @author XXXX
 */
public class Worthaeufigkeitsanalyse {
  // Anstelle der Map dürfen Sie auch andere Datentypen verwenden. Testen Sie auch TreeMap.
  private HashMap<String, Integer> woerterHaeufigkeit = new HashMap<>();

  /**
   * Nimmt die uebergebene Zeichenkette in die Worthaeufigkeitsanalyse auf.
   *
   * @param text zu verarbeitende Zeichenkette
   */
  public void verarbeiteText(String text) {
    // TODO Ihre Implementation
    
    ArrayList<String> splitWords = new ArrayList<>(
        Arrays.asList(text.replaceAll("[^\\p{L}0-9 ]", "").toLowerCase().split("\\s+"))
        );
        
    for (String words: splitWords){
        Integer occurs = woerterHaeufigkeit.get(words);
        woerterHaeufigkeit.put(words, (occurs == null) ? 1 : occurs + 1);
        
    }
  }

  /**
   * Ausgabe der Worthaeufigkeitsanalyse auf der Konsole.
   */
  public void druckeStatistik() {
    for (Map.Entry<String, Integer> wortHaeufigkeit : woerterHaeufigkeit.entrySet()) {
      System.out.printf("%3d %-40s%n", wortHaeufigkeit.getValue(), wortHaeufigkeit.getKey());
    }
  }

  public static void main(String[] args) {
    Worthaeufigkeitsanalyse hauefigkeitsanalyse = new Worthaeufigkeitsanalyse();
    hauefigkeitsanalyse.verarbeiteText("Fritz sagt: \"Die Softwareentwicklung ist meine Leidenschaft!\"");
    hauefigkeitsanalyse.verarbeiteText("Hans meint, er teile die Leidenschaft mit Fritz.");
    hauefigkeitsanalyse.verarbeiteText("John fuegt hinzu, dass die Softwareentwicklung nicht nur aus Programmieren bestehe, sondern es sich dabei um einen komplexen Prozess, bestehend aus vielen kleinen Komponenten, handelt.\"");
    hauefigkeitsanalyse.druckeStatistik();
  }
}