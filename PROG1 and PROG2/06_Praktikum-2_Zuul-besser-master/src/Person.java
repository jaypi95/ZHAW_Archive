import java.util.ArrayList;

/**
 * Diese Klasse modeliert eine Person, die einen Namen
 * hat und einen Rucksack traegt. Im Rucksack kann sie 
 * Gegenstaende herumtragen. Gegenstaende sollen nur dann in den
 * Rucksack gepackt werden, wenn der Rucksack nicht schwerer wird
 * als die Tragkraft der Person. 
 * 
 * @author tebe
 * @version 1.0
 *
 */
public class Person {
	private final String name;
	private final int tragkraft;
	private final ArrayList<Gegenstand> rucksack = new ArrayList<Gegenstand>();

	/**
	 * Erzeugt eine Person mit Namen und Tragkraft.
	 * @param tragkraft
	 */
	public Person(String name, int tragkraft) {
		this.tragkraft = tragkraft;
		this.name = name;
	}

	public ArrayList<Gegenstand> getRucksack() {
		return rucksack;
	}

	/**
	 * Gibt die Tragkraft zurueck.
	 * @return Die Tragkraft
	 */
	public int gibTragkraft() {
		return tragkraft;
	}

	/**
	 * @return Der Name der Person
	 */
	public String gibName() {
		return name;
	}
}
