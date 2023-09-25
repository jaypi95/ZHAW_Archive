package ch.zhaw.pm2.supercoolmarkdown.controller;

import ch.zhaw.pm2.supercoolmarkdown.MessageEnum;
import ch.zhaw.pm2.supercoolmarkdown.model.Document;
import ch.zhaw.pm2.supercoolmarkdown.model.ParsedContent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import javax.swing.JTextPane;
import javax.swing.text.StyledDocument;
import java.io.IOException;
import java.util.Optional;

/**
 * This class is the SuperController for the MainTextEditorWindowController and the MenuController
 *
 * @author baachsil
 * @version 1.0
 */
public abstract class SuperTextEditorWindowController extends SuperWindowController {

    //This is the holy document var on which the user is currently editing
    private static Document document;
    private static JTextPane textPane;
    private boolean isMarkdownShowed;

    /**
     * This method handles if actions are required before changing the state of the document
     */
    public boolean handleNeedToSave() {
        boolean returnValue = false;
        if (isMarkdownShowed) {
            showAlertMessage(MessageEnum.ALERT_MARKDOWN_IS_SHOWN);
        } else {
            if (!document.isSaved()) {
                Alert alert = new Alert(Alert.AlertType.WARNING, MessageEnum.ALERT_WANT_TO_SAVE.toString(), ButtonType.YES, ButtonType.NO);
                alert.setTitle("SuperCoolMarkdown");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.YES) {
                    saveFile();
                }
            }
            returnValue = true;
        }

        return returnValue;
    }

    /**
     * This method saves the file.
     * If this file is new, ask the user where it should be saved, if not save it at the saved place
     */
    protected void saveFile() {
        if (document.getFilePath().isEmpty()) {
            saveAs();
        } else {
            try {
                document.saveDocument();
                getRecentFiles().addRecentOpenedFile(document);
            } catch (IOException e) {
                showAlertMessage(MessageEnum.ALERT_COULD_NOT_WRITE);
            }
        }
    }

    /**
     * This method asks the user for a new path to save the file to
     */
    protected void saveAs() {
        String newFilePath = selectASavePath();
        if (!newFilePath.isBlank()) {
            document.setFilePath(newFilePath);
            saveFile();
        }
    }

    /**
     * This method opens an existing file
     */
    protected void openExisting() {
        handleNeedToSave();
        Document newDocument = openExistingFile(selectAFile());
        if (newDocument != null) {
            setDocument(newDocument);
        }
    }

    /**
     * This method opens a file from the recentOpenedFileList
     */
    protected void openRecentOpenedFile(String filepath) {
        handleNeedToSave();
        setDocument(openExistingFile(filepath));
    }

    /**
     * This method opens a new Document
     */
    protected void newDocument() {
        handleNeedToSave();
        setDocument(new Document());
    }

    /**
     * This methods formats the text in the GUI correct based on the parsed info from
     * the MarkDownInterpreter
     */
    protected void applyMarkdownText(StyledDocument doc) {
        for (ParsedContent format : document.getFormattingInfo()) {
            doc.setCharacterAttributes(format.getStart(), format.getLength(), format.getFormat(), format.getReplace());
        }
    }

    /**
     * @return if markdown is currently shown
     */
    protected boolean isMarkdownShowed() {
        return isMarkdownShowed;
    }

    /**
     * Sets the state if markdown is currently showed
     *
     * @param markdownShowed whats the new state?
     */
    protected void setMarkdownShowed(boolean markdownShowed) {
        isMarkdownShowed = markdownShowed;
    }

    /**
     * @return the document on which the user is currently editing on
     */
    protected Document getDocument() {
        return document;
    }

    /**
     * Sets the document which the user wants to edit
     *
     * @param doc the document which the user wants to edit
     */
    public void setDocument(Document doc) {
        if (doc != null) {
            document = doc;
            textPane.setText(document.getFileContent());
            applyMarkdownText((StyledDocument) textPane.getDocument());
            getDocument().setDocContent((StyledDocument) textPane.getDocument());
        }
    }

    /**
     * @return the JTextPane
     */
    protected JTextPane getTextPane() {
        return textPane;
    }

    /**
     * Sets the textPane
     *
     * @param textPane the textPane
     */
    protected void setTextPane(JTextPane textPane) {
        SuperTextEditorWindowController.textPane = textPane;
    }
}
