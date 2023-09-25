package ch.zhaw.ads; /**
 * ch.zhaw.ads.CommandExecutor -- Praktikum Experimentierkasten -- SW3 Dieses Interface muss
 * von jedem Server implementiert werden.
 *
 * @author E. Mumprecht
 * @version 1.0 -- Geruest fuer irgendeinen Server
 * @version 1.1 -- K. Rege Fehlerueckgabe hinzugefuegt
 */

public interface CommandExecutor {

    /**
     * execute -- nimmt eine Kommandozeile, tut irgendetwas gescheites, und
     * berichtet das Resultat.
     *
     * @param command Kommandozeile
     * @return Resultat, ueblicherweise eine oder mehrere Zeilen.
     */
    String execute(String command) throws Exception;
}//interface ch.zhaw.ads.CommandExecutor