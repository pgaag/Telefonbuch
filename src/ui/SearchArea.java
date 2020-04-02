package ui;

import data.TelefonBook;
import data.TelefonEntry;
import javafx.collections.transformation.FilteredList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class SearchArea {

    private final AnchorPane anchorPane = new AnchorPane();
    private final TextField searchTextField = new TextField();
    private final Button searchButton = new Button("Suchen");
    private TelefonBook telefonBook;

    public SearchArea(TelefonBook telefonBook) {
        searchButton.setPrefSize(80.0, 20.0);

        setTelefonBook(telefonBook);

        AnchorPane.setLeftAnchor(searchTextField, 10.0);
        AnchorPane.setTopAnchor(searchTextField, 10.0);
        AnchorPane.setRightAnchor(searchTextField, 100.0);
        AnchorPane.setBottomAnchor(searchTextField, 10.0);

        AnchorPane.setTopAnchor(searchButton, 10.0);
        AnchorPane.setRightAnchor(searchButton, 10.0);
        AnchorPane.setBottomAnchor(searchButton, 10.0);

        searchButton.setOnAction(event -> searchButtonClicked());

        anchorPane.getChildren().addAll(searchTextField, searchButton);
    }

    private void searchButtonClicked() {
        String filter = this.searchTextField.getText().toLowerCase();
        if(filter.length() == 0) {
            this.telefonBook.getFilteredList().setPredicate(s -> true);
        }
        else {
            this.telefonBook.getFilteredList().setPredicate(s -> s.getFirstName().toLowerCase().contains(filter) ||s.getLastName().toLowerCase().contains(filter) || s.getNumber().toLowerCase().contains(filter));
        }
    }

    public Node getPane() {
        return anchorPane;
    }

    public TelefonBook getTelefonBook() {
        return telefonBook;
    }

    public void setTelefonBook(TelefonBook telefonBook) {
        this.telefonBook = telefonBook;
    }
}