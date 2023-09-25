package ch.zhaw.pm2.supercoolmarkdown.controller;

import ch.zhaw.pm2.supercoolmarkdown.MessageEnum;
import ch.zhaw.pm2.supercoolmarkdown.exceptions.DamagedFileException;
import ch.zhaw.pm2.supercoolmarkdown.model.Font;
import ch.zhaw.pm2.supercoolmarkdown.model.Tag;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.AnchorPane;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.AttributeSet;
import javax.swing.text.Element;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This is the controller class of the TextEditorWindow GUI
 * We need the Initializable interface because of our Swing objects
 *
 * @author Defiljon
 * @version 11.05.2021
 */
public class TextEditorWindowController extends SuperTextEditorWindowController implements Initializable {

    @FXML
    private SwingNode swingNode;
    @FXML
    private ComboBox fontSize;
    @FXML
    private ComboBox fontFamily;
    @FXML
    private javafx.scene.control.Button boldButton;
    @FXML
    private javafx.scene.control.Button underlineButton;
    @FXML
    private javafx.scene.control.Button italicButton;
    @FXML
    private javafx.scene.control.Button strikethroughButton;
    @FXML
    private javafx.scene.control.Button monospaceButton;
    @FXML
    private javafx.scene.control.Button markdownButton;
    @FXML
    private MenuBar menuBar;
    @FXML
    private Menu menu_open_recent;

    private TextEditorMenuController menuController;

    /**
     * Init of the Controller
     * Creates the JTextPane, setup of the menubar actions and more
     * We need this special init methode because of Swing
     *
     * @param location  not needed
     * @param resources not needed
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createJTextPane(swingNode);

        //Makes the swingNode floating in the AnchorPane
        AnchorPane.setTopAnchor(swingNode, 0d);
        AnchorPane.setBottomAnchor(swingNode, 0d);
        AnchorPane.setRightAnchor(swingNode, 0d);
        AnchorPane.setLeftAnchor(swingNode, 0d);

        menuController = new TextEditorMenuController(menuBar, menu_open_recent);
        menuController.setMenubarActions();
        menuController.createSubMenus();

        setupComboBoxes();
        setMarkdownShowed(false);
    }

    /**
     * This method creates the Swing JTextPanel which acts as the main texteditor
     * Couldn't do this setup in the UI class because the whole FXML construct needs to
     * be processed before this code works.
     *
     * Note: Its a Runnable to ensure this code runs in the aws thread and not in the java fx thread
     *
     * @param swingNode the final swingNode
     */
    private void createJTextPane(final SwingNode swingNode) {
        SwingUtilities.invokeLater(() -> {
            JTextPane textPane = new JTextPane();
            JScrollPane jsp = new JScrollPane(textPane);

            textPane.setLayout(null);
            textPane.setPreferredSize(new Dimension(400, 386));

            //Sets the standard font of the editor
            java.awt.Font f = new java.awt.Font(java.awt.Font.SANS_SERIF, java.awt.Font.PLAIN, 20);

            textPane.setFont(f);

            //Adds a listener to the cursor
            textPane.addCaretListener(e -> {
                if (!isMarkdownShowed()) {
                    displayFontPropChanges();
                }
            });

            textPane.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {
                }

                @Override
                public void keyPressed(KeyEvent e) {
                    getDocument().documentChanged();
                    getDocument().setDocContent((StyledDocument) getTextPane().getDocument());
                }

                @Override
                public void keyReleased(KeyEvent e) {
                }
            });

            swingNode.setContent(jsp);
            setTextPane(textPane);
        });
    }

    /**
     * This method setups all combo boxes
     */
    private void setupComboBoxes() {
        Font font = new Font();

        //FontSizeList
        ObservableList<Integer> sizes = FXCollections.observableArrayList(font.getFontSizeList());
        fontSize.setItems(sizes);

        //FontFamilyList
        ObservableList<String> families = FXCollections.observableArrayList(font.getFontFamilyList());
        fontFamily.setItems(families);
    }

    /**
     * This method sets the selection to bold
     */
    @FXML
    private void setTextBold() {
        StyledDocument doc = getDocument().formatText((StyledDocument) getTextPane().getDocument(), Tag.BOLD, getTextPane().getSelectionStart(), getTextPane().getSelectionEnd());
        getTextPane().setDocument(doc);
    }

    /**
     * This method sets the selection to underline
     */
    @FXML
    private void setTextUnderline() {
        StyledDocument doc = getDocument().formatText((StyledDocument) getTextPane().getDocument(), Tag.UNDERLINESTART, getTextPane().getSelectionStart(), getTextPane().getSelectionEnd());
        getTextPane().setDocument(doc);
    }

    /**
     * This method sets the selection to strikethrough
     */
    @FXML
    private void setTextStrikethrough() {
        StyledDocument doc = getDocument().formatText((StyledDocument) getTextPane().getDocument(), Tag.STRIKETHROUGH, getTextPane().getSelectionStart(), getTextPane().getSelectionEnd());
        getTextPane().setDocument(doc);
    }

    /**
     * This method sets the selection to Italic
     */
    @FXML
    private void setTextItalic() {
        StyledDocument doc = getDocument().formatText((StyledDocument) getTextPane().getDocument(), Tag.CURSIVE, getTextPane().getSelectionStart(), getTextPane().getSelectionEnd());
        getTextPane().setDocument(doc);
    }

    /**
     * This method sets the selection to monospace
     */
    @FXML
    private void setTextMonospace() {
        StyledDocument doc = getDocument().formatText((StyledDocument) getTextPane().getDocument(), Tag.MONOSPACE, getTextPane().getSelectionStart(), getTextPane().getSelectionEnd());
        getTextPane().setDocument(doc);
    }

    /**
     * This method sets the size of the text
     */
    @FXML
    private void setTextFontSize() {
        int fontSizeInt = 0;
        try {
            //Trying to cast the user input
            String fontSizeStr = fontSize.getEditor().getText();
            if (!fontSizeStr.isBlank()) {
                fontSizeInt = Integer.parseInt(fontSizeStr);
            }
        } catch (NumberFormatException e) {
            showAlertMessage(MessageEnum.ALERT_INVALID_FONT_SIZE);
            fontSize.valueProperty().setValue(null);
        }

        StyledDocument doc = getDocument().formatTextFont((StyledDocument) getTextPane().getDocument(), getTextPane().getSelectionStart(), getTextPane().getSelectionEnd(), fontSizeInt, "");
        getTextPane().setDocument(doc);
    }

    /**
     * This method sets the font family of the text
     */
    @FXML
    private void setTextFontFamily() {
        String fontFamilyStr = "";
        try {
            fontFamilyStr = fontFamily.getSelectionModel().getSelectedItem().toString();
        } catch (NullPointerException e) {
            //Not important we just ignore the font family
        } finally {
            StyledDocument doc = getDocument().formatTextFont((StyledDocument) getTextPane().getDocument(), getTextPane().getSelectionStart(), getTextPane().getSelectionEnd(), 0, fontFamilyStr);
            getTextPane().setDocument(doc);
        }
    }

    /**
     * This method gets called when a user clicks in the text
     * The fontsize and type need to be adjusted in the GUI
     * The left character of the cursor is taken as reference
     * If there is a selection the format needs to match in order to set the properties to the GUI
     */
    private void displayFontPropChanges() {
        //Ensure that this code is gonna be executed in the java fx thread and not in the awt thread
        Platform.runLater(() -> {

            int selStart = getTextPane().getSelectionStart();
            int selEnd = getTextPane().getSelectionEnd();
            StyledDocument doc = (StyledDocument) getTextPane().getDocument();

            //read first char as reference
            Element e = doc.getCharacterElement(selStart);
            AttributeSet attributeSet = e.getAttributes();
            int fontSizeInt = StyleConstants.getFontSize(attributeSet);
            String fontFamilyStr = StyleConstants.getFontFamily(attributeSet);

            //Loop through the rest
            for (int i = 0; i < (selEnd - selStart); i++) {
                e = doc.getCharacterElement(selStart + i);
                attributeSet = e.getAttributes();
                if (fontSizeInt != StyleConstants.getFontSize(attributeSet)) {
                    fontSizeInt = 0;
                }

                String tmpF = StyleConstants.getFontFamily(attributeSet);
                if (!fontFamilyStr.equals(tmpF)) {
                    fontFamilyStr = "";
                }
            }

            if (fontSizeInt == 0) {
                fontSize.setValue("");
            } else {
                fontSize.setValue(fontSizeInt);
            }
            fontFamily.setValue(fontFamilyStr);
        });
    }

    /**
     * This method toggles the markdown view
     * In the markdown view you can't use any GUI elements for styling
     */
    @FXML
    private void toggleMarkdown() {
        if (isMarkdownShowed()) {
            getDocument().setFileContent(getTextPane().getText());
            try {
                getDocument().updateFormattingList();
                getTextPane().setText("");
                getTextPane().setText(getDocument().getFileContent());
                applyMarkdownText((StyledDocument) getTextPane().getDocument());
                changeGUIState(true);
                setMarkdownShowed(false);
                //Show normal text & enable GUI elements
                markdownButton.setText(MessageEnum.BUTTON_TEXT_SHOW_MARKDOWN.toString());
            } catch (DamagedFileException e) {
                showAlertMessage(MessageEnum.ALERT_MARKDOWN_INVALID);
            }
        } else {
            //Show markdown & disable GUI elements
            markdownButton.setText(MessageEnum.BUTTON_TEXT_SHOW_FORMATTED_TEXT.toString());
            String markdownText = getDocument().getMarkdownContent((StyledDocument) getTextPane().getDocument());
            getTextPane().setText("");
            MutableAttributeSet mas = getTextPane().getInputAttributes();

            //remove all current formatting
            mas.removeAttribute(StyleConstants.Bold);
            mas.removeAttribute(StyleConstants.Italic);
            mas.removeAttribute(StyleConstants.Underline);
            mas.removeAttribute(StyleConstants.StrikeThrough);
            mas.removeAttribute(StyleConstants.FontFamily);
            mas.removeAttribute(StyleConstants.FontSize);

            getTextPane().setText(markdownText);
            changeGUIState(false);
            setMarkdownShowed(true);
        }
    }

    /**
     * Enables or disables the GUI parts which are responsible for formatting
     *
     * @param enable should the GUI gets enabled?
     */
    private void changeGUIState(boolean enable) {
        boldButton.setDisable(!enable);
        strikethroughButton.setDisable(!enable);
        underlineButton.setDisable(!enable);
        italicButton.setDisable(!enable);
        fontSize.setDisable(!enable);
        fontFamily.setDisable(!enable);
        monospaceButton.setDisable(!enable);
    }
}
