package hr.java.production;

import hr.java.production.model.*;
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

public class SearchCategoryController {
    @FXML
    private ChoiceBox<String> categoryChoiceBox;

    @FXML
    private TableView<Category> categoryTableView;

    @FXML
    private TableColumn<Category, String> categoryNameTableColumn;

    @FXML
    private TableColumn<Category, String> categoryDescriptionTableColumn;

    @FXML
    public void initialize() {
        for (Category cat : Main.categories)
            categoryChoiceBox.getItems().add(cat.getName());

        categoryNameTableColumn.setCellValueFactory(name -> new SimpleStringProperty(name.getValue().getName()));

        categoryDescriptionTableColumn.setCellValueFactory(name -> new SimpleStringProperty(name.getValue().getDescription()));

        ObservableList<Category> categoryObservableList = FXCollections.observableList(Main.categories);
        categoryTableView.setItems(categoryObservableList);
    }

    @FXML
    private void onSearchClick() {
        List<Category> filteredList = new ArrayList<>(Main.categories);

        if (categoryChoiceBox.getValue() != null) {
            filteredList = filteredList.stream()
                    .filter(s -> s.getName().equals(categoryChoiceBox.getValue()))
                    .collect(Collectors.toList());
        }

        categoryTableView.setItems(FXCollections.observableList(filteredList));
    }

    @FXML
    private void resetChoice() {
        categoryChoiceBox.setValue(null);
    }
}
