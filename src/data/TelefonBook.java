package data;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.util.ArrayList;
import java.util.List;

public class TelefonBook {
    private ObservableList<TelefonEntry> telefonEntries;
    private FilteredList<TelefonEntry> filteredList;

    public TelefonBook(ObservableList<TelefonEntry> items){
        setTelefonEntries(items);
        setFilteredList(items.filtered(s -> true));
    }

    public FilteredList<TelefonEntry> getFilteredList() {
        return this.filteredList;
    }

    public void setFilteredList(FilteredList<TelefonEntry> filteredList) {
        this.filteredList = filteredList;
    }

    public ObservableList<TelefonEntry> getTelefonEntries() {
        return this.telefonEntries;
    }

    public void setTelefonEntries(ObservableList<TelefonEntry> telefonEntries) {
        this.telefonEntries = telefonEntries;
        setFilteredList(telefonEntries.filtered(s -> true));
    }

    public void add(TelefonEntry telefonEntry){
        this.telefonEntries.add(telefonEntry);
    }
    public void delete(String firstName, String lastName, String number){
        TelefonEntry telefonEntry = this.telefonEntries.stream()
                .filter((entry) -> entry.getFirstName().equals(firstName) && entry.getLastName().equals(lastName) && entry.getNumber().equals(number))
                .findFirst()
                .orElse(null);
        this.telefonEntries.remove(telefonEntry);
    }
    public void searchAndFilter(String filter){
        if(filter.length() == 0) {
            this.getFilteredList().setPredicate(s -> true);
        }
        else {
            this.getFilteredList().setPredicate(s -> s.getFirstName().toLowerCase().contains(filter) ||s.getLastName().toLowerCase().contains(filter) || s.getNumber().toLowerCase().contains(filter));
        }
    }
    public void loadTelefonEntires(ObservableList<TelefonEntry> telefonEntries){
        this.telefonEntries.setAll(telefonEntries);
        setFilteredList(this.telefonEntries.filtered(s -> true));
    }
    public static TelefonBook createDummyTelefonBook(){
        var list = new ArrayList<TelefonEntry>();
        ObservableList<TelefonEntry> telefonEntries = FXCollections.observableList(list);
        var telefonBook = new TelefonBook(telefonEntries);
        return telefonBook;
    }
}
