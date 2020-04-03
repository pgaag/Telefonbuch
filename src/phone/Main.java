package phone;

import data.TelefonBook;
import data.TelefonEntry;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ui.AddArea;
import ui.EntryArea;
import ui.SearchArea;

import java.util.ArrayList;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        BorderPane root = new BorderPane();

        var list = new ArrayList<TelefonEntry>();
        list.add(new TelefonEntry("Hallo", "Test", "12345"));
        list.add(new TelefonEntry("Rosie", "Rosie", "32168"));
        list.add(new TelefonEntry("The", "Beast", "666"));

        ObservableList<TelefonEntry> telefonEntries = FXCollections.observableList(list);
        var telefonBook = new TelefonBook(telefonEntries);


        EntryArea entryArea = new EntryArea(telefonBook);
        SearchArea searchArea = new SearchArea(telefonBook);
        AddArea addArea = new AddArea(telefonBook);

        root.setTop(searchArea.getPane());
        root.setCenter(addArea.getPane());
        root.setBottom(entryArea.getAnchorPane());

        primaryStage.setTitle("Telefonbuch");
        primaryStage.setScene(new Scene(root, 400, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}