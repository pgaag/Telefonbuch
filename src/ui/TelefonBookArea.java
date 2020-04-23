package ui;

import data.TelefonBookInterfaceImplementation;
import interfaces.*;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class TelefonBookArea {

    public BorderPane borderPane = new BorderPane();


    public TelefonBookArea(TelefonBookInterface telefonBookInterface) {

        EntryArea entryArea = new EntryArea(telefonBookInterface::get, telefonBookInterface::delete, telefonBookInterface::add);
        SearchArea searchArea = new SearchArea(telefonBookInterface::search);
        AddArea addArea = new AddArea(telefonBookInterface::add);
        // I opted for a MenuBar to implement the save and load functionality
        SaveAndLoadMenuBar searchAndLoadMenuBar= new SaveAndLoadMenuBar(telefonBookInterface::getEntriesFromFile, telefonBookInterface::loadTelefonBook, telefonBookInterface::saveTelefonBook);

        VBox vBox = new VBox( searchAndLoadMenuBar.getMenuBar(), searchArea.getPane());

        borderPane.setTop(vBox);
        borderPane.setCenter(entryArea.getAnchorPane());
        borderPane.setBottom(addArea.getPane());
    }

    public BorderPane getBorderPane(){
        return this.borderPane;
    }
}
