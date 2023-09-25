package ch.zhaw.pm2.supercoolmarkdown.controller;

import ch.zhaw.pm2.supercoolmarkdown.model.Document;
import ch.zhaw.pm2.supercoolmarkdown.view.TextEditorWindowUI;
import ch.zhaw.pm2.supercoolmarkdown.view.WelcomeWindowUI;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

import java.net.URI;
import java.util.List;

/**
 * This is the MenuController which handles everything related to menus in the main TextEditorWindow
 *
 * @author baachsil
 * @version 1.0
 */
public class TextEditorMenuController extends SuperTextEditorWindowController {

    public static final String HELP_URL = "https://github.zhaw.ch/PM2-IT20taWIN-bles-mach-kars/hk2-team2-itsafeature-project2-SuperCoolMarkdownEditor";
    private final MenuBar menuBar;
    private final Menu menu_open_recent;

    /**
     * Init the TextEditorMenuController
     *
     * @param menuBar          the mainMenubar of the Window
     * @param menu_open_recent the special submenu in which further submenus gonna be generated. Just for convenience
     */
    public TextEditorMenuController(MenuBar menuBar, Menu menu_open_recent) {
        this.menuBar = menuBar;
        this.menu_open_recent = menu_open_recent;
    }

    /**
     * This method creates the submenus for open recent
     */
    protected void createSubMenus() {
        List<Document> recentOpenedFiles = getRecentFiles().getRecentOpenedFilesList();
        for (Document doc : recentOpenedFiles) {
            MenuItem submenu = new MenuItem(doc.getFileName());
            submenu.setOnAction((event) -> openRecentOpenedFile(doc.getFilePath()));
            menu_open_recent.getItems().add(submenu);
        }
    }

    /**
     * This method sets all the menubar actions needed
     */
    protected void setMenubarActions() {
        // we wanna exit the app without reopening the welcome window
        getMenuBarItem("menuitem_exit").setOnAction((event) -> {
            closeWindow();
            Platform.exit();
        });

        // close the TextEditor window and after show the welcome window again
        getMenuBarItem("menuitem_close_file").setOnAction((event) -> {
            closeWindow();
            WelcomeWindowUI.showWindow();
        });

        // save
        getMenuBarItem("menuitem_save_file").setOnAction((event) -> saveFile());

        // save as
        getMenuBarItem("menuitem_save_as_file").setOnAction((event) -> saveAs());

        //open existing
        getMenuBarItem("menuitem_open").setOnAction((event) -> openExisting());

        //create new
        getMenuBarItem("menuitem_new").setOnAction((event) -> newDocument());

        //help
        getMenuBarItem("menuitem_about").setOnAction((event) -> openHelp());
    }

    /**
     * Returns a MenuItem based on a given id
     *
     * @param id the id of the menuItem
     * @return the menuItem
     */
    private MenuItem getMenuBarItem(String id) {
        ObservableList<Menu> menuList = menuBar.getMenus();

        MenuItem searchedMenu = null;
        for (Menu menu : menuList) {
            ObservableList<MenuItem> menuItemList = menu.getItems();

            for (MenuItem m : menuItemList) {
                if (id.equals(m.getId())) {
                    searchedMenu = m;
                }
            }
        }
        return searchedMenu;
    }

    /**
     * This method open the readme on github to help the user
     */
    private void openHelp() {
        try {
            URI uri = new URI(HELP_URL);
            java.awt.Desktop.getDesktop().browse(uri);
        } catch (Exception e) {
            System.err.println("Error while trying to open the browser!");
        }
    }

    /**
     * This method closes the Window
     * If the Document is not saved, ask the user if he wants to save it before closing the window
     */
    private void closeWindow() {
        handleNeedToSave();
        TextEditorWindowUI.closeWindow();
    }
}
