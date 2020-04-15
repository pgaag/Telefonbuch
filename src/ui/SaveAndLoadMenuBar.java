package ui;

import data.TelefonBook;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class SaveAndLoadMenuBar {
    private final MenuBar bar = new MenuBar();
    private final Menu menu = new Menu("File");
    private MenuItem saveMenuItem = new MenuItem("Save");
    private MenuItem loadMenuItem = new MenuItem("Load");
    private TelefonBook telefonBook;


    public SaveAndLoadMenuBar(TelefonBook telefonBook) {
        setTelefonBook(telefonBook);

        menu.getItems().add(saveMenuItem);
        menu.getItems().add(loadMenuItem);
        
        bar.getMenus().add(menu);
    }

    public MenuItem getSaveMenuItem(){
        return saveMenuItem;
    }
    public MenuItem getLoadMenuItem(){
        return loadMenuItem;
    }

    public MenuBar getMenuBar(){
        return this.bar;
    }


    public void setTelefonBook(TelefonBook telefonBook) {
        this.telefonBook = telefonBook;
    }
}
