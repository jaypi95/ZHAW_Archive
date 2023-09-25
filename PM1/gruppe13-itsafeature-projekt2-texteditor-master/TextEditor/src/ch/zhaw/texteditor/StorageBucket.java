package ch.zhaw.texteditor;

import java.util.ArrayList;

/**
 * This class manages the program's data, such as adding,
 * removing, updating entries.
 * Its the MODEL from the MVC Pattern
 * Autor: Jon Defilla
 */
public class StorageBucket {

    private ArrayList<String> paragraphList;
    private static final String defaultText =
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt" +
                    " ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris" +
                    " nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit" +
                    " esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt" +
                    " in culpa qui officia deserunt mollit anim id est laborum.";
    private int outputWidth = 0; // value 0 is the default -> full console width
    private static StorageBucket storageBucket; //Instance for the Singleton

    /**
     * Private constructor
     * Only used by this class to create the one and only instance
     */
    private StorageBucket() {
        paragraphList = new ArrayList<String>();
    }

    /**
     * Public constructor which implements the singleton pattern
     *
     * @return storageBucket instance
     * !Important! : Not Multithread safe
     */
    public static StorageBucket getInstance() {
        if (StorageBucket.storageBucket == null) {
            StorageBucket.storageBucket = new StorageBucket(); //if there is no instance yet create one
        }
        return StorageBucket.storageBucket; //returns the one and only instance
    }

    /**
     * Insert content to the list at the specified index
     *
     * @param content new content to insert
     * @param index   index
     */
    public void addNewParagraph(String content, int index) {
        if (index >= 0 && index <= paragraphList.size()) {
            paragraphList.add(index, content);
        }
    }

    /**
     * Remove paragraph at index
     *
     * @param index index at which to remove the paragraph
     */
    public void deleteParagraph(int index) {
        // check if index matches something in paragraphList
        if (index >= 0 && index < paragraphList.size()) {
            paragraphList.remove(index);
        }
    }

    /**
     * This method overrides the paragraph with content at index
     *
     * @param content new content
     * @param index   index
     */
    public void updateParagraph(String content, int index) {
        // check if index in within the list bounds
        if (index >= 0 && index < paragraphList.size()) {
            paragraphList.set(index, content);
        }
    }

    /**
     * @param index Get paragraph at index
     * @return paragraph at given index
     */
    public String getParagraph(int index) {
        return paragraphList.get(index);
    }

    /**
     * @return the output width
     */
    public int getOutputWidth() {
        return outputWidth;
    }

    /**
     * @return the paragraph list containing all the paragraphs
     */
    public ArrayList<String> getParagraphList() {
        return paragraphList;
    }

    /**
     * Sets the output width when printing the paragraphs
     *
     * @param outputWidth int bigger than 0
     */
    public void setOutputWidth(int outputWidth) {
        if (outputWidth >= 0) {
            this.outputWidth = outputWidth;
        }
    }

    /**
     * @return the default text
     */
    public String getDefaultText() {
        return defaultText;
    }

    /**
     * @return the amount of paragraphs stored
     */
    public int getParagraphCount() {
        return paragraphList.size();
    }

    /**
     * Reset the instance starting value
     */
    public void reset() {
        StorageBucket.storageBucket = null;
    }
}
