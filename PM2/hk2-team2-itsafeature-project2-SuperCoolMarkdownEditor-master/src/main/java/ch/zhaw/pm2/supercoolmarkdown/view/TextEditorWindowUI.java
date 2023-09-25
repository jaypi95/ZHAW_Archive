package ch.zhaw.pm2.supercoolmarkdown.view;

import ch.zhaw.pm2.supercoolmarkdown.controller.TextEditorWindowController;
import ch.zhaw.pm2.supercoolmarkdown.model.Document;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * This class handles the opening of the TextEditor opening
 *
 * @author Baachsil
 * @version 12.05.2021
 */
public class TextEditorWindowUI {

    public static final String FXML_FILE_PATH = "TextEditorWindow.fxml";
    public static final String ICON_FILE_PATH = "icon.png";
    private static Stage primaryStage;

    /**
     * This method closes the window
     */
    public static void closeWindow() {
        primaryStage.close();
    }

    /**
     * Configures all properties and opens the TextEditorWindow
     */
    public void openTexteditorWindow(Stage stage, Document document) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML_FILE_PATH));
            BorderPane rootPane = loader.load();

            Scene scene = new Scene(rootPane);
            stage.getIcons().add(new Image(WelcomeWindowUI.class.getResourceAsStream(ICON_FILE_PATH)));
            primaryStage = stage;

            // configure and show stage
            primaryStage.setScene(scene);
            primaryStage.setTitle("SuperCoolMarkdownEditor");

            displayIcons(scene);

            //load the document to the controller
            TextEditorWindowController controller = loader.getController();
            controller.setDocument(document);

            primaryStage.setOnCloseRequest(event -> {
                boolean canCloseWindow = controller.handleNeedToSave();
                if (canCloseWindow) {
                    WelcomeWindowUI.showWindow();
                } else {
                    event.consume();
                }
            });

            //Disable vertically resizing the window
            primaryStage.heightProperty().addListener((o, oldValue, newValue) -> {
                if (newValue.intValue() != oldValue.intValue()) {
                    primaryStage.setResizable(false);
                    primaryStage.setHeight(530);
                    primaryStage.setResizable(true);
                }
            });

            //restrict horizontal resizing the window
            primaryStage.widthProperty().addListener((o, oldValue, newValue) -> {
                if (newValue.intValue() < 500) {
                    primaryStage.setResizable(false);
                    primaryStage.setWidth(500);
                    primaryStage.setResizable(true);
                }
            });

            primaryStage.show();
        } catch (Exception e) {
            //This error can't be handled
            e.printStackTrace();
            Platform.exit();
        }
    }

    /**
     * Adds Icons to the buttons
     *
     * @param scene the scene in which the buttons are located
     */
    private void displayIcons(Scene scene) {
        try {
            ClassLoader classLoader = this.getClass().getClassLoader();

            //Set Icon to Bold Button
            Button boldButton = (Button) scene.lookup("#boldButton");
            boldButton.setGraphic(new ImageView(new Image(classLoader.getResource("bold.png").toExternalForm(), 16, 16, false, false)));

            //Set Icon to Italic Button
            Button italicButton = (Button) scene.lookup("#italicButton");
            italicButton.setGraphic(new ImageView(new Image(classLoader.getResource("italic.png").toExternalForm(), 16, 16, false, false)));

            //Set Icon to underline Button
            Button underlineButton = (Button) scene.lookup("#underlineButton");
            underlineButton.setGraphic(new ImageView(new Image(classLoader.getResource("underline.png").toExternalForm(), 16, 16, false, false)));

            //Set Icon to strikethrough Button
            Button strikethroughButton = (Button) scene.lookup("#strikethroughButton");
            strikethroughButton.setGraphic(new ImageView(new Image(classLoader.getResource("strikethrough.png").toExternalForm(), 16, 16, false, false)));

            //Set Icon to monospace Button
            Button monospaceButton = (Button) scene.lookup("#monospaceButton");
            monospaceButton.setGraphic(new ImageView(new Image(classLoader.getResource("monospace.png").toExternalForm(), 16, 16, false, false)));
        } catch (Exception e) {
            System.err.println("Error while trying to fetch the Icons!");
        }
    }
}
