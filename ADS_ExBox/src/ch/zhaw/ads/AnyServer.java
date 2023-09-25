package ch.zhaw.ads;

/**
 * ch.zhaw.ads.AnyServer -- Praktikum Experimentierkasten -- ADS
 *
 * @author E. Mumprecht
 * @version 1.0 -- Geruest fuer irgendeinen Server
 */

public class AnyServer implements CommandExecutor {

    //----- Dies implementiert das ch.zhaw.ads.CommandExecutor Interface.
    @Override
    public String execute(String command) {
        StringBuffer result = new StringBuffer(100);
        result.append("Die Eingabe war \"");
        result.append(command);
        result.append("\"\n");
        return result.toString();
    }
}//ch.zhaw.ads.AnyServer
