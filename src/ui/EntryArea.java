package ui;

import data.TelefonBook;
import data.TelefonEntry;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

import java.util.List;

public class EntryArea {
    private final AnchorPane anchorPane = new AnchorPane();
    private final TableView<TelefonEntry> tableView;
    private TelefonBook telefonBook;


    public EntryArea(TelefonBook telefonBook) {
        setTelefonBook(telefonBook);
        tableView = new TableView<>();
        AnchorPane.setLeftAnchor(tableView, 10.0);
        AnchorPane.setRightAnchor(tableView, 10.0);
        AnchorPane.setTopAnchor(tableView, 0.0);
        AnchorPane.setBottomAnchor(tableView, 0.0);
        anchorPane.getChildren().addAll(tableView);

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
        tableView.setItems(telefonBook.getFilteredList());
        tableView.setEditable(true);
        tableView.setOnKeyPressed(keyEvent -> keyPressed(keyEvent));
    }

    private void keyPressed(KeyEvent keyEvent){
        var selection = tableView.getSelectionModel().getSelectedItem();
        if (selection != null) {
            if ( keyEvent.getCode().equals( KeyCode.DELETE ) ){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete " + selection.getFirstName() + " " + selection.getLastName() + " Tel.: " + selection.getNumber() + " ?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
                alert.showAndWait();

                if (alert.getResult() == ButtonType.YES) {
                    var telefonEntry = telefonBook.getTelefonEntry(selection.getFirstName(), selection.getLastName(), selection.getNumber());
                    if(telefonEntry != null)
                        telefonBook.getTelefonEntries().remove(telefonEntry);
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

    public ObservableList<TelefonEntry> getItems() {
        return tableView.getItems();
    }

    public AnchorPane getAnchorPane() {
        return anchorPane;
    }

    public ObservableList<TelefonEntry> getSelectedEntries() {
        return tableView.getSelectionModel().getSelectedItems();
    }

    public TelefonBook getTelefonBook() {
        return telefonBook;
    }

    public void setTelefonBook(TelefonBook telefonBook) {
        this.telefonBook = telefonBook;
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