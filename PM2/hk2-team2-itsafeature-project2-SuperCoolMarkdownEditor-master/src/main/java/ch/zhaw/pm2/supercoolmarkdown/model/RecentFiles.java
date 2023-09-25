package ch.zhaw.pm2.supercoolmarkdown.model;

import ch.zhaw.pm2.supercoolmarkdown.exceptions.DamagedFileException;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * To display recent opened files we need a txt file along side of the .jar
 * This class handles everything from ensuring the file exists / creating it
 * to append recent opened files to the txt
 * This class is self-sufficient. This means no errors gonna leave this class.
 * If there is any unsolvable data problem the recent opened file list is
 * just empty.
 *
 * @author Baachsil
 * @version 11.05.2021
 */
public class RecentFiles {

    public static final String STORAGE_FILE_NAME = "recentOpenedFiles.txt";
    public static final int MAX_RECENT_FILES = 6;
    private File storageFile;
    private final List<Document> recentOpenedFilesList; // contains the absolute path to the document

    /**
     * The Constructor of this class
     */
    public RecentFiles() {
        recentOpenedFilesList = new ArrayList<>();
        ensureStructureIsValid();
        readRecentOpenedFiles();
        updateStorageFile(); //Update the file in case some documents couldn't be converted
    }

    /**
     * This method checks if the resource file exists
     * If this is not the case it creates the file
     */
    private void ensureStructureIsValid() {
        ClassLoader classLoader = this.getClass().getClassLoader();
        String storageFilePath = "";
        try {
            storageFilePath = classLoader.getResource(STORAGE_FILE_NAME).getPath();
            storageFilePath = storageFilePath.replaceAll("%20", " ");
        } catch (Exception e) {
            storageFilePath = "";
        } finally {
            if (storageFilePath.isBlank()) {
                File jarPath = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath().replaceAll("%20", " "));
                File file = new File(jarPath.getParentFile().getAbsolutePath() + "/" + STORAGE_FILE_NAME);
                try {
                    file.createNewFile();
                    storageFile = file;
                } catch (IOException e) {
                    //Just catch any error - if it doesn't work we can't do anything about it
                    System.err.println("Error while trying to create the storageFile!");
                }
            } else {
                storageFile = new File(storageFilePath);
            }
        }
    }

    /**
     * This method reads all recent opened Files and converts them into
     * documents
     */
    private void readRecentOpenedFiles() {
        try {
            String storageFileContent = Files.readString(storageFile.toPath(), StandardCharsets.UTF_8);

            for (String documentPath : storageFileContent.strip().split("\n")) {
                if (!documentPath.isBlank()) {
                    try {
                        recentOpenedFilesList.add(new Document(documentPath));
                    } catch (IOException | DamagedFileException ignored) {
                    }
                }
            }
        } catch (IOException e) {
            //Just ignore it - nothing we can do about it
            System.err.println("Error while trying to read the storage File: " + e);
        }
    }

    /**
     * Adds a new opened File
     * If the MAX_RECENT_FILES is reached the last will be deleted
     * If the File is already in the list put it on top
     *
     * @param document the document which got opened
     */
    public void addRecentOpenedFile(Document document) {
        // if the document is not already present in the recent files
        if (recentOpenedFilesList.stream().noneMatch(doc -> doc.getFilePath().equals(document.getFilePath()))) {
            recentOpenedFilesList.add(0, document);
            if (recentOpenedFilesList.size() > MAX_RECENT_FILES) {
                recentOpenedFilesList.remove(recentOpenedFilesList.size() - 1); //Remove the last entry
            }
            updateStorageFile();
        }
    }

    /**
     * @return the list with all recent opened files
     */
    public List<Document> getRecentOpenedFilesList() {
        recentOpenedFilesList.clear();
        readRecentOpenedFiles();
        return recentOpenedFilesList;
    }

    /**
     * Writes the whole recentOpenedFilesList in the storageFile
     */
    private void updateStorageFile() {
        try {
            //Firstly clear the File
            new PrintWriter(storageFile.getPath()).close();

            PrintWriter printWriter = new PrintWriter(storageFile.getPath());
            for (Document document : recentOpenedFilesList) {
                printWriter.append(document.getFilePath()).append(System.lineSeparator());
            }

            printWriter.flush();
            printWriter.close();

        } catch (Exception e) {
            //Just ignore it - nothing we can do about it
            System.err.println("Error while trying to update the storage File!");
        }
    }

    /**
     * This method removes a recentDocument from the list of recentFiles
     *
     * @param filePath The filepath of the document
     */
    public void removeRecentDocument(String filePath) {
        recentOpenedFilesList.removeIf(document -> document.getFilePath().equals(filePath));
        updateStorageFile();
    }
}
