package data;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsonFileSystemDataHandler {

    public List loadTelefonBook(File file) {
        try(InputStream in = Files.newInputStream(Paths.get(file.getPath()))){
            ObjectMapper mapper = new ObjectMapper();
            // new ArrayList required because Array.asList creates a list without the add-Method implemented, this caused some nasty side effects
            return new ArrayList(Arrays.asList(mapper.readValue(file, TelefonEntry[].class)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void saveTelefonBook(File dest, ObservableList<TelefonEntry> entries) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(dest, entries);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
