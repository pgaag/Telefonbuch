package phone;

import data.JsonFileSystemDataHandler;
import data.TelefonBook;
import data.TelefonBookInterfaceImplementation;
import data.TelefonEntry;
import interfaces.JsonInterface;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ui.*;


import java.beans.EventHandler;
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

        TelefonBookInterfaceImplementation impl = new TelefonBookInterfaceImplementation(telefonBook);



        TelefonbookArea telefonbookArea = new TelefonbookArea(
                telefonBook::getFilteredList,
                telefonBook::delete,
                telefonBook::searchAndFilter,
                telefonBook::add,
                jsonDataHandler::loadTelefonBook,
                loadedEntries -> telefonBook.loadTelefonEntires(FXCollections.observableList(loadedEntries)),
                saveDest -> jsonDataHandler.saveTelefonBook(saveDest, telefonBook.getTelefonEntries())
                );


        root.setRight(abc().getBorderPane());
        root.setCenter(telefonbookArea.getBorderPane());



        Scene mainScene = new Scene(root, 400, 275);

        primaryStage.setTitle("Telefonbuch");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }


    private TelefonbookArea abc() {
        var telefonBook = TelefonBook.createDummyTelefonBook();
        var jsonDataHandler = new JsonFileSystemDataHandler();
        var newTelefonBookArea = new TelefonbookArea(
                telefonBook::getFilteredList,
                telefonBook::delete,
                telefonBook::searchAndFilter,
                telefonBook::add,
                jsonDataHandler::loadTelefonBook,
                loadedEntries -> telefonBook.loadTelefonEntires(FXCollections.observableList(loadedEntries)),
                saveDest -> jsonDataHandler.saveTelefonBook(saveDest, telefonBook.getTelefonEntries())
        );
        return newTelefonBookArea;
    }

    public static void main(String[] args) {
        launch(args);
    }
}