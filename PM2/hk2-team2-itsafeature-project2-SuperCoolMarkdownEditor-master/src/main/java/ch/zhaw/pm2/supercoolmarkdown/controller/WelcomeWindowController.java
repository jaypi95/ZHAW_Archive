package ch.zhaw.pm2.supercoolmarkdown.controller;

import ch.zhaw.pm2.supercoolmarkdown.model.Document;
import ch.zhaw.pm2.supercoolmarkdown.view.TextEditorWindowUI;
import ch.zhaw.pm2.supercoolmarkdown.view.WelcomeWindowUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.util.List;

/**
 * This is the controller class of the WelcomeWindow GUI
 *
 * @author Baachsil
 * @version 11.05.2021
 */
public class WelcomeWindowController extends SuperWindowController {

    @FXML
    private ListView recentFilesList;

    /**
     * Init method which populates the recent files list
     */
    @FXML
    public void initialize() {
        populateRecentFileList();

        //Add the click event
        recentFilesList.setOnMouseClicked(event -> {
            //Only on Double Click
            if (event.getClickCount() == 2) {
                String docPath = recentFilesList.getSelectionModel().getSelectedItem().toString();
                docPath = docPath.split("  |  ")[2];
                Document document = openExistingFile(docPath);
                if (document != null) {
                    openTextEditor(document);
                } else { // remove the document from the recent files
                    getRecentFiles().removeRecentDocument(docPath);
                    populateRecentFileList();
                }
            }
        });
    }

    /**
     * Creates a new Document and opens the TextEditorWindow
     */
    @FXML
    private void createNewFile() {
        WelcomeWindowUI.closeWindow();
        openTextEditor(new Document());
    }

    /**
     * This method lets the user open an existing file
     */
    @FXML
    private void openExistingFile() {
        String filePath = selectAFile();
        if (filePath != null) {
            Document document = openExistingFile(filePath);
            if (document != null) {
                openTextEditor(document);
            }
        }
    }

    /**
     * Opens the TextEditor GUI
     */
    private void openTextEditor(Document document) {
        TextEditorWindowUI textEditorWindowUI = new TextEditorWindowUI();
        textEditorWindowUI.openTexteditorWindow(new Stage(), document);
    }

    /**
     * This method populates the recentFilesList in the GUI based on
     * the list in the RecentFiles class
     */
    private void populateRecentFileList() {
        ObservableList<String> items = FXCollections.observableArrayList();
        List<Document> recentDocuments = getRecentFiles().getRecentOpenedFilesList();

        for (Document document : recentDocuments) {
            items.add(document.getFileName() + "  |  " + document.getFilePath());
        }

        if (items.size() > 0) {
            recentFilesList.setItems(items);
        }
    }
}

