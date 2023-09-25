import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.HashMap;

public class Test_worthaeufigkeit {

    private Worthaeufigkeitsanalyse analyse;

    @BeforeEach
    public void setUp(){
        analyse = new Worthaeufigkeitsanalyse();

    }

    @Test
    public void testCapitalization(){
        analyse.testVerarbeiteText("text, TEXT, tExt");
        assertEquals(new HashMap<String, Integer>() {{
            put("text", 3);
        }}, analyse.testReturnCount());
    }

    @Test
    public void testOccurrence(){
        analyse.testVerarbeiteText("This text tests if all the words in this text get counted accurately. All words within this text have to show up on the list. But no whitespaces or special characters. No, no no!");
        assertEquals(new HashMap<String, Integer>() {{
            put("this", 3);
            put("text", 3);
            put("tests", 1);
            put("if", 1);
            put("all", 2);
            put("the", 2);
            put("words", 2);
            put("in", 1);
            put("get", 1);
            put("counted", 1);
            put("accurately", 1);
            put("within", 1);
            put("have", 1);
            put("to", 1);
            put("show", 1);
            put("up", 1);
            put("on", 1);
            put("list", 1);
            put("but", 1);
            put("no", 4);
            put("whitespaces", 1);
            put("or", 1);
            put("special", 1);
            put("characters", 1);
        }}, analyse.testReturnCount());
    }

    @Test
    public void testEmptyString(){
        analyse.testVerarbeiteText("");
        assertEquals(new HashMap<String, Integer>(){{

        }}, analyse.testReturnCount());

    }

    @Test
    public void testOnlySpaces(){
        analyse.testVerarbeiteText("             ");
        assertEquals(new HashMap<String, Integer>(){{

        }}, analyse.testReturnCount());
    }
}
