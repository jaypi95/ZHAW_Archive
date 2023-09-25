import java.util.ArrayList;
import java.util.List;

/**
 * Diese Klasse implementiert einen Briefdrucker
 * 
 * @author tebe
 */
public class Briefdrucker {

    private List<Adresse> empfaengerlist = new ArrayList<>();

    private BriefdruckStrategie druckstrategie;

    public void setStrategie(BriefdruckStrategie strategieKandidat){
        druckstrategie = strategieKandidat;
    }

    public void druckeBrief(Brief brief){
        druckstrategie.druckeBrief(brief);
    }

    public void druckenSerienbrief(Adresse sender, ArrayList empfaengerlist, Inhalt inhalt){
        for(int i = 0; i < empfaengerlist.size(); i++){
            Brief brief = new Brief(sender, (Adresse) empfaengerlist.get(i), inhalt);
            druckstrategie.druckeBrief(brief);
        }
    }
	
}
