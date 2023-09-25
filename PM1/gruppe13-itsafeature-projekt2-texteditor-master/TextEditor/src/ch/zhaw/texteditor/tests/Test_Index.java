package ch.zhaw.texteditor.tests;

import ch.zhaw.texteditor.DataProcessing;
import ch.zhaw.texteditor.StorageBucket;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.HashMap;

/**
 * This class tests the index method on its functionality for easier deployment
 *
 * @author J. Peter
 * @version 2020-11-05
 */

public class Test_Index {
    private StorageBucket bucket;
    private DataProcessing dataProcessing;

    /**
     * This gets the StorageBucket instance and prepares a new DataProcessing instance before each test
     */
    @BeforeEach
    public void setUp() {
        bucket = StorageBucket.getInstance();
        dataProcessing = new DataProcessing();
    }

    /**
     * After each test the bucket gets reset and the dataProcessing instance gets set to null
     */
    @AfterEach
    public void reset() {
        bucket.reset();
        dataProcessing = null;
    }

    /**
     * This tests the index with a single paragraph where the same word appears four times.
     * It tests the basic functionality of the index method.
     */
    @Test
    public void testIndexSingleParagraph() {
        bucket.addNewParagraph("This is a Text. A Text where the word Text appears four times and begins with a capital. Text.", bucket.getParagraphCount());

        assertEquals(new HashMap<String, String>() {{
            put("Text", "1");
        }}, dataProcessing.index());
    }

    /**
     * This test shows that the index method also works over multiple paragraphs and does not count the same paragraph twice if a word appears more than once in there.
     * It also excludes accents and umlauts as those get treated as different characters.
     */
    @Test
    public void testIndexMultipleParagraphs() {
        bucket.addNewParagraph("This is a Text.", bucket.getParagraphCount());
        bucket.addNewParagraph("A Text, a Text, a Text.", bucket.getParagraphCount());
        bucket.addNewParagraph("This Text is completely random.", bucket.getParagraphCount());
        bucket.addNewParagraph("It should differentiate between Téxt and Tèxt. They should not appear in the list.", bucket.getParagraphCount());

        assertEquals(new HashMap<String, String>() {{
            put("Text", "1, 2, 3");
        }}, dataProcessing.index());
    }

    /**
     * This shows that the method works with multiple words.
     * It also shows that the index only counts words that begin with a capital and treats words that are capitalized in a weird way as a different word.
     */
    @Test
    public void testIndexCapitalization() {
        bucket.addNewParagraph("In this text the word Text appears with different capitalization in multiple paragraphs.", bucket.getParagraphCount());
        bucket.addNewParagraph("It shows that the index differentiates between text and TeXt.", bucket.getParagraphCount());
        bucket.addNewParagraph("A Text like this is probably really uncommon and only shows how a text gets handled.", bucket.getParagraphCount());
        bucket.addNewParagraph("Reading this you would think I made a typo writing TeX but no I mean TeXt.", bucket.getParagraphCount());
        bucket.addNewParagraph("This paragraph contains all three variants: Text, text and TeXt.", bucket.getParagraphCount());
        bucket.addNewParagraph("Only so that you can see that text gets ignored while Text and TeXt do not because they start with a capital letter.", bucket.getParagraphCount());
        assertEquals(new HashMap<String, String>() {{
            put("Text", "1, 3, 5, 6");
            put("TeXt", "2, 4, 5, 6");
        }}, dataProcessing.index());
    }

    /**
     * This test shows that a word only gets counted if the whole word matches. Part of words do not match.
     */
    @Test
    public void testIndexSubStrings() {
        bucket.addNewParagraph("Random text in English.", bucket.getParagraphCount());
        bucket.addNewParagraph("This is a text with Random words in it in English.", bucket.getParagraphCount());
        bucket.addNewParagraph("This is a sentence with the word Randomize to show it doesn't count substrings.", bucket.getParagraphCount()); //Randomize should not appear
        bucket.addNewParagraph("German is such a random language. Random Random Random.", bucket.getParagraphCount());

        assertEquals(new HashMap<String, String>() {{
            put("Random", "1, 2, 4");
        }}, dataProcessing.index());
    }

    /**
     * The method returns an informative statement if the paragraph does not contain any words.
     */
    @Test
    public void testIndexEmpty() {
        assertEquals(new HashMap<String, String>() {{
            put("This index does not contain any words.", "");
        }}, dataProcessing.index());
    }

    /**
     * It returns the same statement if a paragraph does not contain a word often enough to be counted.
     */
    @Test
    public void testIndexNotEnoughRepetition() {
        bucket.addNewParagraph("This text doesn't have enough repeated words to break through the three words barrier.", bucket.getParagraphCount());

        assertEquals(new HashMap<String, String>() {{
            put("This index does not contain any words.", "");
        }}, dataProcessing.index());
    }

    /**
     * This test shows that the index method counts correctly even when multiple paragraphs contain the exact same string.
     */
    @Test
    public void testExactSameParagraph() {
        bucket.addNewParagraph("Test", bucket.getParagraphCount());
        bucket.addNewParagraph("Test", bucket.getParagraphCount());
        bucket.addNewParagraph("Test", bucket.getParagraphCount());
        bucket.addNewParagraph("Test", bucket.getParagraphCount());

        assertEquals(new HashMap<String, String>() {{
            put("Test", "1, 2, 3, 4");
        }}, dataProcessing.index());
    }
}
