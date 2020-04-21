package data;

import interfaces.TelefonBookInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.util.List;

public class TelefonBookInterfaceImplementation implements TelefonBookInterface {
    private JsonFileSystemDataHandler jsonFileSystemDataHandler = new JsonFileSystemDataHandler();
    private TelefonBook telefonBook;

    public TelefonBookInterfaceImplementation(TelefonBook telefonBook){
        this.telefonBook = telefonBook;
    }

    @Override
    public void add(TelefonEntry entry) {
        this.telefonBook.add(entry);
    }

    @Override
    public void delete(String firstName, String lastName, String number) {
        this.telefonBook.delete(firstName, lastName, number);
    }

    @Override
    public ObservableList<TelefonEntry> get() {
        return this.telefonBook.getFilteredList();
    }

    @Override
    public List getEntriesFromFile(File file) {
        return this.jsonFileSystemDataHandler.loadTelefonBook(file);
    }

    @Override
    public void loadTelefonBook(List entries) {
        this.telefonBook.loadTelefonEntires(FXCollections.observableList(entries));
    }

    @Override
    public void saveTelefonBook(File dest) {
        this.jsonFileSystemDataHandler.saveTelefonBook(dest, this.telefonBook.getTelefonEntries());
    }

    @Override
    public void search(String filter) {
        this.telefonBook.searchAndFilter(filter);
    }
}
