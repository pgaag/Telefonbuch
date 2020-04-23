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

        var telefonBookImpl = new TelefonBookInterfaceImplementation(telefonBook);
        var secondTelefonBookImpl = new TelefonBookInterfaceImplementation(secondTelefonBook);

        TelefonBookArea mainTelefonBookArea = new TelefonBookArea(telefonBookImpl);
        TelefonBookArea secondTelefonBookArea = new TelefonBookArea(secondTelefonBookImpl);

        root.getChildren().addAll(mainTelefonBookArea.getBorderPane(), secondTelefonBookArea.getBorderPane());

        Scene mainScene = new Scene(root, 1180, 530);

        primaryStage.setTitle("Telefonbuch");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}