package ch.zhaw.texteditor.tests;

import ch.zhaw.texteditor.StorageBucket;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Test_StorageBucket {
    private StorageBucket bucket;

    @BeforeEach
    public void setUp() {
        bucket = StorageBucket.getInstance();
    }

    @AfterEach
    public void reset() {
        bucket.reset();
    }

    /**
     * This method tests adding a paragraph to the storage bucket.
     */
    @Test
    public void testAddParagraph() {
        bucket.addNewParagraph("Hello", 0);
        bucket.addNewParagraph("world", 1);
        bucket.addNewParagraph("beautiful", 1);
        bucket.addNewParagraph("Moin Moin!", 100); // invalid
        assertEquals(new ArrayList<String>() {{
            add("Hello");
            add("beautiful");
            add("world");
        }}, bucket.getParagraphList());
    }

    /**
     * This method tests setting the output width
     */
    @Test
    public void testSetOutputWidth() {
        assertEquals(0, bucket.getOutputWidth());
        bucket.setOutputWidth(-5);
        assertEquals(0, bucket.getOutputWidth());
        bucket.setOutputWidth(5);
        assertEquals(5, bucket.getOutputWidth());
    }

    /**
     * This method tests the deletion of paragraphs in the storage bucket
     */
    @Test
    public void testDeleteParagraph() {
        bucket.addNewParagraph("Hello", bucket.getParagraphCount());
        bucket.addNewParagraph("beautiful", bucket.getParagraphCount());
        bucket.addNewParagraph("world", bucket.getParagraphCount());
        bucket.addNewParagraph("it's", bucket.getParagraphCount());
        bucket.addNewParagraph("me", bucket.getParagraphCount());
        // delete a negative index
        bucket.deleteParagraph(-1); // invalid
        // delete something out of range
        bucket.deleteParagraph(5); // nothing should be deleted
        // delete the first element
        bucket.deleteParagraph(0);
        // delete the last element
        bucket.deleteParagraph(3);
        assertEquals(new ArrayList<String>() {{
            add("beautiful");
            add("world");
            add("it's");
        }}, bucket.getParagraphList());
    }

    /**
     * This method tests the update of paragraphs in the storage bucket
     */
    @Test
    public void testUpdateParagraph() {
        bucket.addNewParagraph("Hello", bucket.getParagraphCount());
        bucket.addNewParagraph("beautiful", bucket.getParagraphCount());
        bucket.addNewParagraph("world", bucket.getParagraphCount());
        bucket.addNewParagraph("it's", bucket.getParagraphCount());
        bucket.addNewParagraph("me", bucket.getParagraphCount());
        // update something invalid
        bucket.updateParagraph("test", -1); // nothing should happen
        bucket.updateParagraph("test", 5); // invalid
        // update something valid
        bucket.updateParagraph("ugly", 1);
        assertEquals(new ArrayList<String>() {{
            add("Hello");
            add("ugly");
            add("world");
            add("it's");
            add("me");
        }}, bucket.getParagraphList());
    }
}