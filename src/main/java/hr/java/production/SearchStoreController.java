package hr.java.production;

import hr.java.production.model.Store;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.*;
import java.util.stream.Collectors;

public class SearchStoreController {
    @FXML
    private TextField storeItemNameField;

    @FXML
    private TableView<Store> storeTableView;

    @FXML
    private TableColumn<Store, String> storeNameTableColumn;

    @FXML
    private TableColumn<Store, String> storeWebAddressTableColumn;

    @FXML
    private TableColumn<Store, String> storeItemsTableColumn;

    @FXML
    public void initialize() {

        storeNameTableColumn.setCellValueFactory(name -> new SimpleStringProperty(name.getValue().getName()));

        storeWebAddressTableColumn.setCellValueFactory(address -> new SimpleStringProperty(address.getValue().getWebAddress()));

        storeItemsTableColumn.setCellValueFactory(items -> new SimpleStringProperty(items.getValue().toString()));

        ObservableList<Store> storeObservableList = FXCollections.observableList(Main.stores);
        storeTableView.setItems(storeObservableList);
    }

    @FXML
    private void onSearchClick() {
        String enteredName = storeItemNameField.getText();
        List<Store> filteredList = new ArrayList<>(Main.stores);

        if (enteredName.length() > 0) {
            filteredList = filteredList.stream()
                    .filter(s -> s.toString().toLowerCase().contains(enteredName.toLowerCase()))
                    .collect(Collectors.toList());
        }

        storeTableView.setItems(FXCollections.observableList(filteredList));
    }
}
