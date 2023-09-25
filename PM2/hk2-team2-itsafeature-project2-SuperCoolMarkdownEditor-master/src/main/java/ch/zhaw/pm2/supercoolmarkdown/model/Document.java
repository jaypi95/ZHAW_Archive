package ch.zhaw.pm2.supercoolmarkdown.model;

import ch.zhaw.pm2.supercoolmarkdown.exceptions.DamagedFileException;

import javax.swing.text.AttributeSet;
import javax.swing.text.Element;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the document which the user sees in the mainGUI.
 * It handles the storage of content as well as providing methods to format the text
 *
 * @author Baachsil
 * @version 11.05.2021
 */
public class Document {
    private Path filePath; // this path should include the file and its extension
    private String fileContent; // content of file as a plain text (includes markdown tags) (UTF-8 encoded)
    private boolean isSaved;
    private MarkDownWriter markDownWriter;

    private MarkDownInterpreter markDownInterpreter;
    private List<ParsedContent> formattingList;
    private StyledDocument docContent;

    /**
     * Constructor for a new document
     */
    public Document() {
        filePath = null;
        fileContent = "";
        isSaved = false;
        markDownInterpreter = new MarkDownInterpreter();
        formattingList = new ArrayList<>();
        markDownWriter = new MarkDownWriter();
    }

    /**
     * Constructor for opening an existing document
     *
     * @param filePath path to the document including the extension
     */
    public Document(String filePath) throws IOException, DamagedFileException {
        this.filePath = Paths.get(filePath.strip());
        fileContent = readDocument();
        isSaved = true;
        markDownInterpreter = new MarkDownInterpreter();
        formattingList = markDownInterpreter.interpretMarkdown(fileContent);
        markDownWriter = new MarkDownWriter();
    }

    /**
     * This method returns the filepath of this document as a string or an empty string if it's null
     *
     * @return the filepath of this document
     */
    public String getFilePath() {
        if (filePath != null) {
            return filePath.toString();
        } else {
            return "";
        }
    }

    /**
     * This method sets a new filepath for the document
     *
     * @param filePath The new filepath
     */
    public void setFilePath(String filePath) {
        this.filePath = Paths.get(filePath);
    }

    /**
     * This method returns the filename of this document including its extension or an empty string if it's null
     *
     * @return the filename of this document
     */
    public String getFileName() {
        if (filePath != null) {
            return filePath.getFileName().toString();
        } else {
            return "";
        }
    }

    /**
     * This method returns true if the document was saved
     *
     * @return true if the document was saved
     */
    public boolean isSaved() {
        return isSaved;
    }

    /**
     * This method will be called when the document gets changed. It will reset the saved state of the document
     */
    public void documentChanged() {
        isSaved = false;
    }

    /**
     * Returns the markdown content based on an StyledDocument
     *
     * @param doc the document
     * @return the markdown content
     */
    public String getMarkdownContent(StyledDocument doc) {
        return markDownWriter.getMarkDownText(doc);
    }

    /**
     * This method formats a text based on a selection
     *
     * @param doc      the document
     * @param tag      the tag
     * @param selStart the start of the selection
     * @param selEnd   the end of the selection
     * @return the changed document
     */
    public StyledDocument formatText(StyledDocument doc, Tag tag, int selStart, int selEnd) {
        boolean shouldBeFormatted = decideApplyingFormatting(doc, tag, selStart, selEnd);
        applyFormatting(doc, tag, selStart, selEnd, shouldBeFormatted);
        return doc;
    }

    /**
     * This method formats a text and the font based on a selection
     *
     * @param doc      the document
     * @param selStart the start of the selection
     * @param selEnd   the end of the selection
     * @param fontSize the new size of the font
     * @param fontName the new name of the font
     * @return the changed document
     */
    public StyledDocument formatTextFont(StyledDocument doc, int selStart, int selEnd, int fontSize, String fontName) {
        for (int i = 0; i < (selEnd - selStart); i++) {
            Element element = doc.getCharacterElement(i + selStart);
            AttributeSet attributeSet = element.getAttributes();
            MutableAttributeSet attributeSetNew = new SimpleAttributeSet(attributeSet.copyAttributes());
            if (fontSize != 0) {
                StyleConstants.setFontSize(attributeSetNew, fontSize);
            }
            if (!fontName.isBlank()) {
                StyleConstants.setFontFamily(attributeSetNew, fontName);
            }
            doc.setCharacterAttributes(selStart + i, 1, attributeSetNew, true);
        }
        return doc;
    }

    /**
     * This method decides if a formatting should be applied to a text
     * If only one char is not formatted correctly the whole text is gonna be formatted
     *
     * @param doc      the document
     * @param tag      the tag that should be applied
     * @param selStart start of the selection made by the user
     * @param selEnd   end of the selection made by the user
     * @return yes or no if the formatting should be applied
     */
    private boolean decideApplyingFormatting(StyledDocument doc, Tag tag, int selStart, int selEnd) {
        boolean applyFormatting = false;

        for (int i = 0; i < (selEnd - selStart); i++) {
            Element element = doc.getCharacterElement(i + selStart);
            AttributeSet attributeSet = element.getAttributes();
            if (!checkFormatting(attributeSet, tag)) {
                applyFormatting = true;
            }
        }

        return applyFormatting;
    }

    /**
     * This method applies a formatting to a selection
     *
     * @param doc             the document
     * @param tag             the tag which should be used
     * @param selStart        start of the selection by the user
     * @param selEnd          end of the selection by the user
     * @param applyFormatting should the formatting be applied
     */
    private void applyFormatting(StyledDocument doc, Tag tag, int selStart, int selEnd, boolean applyFormatting) {
        for (int i = 0; i < (selEnd - selStart); i++) {
            Element element = doc.getCharacterElement(i + selStart);
            AttributeSet attributeSet = element.getAttributes();
            MutableAttributeSet attributeSetNew = new SimpleAttributeSet(attributeSet.copyAttributes());
            setFormatting(attributeSetNew, tag, applyFormatting);
            doc.setCharacterAttributes(selStart + i, 1, attributeSetNew, true);
        }
    }

    /**
     * Returns true if an attributeSet has a specific value set
     *
     * @param attributeSet the attributeSet to check on
     * @param tag          the tag
     * @return the result of the StyleConstants function
     */
    private boolean checkFormatting(AttributeSet attributeSet, Tag tag) {
        boolean returnValue = false;
        switch (tag) {
            case BOLD:
                returnValue = StyleConstants.isBold(attributeSet);
                break;
            case CURSIVE:
                returnValue = StyleConstants.isItalic(attributeSet);
                break;
            case STRIKETHROUGH:
                returnValue = StyleConstants.isStrikeThrough(attributeSet);
                break;
            case UNDERLINESTART:
                returnValue = StyleConstants.isUnderline(attributeSet);
                break;
            case MONOSPACE:
                returnValue = StyleConstants.getFontFamily(attributeSet).equalsIgnoreCase("monospaced");
                break;
        }
        return returnValue;
    }

    /**
     * Returns an attributeSet with changed formatting
     *
     * @param attributeSet the attributeSet to format
     * @param tag          the tag
     */
    private void setFormatting(MutableAttributeSet attributeSet, Tag tag, boolean shouldBeFormatted) {
        switch (tag) {
            case BOLD:
                StyleConstants.setBold(attributeSet, shouldBeFormatted);
                break;
            case CURSIVE:
                StyleConstants.setItalic(attributeSet, shouldBeFormatted);
                break;
            case STRIKETHROUGH:
                StyleConstants.setStrikeThrough(attributeSet, shouldBeFormatted);
                break;
            case UNDERLINESTART:
                StyleConstants.setUnderline(attributeSet, shouldBeFormatted);
                break;
            case MONOSPACE:
                StyleConstants.setFontFamily(attributeSet, "Monospaced");
                break;
        }
    }

    /**
     * This method saves the content of the document (string) to the file (filePath)
     * When calling this function, filePath must not be null
     *
     * @throws IOException if failed to write
     */
    public void saveDocument() throws IOException {
        BufferedWriter writer = Files.newBufferedWriter(filePath);
        writer.write(markDownWriter.getMarkDownText(docContent));
        writer.close();
        isSaved = true;
    }

    /**
     * This method checks if a file exists, and if it does it will read it as an UTF-8 file.
     *
     * @return String The read string
     */
    private String readDocument() throws IOException {
        if (!filePath.toFile().exists()) {
            throw new FileNotFoundException();
        }
        return Files.readString(filePath, StandardCharsets.UTF_8);
    }

    /**
     * @return all formatting Infos of the currently loaded document
     */
    public List<ParsedContent> getFormattingInfo() {
        return formattingList;
    }

    /**
     * This method returns the content of the file excluding the markdown tags
     *
     * @return the content of the file
     */
    public String getFileContent() {
        return markDownInterpreter.removeAllMarkdownTags(fileContent);
    }

    /**
     * Sets the file content based on already generated markdown content
     *
     * @param markdownContent the markdown content
     */
    public void setFileContent(String markdownContent) {
        this.fileContent = markdownContent;
    }

    /**
     * Updates the formattingList based on the current document content
     *
     * @throws DamagedFileException is the current content is not valid markdown
     */
    public void updateFormattingList() throws DamagedFileException {
        formattingList.clear();
        formattingList = markDownInterpreter.interpretMarkdown(fileContent);
    }

    /**
     * Sets the document content
     *
     * @param docContent the document content
     */
    public void setDocContent(StyledDocument docContent) {
        this.docContent = docContent;
    }
}
