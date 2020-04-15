package phone;

import data.JsonFileSystemDataHandler;
import data.TelefonBook;
import data.TelefonEntry;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ui.AddArea;
import ui.EntryArea;
import ui.SaveAndLoadMenuBar;
import ui.SearchArea;


import java.util.ArrayList;


public class Main extends Application  {

    @Override
    public void start(Stage primaryStage) throws Exception{
        BorderPane root = new BorderPane();

        var list = new ArrayList<TelefonEntry>();
        list.add(new TelefonEntry("Hallo", "Test", "12345"));
        list.add(new TelefonEntry("Rosie", "Rosie", "32168"));
        list.add(new TelefonEntry("The", "Beast", "666"));

        ObservableList<TelefonEntry> telefonEntries = FXCollections.observableList(list);
        var telefonBook = new TelefonBook(telefonEntries);
        var jsonDataHandler = new JsonFileSystemDataHandler();

        EntryArea entryArea = new EntryArea(telefonBook::getFilteredList, telefonBook::delete);
        SearchArea searchArea = new SearchArea(telefonBook::searchAndFilter);
        AddArea addArea = new AddArea(telefonBook::add);

        // I opted for a MenuBar to implement the save and load functionality
        SaveAndLoadMenuBar searchAndLoadMenuBar= new SaveAndLoadMenuBar(
                jsonDataHandler::loadTelefonBook,
                loadedEntries -> telefonBook.loadTelefonEntires(FXCollections.observableList(loadedEntries)),
                saveDest -> jsonDataHandler.saveTelefonBook(saveDest, telefonBook.getTelefonEntries()));

        root.setTop(searchArea.getPane());
        root.setCenter(entryArea.getAnchorPane());
        root.setBottom(addArea.getPane());

        VBox vBox = new VBox(searchAndLoadMenuBar.getMenuBar(), root);
        Scene mainScene = new Scene(vBox, 400, 275);

        primaryStage.setTitle("Telefonbuch");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}