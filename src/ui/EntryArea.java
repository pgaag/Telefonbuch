package ui;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import interfaces.AddInterface;
import data.TelefonEntry;
import interfaces.DeleteInterface;
import interfaces.GetInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

import java.util.List;

public class EntryArea {
    private final AnchorPane anchorPane = new AnchorPane();
    private final TableView<TelefonEntry> tableView;


    public EntryArea(GetInterface getInterface, DeleteInterface deleteInterface, AddInterface addInterface) {
        tableView = new TableView<>();
        AnchorPane.setLeftAnchor(tableView, 10.0);
        AnchorPane.setRightAnchor(tableView, 10.0);
        AnchorPane.setTopAnchor(tableView, 0.0);
        AnchorPane.setBottomAnchor(tableView, 0.0);
        anchorPane.getChildren().addAll(tableView);
        anchorPane.setPadding(new Insets(0,0,10.0,0));

        Callback<TableColumn<TelefonEntry, String>, TableCell<TelefonEntry, String>> cellFactory = p -> new EditingCell();

        TableColumn<TelefonEntry, String> lastNameCol = new TableColumn<>("Last Name");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        lastNameCol.setCellFactory(cellFactory);
        lastNameCol.setOnEditCommit(t -> getCurrentRow(t).setLastName(t.getNewValue()));

        TableColumn<TelefonEntry, String> firstNameCol = new TableColumn<>("First Name");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        firstNameCol.setCellFactory(cellFactory);
        firstNameCol.setOnEditCommit(t -> getCurrentRow(t).setFirstName(t.getNewValue()));

        TableColumn<TelefonEntry, String> emailCol = new TableColumn<>("Number");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("number"));
        emailCol.setCellFactory(cellFactory);
        emailCol.setOnEditCommit(t -> getCurrentRow(t).setNumber(t.getNewValue()));

        tableView.getColumns().add(firstNameCol);
        tableView.getColumns().add(lastNameCol);
        tableView.getColumns().add(emailCol);
        tableView.setItems(getInterface.get());
        tableView.setEditable(true);
        tableView.setOnKeyPressed(keyEvent -> keyPressed(keyEvent, deleteInterface));
        tableView.setOnDragDetected(event -> handleDragDetection(event));
        tableView.setOnDragOver(event -> handleTextDragOver(event));
        tableView.setOnDragDropped(event -> setOnDragDropped(event, addInterface, getInterface));
    }

    private void handleTextDragOver(DragEvent event) {
        if (event.getDragboard().hasString()){
            event.acceptTransferModes(TransferMode.ANY);
        }
        event.consume();
    }

    private void handleDragDetection(MouseEvent event){
        TelefonEntry selected = tableView.getSelectionModel().getSelectedItem();
        if(selected != null){
            Dragboard dragboard = tableView.startDragAndDrop(TransferMode.ANY);
            ClipboardContent content = new ClipboardContent();
            ObjectMapper mapper = new ObjectMapper();
            String string = null;
            try {
                string = mapper.writeValueAsString(selected);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            content.putString(string);
            dragboard.setContent(content);
        }
        event.consume();
    }

    private void setOnDragDropped(DragEvent event, AddInterface addInterface, GetInterface getInterface){
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (event.getDragboard().hasString()) {
            String draggedString = db.getString();
            ObjectMapper mapper = new ObjectMapper();
            TelefonEntry entry = null;
            try {
                entry = mapper.readValue(draggedString, TelefonEntry.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            addInterface.add(entry);
            tableView.setItems(getInterface.get());
            success = true;
        }
        event.setDropCompleted(success);
        event.consume();
    }
    

    private void keyPressed(KeyEvent keyEvent, DeleteInterface deleteInterface){
        var selection = tableView.getSelectionModel().getSelectedItem();
        if (selection != null) {
            if ( keyEvent.getCode().equals( KeyCode.DELETE ) ){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete " + selection.getFirstName() + " " + selection.getLastName() + " Tel.: " + selection.getNumber() + " ?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.YES) {
                    deleteInterface.delete(selection.getFirstName(), selection.getLastName(), selection.getNumber());
                }
            }
        }
    }

    public void setItems(List<TelefonEntry> items) {
        if (items instanceof ObservableList) {
            tableView.setItems((ObservableList<TelefonEntry>) items);
        } else {
            tableView.setItems(FXCollections.observableList(items));
        }
    }

    public AnchorPane getAnchorPane() {
        return anchorPane;
    }


private static class EditingCell extends TableCell<TelefonEntry, String> {

    private TextField textField;

    private EditingCell() {
    }

    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            createTextField();
            setText(null);
            setGraphic(textField);
            textField.selectAll();
        }
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();

        setText(getItem());
        setGraphic(null);
    }

    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (textField != null) {
                    textField.setText(getString());
                }
                setText(null);
                setGraphic(textField);
            } else {
                setText(getString());
                setGraphic(null);
            }
        }
    }

    private void createTextField() {
        textField = new TextField(getString());
        textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
        textField.focusedProperty().addListener((arg0, arg1, arg2) -> {
            if (!arg2) {
                commitEdit(textField.getText());
            }
        });
    }

    private String getString() {
        return getItem() == null ? "" : getItem();
    }

}

    private static TelefonEntry getCurrentRow(TableColumn.CellEditEvent<TelefonEntry, String> t) {
        return t.getTableView().getItems().get(t.getTablePosition().getRow());
    }



}