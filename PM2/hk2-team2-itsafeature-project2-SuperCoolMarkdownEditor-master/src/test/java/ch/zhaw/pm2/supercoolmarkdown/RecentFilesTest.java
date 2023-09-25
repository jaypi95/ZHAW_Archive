package ch.zhaw.pm2.supercoolmarkdown;

import ch.zhaw.pm2.supercoolmarkdown.model.Document;
import ch.zhaw.pm2.supercoolmarkdown.model.RecentFiles;
import ch.zhaw.pm2.supercoolmarkdown.model.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class tests the RecentFiles class
 *
 * @author Outitbad
 * @version 14.05.2021
 */

public class RecentFilesTest {
    private RecentFiles recentFiles;

    private File file1;
    private File file2;
    private File file3;
    private File file4;
    private File file5;
    private File file6;
    private File file7;

    private Document doc1;
    private Document doc2;
    private Document doc3;
    private Document doc4;
    private Document doc5;
    private Document doc6;
    private Document doc7;

    @BeforeEach
    void setup() {
        Tag.initTags();
        recentFiles = new RecentFiles();

        //Remove old files that may be lying around from earlier program runs
        File recentsFile = new File("build/classes/java/main/" + RecentFiles.STORAGE_FILE_NAME);
        if(recentsFile.exists()){
            recentsFile.delete();
        }
    }

    /**
     * Test if the recentOpenFileList can add a Document
     */
    @Test
    void addRecentOpenedFileAndGetRecentOpenedFilesListTest1File() {

        File file = new File("src/main/java/test1.txt");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            file.delete();
        }
        doc1 = new Document();
        doc1.setFilePath(file.getPath());
        recentFiles.addRecentOpenedFile(doc1);


        assertEquals(1, recentFiles.getRecentOpenedFilesList().size());

        file.delete();
    }

    /**
     * Test if the recentOpenFileList can add up to 6 Documents
     */
    @Test
    void addRecentOpenedFileAndGetRecentOpenedFilesListTest6Files() {
        initSevenDocAndFiles();


        recentFiles.addRecentOpenedFile(doc1);
        recentFiles.addRecentOpenedFile(doc2);
        recentFiles.addRecentOpenedFile(doc3);
        recentFiles.addRecentOpenedFile(doc4);
        recentFiles.addRecentOpenedFile(doc5);
        recentFiles.addRecentOpenedFile(doc6);


        assertEquals(6, recentFiles.getRecentOpenedFilesList().size());
        deleteFiles();
    }

    /**
     * Test if the recentFilesList can not be bigger than 6 Documents
     */
    @Test
    void addRecentOpenedFileAndGetRecentOpenedFilesListTest7Files() {
        initSevenDocAndFiles();

        recentFiles.addRecentOpenedFile(doc1);
        recentFiles.addRecentOpenedFile(doc2);
        recentFiles.addRecentOpenedFile(doc3);
        recentFiles.addRecentOpenedFile(doc4);
        recentFiles.addRecentOpenedFile(doc5);
        recentFiles.addRecentOpenedFile(doc6);
        recentFiles.addRecentOpenedFile(doc7);


        assertEquals(6, recentFiles.getRecentOpenedFilesList().size());
        deleteFiles();
    }

    /**
     * Test if the Document gets removed from the list
     */
    @Test
    void removeRecentDocumentTest1File() {
        initSevenDocAndFiles();
        recentFiles.addRecentOpenedFile(doc1);
        assertEquals(1, recentFiles.getRecentOpenedFilesList().size());
        recentFiles.removeRecentDocument(doc1.getFilePath());
        assertEquals(0, recentFiles.getRecentOpenedFilesList().size());


        deleteFiles();
    }

    /**
     * Test if the document in the list does not get removed if the filepath is wrong
     */
    @Test
    void removeRecentDocumentTestWrongFilePath() {
        initSevenDocAndFiles();
        recentFiles.addRecentOpenedFile(doc1);
        recentFiles.removeRecentDocument(doc2.getFilePath());

        assertEquals(1, recentFiles.getRecentOpenedFilesList().size());


        deleteFiles();
    }


    /**
     * Initialize 7 Document and files
     * www.init7.net <-- shilling for the best ISP
     */
    private void initSevenDocAndFiles() {
        file1 = new File("src/main/java/test1.txt");
        file2 = new File("src/main/java/test2.txt");
        file3 = new File("src/main/java/test3.txt");
        file4 = new File("src/main/java/test4.txt");
        file5 = new File("src/main/java/test5.txt");
        file6 = new File("src/main/java/test6.txt");
        file7 = new File("src/main/java/test7.txt");

        doc1 = new Document();
        doc2 = new Document();
        doc3 = new Document();
        doc4 = new Document();
        doc5 = new Document();
        doc6 = new Document();
        doc7 = new Document();

        try {
            file1.createNewFile();
            file2.createNewFile();
            file3.createNewFile();
            file4.createNewFile();
            file5.createNewFile();
            file6.createNewFile();
            file7.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        doc1.setFilePath(file1.getPath());
        doc2.setFilePath(file2.getPath());
        doc3.setFilePath(file3.getPath());
        doc4.setFilePath(file4.getPath());
        doc5.setFilePath(file5.getPath());
        doc6.setFilePath(file6.getPath());
        doc7.setFilePath(file7.getPath());

    }

    /**
     * Delete the 7 Files
     */
    private void deleteFiles() {
        file1.delete();
        file2.delete();
        file3.delete();
        file4.delete();
        file5.delete();
        file6.delete();
        file7.delete();
    }
}
