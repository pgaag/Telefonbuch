package ui;

import interfaces.JsonInterface;
import interfaces.LoadInterface;
import interfaces.SaveInterface;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;

public class SaveAndLoadMenuBar {
    private final MenuBar bar = new MenuBar();
    private final Menu menu = new Menu("File");
    private MenuItem saveMenuItem = new MenuItem("Save");
    private MenuItem loadMenuItem = new MenuItem("Load");


    public SaveAndLoadMenuBar(JsonInterface jsonInterface, LoadInterface loadInterface, SaveInterface saveInterface) {

        menu.getItems().add(saveMenuItem);
        menu.getItems().add(loadMenuItem);

        loadMenuItem.setOnAction(actionEvent -> loadTelefonBook(jsonInterface, loadInterface));
        saveMenuItem.setOnAction(actionEvent -> saveTelefonBook(saveInterface));

        bar.getMenus().add(menu);
    }

    private void saveTelefonBook(SaveInterface saveInterface) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JSON", "*.json")
        );
        // this fileChooser needs a stage as owner
        Window owner = Stage.getWindows().stream()
                .filter(Window::isShowing)
                .findFirst()
                .get();
        File dest = fileChooser.showSaveDialog(owner);
        saveInterface.saveTelefonBook(dest);

    }

    private void loadTelefonBook(JsonInterface jsonInterface, LoadInterface loadInterface) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Telefonbook");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JSON", "*.json"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );
        // this fileChooser needs a stage as owner
        Window owner = Stage.getWindows().stream()
                .filter(Window::isShowing)
                .findFirst()
                .orElse(null);
        File file = fileChooser.showOpenDialog(owner);
        if(file != null)
            loadInterface.loadTelefonBook(jsonInterface.getEntriesFromFile(file));
    }


    public MenuBar getMenuBar(){
        return this.bar;
    }


}
