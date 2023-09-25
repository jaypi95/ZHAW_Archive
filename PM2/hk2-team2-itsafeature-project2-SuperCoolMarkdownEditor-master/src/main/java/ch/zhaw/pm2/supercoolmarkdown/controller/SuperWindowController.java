package ch.zhaw.pm2.supercoolmarkdown.controller;

import ch.zhaw.pm2.supercoolmarkdown.MessageEnum;
import ch.zhaw.pm2.supercoolmarkdown.exceptions.DamagedFileException;
import ch.zhaw.pm2.supercoolmarkdown.model.Document;
import ch.zhaw.pm2.supercoolmarkdown.model.RecentFiles;
import ch.zhaw.pm2.supercoolmarkdown.view.WelcomeWindowUI;
import javafx.scene.control.Alert;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * This is the super class for the windowControllers
 * It handles how to get, open and save a file
 *
 * @author Baachsil
 * @version 11.05.2021
 */
public abstract class SuperWindowController {

    private final RecentFiles recentFiles;
    private final JFileChooser fileChooser;

    /**
     * Init recentFiles and set the fileChooser
     */
    public SuperWindowController() {
        recentFiles = new RecentFiles();
        fileChooser = new JFileChooser();
        fileChooser.setDragEnabled(false);
        fileChooser.setFileHidingEnabled(true);
        fileChooser.setMultiSelectionEnabled(false);
        FileFilter filter = new FileNameExtensionFilter("TXT Files", "txt");
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        fileChooser.setFileFilter(filter);
        fileChooser.setAcceptAllFileFilterUsed(false);
    }

    /**
     * This method lets the user open an existing file
     */
    protected Document openExistingFile(String path) {
        Document document = null;
        try {
            document = new Document(path);
            WelcomeWindowUI.closeWindow();
            recentFiles.addRecentOpenedFile(document);
        } catch (FileNotFoundException e) {
            showAlertMessage(MessageEnum.FILE_NOT_FOUND);
        } catch (DamagedFileException e) {
            showAlertMessage(MessageEnum.DAMAGED_FILE);
        } catch (IOException e) {
            showAlertMessage(MessageEnum.FILE_IO_ERROR);
        } catch (NullPointerException e) {
            System.err.println("Error: document is null. Continuing...");
        }
        return document;
    }

    /**
     * Creates and displays an error message
     *
     * @param message the message which should get displayed
     */
    protected void showAlertMessage(MessageEnum message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(MessageEnum.ALERT_TITLE.toString());
        alert.setHeaderText(message.toString());
        alert.showAndWait();
    }

    /**
     * @return the recentFiles object
     */
    public RecentFiles getRecentFiles() {
        return recentFiles;
    }


    /**
     * Lets the user select a file he wants to open
     *
     * @return the filePath or empty if none was selected
     */
    protected String selectAFile() {
        //FileChooser Setup
        fileChooser.setDialogTitle(MessageEnum.FILECHOOSER_ASK_FOR_FILE.toString());

        int result = fileChooser.showOpenDialog(fileChooser.getParent());

        String filePath;
        if (result == JFileChooser.APPROVE_OPTION) {
            filePath = fileChooser.getSelectedFile().getAbsolutePath();
        } else {
            filePath = null;
        }

        return filePath;
    }

    /**
     * Lets the user select a dir and a file name
     *
     * @return the filePath
     */
    protected String selectASavePath() {
        //FileChooser Setup
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        fileChooser.setDialogTitle(MessageEnum.FILECHOOSER_ASK_FOR_DIR.toString());

        int result = fileChooser.showSaveDialog(fileChooser.getParent());

        String filePath = "";
        if (result == JFileChooser.APPROVE_OPTION) {
            filePath = fileChooser.getSelectedFile().getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".txt")) {
                filePath += ".txt";
            }
        }
        return filePath;
    }
}
