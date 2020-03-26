package phone;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


public class SearchArea extends Group {

    private TextField textField;
    private Button button;

    public SearchArea(){
        Initialize();
    }

    public void Initialize(){

        this.button = new Button("Print");
        this.button.setLayoutX(160);
        this.button.setLayoutY(0);
        this.button.setPrefHeight(25);
        this.button.setPrefWidth(150);
        this.button.setOnAction(event -> buttonSaveClicked());


        this.textField = new TextField();
        this.textField.setLayoutX(0);
        this.textField.setLayoutY(0);
        this.textField.setPrefHeight(25);
        this.textField.setPrefWidth(150);
        this.getChildren().add(this.button);
        this.getChildren().add(this.textField);

    }
    private void buttonSaveClicked() {
        System.out.println(this.textField.getText());
    }


}
