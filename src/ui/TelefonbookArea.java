package ui;

import data.TelefonBookInterfaceImplementation;
import interfaces.*;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class TelefonbookArea {

    public BorderPane borderPane = new BorderPane();


    public TelefonbookArea(GetInterface getFilteredList, DeleteInterface delete, SearchInterface searchAndFilter, AddInterface add, JsonInterface jsonInterface, LoadInterface loadInterface, SaveInterface saveInterface) {

        EntryArea entryArea = new EntryArea(getFilteredList, delete);
        SearchArea searchArea = new SearchArea(searchAndFilter);
        AddArea addArea = new AddArea(add);
        // I opted for a MenuBar to implement the save and load functionality
        SaveAndLoadMenuBar searchAndLoadMenuBar= new SaveAndLoadMenuBar(jsonInterface, loadInterface, saveInterface);

        VBox vBox = new VBox( searchAndLoadMenuBar.getMenuBar(), searchArea.getPane());

        borderPane.setTop(vBox);
        borderPane.setCenter(entryArea.getAnchorPane());
        borderPane.setBottom(addArea.getPane());
    }

    public BorderPane getBorderPane(){
        return this.borderPane;
    }
}
