package ch.zhaw.pm2.supercoolmarkdown;

import ch.zhaw.pm2.supercoolmarkdown.model.Tag;
import ch.zhaw.pm2.supercoolmarkdown.view.WelcomeWindowUI;
import javafx.application.Application;

import javax.swing.UIManager;

/**
 * Contains main function for this super cool app
 *
 * @author Defiljon
 * @version 12.05.2021
 */
public class SuperCoolMarkdown {

    /**
     * Main function for the super cool app
     *
     * @param args args
     */
    public static void main(String[] args) {
        try { // Gives the fileChooser a better look
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        //init the Tag enum
        Tag.initTags();

        // Start UI
        System.out.println("Main UI starting.");
        Application.launch(WelcomeWindowUI.class, args);
        System.out.println("App ended");
    }
}

