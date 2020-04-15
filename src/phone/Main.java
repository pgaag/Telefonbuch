package phone;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.TelefonBook;
import data.TelefonEntry;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ui.AddArea;
import ui.EntryArea;
import ui.SaveAndLoadMenuBar;
import ui.SearchArea;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main extends Application  {

    private Stage primaryStage;
    private TelefonBook telefonBook;


    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        BorderPane root = new BorderPane();

        var list = new ArrayList<TelefonEntry>();
        list.add(new TelefonEntry("Hallo", "Test", "12345"));
        list.add(new TelefonEntry("Rosie", "Rosie", "32168"));
        list.add(new TelefonEntry("The", "Beast", "666"));

        ObservableList<TelefonEntry> telefonEntries = FXCollections.observableList(list);
        var telefonBook = new TelefonBook(telefonEntries);
        this.telefonBook = telefonBook;

        EntryArea entryArea = new EntryArea(PhoneBookEntry -> this.telefonBook.delete(PhoneBookEntry));

        SearchArea searchArea = new SearchArea(PhoneBookEntry -> this.telefonBook.add(
                new TelefonEntry(PhoneBookEntry.getFirstName(), PhoneBookEntry.getLastName(),
                        PhoneBookEntry.getNumber())));

        AddArea addArea = new AddArea(PhoneBookEntry -> this.telefonBook.add(
                new TelefonEntry(PhoneBookEntry.getFirstName(), PhoneBookEntry.getLastName(),
                        PhoneBookEntry.getNumber())));

        // I opted for a MenuBar to implement the save and load functionality
        SaveAndLoadMenuBar searchAndLoadMenuBar= new SaveAndLoadMenuBar(telefonBook);
        searchAndLoadMenuBar.getLoadMenuItem().setOnAction(actionEvent -> loadTelefonBook());
        searchAndLoadMenuBar.getSaveMenuItem().setOnAction(actionEvent -> saveTelefonBook());

        root.setTop(searchArea.getPane());
        root.setCenter(entryArea.getAnchorPane());
        root.setBottom(addArea.getPane());

        VBox vBox = new VBox(searchAndLoadMenuBar.getMenuBar(), root);
        Scene mainScene = new Scene(vBox, 400, 275);

        this.primaryStage.setTitle("Telefonbuch");
        this.primaryStage.setScene(mainScene);
        this.primaryStage.show();
    }

    private void saveTelefonBook() {
        try {
            //generateJsonManually();
            generateJsonWithObjectMapper();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadTelefonBook() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Telefonbook");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JSON", "*.json"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );
        // i felt like the logic for this needed to be in the main class because this fileChooser requires a stage
        File file = fileChooser.showOpenDialog(this.primaryStage);
        if(file != null)
            openFile(Paths.get(file.getPath()));
    }

    private void openFile(Path path) {
        try(InputStream in = Files.newInputStream(path)){
            ObjectMapper mapper = new ObjectMapper();
            // new ArrayList required because Array.asList creates a list without the add-Method implemented, this caused some nasty side effects
            List telefonEntires = new ArrayList(Arrays.asList(mapper.readValue(new File("test.json"), TelefonEntry[].class)));
            updateTelefonBook(telefonEntires);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateTelefonBook(List telefonEntires) {
        this.telefonBook.setTelefonEntries(FXCollections.observableList(telefonEntires));
        entryArea.setItems(this.telefonBook.getFilteredList());
    }

    private void generateJsonManually() throws IOException {
        // http://tutorials.jenkov.com/java-json/jackson-jsongenerator.html
        JsonFactory factory = new JsonFactory();

        JsonGenerator generator = factory.createGenerator(new File("outpoot.json"), JsonEncoding.UTF8);
        generator.writeStartObject();

        generator.writeFieldName("telefonbook");
        generator.writeStartObject();
        generator.writeFieldName("entries");
        generator.writeStartArray();
        for (TelefonEntry entry : this.telefonBook.getTelefonEntries()) {
            generator.writeStartObject();
            generator.writeStringField("first", entry.getFirstName());
            generator.writeStringField("last", entry.getLastName());
            generator.writeStringField("number", entry.getNumber());
            generator.writeEndObject();
        }
        generator.writeEndArray();
        generator.writeEndObject();
        generator.writeEndObject();
        generator.close();
    }
    private void generateJsonWithObjectMapper() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JSON", "*.json")
        );
        File dest = fileChooser.showSaveDialog(this.primaryStage);

        mapper.writeValue(dest, this.telefonBook.getTelefonEntries());
    }

    public static void main(String[] args) {
        launch(args);
    }
}