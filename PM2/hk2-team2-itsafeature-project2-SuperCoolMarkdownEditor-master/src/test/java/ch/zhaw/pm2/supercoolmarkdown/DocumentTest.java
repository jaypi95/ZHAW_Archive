package ch.zhaw.pm2.supercoolmarkdown;

import ch.zhaw.pm2.supercoolmarkdown.exceptions.DamagedFileException;
import ch.zhaw.pm2.supercoolmarkdown.model.Document;
import ch.zhaw.pm2.supercoolmarkdown.model.ParsedContent;
import ch.zhaw.pm2.supercoolmarkdown.model.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import javax.swing.text.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class is a test class for the Document class
 *
 * @author Outitbad
 * @version 14.05.2021
 */
public class DocumentTest {
    Document document;

    private JTextPane textPaneText;
    private StyledDocument docText;
    private AttributeSet attributeSetBOLD;
    private AttributeSet attributeSetFONT;


    @BeforeEach
    void setup() {
        document = new Document();
        Tag.initTags();
    }

    /**
     * Test if creating a new document without giving file works
     */
    @Test
    void testEmptyDocument() {
        assertEquals("", document.getFilePath());
        assertEquals("", document.getFileContent());
        assertFalse(document.isSaved());
    }

    /**
     * Make sure that creating a document that doesn't exists throws an error
     */
    @Test
    void testOpenDocumentNotFound() {
        assertThrows(FileNotFoundException.class, () -> new Document("this/path/does/not/exist/file.txt"), "it didn't");
    }

    /**
     * Test reading a file from disk and read the content
     */
    @Test
    void testReadDocument() {
        try {
            Files.write(Paths.get("the-file-name.txt"), Arrays.asList("The first line", "The second line"), StandardCharsets.UTF_8);
        } catch (IOException ignored) {
        }

        Document doc = null;
        try {
            doc = new Document("the-file-name.txt");
        } catch (IOException | DamagedFileException ignored) {
        }
        assertNotNull(doc);

        assertEquals("the-file-name.txt", doc.getFilePath()); // filePath.toUri().path
        assertEquals("the-file-name.txt", doc.getFileName());

        assertTrue(doc.isSaved());
        doc.documentChanged();
        assertFalse(doc.isSaved());

        assertEquals("The first line\nThe second line\n", doc.getFileContent().replace("\r", ""));

        try {
            Files.deleteIfExists(Paths.get("the-file-name.txt"));
        } catch (IOException ignored) {
        }
    }

    /**
     * Test if the method formatText does not change the style if the param are invalid
     */
    @Test
    void formatTextTestNegativeParam() {
        //Setup
        setAttributeSet();
        setupDoc();

        StyledDocument testDoc = document.formatText(docText, Tag.BOLD, 0, 1);
        assertEquals(docText, testDoc);
    }

    /**
     * Test if the method formatText changes the Style if the param are valid
     */
    @Test
    void formatTextTestValid() {
        //Setup
        setAttributeSet();
        setupDoc();

        StyledDocument testDoc = document.formatText(docText, Tag.BOLD, 0, 1);

        docText.setCharacterAttributes(0, 1, attributeSetBOLD, true);

        assertEquals(docText, testDoc);
    }

    /**
     * Test if the method formatTextFont does not change the font if the param are invalid
     */
    @Test
    void formatTextFontTestInvalidParam() {
        //Setup
        setAttributeSet();
        setupDoc();

        StyledDocument testDoc = document.formatTextFont(docText, 0, 1, 0, "");
        assertEquals(docText, testDoc);
    }

    /**
     * Test if the method formatTextFont does not change the font if the param are invalid
     */
    @Test
    void formatTextFontTestValid() {
        //Setup
        setAttributeSet();
        setupDoc();

        StyledDocument testDoc = document.formatTextFont(docText, 0, 1, 8, "Papyrus");
        docText.setCharacterAttributes(0, 1, attributeSetFONT, true);
        assertEquals(docText, testDoc);

    }

    /**
     * Test if the saveDocument method saves the file and if the content is correct
     */
    @Test
    void saveDocumentTest() {
        setAttributeSet();
        setupDoc();

        File file = new File("src/main/java/test1.txt");
        Document document = new Document();
        document.setFilePath("src/main/java/test1.txt");
        document.setDocContent(docText);

        try {
            document.saveDocument();
        } catch (IOException e) {
        }

        assertTrue(file.exists());
        String line;
        String content = "";
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file.getAbsolutePath()));
            while ((line = bufferedReader.readLine()) != null) {
                content = content + line;
            }
        } catch (IOException e) {
            e.printStackTrace();
            file.delete();
        }

        assertEquals("<font face=Dialog size=12>Hello, this is a test</font>", content);

        file.delete();
    }

    /**
     * Test if the method updateFormattingList saves the correct content
     */
    @Test
    void updateFormattingListTest() {
        setAttributeSet();
        List<ParsedContent> list = new ArrayList<ParsedContent>();
        Boolean tag = false;
        list.add(new ParsedContent("Hallo", 0, 5, attributeSetBOLD, false));

        document.setFileContent("**Hallo**");
        try {
            document.updateFormattingList();
        } catch (Exception e) {
        }

        assertEquals(list.get(0).getLength(), document.getFormattingInfo().get(0).getLength());
        assertEquals(list.get(0).getStart(), document.getFormattingInfo().get(0).getStart());
        assertEquals(list.get(0).getContent(), document.getFormattingInfo().get(0).getContent());
        assertEquals(list.get(0).getReplace(), document.getFormattingInfo().get(0).getReplace());
        if (document.getFormattingInfo().get(0).getFormat().containsAttributes(attributeSetBOLD)) {
            tag = true;
        }
        assertTrue(tag);
    }

    /**
     * Test if the method updateFormattingList throws a exception, when de MD format is inavlid
     */
    @Test
    void updateFormattingListTestInvalidFormat() {
        setAttributeSet();

        Boolean thrown = false;


        document.setFileContent("Hallo**");
        try {
            document.updateFormattingList();
        } catch (DamagedFileException e) {
            thrown = true;
        }
        assertTrue(thrown);

    }

    /**
     * This method init and sets all the attributeSets
     */
    private void setAttributeSet() {
        //AttributeSet for BOLD
        attributeSetBOLD = new SimpleAttributeSet();
        StyleConstants.setBold((MutableAttributeSet) attributeSetBOLD, true);


        //AttributeSet for Font
        attributeSetFONT = new SimpleAttributeSet();
        StyleConstants.setFontSize((MutableAttributeSet) attributeSetFONT, 8);
        StyleConstants.setFontFamily((MutableAttributeSet) attributeSetFONT, "Papyrus");

    }

    private void setupDoc() {
        textPaneText = new JTextPane();
        textPaneText.setText("Hello, this is a test");
        docText = (StyledDocument) textPaneText.getDocument();
    }
}
