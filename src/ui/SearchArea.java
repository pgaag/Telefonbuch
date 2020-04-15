package ui;

import interfaces.SearchInterface;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class SearchArea {

    private final AnchorPane anchorPane = new AnchorPane();
    private final TextField searchTextField = new TextField();
    private final Button searchButton = new Button("Suchen");

    public SearchArea(SearchInterface searchInterface) {
        searchButton.setPrefSize(80.0, 20.0);


        AnchorPane.setLeftAnchor(searchTextField, 10.0);
        AnchorPane.setTopAnchor(searchTextField, 10.0);
        AnchorPane.setRightAnchor(searchTextField, 100.0);
        AnchorPane.setBottomAnchor(searchTextField, 10.0);

        AnchorPane.setTopAnchor(searchButton, 10.0);
        AnchorPane.setRightAnchor(searchButton, 10.0);
        AnchorPane.setBottomAnchor(searchButton, 10.0);

        searchButton.setOnAction(event -> searchButtonClicked(searchInterface));

        anchorPane.getChildren().addAll(searchTextField, searchButton);
    }

    private void searchButtonClicked(SearchInterface searchInterface) {
        searchInterface.search(this.searchTextField.getText().toLowerCase());
    }

    public Node getPane() {
        return anchorPane;
    }

}