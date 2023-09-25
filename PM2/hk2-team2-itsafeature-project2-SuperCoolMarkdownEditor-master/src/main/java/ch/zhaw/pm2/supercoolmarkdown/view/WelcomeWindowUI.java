package ch.zhaw.pm2.supercoolmarkdown.view;

import ch.zhaw.pm2.supercoolmarkdown.controller.WelcomeWindowController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * This class handles the opening of the WelcomeWindow opening
 *
 * @author Baachsil
 * @version 12.05.2021
 */
public class WelcomeWindowUI extends Application {

    public static final String FXML_FILE_PATH = "WelcomeWindow.fxml";
    public static final String ICON_FILE_PATH = "icon.png";
    private static Stage primaryStage;
    private static FXMLLoader loader;

    /**
     * This method closes the WelcomeWindow
     */
    public static void closeWindow() {
        primaryStage.close();
    }

    /**
     * This method closes the WelcomeWindow
     */
    public static void showWindow() {
        primaryStage.show();
        WelcomeWindowController controller = loader.getController();
        controller.initialize();
    }

    /**
     * Opens the WelcomeWindow
     *
     * @param stage The primaryStage
     */
    @Override
    public void start(Stage stage) {
        stage.getIcons().add(new Image(WelcomeWindowUI.class.getResourceAsStream(ICON_FILE_PATH)));
        primaryStage = stage;
        openWelcomeWindow(primaryStage);
    }

    /**
     * Configures all properties to open the WelcomeWindow
     *
     * @param primaryStage the stage
     */
    private void openWelcomeWindow(Stage primaryStage) {
        try {
            loader = new FXMLLoader(getClass().getResource(FXML_FILE_PATH));
            Pane rootPane = loader.load();
            Scene scene = new Scene(rootPane);

            // configure and show stage
            primaryStage.setScene(scene);
            primaryStage.setTitle("SuperCoolMarkdown");
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (Exception e) {
            //This error can't be handled
            System.err.println("Error while trying to start the application: ");
            e.printStackTrace();
            Platform.exit();
        }
    }
}
