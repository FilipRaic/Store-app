package hr.java.production;

import hr.java.production.enums.Cities;
import hr.java.production.model.Factory;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SearchFactoryController {
    @FXML
    private ChoiceBox<String> cityChoiceBox;

    @FXML
    private TableView<Factory> factoryTableView;

    @FXML
    private TableColumn<Factory, String> factoryNameTableColumn;

    @FXML
    private TableColumn<Factory, String> factoryAddressTableColumn;

    @FXML
    private TableColumn<Factory, String> factoryItemsTableColumn;

    @FXML
    public void initialize() {
        for (Cities city : Cities.values())
            cityChoiceBox.getItems().add(city.getCityName());

        factoryNameTableColumn.setCellValueFactory(name -> new SimpleStringProperty(name.getValue().getName()));

        factoryAddressTableColumn.setCellValueFactory(address -> new SimpleStringProperty(address.getValue().getAddress().getCity().getCityName() + ", " + address.getValue().getAddress().getStreet() + " "
                + address.getValue().getAddress().getHouseNumber()));

        factoryItemsTableColumn.setCellValueFactory(items -> new SimpleStringProperty(items.getValue().toString()));

        ObservableList<Factory> factoryObservableList = FXCollections.observableList(Main.factories);
        factoryTableView.setItems(factoryObservableList);
    }

    @FXML
    private void onSearchClick() {
        List<Factory> filteredList = new ArrayList<>(Main.factories);

        if (cityChoiceBox.getValue() != null) {
            filteredList = filteredList.stream()
                    .filter(s -> s.getAddress().getCity().getCityName().equals(cityChoiceBox.getValue()))
                    .collect(Collectors.toList());
        }

        factoryTableView.setItems(FXCollections.observableList(filteredList));
    }

    @FXML
    private void resetChoice() {
        cityChoiceBox.setValue(null);
    }
}
