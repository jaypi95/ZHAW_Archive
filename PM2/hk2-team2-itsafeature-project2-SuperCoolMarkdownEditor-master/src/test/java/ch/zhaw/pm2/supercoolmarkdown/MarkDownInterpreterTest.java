package ch.zhaw.pm2.supercoolmarkdown;

import ch.zhaw.pm2.supercoolmarkdown.exceptions.DamagedFileException;
import ch.zhaw.pm2.supercoolmarkdown.model.MarkDownInterpreter;
import ch.zhaw.pm2.supercoolmarkdown.model.ParsedContent;
import ch.zhaw.pm2.supercoolmarkdown.model.Tag;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * This class is responsible for testing all public methods of MarkDownInterpreter.
 *
 * @author peterju1
 * @version 2021-05-14
 */
public class MarkDownInterpreterTest {
    private MarkDownInterpreter mdInterpreter;
    private MutableAttributeSet attributeSetBOLD;
    private MutableAttributeSet attributeSetITALIC;
    private MutableAttributeSet attributeSetSTRIKETHROUGH;
    private MutableAttributeSet attributeSetMONOSPACE;
    private MutableAttributeSet attributeSetUNDERLINE;
    private MutableAttributeSet attributeSetFONT;
    private MutableAttributeSet attributeSetBoldAndItalic;
    private MutableAttributeSet attributeSetBoldAndUnderline;
    private MutableAttributeSet attributeSetBoldItalicSizeFont;
    private MutableAttributeSet attributeSetBoldMonospaceStrikethrough;
    private MutableAttributeSet attributeSetPapyrusSizeFont;

    /**
     * Sets up a new interpreter, new attributeSets and new tags before each test
     */
    @BeforeEach
    void setup() {
        mdInterpreter = new MarkDownInterpreter();

        setAttributeSet();
        Tag.initTags();
    }

    /**
     * This test uses a test String which it feeds to the markdown interpreter. It compares the returned list of formats to another list that was manually crafted.
     */
    @Test
    void interpretMarkdownTestValidFile() {
        List<ParsedContent> testFormatting = new ArrayList<>();
        List<ParsedContent> actualFormatting = null;

        //Set up ArrayList containing the formatting
        testFormatting.add(new ParsedContent("This is a bold statement", 0, 24, attributeSetBOLD, false));
        testFormatting.add(new ParsedContent("Questa affermazione è in corsivo", 52, 32, attributeSetITALIC, false));
        testFormatting.add(new ParsedContent("this is strikethrough AND italics", 85, 33, attributeSetITALIC, false));
        testFormatting.add(new ParsedContent("this is strikethrough AND italics", 85, 33, attributeSetSTRIKETHROUGH, false));

        //Create test string
       String testString = Tag.BOLD + "This is a bold statement" + Tag.BOLD + " This is a normal statement " + Tag.CURSIVE + "Questa affermazione è in corsivo" + Tag.CURSIVE + " " + Tag.CURSIVE + Tag.STRIKETHROUGH + "this is strikethrough AND italics" + Tag.STRIKETHROUGH + Tag.CURSIVE;


        try {
            actualFormatting = mdInterpreter.interpretMarkdown(testString);

        } catch (DamagedFileException e) {
            System.out.println(e);
        }

        for(int i = 0; i < actualFormatting.size(); i++) {
            assertThat(actualFormatting.get(i).getFormat(), is(testFormatting.get(i).getFormat()));
            assertThat(actualFormatting.get(i).getStart(), is(testFormatting.get(i).getStart()));
            assertThat(actualFormatting.get(i).getLength(), is(testFormatting.get(i).getLength()));
            assertThat(actualFormatting.get(i).getContent(), is(testFormatting.get(i).getContent()));
            assertThat(actualFormatting.get(i).getReplace(), is(testFormatting.get(i).getReplace()));
        }
    }

    /**
     * Tests if the file validator throws an error when trying to feed a damaged file.
     */
    @Test
    void interpretMarkdownTestInvalidFile() {
        boolean thrown = false;


        //Create invalid test string
        String testString = Tag.BOLD + "This is a bold statement" + " This is a normal statement " + Tag.CURSIVE + "Questa affermazione è in corsivo" + Tag.CURSIVE + " " + Tag.CURSIVE + Tag.STRIKETHROUGH + "this is strikethrough AND italics" + Tag.STRIKETHROUGH + Tag.CURSIVE;

        try {
           mdInterpreter.interpretMarkdown(testString);

        } catch (DamagedFileException e){
           thrown = true;
        }
        Assertions.assertTrue(thrown);

    }
    /**
     * Tests if the file validator throws an error or if the parser fails when trying to feed an empty file.
     */
    @Test
    void interpretMarkdownTestEmptyFile() {
        boolean thrown = false;
        List<ParsedContent> actualFormatting = null;

        //Create invalid test string
        String testString = "";

        try {
            actualFormatting = mdInterpreter.interpretMarkdown(testString);

        } catch (DamagedFileException e){
            thrown = true;
        }
        Assertions.assertFalse(thrown);
        Assertions.assertTrue(actualFormatting.isEmpty());
    }

    /**
     * Tests if the file validator throws an error or if the parser fails when trying to feed a file without tags.
     */
    @Test
    void interpretMarkdownTestUnformattedFile() {
        boolean thrown = false;
        List<ParsedContent> actualFormatting = null;

        //Create invalid test string
        String testString = "This is a file that is completely unformatted.";

        try {
            actualFormatting = mdInterpreter.interpretMarkdown(testString);

        } catch (DamagedFileException e){
            thrown = true;
        }
        Assertions.assertFalse(thrown);
        Assertions.assertTrue(actualFormatting.isEmpty());
    }

    /**
     * This method tests if the file validator and parser can handle font tags correctly
     */
    @Test
    void interpretMarkdownTestFontTags(){
        List<ParsedContent> testFormatting = new ArrayList<>();
        List<ParsedContent> actualFormatting = null;

        //Set up ArrayList containing the formatting
        testFormatting.add(new ParsedContent("This is a papyrus statement", 0, 27, attributeSetBOLD, false));
        testFormatting.add(new ParsedContent("This is a papyrus statement", 0, 27, attributeSetITALIC, false));
        testFormatting.add(new ParsedContent("This is a papyrus statement", 0, 27, attributeSetPapyrusSizeFont, false));


        //Create test string
        String testString = "<font face=Papyrus size=8>" + Tag.CURSIVE + Tag.BOLD + "This is a papyrus statement" + Tag.BOLD + Tag.CURSIVE + Tag.FONTEND;


        try {
            actualFormatting = mdInterpreter.interpretMarkdown(testString);

        } catch (DamagedFileException e) {
            System.out.println(e);
        }

        for(int i = 0; i < actualFormatting.size(); i++) {
            assertThat(actualFormatting.get(i).getFormat(), is(testFormatting.get(i).getFormat()));
            assertThat(actualFormatting.get(i).getStart(), is(testFormatting.get(i).getStart()));
            assertThat(actualFormatting.get(i).getLength(), is(testFormatting.get(i).getLength()));
            assertThat(actualFormatting.get(i).getContent(), is(testFormatting.get(i).getContent()));
            assertThat(actualFormatting.get(i).getReplace(), is(testFormatting.get(i).getReplace()));
        }
    }

    /**
     * This method tests if unknown html tags get ignored
     */
    @Test
    void interpretMarkdownTestUnknownTags() {
        List<ParsedContent> actualFormatting = null;
        boolean thrown = false;

        //Create test string
        String testString = "This is a string containing an <UNKNOWN>tag</UNKNOWN>";

        try {
            actualFormatting = mdInterpreter.interpretMarkdown(testString);

        } catch(DamagedFileException e) {
            thrown = true;
        }
        Assertions.assertFalse(thrown);
        Assertions.assertTrue(actualFormatting.isEmpty());
    }

    /**
     * This method tests if the file validator can handle an uneven number of complete but asymmetric tag blocks. Since the file validator counts tags and verifies them by dividing them by two.
     * It could potentially fail if it sees three start tags and three end tags if that's not handled correctly.
     */
    @Test
    void interpretMarkdownTestOddNumberOfTags() {
        List<ParsedContent> testFormatting = new ArrayList<>();
        List<ParsedContent> actualFormatting = null;
        boolean thrown = false;

        //Set up ArrayList containing the formatting
        testFormatting.add(new ParsedContent("This is the first underlined statement", 0, 38, attributeSetUNDERLINE, false));
        testFormatting.add(new ParsedContent("This is the second underlined statement", 39, 39, attributeSetUNDERLINE, false));
        testFormatting.add(new ParsedContent("This is the third underlined statement", 79, 38, attributeSetUNDERLINE, false));

        //Create test string
        String testString = Tag.UNDERLINESTART + "This is the first underlined statement" + Tag.UNDERLINEEND + " " + Tag.UNDERLINESTART + "This is the second underlined statement" + Tag.UNDERLINEEND + " " + Tag.UNDERLINESTART + "This is the third underlined statement" + Tag.UNDERLINEEND;

        try {
            actualFormatting = mdInterpreter.interpretMarkdown(testString);

        } catch (DamagedFileException e) {
            thrown = true;
        }

        for(int i = 0; i < actualFormatting.size(); i++) {
            assertThat(actualFormatting.get(i).getFormat(), is(testFormatting.get(i).getFormat()));
            assertThat(actualFormatting.get(i).getStart(), is(testFormatting.get(i).getStart()));
            assertThat(actualFormatting.get(i).getLength(), is(testFormatting.get(i).getLength()));
            assertThat(actualFormatting.get(i).getContent(), is(testFormatting.get(i).getContent()));
            assertThat(actualFormatting.get(i).getReplace(), is(testFormatting.get(i).getReplace()));
        }
        Assertions.assertFalse(thrown);
    }

    /**
     * This method tests if all markdown tags get removed correctly for displaying on screen if using a valid file.
     */
    @Test
    void removeMarkdownTagsValid(){
        //Set up the test String
        String testString = Tag.BOLD + "This is a bold statement" + Tag.BOLD + " This is a normal statement " + Tag.CURSIVE + "Questa affermazione è in corsivo" + Tag.CURSIVE + " " + Tag.CURSIVE + Tag.STRIKETHROUGH + "this is strikethrough AND italics" + Tag.STRIKETHROUGH + Tag.CURSIVE;

        //Set up the validation String
        String validationString = "This is a bold statement This is a normal statement Questa affermazione è in corsivo this is strikethrough AND italics";

        Assertions.assertEquals(validationString, mdInterpreter.removeAllMarkdownTags(testString));
    }

    /**
     * This method tests if all markdown tags still get removed even if we would be able to slip by an invalid file by the fileValidator (which will obviously never happen)
     */
    @Test
    void removeMarkdownTagsInvalid(){
        //Set up the test String
        String testString = Tag.BOLD + "This is a bold statement" + Tag.BOLD + " This is a normal statement " + Tag.CURSIVE + "Questa affermazione è in corsivo" + Tag.CURSIVE + " " + Tag.STRIKETHROUGH + "this is strikethrough AND italics" + Tag.STRIKETHROUGH + Tag.CURSIVE;

        //Set up the validation String
        String validationString = "This is a bold statement This is a normal statement Questa affermazione è in corsivo this is strikethrough AND italics";

        Assertions.assertEquals(validationString, mdInterpreter.removeAllMarkdownTags(testString));
    }

    /**
     * This method tests if the markdown remover correctly returns an empty String if it gets fed an empty one.
     */
    @Test
    void removeMarkdownTagsEmpty(){
        //Set up the test String
        String testString = "";

        //Set up the validation String
        String validationString = "";

        Assertions.assertEquals(validationString, mdInterpreter.removeAllMarkdownTags(testString));
    }

    /**
     * This method tests if an unknown but similarly styled tag remains unchanged but all other tags still get correctly removed
     */
    @Test
    void removeMarkdownTagsUnknownTag(){
        //Set up the test String
        String testString = Tag.BOLD + "This is a bold statement" + Tag.BOLD + " This is a normal statement containing an <UNKNOWN>tag that should remain</UNKNOWN> " + Tag.CURSIVE + "Questa affermazione è in corsivo" + Tag.CURSIVE + " " + Tag.STRIKETHROUGH + "this is strikethrough AND italics" + Tag.STRIKETHROUGH + Tag.CURSIVE;

        //Set up the validation String
        String validationString = "This is a bold statement This is a normal statement containing an <UNKNOWN>tag that should remain</UNKNOWN> Questa affermazione è in corsivo this is strikethrough AND italics";

        Assertions.assertEquals(validationString, mdInterpreter.removeAllMarkdownTags(testString));
    }

    /**
     * This method initialises and sets all the attributeSets
     */
    private void setAttributeSet() {
        //AttributeSet for BOLD
        attributeSetBOLD = new SimpleAttributeSet();
        StyleConstants.setBold(attributeSetBOLD, true);

        //AttributeSet for Italic
        attributeSetITALIC = new SimpleAttributeSet();
        StyleConstants.setItalic(attributeSetITALIC, true);

        //AttributeSet for Strikethrough
        attributeSetSTRIKETHROUGH = new SimpleAttributeSet();
        StyleConstants.setStrikeThrough(attributeSetSTRIKETHROUGH, true);

        //AttributeSet for Underline
        attributeSetUNDERLINE = new SimpleAttributeSet();
        StyleConstants.setUnderline(attributeSetUNDERLINE, true);

        //AttributeSet for Monospace
        attributeSetMONOSPACE = new SimpleAttributeSet();
        StyleConstants.setFontFamily(attributeSetMONOSPACE, "Monospaced");

        //AttributeSet for Font
        attributeSetFONT = new SimpleAttributeSet();
        StyleConstants.setFontSize(attributeSetFONT, 8);
        StyleConstants.setFontFamily(attributeSetFONT, "Papyrus");

        //AttributeSet Bold Monospaced and Striketrough
        attributeSetBoldMonospaceStrikethrough = new SimpleAttributeSet();
        StyleConstants.setBold(attributeSetBoldMonospaceStrikethrough, true);
        StyleConstants.setStrikeThrough(attributeSetBoldMonospaceStrikethrough, true);
        StyleConstants.setFontFamily(attributeSetBoldMonospaceStrikethrough, "Monospaced");

        //AttributeSet for BoldAndItalic
        attributeSetBoldAndItalic = new SimpleAttributeSet();
        StyleConstants.setBold(attributeSetBoldAndItalic, true);
        StyleConstants.setItalic(attributeSetBoldAndItalic, true);

        //AttributeSet for BoldAndUnderline
        attributeSetBoldAndUnderline = new SimpleAttributeSet();
        StyleConstants.setBold(attributeSetBoldAndUnderline, true);
        StyleConstants.setUnderline(attributeSetBoldAndUnderline, true);

        //AttributeSet with Tags and Fonts
        attributeSetBoldItalicSizeFont = new SimpleAttributeSet();
        StyleConstants.setBold(attributeSetBoldItalicSizeFont, true);
        StyleConstants.setItalic(attributeSetBoldItalicSizeFont, true);
        StyleConstants.setFontSize(attributeSetBoldItalicSizeFont, 8);
        StyleConstants.setFontFamily(attributeSetBoldItalicSizeFont, "Papyrus");

        //AttributeSet only Papyrus Font and Size
        attributeSetPapyrusSizeFont = new SimpleAttributeSet();
        StyleConstants.setFontSize(attributeSetPapyrusSizeFont, 8);
        StyleConstants.setFontFamily(attributeSetPapyrusSizeFont, "Papyrus");
    }
}
