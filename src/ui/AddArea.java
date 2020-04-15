package ui;

import interfaces.AddInterface;
import data.TelefonBook;
import data.TelefonEntry;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class AddArea{

    private final GridPane gridPane = new GridPane();
    private final AnchorPane anchorPane = new AnchorPane();
    private final TextField firstNameTextField = new TextField();
    private final TextField lastNameTextField = new TextField();
    private final TextField numberTextField = new TextField();
    private final Button addButton  = new Button("Hinzufügen");
    private TelefonBook telefonBook;


    public AddArea(AddInterface addInterface) {
        addButton.setPrefSize(80.0, 20.0);

        firstNameTextField.setPromptText("First name");
        lastNameTextField.setPromptText("Last name");
        numberTextField.setPromptText("Number");

        gridPane.setHgap(10.0);

        GridPane.setColumnIndex(firstNameTextField, 0);
        GridPane.setColumnIndex(lastNameTextField, 1);
        GridPane.setColumnIndex(numberTextField, 2);

        AnchorPane.setLeftAnchor(gridPane, 10.0);
        AnchorPane.setBottomAnchor(gridPane, 10.0);
        AnchorPane.setRightAnchor(gridPane, 100.0);

        AnchorPane.setRightAnchor(addButton, 10.0);
        AnchorPane.setBottomAnchor(addButton, 10.0);

        addButton.setOnAction(event -> addButtonClicked(addInterface));

        gridPane.getChildren().addAll(lastNameTextField, firstNameTextField, numberTextField);
        anchorPane.getChildren().addAll(gridPane, addButton);
    }

    private void addButtonClicked(AddInterface addInterface) {
        var newTelefonEntry = new TelefonEntry(this.firstNameTextField.getText(), this.lastNameTextField.getText(), this.numberTextField.getText());
        addInterface.add(newTelefonEntry);
        clearTextFields();
    }
    private void clearTextFields(){
        this.firstNameTextField.clear();
        this.lastNameTextField.clear();
        this.numberTextField.clear();
    }



    public Node getPane() {
        return anchorPane;
    }


    public void setTelefonBook(TelefonBook telefonBook) {
        this.telefonBook = telefonBook;
    }
}