package hr.java.production;

import hr.java.production.enums.Cities;
import hr.java.production.genericsi.FoodStore;
import hr.java.production.genericsi.TechnicalStore;
import hr.java.production.model.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

public class FirstScreenController {
    private static int br1 = 0;
    private static int br2 = 0;
    private static int br3 = 0;
    private static int skipCounter = 0;

    /**
     * Used to add new categories to the corresponding array (categories)

     * @param i Integer used to skip lines
     * @return Returns a new object of type Optional<Category> to be stored in the categories array
     */
    public static Optional<Category> inputCategories(Integer i) {
        String id = "none", name = "none", description = "none";
        int skip = i*3;

        File fileIn = new File("dat/categories.txt");

        try (BufferedReader in = new BufferedReader(new FileReader(fileIn))) {
            if (skip != 0)
                for (int j = 0; j < skip; j++)
                    in.readLine();
            id = in.readLine();
            name = in.readLine();
            description = in.readLine();
        }
        catch (IOException ex)
        {
            Main.logger.error("File not found!", ex);
            ex.printStackTrace();
        }
        if(fileIn.exists())
            return Optional.of(new Category(Long.parseLong(id), name, description));
        else
            return Optional.empty();
    }

    /**
     * Used to add new items to the corresponding array (items)
     *
     * @param i Integer used to number the outputs
     * @param categories Used to print out all the categories to assign one to each item
     * @return Returns new object of type Optional<Item> to be stored in the items array
     */
    public static Optional<Item> inputItems(Integer i, List<Category> categories) {
        String id = "none", name = "none", category, width = "none", height = "none", length = "none", productionCost = "none",
                sellingPrice = "none", discount = "none", weight, warranty, isLaptop;
        int skip = i*9;
        Category itemCategory = categories.get(0);

        File fileIn = new File("dat/items.txt");

        try (BufferedReader in = new BufferedReader(new FileReader(fileIn))) {
            if (skip != 0)
                for (int j = 0; j < skip + skipCounter; j++)
                    in.readLine();
            id = in.readLine();
            name = in.readLine();
            category = in.readLine();
            width = in.readLine();
            height = in.readLine();
            length = in.readLine();
            productionCost = in.readLine();
            sellingPrice = in.readLine();
            discount = in.readLine();

            itemCategory = categories.get(Integer.parseInt(category)-1);

            if (itemCategory.getName().equals("Hrana") || itemCategory.getName().equals("Piće")) {
                skipCounter++;
                weight = in.readLine();
                if (itemCategory.getName().equals("Hrana"))
                {
                    if (fileIn.exists()) {
                        Food newFood = new Food(Long.parseLong(id), name, itemCategory, BigDecimal.valueOf(Integer.parseInt(width)), BigDecimal.valueOf(Integer.parseInt(height)),
                                BigDecimal.valueOf(Integer.parseInt(length)), BigDecimal.valueOf(Integer.parseInt(productionCost)),
                                BigDecimal.valueOf(Integer.parseInt(sellingPrice)), new Discount(Integer.parseInt(discount)), BigDecimal.valueOf(Integer.parseInt(weight)));
                        Main.foods.add(newFood);
                        br1++;
                        return Optional.of(newFood);
                    }
                    else
                        return Optional.empty();
                }
                else
                {
                    if (fileIn.exists()) {
                        Drink newDrink = new Drink(Long.parseLong(id), name, itemCategory, BigDecimal.valueOf(Integer.parseInt(width)), BigDecimal.valueOf(Integer.parseInt(height)),
                                BigDecimal.valueOf(Integer.parseInt(length)), BigDecimal.valueOf(Integer.parseInt(productionCost)),
                                BigDecimal.valueOf(Integer.parseInt(sellingPrice)), new Discount(Integer.parseInt(discount)), BigDecimal.valueOf(Integer.parseInt(weight)));
                        Main.drinks.add(newDrink);
                        br2++;
                        return Optional.of(newDrink);
                    }
                    else
                        return Optional.empty();
                }
            }
            else if (itemCategory.getName().equals("Tehnika") || itemCategory.getName().equals("Tehnologija") || itemCategory.getName().equals("Elektronika")) {
                skipCounter++;
                isLaptop = in.readLine();
                if (Integer.parseInt(isLaptop) == 1)
                {
                    if (fileIn.exists()) {
                        skipCounter++;
                        warranty = in.readLine();
                        Laptop newLaptop = new Laptop(Long.parseLong(id), name, itemCategory, BigDecimal.valueOf(Integer.parseInt(width)), BigDecimal.valueOf(Integer.parseInt(height)),
                                BigDecimal.valueOf(Integer.parseInt(length)), BigDecimal.valueOf(Integer.parseInt(productionCost)),
                                BigDecimal.valueOf(Integer.parseInt(sellingPrice)), new Discount(Integer.parseInt(discount)), Integer.parseInt(warranty));
                        Main.laptops.add(newLaptop);
                        br3++;
                        return Optional.of(newLaptop);
                    }
                    else
                        return Optional.empty();
                }
            }
        }
        catch (IOException ex)
        {
            Main.logger.error("File not found!", ex);
            ex.printStackTrace();
        }
        if (fileIn.exists()) {
            return Optional.of(new Item(Long.parseLong(id), name, itemCategory, BigDecimal.valueOf(Integer.parseInt(width)), BigDecimal.valueOf(Integer.parseInt(height)),
                    BigDecimal.valueOf(Integer.parseInt(length)), BigDecimal.valueOf(Integer.parseInt(productionCost)),
                    BigDecimal.valueOf(Integer.parseInt(sellingPrice)), new Discount(Integer.parseInt(discount))));
        }
        else
            return Optional.empty();
    }

    /**
     * Used to add new factories to the corresponding array (factories)
     *
     * @param i Integer used to number the outputs
     * @param items Used to print out all the items to assign a desired amount to each factory
     * @return Returns new object of type Optional<Factory> to be stored in the factories array
     */
    public static Optional<Factory> inputFactories(Integer i, List<Item> items) {
        String id = "none", name = "none", street, streetNumber, city, numItems;
        int skip = i*7;
        Address address = new Address.Builder().build();
        Set<Item> factoryItems = new HashSet<>();

        File fileIn = new File("dat/factories.txt");

        try (BufferedReader in = new BufferedReader(new FileReader(fileIn))) {
            if (skip != 0)
                for (int j = 0; j < skip; j++)
                    in.readLine();
            id = in.readLine();
            name = in.readLine();
            street = in.readLine();
            streetNumber = in.readLine();
            city = in.readLine();
            numItems = in.readLine();
            String[] choice = in.readLine().split(",");

            List<Cities> cities = new ArrayList<>(List.of(Cities.class.getEnumConstants()));
            Cities City = cities.get(Integer.parseInt(city)-1);
            address = new Address.Builder(street)
                    .withHouseNumber(streetNumber)
                    .withCity(City)
                    .build();

            for (int j = 0; j < Integer.parseInt(numItems); j++)
                factoryItems.add(items.get(Integer.parseInt(choice[j]) - 1));
        }
        catch (IOException ex)
        {
            Main.logger.error("File not found!", ex);
            ex.printStackTrace();
        }

        if(fileIn.exists())
            return Optional.of(new Factory(Long.parseLong(id), name, address, factoryItems));
        else
            return Optional.empty();
    }

    /**
     * Used to add new factories to the corresponding array (stores)
     *
     * @param i Integer used to number the outputs
     * @param items Used to print out all the items to assign a desired amount to each store
     * @return Returns new object of type Store to be stored in the stores array
     */
    public static Optional<Store> inputStores(Integer i, List<Item> items) {
        String id = "none", name = "none", webAddress = "none", numItems;
        int skip = i*5;
        Set<Item> storeItems = new HashSet<>();

        File fileIn = new File("dat/stores.txt");

        try (BufferedReader in = new BufferedReader(new FileReader(fileIn))) {
            if (skip != 0)
                for (int j = 0; j < skip; j++)
                    in.readLine();
            id = in.readLine();
            name = in.readLine();
            webAddress = in.readLine();
            numItems = in.readLine();
            String[] choice = in.readLine().split(",");

            for (int j = 0; j < Integer.parseInt(numItems); j++)
                storeItems.add(items.get(Integer.parseInt(choice[j]) - 1));
        }
        catch (IOException ex)
        {
            Main.logger.error("File not found!", ex);
            ex.printStackTrace();
        }
        if(fileIn.exists())
            return Optional.of(new Store(Long.parseLong(id), name, webAddress, storeItems));
        else
            return Optional.empty();
    }

    public static FoodStore addFoodStore(Long id, String name, String webAddress, List<Food> foods, List<Drink> drinks) {
        List<Edible> edibles = new ArrayList<>();
        edibles.addAll(foods);
        edibles.addAll(drinks);
        return new FoodStore<>(id, name, webAddress, edibles);
    }

    public static TechnicalStore addTechStore(Long id, String name, String webAddress, List<Laptop> laptops) {
        return new TechnicalStore(id, name, webAddress, laptops);
    }
}