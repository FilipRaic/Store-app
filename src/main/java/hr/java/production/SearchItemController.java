package hr.java.production;

import hr.java.production.model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SearchItemController {
    @FXML
    private TextField itemNameField;

    @FXML
    private ChoiceBox<String> itemCategoryChoiceBox;

    @FXML
    private TableView<Item> itemTableView;

    @FXML
    private TableColumn<Item, String> itemNameTableColumn;

    @FXML
    private TableColumn<Item, String> itemCategoryTableColumn;

    @FXML
    private TableColumn<Item, String> itemWidthTableColumn;

    @FXML
    private TableColumn<Item, String> itemHeightTableColumn;

    @FXML
    private TableColumn<Item, String> itemLengthTableColumn;

    @FXML
    private TableColumn<Item, String> itemProductionCostTableColumn;

    @FXML
    private TableColumn<Item, String> itemSellingPriceTableColumn;

    @FXML
    public void initialize() {
        for (Category cat : Main.categories)
            itemCategoryChoiceBox.getItems().add(cat.getName());

        itemNameTableColumn.setCellValueFactory(name -> new SimpleStringProperty(name.getValue().getName()));

        itemCategoryTableColumn.setCellValueFactory(category -> new SimpleStringProperty(category.getValue().getCategory().getName()));

        itemWidthTableColumn.setCellValueFactory(width -> new SimpleStringProperty(width.getValue().getWidth().toString()));

        itemHeightTableColumn.setCellValueFactory(height -> new SimpleStringProperty(height.getValue().getHeight().toString()));

        itemLengthTableColumn.setCellValueFactory(length -> new SimpleStringProperty(length.getValue().getLength().toString()));

        itemProductionCostTableColumn.setCellValueFactory(cost -> new SimpleStringProperty(cost.getValue().getProductionCost().toString()));

        itemSellingPriceTableColumn.setCellValueFactory(price -> new SimpleStringProperty(price.getValue().getSellingPrice().toString()));

        ObservableList<Item> itemObservableList = FXCollections.observableList(Main.items);
        itemTableView.setItems(itemObservableList);
    }

    @FXML
    private void onSearchClick() {
        String enteredName = itemNameField.getText();

        List<Item> filteredList = new ArrayList<>(Main.items);

        if (enteredName.length() > 0) {
            filteredList = filteredList.stream()
                    .filter(s -> s.getName().toLowerCase().startsWith(enteredName.toLowerCase()))
                    .collect(Collectors.toList());
        }

        if (itemCategoryChoiceBox.getValue() != null) {
            filteredList = filteredList.stream()
                    .filter(s -> s.getCategory().getName().equals(itemCategoryChoiceBox.getValue()))
                    .collect(Collectors.toList());
        }

        itemTableView.setItems(FXCollections.observableList(filteredList));
    }

    @FXML
    private void resetChoice() {
        itemCategoryChoiceBox.setValue(null);
    }
}