package hr.java.production;

import hr.java.production.genericsi.FoodStore;
import hr.java.production.genericsi.TechnicalStore;
import hr.java.production.model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;
import java.util.*;

public class Main extends Application {
    public static final int NUM_CATG = 3;
    public static final int NUM_ITEM = 5;
    public static final int NUM_FACT = 2;
    public static final int NUM_STORE = 2;
    private static Stage mainStage;

    protected static List<Food> foods = new ArrayList<>();
    protected static List<Drink> drinks = new ArrayList<>();
    protected static List<Laptop> laptops = new ArrayList<>();
    protected static List<Category> categories = new ArrayList<>();
    protected static List<Item> items = new ArrayList<>();
    protected static List<Factory> factories = new ArrayList<>();
    protected static List<Store> stores = new ArrayList<>();

    protected static final Logger logger = LoggerFactory.getLogger(Main.class);

    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("firstScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Raic-7");
        stage.setScene(scene);
        stage.setMinWidth(616.0);
        stage.setMinHeight(420.0);
        stage.show();
    }

    public static void main(String[] args) {
        logger.info("\n-------------------- Application started --------------------\n");

        for (int i = 0; i < NUM_CATG; i++) {
            Optional<Category> category = FirstScreenController.inputCategories(i);
            if (category.isPresent())
                categories.add(category.get());
            else {
                logger.error("File dat/categories.txt was not found!");
                System.exit(1);
            }
        }
        logger.info("categories.txt was successfully read");

        for (int i = 0; i < NUM_ITEM; i++) {
            Optional<Item> item = FirstScreenController.inputItems(i, categories);
            if (item.isPresent())
                items.add(item.get());
            else {
                logger.error("File dat/items.txt was not found!");
                System.exit(1);
            }
        }
        logger.info("items.txt was successfully read");

        for (int i = 0; i < NUM_FACT; i++) {
            Optional<Factory> factory = FirstScreenController.inputFactories(i, items);
            if (factory.isPresent()) {
                factories.add(factory.get());
                try {
                    if (factories.get(i).getItems().size() >= 5) {
                        List<Factory> factoryList = new ArrayList<>();
                        factoryList.add(factories.get(i));
                        FileOutputStream fout = new FileOutputStream("dat/factories.ser");
                        ObjectOutputStream oos = new ObjectOutputStream(fout);
                        oos.writeObject(factoryList);
                    }
                }
                catch (IOException ex) {
                    logger.error("File dat/factories.txt was not found!", ex);
                    ex.printStackTrace();
                }
            }
            else
                System.exit(1);
        }
        logger.info("factories.txt was successfully read");

        for (int i = 0; i < NUM_STORE; i++) {
            Optional<Store> store = FirstScreenController.inputStores(i, items);
            if (store.isPresent()) {
                stores.add(store.get());
                try {
                    if (stores.get(i).getItems().size() >= 5) {
                        List<Store> storeList = new ArrayList<>();
                        storeList.add(stores.get(i));
                        FileOutputStream fout = new FileOutputStream("dat/stores.ser");
                        ObjectOutputStream oos = new ObjectOutputStream(fout);
                        oos.writeObject(storeList);
                    }
                }
                catch (IOException ex) {
                    logger.error("File dat/stores.txt was not found!", ex);
                    ex.printStackTrace();
                }
            }
            else
                System.exit(1);
        }
        logger.info("stores.txt was successfully read");

        TechnicalStore Links = FirstScreenController.addTechStore(0L, "Links", "www.links.hr", laptops);
        FoodStore Lidl = FirstScreenController.addFoodStore(0L, "Lidl", "www.lidl.hr", foods, drinks);
        stores.add(Links);
        stores.add(Lidl);
        launch();
    }

    public static Stage getStage() {
        return mainStage;
    }
}