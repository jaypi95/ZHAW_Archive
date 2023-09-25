import java.util.Calendar;
import java.util.Date;

public class StandardBriefdruckStrategie implements BriefdruckStrategie {


    public void druckeBrief(Brief brief){
        System.out.print("\n");
        printDatum(brief);

        System.out.print("\n");
        printSender(brief);

        System.out.print("\n");
        printEmpfaenger(brief);

        System.out.print("\n");
        printTitel(brief);

        System.out.print("\n");
        printAnrede(brief);

        System.out.print("\n");
        printText(brief);

    }

    private void printDatum(Brief brief){
        Date datum = extractInhalt(brief).getDatum().getTime();
        System.out.println(datum);
    }
    private void printSender(Brief brief){
        System.out.print(extractSender(brief).getVorname() + " ");
        System.out.println(extractSender(brief).getNachname());

        System.out.print(extractSender(brief).getStrasse() + " ");
        System.out.println(extractSender(brief).getHausnummer());

        System.out.print(extractSender(brief).getPlz() + " ");
        System.out.println(extractSender(brief).getOrt());
    }
    private void printEmpfaenger(Brief brief){
        System.out.print(extractEmpfaenger(brief).getVorname() + " ");
        System.out.println(extractEmpfaenger(brief).getNachname());

        System.out.print(extractEmpfaenger(brief).getStrasse() + " ");
        System.out.println(extractEmpfaenger(brief).getHausnummer());

        System.out.print(extractEmpfaenger(brief).getPlz() + " ");
        System.out.println(extractEmpfaenger(brief).getOrt());
    }
    private void printTitel(Brief brief){
        System.out.println(extractInhalt(brief).getTitel());
    }
    private void printAnrede(Brief brief){
        System.out.println(extractInhalt(brief).getAnrede());
    }
    private void printText(Brief brief){
        System.out.println(extractInhalt(brief).getText());
    }

    private Adresse extractEmpfaenger(Brief brief){
        Adresse empfaenger = brief.getEmpfaenger();
        return empfaenger;
    }
    private Adresse extractSender(Brief brief){
        Adresse sender = brief.getSender();
        return sender;
    }
    private Inhalt extractInhalt(Brief brief){
        Inhalt inhalt = brief.getInhalt();
        return inhalt;
    }

}
