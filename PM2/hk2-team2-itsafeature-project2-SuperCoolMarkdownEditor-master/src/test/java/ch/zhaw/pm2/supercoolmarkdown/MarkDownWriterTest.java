package ch.zhaw.pm2.supercoolmarkdown;

import ch.zhaw.pm2.supercoolmarkdown.model.MarkDownWriter;
import ch.zhaw.pm2.supercoolmarkdown.model.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import javax.swing.*;
import javax.swing.text.*;

/**
 * This class is a test class for the class MarkDownWriter
 *
 * @author Outitbad
 * @version 12.05.2021
 */
public class MarkDownWriterTest {
    private MarkDownWriter mDWriter;
    private StyledDocument docSmallText;
    private StyledDocument docBigText;
    private JTextPane textPaneSmallText;
    private JTextPane textPaneBigText;
    private AttributeSet attributeSetBOLD;
    private AttributeSet attributeSetITALIC;
    private AttributeSet attributeSetSTRIKETHROUGH;
    private AttributeSet attributeSetMONOSPACE;
    private AttributeSet attributeSetUNDERLINE;
    private AttributeSet attributeSetFONT;
    private AttributeSet attributeSetBoldAndItalic;
    private AttributeSet attributeSetBoldAndUnderline;
    private AttributeSet attributeSetBoldItalicSizeFont;
    private AttributeSet attributeSetBoldMonospaceStrikethrough;

    /**
     * This method setups the test methods with init the Pane and get the Texts. It als sets all attributeSets
     */
    @BeforeEach
    void setUp(){
        Tag.initTags();
        this.mDWriter = new MarkDownWriter();
        textPaneSmallText = new JTextPane();
        textPaneBigText = new JTextPane();
        textPaneSmallText.setText("Hello, this is a test");
        textPaneBigText.setText("This is styled BOLD"+ "This is styled Underlined" + "This is styled Bold and Underlined" + "This is styled with font papyrus" + "This is bold, strikedthrough and monospace");
        docSmallText = (StyledDocument) textPaneSmallText.getDocument();
        docBigText = (StyledDocument) textPaneBigText.getDocument();
        //Setup
        setAttributeSet();

    }

    /**
     * This method tests the getMarkDownText method with a small text.
     * It tests if the style tags will be put correctly
     */
    @Test
    void getMarkDownTextTestStyleTags(){
        //Make "Hello" have Tag attributes
        docSmallText.setCharacterAttributes(0,5, attributeSetBoldAndItalic,true);
        String expectedResult = Tag.BOLD.toString()+Tag.CURSIVE+"<font face=Dialog size=12>"+"Hello"+Tag.BOLD+Tag.CURSIVE +", this is a test</font>";
        assertEquals(expectedResult, mDWriter.getMarkDownText(docSmallText));
    }
    /**
     * This method tests the getMarkDownText method with a small text.
     * It tests if the font tags will be put correctly
     */
    @Test
    void getMarkDownTextTestFontTags() {
        //Make "test" have Font attributes
        docSmallText.setCharacterAttributes(17, 4, attributeSetFONT, true);
        String expectedResult = "<font face=Dialog size=12>" + "Hello, this is a " +  Tag.FONTEND+ "<font face=Papyrus size=8>" + "test"+Tag.FONTEND;
        assertEquals(expectedResult, mDWriter.getMarkDownText(docSmallText));
    }
    /**
     * This method tests the getMarkDownText method with a small text.
     * It tests if the style and font tags will be put correctly
     */
    @Test
    void getMarkDownTextTestStyleAndFontTags() {
        //Make "test" have Font attributes
        docSmallText.setCharacterAttributes(17, 4, attributeSetBoldItalicSizeFont, true);
        String expectedResult = "<font face=Dialog size=12>" + "Hello, this is a " +Tag.FONTEND+ Tag.BOLD+Tag.CURSIVE+ "<font face=Papyrus size=8>" + "test"+Tag.FONTEND +Tag.BOLD+Tag.CURSIVE;
        assertEquals(expectedResult, mDWriter.getMarkDownText(docSmallText));
    }
    /**
     * This method tests the getMarkDownText method with a small text.
     * It tests if there will be no tags put, if the text is empty
     */
    @Test
    void getMarkDownTextEmpty(){
        //Setup
        JTextPane emptyTextPane = new JTextPane();
        emptyTextPane.setText("");
        docSmallText = (StyledDocument) emptyTextPane.getDocument();

        String expectedResult = "";
        assertEquals(expectedResult, mDWriter.getMarkDownText(docSmallText));
    }
    /**
     * This method tests the getMarkDownText method with a Big text.
     * It tests if all the tags will be put correctly
     */
    @Test
    void getMarkDownTextBigTextTag(){
        //Add the Attributes to the Text
        docBigText.setCharacterAttributes(0, 19, attributeSetBOLD, true);
        docBigText.setCharacterAttributes(19, 25, attributeSetUNDERLINE, true);
        docBigText.setCharacterAttributes(44, 34, attributeSetBoldAndUnderline, true);
        docBigText.setCharacterAttributes(78, 32, attributeSetFONT, true);
        docBigText.setCharacterAttributes(110, 43, attributeSetBoldMonospaceStrikethrough, true);



        String expectedResult = Tag.BOLD+"<font face=Dialog size=12>"+ "This is styled BOLD"+Tag.BOLD+ Tag.UNDERLINESTART+"This is styled Underlined" + Tag.BOLD+"This is styled Bold and Underlined" +Tag.FONTEND+Tag.UNDERLINEEND+Tag.BOLD+
                "<font face=Papyrus size=8>"+"This is styled with font papyrus" +Tag.BOLD+Tag.STRIKETHROUGH+Tag.MONOSPACE+ "This is bold, strikedthrough and monospace"+Tag.FONTEND+Tag.BOLD+Tag.STRIKETHROUGH+Tag.MONOSPACE;
        assertEquals(expectedResult, mDWriter.getMarkDownText(docBigText));
    }

    /**
     * This method init and sets all the attributeSets
     */
    private void setAttributeSet(){
        //AttributeSet for BOLD
        attributeSetBOLD = new SimpleAttributeSet();
        StyleConstants.setBold((MutableAttributeSet) attributeSetBOLD, true);

        //AttributeSet for Italic
        attributeSetITALIC = new SimpleAttributeSet();
        StyleConstants.setItalic((MutableAttributeSet) attributeSetITALIC,true);

        //AttributeSet for Strikethrough
        attributeSetSTRIKETHROUGH = new SimpleAttributeSet();
        StyleConstants.setStrikeThrough((MutableAttributeSet) attributeSetSTRIKETHROUGH, true);

        //AttributeSet for Underline
        attributeSetUNDERLINE = new SimpleAttributeSet();
        StyleConstants.setUnderline((MutableAttributeSet) attributeSetUNDERLINE, true);

        //AttributeSet for Monospace
        attributeSetMONOSPACE = new SimpleAttributeSet();
        StyleConstants.setFontFamily((MutableAttributeSet) attributeSetMONOSPACE, "Monospaced");

        //AttributeSet for Font
        attributeSetFONT = new SimpleAttributeSet();
        StyleConstants.setFontSize((MutableAttributeSet) attributeSetFONT, 8);
        StyleConstants.setFontFamily((MutableAttributeSet) attributeSetFONT, "Papyrus");

        //AttributeSet Bold Monospaced and Striketrough
        attributeSetBoldMonospaceStrikethrough = new SimpleAttributeSet();
        StyleConstants.setBold((MutableAttributeSet)attributeSetBoldMonospaceStrikethrough, true);
        StyleConstants.setStrikeThrough((MutableAttributeSet)attributeSetBoldMonospaceStrikethrough, true);
        StyleConstants.setFontFamily((MutableAttributeSet)attributeSetBoldMonospaceStrikethrough, "Monospaced");

        //AttributeSet for BoldAndItalic
        attributeSetBoldAndItalic = new SimpleAttributeSet();
        StyleConstants.setBold((MutableAttributeSet) attributeSetBoldAndItalic, true);
        StyleConstants.setItalic((MutableAttributeSet) attributeSetBoldAndItalic, true);

        //AttributeSet for BoldAndUnderline
        attributeSetBoldAndUnderline = new SimpleAttributeSet();
        StyleConstants.setBold((MutableAttributeSet) attributeSetBoldAndUnderline, true);
        StyleConstants.setUnderline((MutableAttributeSet) attributeSetBoldAndUnderline, true);

        //AttributeSet with Tags and Fonts
        attributeSetBoldItalicSizeFont = new SimpleAttributeSet();
        StyleConstants.setBold((MutableAttributeSet) attributeSetBoldItalicSizeFont, true);
        StyleConstants.setItalic((MutableAttributeSet) attributeSetBoldItalicSizeFont, true);
        StyleConstants.setFontSize((MutableAttributeSet) attributeSetBoldItalicSizeFont, 8);
        StyleConstants.setFontFamily((MutableAttributeSet) attributeSetBoldItalicSizeFont, "Papyrus");
    }

}
