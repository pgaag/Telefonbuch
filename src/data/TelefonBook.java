package data;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

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
    }
    public TelefonEntry getTelefonEntry(String firstName, String lastName, String number){
        for (TelefonEntry telefonEntry: this.telefonEntries) {
            if(telefonEntry.getNumber().equals(number) && telefonEntry.getLastName().equals(lastName) && telefonEntry.getFirstName().equals(firstName))
                return telefonEntry;
        }
        return null;
    }
}
