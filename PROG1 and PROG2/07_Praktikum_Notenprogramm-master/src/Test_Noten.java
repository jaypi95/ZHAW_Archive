import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Test_Noten {
    private Pruefungsverwaltung pruefungsverwaltung;

    @Test
    public void testRunden(){
        pruefungsverwaltung = new Pruefungsverwaltung();

        assertEquals(2.5, pruefungsverwaltung.rundeAufHalbeNote(2.25));
        assertEquals(2.0, pruefungsverwaltung.rundeAufHalbeNote(2.24));
        assertEquals(3, pruefungsverwaltung.rundeAufHalbeNote(2.75));
        assertEquals(2.5, pruefungsverwaltung.rundeAufHalbeNote(2.74));
    }
}
