package phone;

import data.JsonFileSystemDataHandler;
import data.TelefonBook;
import data.TelefonBookInterfaceImplementation;
import data.TelefonEntry;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import ui.*;


import java.util.ArrayList;


public class Main extends Application  {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FlowPane root = new FlowPane();

        var list = new ArrayList<TelefonEntry>();
        list.add(new TelefonEntry("Hallo", "Test", "12345"));
        list.add(new TelefonEntry("Rosie", "Rosie", "32168"));
        list.add(new TelefonEntry("The", "Beast", "666"));

        ObservableList<TelefonEntry> telefonEntries = FXCollections.observableList(list);
        var telefonBook = new TelefonBook(telefonEntries);
        var secondTelefonBook = TelefonBook.createDummyTelefonBook();
        var jsonDataHandler = new JsonFileSystemDataHandler();

        TelefonBookInterfaceImplementation impl = new TelefonBookInterfaceImplementation(telefonBook);




        TelefonBookArea mainTelefonBookArea = new TelefonBookArea(
                telefonBook::getFilteredList,
                telefonBook::delete,
                telefonBook::searchAndFilter,
                telefonBook::add,
                jsonDataHandler::loadTelefonBook,
                loadedEntries -> telefonBook.loadTelefonEntires(FXCollections.observableList(loadedEntries)),
                saveDest -> jsonDataHandler.saveTelefonBook(saveDest, telefonBook.getTelefonEntries())
                );
        TelefonBookArea secondTelefonBookArea = new TelefonBookArea(
                secondTelefonBook::getFilteredList,
                secondTelefonBook::delete,
                secondTelefonBook::searchAndFilter,
                secondTelefonBook::add,
                jsonDataHandler::loadTelefonBook,
                loadedEntries -> telefonBook.loadTelefonEntires(FXCollections.observableList(loadedEntries)),
                saveDest -> jsonDataHandler.saveTelefonBook(saveDest, telefonBook.getTelefonEntries())
        );

        root.getChildren().addAll(mainTelefonBookArea.getBorderPane(), secondTelefonBookArea.getBorderPane());



        Scene mainScene = new Scene(root, 1180, 530);

        primaryStage.setTitle("Telefonbuch");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }


    private TelefonBookArea abc() {
        var telefonBook = TelefonBook.createDummyTelefonBook();
        var jsonDataHandler = new JsonFileSystemDataHandler();
        var newTelefonBookArea = new TelefonBookArea(
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