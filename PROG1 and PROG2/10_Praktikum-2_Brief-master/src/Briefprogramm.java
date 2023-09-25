import java.util.ArrayList;
import java.util.Calendar;

/**
 * Diese Klasse implementiert ein Briefprogramm.
 * 
 * @author tebe
 */
public class Briefprogramm {



  /**
   * main Methode.
   * @param args Es werden keine Kommandozeilenparameter verwendet
   */
	public static void main(String[] args) {

		Briefdrucker drucker = new Briefdrucker();
		BriefdruckStrategie normaldruck = new StandardBriefdruckStrategie();
		BriefdruckStrategie fensterdruck = new FensterBriefdruckStrategie();

		Calendar calendar = Calendar.getInstance();

		Adresse empfaenger1 = new Adresse("Hans", "Peter", "Bürglistrasse", 10, 8400, "Winterthur");
		Adresse sender1 = new Adresse("Moritz", "Müller", "Hausstrasse", 50, 8000, "Zurigo");

		Adresse empfaenger2 = new Adresse("Ueli","Fleischhauer","Füdlistrasse", 69, 9000, "St. Gallen");
		Adresse sender2 = new Adresse("Yanis", "Varoufakis","Möslistrasse",666,8303, "Bassersdorf");

		Inhalt inhalt1 = new Inhalt(Calendar.getInstance(), "Beschwerde ueber Laerm", "Sehr geehrte*r Empfaenger*in", "Dies ist ein Beschwerdebrief");
		Inhalt inhalt2 = new Inhalt(Calendar.getInstance(), "Sie Nüsschen", "Sehr geehrtes Nüsschen", "Warum sind Sie keine Schokolade?");

		Brief brief1 = new Brief(sender1, empfaenger1, inhalt1);
		Brief brief2 = new Brief(sender2, empfaenger2, inhalt2);


		//Druck normalen Brief
		System.out.println("\n\n\n ########## THIS IS A NORMAL BRIEF ########## \n\n\n");
		drucker.setStrategie(normaldruck);
		drucker.druckeBrief(brief1);

		//Druck Fensterbrief
		System.out.println("\n\n\n ########## THIS IS A FENSTERBRIEF ########## \n\n\n");
		drucker.setStrategie(fensterdruck);
		drucker.druckeBrief(brief2);

		//Druck normalen Serienbrief
		System.out.println("\n\n\n ########## THIS IS A NORMAL SERIENBRIEF! NOT SUS AT ALL ########## \n\n\n");
		ArrayList<Adresse> empfaengerlist = new ArrayList<>();
		drucker.setStrategie(normaldruck);
		empfaengerlist.add(empfaenger1);
		empfaengerlist.add(empfaenger2);

		drucker.druckenSerienbrief(sender1, empfaengerlist, inhalt1);

		//Druck Fenster Serienbrief
		System.out.println("\n\n\n ########## THIS IS A FENSTER SERIENBRIEF ########## \n\n\n");
		drucker.setStrategie(fensterdruck);
		drucker.druckenSerienbrief(sender2, empfaengerlist, inhalt2);

	  
	}
}