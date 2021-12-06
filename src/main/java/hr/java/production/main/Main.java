package hr.java.production.main;

import hr.java.production.enums.Cities;
import hr.java.production.exception.*;
import hr.java.production.genericsi.FoodStore;
import hr.java.production.genericsi.TechnicalStore;
import hr.java.production.model.*;
import hr.java.production.sort.ProductionSorter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Main class used to implement all methods and other classes
 */
public class Main {

    private static final int NUM_CATG = 3;
    private static final int NUM_ITEM = 5;
    private static final int NUM_FACT = 2;
    private static final int NUM_STORE = 2;
    public static List<Food> foods = new ArrayList<>();
    public static List<Drink> drinks = new ArrayList<>();
    public static List<Laptop> laptops = new ArrayList<>();
    private static int br1 = 0;
    private static int br2 = 0;
    private static int br3 = 0;

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    /**
     * Used to access all other methods
     *
     * @param args Takes arguments that we enter into the terminal
     */
    public static void main(String[] args) {

        logger.info("\n------------------------------------------------\nExample log from {}", Main.class.getSimpleName());
        Scanner input = new Scanner(System.in);

        Category[] categories = new Category[NUM_CATG];
        List<Item> items = new ArrayList<>();
        Factory[] factories = new Factory[NUM_FACT];
        Store[] stores = new Store[NUM_STORE];

        System.out.println("Unesite podatke za " + NUM_CATG + " kategorije artikla:");
        boolean repeatInput;
        for (int i = 0; i < NUM_CATG; i++) {
            do {
                repeatInput = false;
                try {
                    categories[i] = inputCategories(input, i, categories);
                } catch (SameCategory ex) {
                    repeatInput = true;
                    logger.error("Category already exists!");
                }
            } while (repeatInput);
        }

        System.out.println("Unesite podatke za " + NUM_ITEM + " artikla:");
        for (int i = 0; i < NUM_ITEM; i++)
            items.add(inputItems(input, i, categories));

        System.out.println("Unesite podatke za " + NUM_FACT + " tvornice:");
        for (int i = 0; i < NUM_FACT; i++)
            factories[i] = inputFactories(input, i, items);

        System.out.println("Unesite podatke za " + NUM_STORE + " trgovine:");
        for (int i = 0; i < NUM_STORE; i++)
            stores[i] = inputStores(input, i, items);

        System.out.println("-Odaberite kako želite sortirati proizvode:\n1 <- Uzlazno\n2 <- Silazno");
        int choice = 0;
        logger.info("Map sorting type choosing");
        do {
            repeatInput = false;
            try {
                choice = inputInt(input, "-Unesite broj odgovarajućeg sortiranja: ", "Neispravan unos, unesite broj pored sortiranja!", NUM_CATG);
            }
            catch (NotInt ex) {
                repeatInput = true;
                logger.error("Wrong data type entered!", ex);
            }
            catch (InvalidInt ex) {
                repeatInput = true;
                logger.error("Wrong number!", ex);
            }
        } while (repeatInput);

        biggestItem(factories);
        cheapestIteam(stores);
        if(br1 != 0 || br2 != 0)
            bestGroceries(foods, drinks);
        if(br3 != 0)
            shortestWarranty(laptops);

        cheapAndExpensive(items, categories, choice);

        TechnicalStore Links = addTechStore("Links", "www.links.hr", laptops);
        FoodStore Lidl = addFoodStore("Lidl", "www.lidl.hr", foods, drinks);

        long startTime = System.nanoTime();
        List<Item> sortedByVolume = sortByVolume(items);
        long endTime = System.nanoTime();
        System.out.println("Duration with lambdas: " + (endTime-startTime)/1000000.0 + " ms");

        startTime = System.nanoTime();
        List<Item> sortedByVolumeNoLambda = sortByVolumeNoLambda(items);
        endTime = System.nanoTime();
        System.out.println("Duration without lambdas: " + (endTime-startTime)/1000000.0 + " ms");

        startTime = System.nanoTime();
        sortByPrice(sortedByVolume);
        endTime = System.nanoTime();
        System.out.println("Duration with lambdas: " + (endTime-startTime)/1000000.0 + " ms");

        startTime = System.nanoTime();
        sortByPriceNoLambda(sortedByVolumeNoLambda);
        endTime = System.nanoTime();
        System.out.println("Duration without lambdas: " + (endTime-startTime)/1000000.0 + " ms");

        startTime = System.nanoTime();
        List<Store> allStores = filteredStores(stores, Links, Lidl);
        endTime = System.nanoTime();
        System.out.println("Duration with lambdas: " + (endTime-startTime)/1000000.0 + " ms");

        startTime = System.nanoTime();
        filteredStoresNoLambda(stores, Links, Lidl);
        endTime = System.nanoTime();
        System.out.println("Duration without lambdas: " + (endTime-startTime)/1000000.0 + " ms");

        Optional<List<Item>> listOfDiscount = filterDiscount(items);
        itemsPerStore(allStores);
    }

    /**
     * Used to add new categories to the corresponding array (categories)
     *
     * @param input Scanner object used to get strings for the class Category name and description
     * @param i Integer used go through the array to check if a category already exists
     * @param array Array used to read previously added categories
     * @return Returns a new object of type Category to be stored in the categories array
     */
    public static Category inputCategories(Scanner input, Integer i, Category[] array) {
        System.out.print("-Unesite naziv " + (i + 1) + ". kategorije: ");
        String name = input.nextLine();
        for (int j = 0; j < i; j++)
            if(name.equals(array[j].getName())) {
                System.out.println("Kategorija je već unesena!");
                throw new SameCategory();
            }
        System.out.print("-Unesite opis za " + (i + 1) + ". kategoriju: ");
        String description = input.nextLine();

        return new Category(name, description);
    }

    /**
     * Used to add new items to the corresponding array (items)
     *
     * @param input Scanner object used to get numbers and strings for all arguments of the Item, Food, Drink and Laptop classes
     * @param i Integer used to number the outputs
     * @param categories Used to print out all the categories to assign one to each item
     * @return Returns new object of type Item to be stored in the items array
     */
    public static Item inputItems(Scanner input, Integer i, Category[] categories) {
        int choice = 0;

        System.out.print("-Unesite naziv " + (i+1) + ". artikla: ");
        String name = input.nextLine();

        System.out.println("-Odaberite kategoriju " + (i+1) + ". artikla: ");
        for (int j=0; j<NUM_CATG; j++)
            System.out.println((j+1) + ". - " + categories[j].getName() + " Opis: " + categories[j].getDescription());

        boolean repeatInput;
        logger.info("Item category choosing");
        do {
            repeatInput = false;
            try {
                choice = inputInt(input, "-Unesite broj odgovarajuće kategorije: ", "Neispravan unos, unesite broj pored kategorije!", NUM_CATG);
            }
            catch (NotInt ex) {
                repeatInput = true;
                logger.error("Wrong data type entered!", ex);
            }
            catch (InvalidInt ex) {
                repeatInput = true;
                logger.error("Wrong number!", ex);
            }
        } while (repeatInput);
        Category itemCategory = categories[choice-1];

        BigDecimal width = BigDecimal.valueOf(0);
        logger.info("Item width input");
        do {
            repeatInput = false;
            try {
                width = inputBigDecimal(input, "-Unesite širinu " + (i+1) + ". artikla: ", "Neispravan unos širine, molimo pokušajte ponovno!");
            }
            catch (NotBigDecimal ex) {
                repeatInput = true;
                logger.error("Wrong data type entered!", ex);
            }
            catch (InvalidBigDecimal ex) {
                repeatInput = true;
                logger.error("Wrong number!", ex);
            }
        } while (repeatInput);

        BigDecimal height = BigDecimal.valueOf(0);
        logger.info("Item height input");
        do {
            repeatInput = false;
            try {
                height = inputBigDecimal(input, "-Unesite visinu " + (i+1) + ". artikla: ", "Neispravan unos visine, molimo pokušajte ponovno!");
            }
            catch (NotBigDecimal ex) {
                repeatInput = true;
                logger.error("Wrong data type entered!", ex);
            }
            catch (InvalidBigDecimal ex) {
                repeatInput = true;
                logger.error("Wrong number!", ex);
            }
        } while (repeatInput);

        BigDecimal length = BigDecimal.valueOf(0);
        logger.info("Item length input");
        do {
            repeatInput = false;
            try {
                length = inputBigDecimal(input, "-Unesite duljinu " + (i+1) + ". artikla: ", "Neispravan unos duljine, molimo pokušajte ponovno!");
            }
            catch (NotBigDecimal ex) {
                repeatInput = true;
                logger.error("Wrong data type entered!", ex);
            }
            catch (InvalidBigDecimal ex) {
                repeatInput = true;
                logger.error("Wrong number!", ex);
            }
        } while (repeatInput);

        BigDecimal productionCost = BigDecimal.valueOf(0);
        logger.info("Item productionCost input");
        do {
            repeatInput = false;
            try {
                productionCost = inputBigDecimal(input, "-Unesite troškove proizvodnje " + (i+1) + ". artikla: ", "Neispravan unos troškova proizvodnje, molimo pokušajte ponovno!");
            }
            catch (NotBigDecimal ex) {
                repeatInput = true;
                logger.error("Wrong data type entered!", ex);
            }
            catch (InvalidBigDecimal ex) {
                repeatInput = true;
                logger.error("Wrong number!", ex);
            }
        } while (repeatInput);

        BigDecimal sellingPrice = BigDecimal.valueOf(0);
        logger.info("Item sellingPrice input");
        do {
            repeatInput = false;
            try {
                sellingPrice = inputBigDecimal(input, "-Unesite prodajnu cijenu " + (i+1) + ". artikla: ", "Neispravan unos prodajne cijene, molimo pokušajte ponovno!");
            }
            catch (NotBigDecimal ex) {
                repeatInput = true;
                logger.error("Wrong data type entered!", ex);
            }
            catch (InvalidBigDecimal ex) {
                repeatInput = true;
                logger.error("Wrong number!", ex);
            }
        } while (repeatInput);

        int discount = 0;
        logger.info("Item discount input");
        do {
            repeatInput = false;
            try {
                discount = inputInt(input, "-Unesite popust na " + (i+1) + ". artikal u postocima: ", "Neispravan unos popusta, molimo pokušajte ponovno!", 100);
            }
            catch (NotInt ex) {
                repeatInput = true;
                logger.error("Wrong data type entered!", ex);
            }
            catch (InvalidInt ex) {
                repeatInput = true;
                logger.error("Wrong number!", ex);
            }
        } while (repeatInput);

        if (itemCategory.getName().equals("Hrana") || itemCategory.getName().equals("Piće")) {
            BigDecimal weight = BigDecimal.valueOf(0);
            logger.info("Item (Food or drink) weight input");
            do {
                repeatInput = false;
                try {
                    weight = inputBigDecimal(input, "-Unesite koliko kilograma namirnice želite: ", "Neispravan unos kilaže, molimo pokušajte ponovno!");
                }
                catch (NotBigDecimal ex) {
                    repeatInput = true;
                    logger.error("Wrong data type entered!", ex);
                }
                catch (InvalidBigDecimal ex) {
                    repeatInput = true;
                    logger.error("Wrong number!", ex);
                }
            } while (repeatInput);

            if (itemCategory.getName().equals("Hrana"))
            {
                Food newFood = new Food(name, itemCategory, width, height, length, productionCost, sellingPrice, new Discount(discount), weight);
                System.out.println(weight + " kilograma " + newFood.getName() + " sadrži: " + newFood.calculateKilocalories() + " kcal");
                System.out.println(weight + " kilograma " + newFood.getName() + " košta: " + newFood.calculatePrice() + " kn");
                foods.add(newFood);
                br1++;
                input.nextLine();
                return newFood;
            }
            else
            {
                Drink newDrink = new Drink(name, itemCategory, width, height, length, productionCost, sellingPrice, new Discount(discount), weight);
                System.out.println(weight + " kilograma " + newDrink.getName() + " sadrži: " + newDrink.calculateKilocalories() + " kcal");
                System.out.println(weight + " kilograma " + newDrink.getName() + " košta: " + newDrink.calculatePrice() + " kn");
                drinks.add(newDrink);
                br2++;
                input.nextLine();
                return newDrink;
            }
        }
        else if (itemCategory.getName().equals("Tehnika") || itemCategory.getName().equals("Tehnologija") || itemCategory.getName().equals("Elektronika")) {
            System.out.println("-Da li je uređaj laptop:\n1 <- DA\n2 <- NE");
            int isLaptop = 0;
            logger.info("Item is/not laptop input");
            do {
                repeatInput = false;
                try {
                    isLaptop = inputInt(input, "-Unesite da li je uređaj laptop: ", "Neispravan unos, molimo pokušajte ponovno!", 2);
                }
                catch (NotInt ex) {
                    repeatInput = true;
                    logger.error("Wrong data type entered!", ex);
                }
                catch (InvalidInt ex) {
                    repeatInput = true;
                    logger.error("Wrong number!", ex);
                }
            } while (repeatInput);

            if (isLaptop == 1)
            {
                int warranty = 0;
                logger.info("Item (Laptop) warranty input");
                do {
                    repeatInput = false;
                    try {
                        warranty = inputInt(input, "Unesite trajanje garanjiskog perioda za uređaj u mjesecima: ", "Neispravan unos garancije, molimo pokušajte ponovno!", 120);
                    }
                    catch (NotInt ex) {
                        repeatInput = true;
                        logger.error("Wrong data type entered!", ex);
                    }
                    catch (InvalidInt ex) {
                        repeatInput = true;
                        logger.error("Wrong number!", ex);
                    }
                } while (repeatInput);
                Laptop newLaptop = new Laptop(name, itemCategory, width, height, length, productionCost, sellingPrice, new Discount(discount), warranty);
                laptops.add(newLaptop);
                br3++;
                input.nextLine();
                return newLaptop;
            }
        }
        input.nextLine();
        return new Item(name, itemCategory, width, height, length, productionCost, sellingPrice, new Discount(discount));
    }

    /**
     * Used to add new factories to the corresponding array (factories)
     *
     * @param input Scanner object used to get numbers and strings for all arguments of the Factory class
     * @param i Integer used to number the outputs
     * @param items Used to print out all the items to assign a desired amount to each factory
     * @return Returns new object of type Factory to be stored in the factories array
     */
    public static Factory inputFactories(Scanner input, Integer i, List<Item> items) {
        System.out.print("-Unesite naziv " + (i + 1) + ". tvornice: ");
        String name = input.nextLine();

        boolean repeatInput;
        List<Cities> cities = new ArrayList<>(List.of(Cities.class.getEnumConstants()));

        System.out.println("-Unesite adresu " + (i + 1) + ". tvornice: ");
        System.out.print("--Unesite ulicu " + (i + 1) + ". tvornice: ");
        String s = input.nextLine();
        System.out.print("--Unesite kućni broj " + (i + 1) + ". tvornice: ");
        String hn = input.nextLine();
        System.out.println("--Odaberite grad " + (i + 1) + ". tvornice: ");
        for (int j = 0; j < Cities.values().length;  j++)
            System.out.println((j + 1) + ". " + cities.get(j).getPostalCode() + " " + cities.get(j).getCityName());
        int tmpCity = 0;
        logger.info("Factory address input");
        do {
            repeatInput = false;
            try {
                tmpCity = inputInt(input, "-Odaberite u kojem gradu se nalazi " + (i + 1) + ". tvornica: ", "Neispravan odabir grada, molimo pokušajte ponovno!", cities.size());
            }
            catch (NotInt ex) {
                repeatInput = true;
                logger.error("Wrong data type entered!", ex);
            }
            catch (InvalidInt ex) {
                repeatInput = true;
                logger.error("Wrong number!", ex);
            }
        } while (repeatInput);
        Cities City = cities.get(tmpCity-1);

        Address address = new Address.Builder(s)
                .withHouseNumber(hn)
                .withCity(City)
                .build();

        int count = 0;
        logger.info("Factory item count input");
        do {
            repeatInput = false;
            try {
                count = inputInt(input, "-Odaberite koliko artikala proizvodi " + (i + 1) + ". tvornica: ", "Neispravan unos broja artikla, molimo pokušajte ponovno!", NUM_ITEM);
            }
            catch (NotInt ex) {
                repeatInput = true;
                logger.error("Wrong data type entered!", ex);
            }
            catch (InvalidInt ex) {
                repeatInput = true;
                logger.error("Wrong number!", ex);
            }
        } while (repeatInput);
        Set<Item> factoryItems = new HashSet<>();

        System.out.println("-Odaberite koja " + count + " artikla proizvodi " + (i + 1) + ". tvornica: ");
        for (int j = 0; j < NUM_ITEM; j++)
            System.out.println((j + 1) + ". - " + items.get(j).getName() + " Kategorija: " + items.get(j).getCategory().getName());

        int[] choice = new int[count];
        logger.info("Factory item input");
        for (int j = 0; j < count; j++) {
            do {
                repeatInput = false;
                try {
                    choice[j] = inputInt(input, "Unesite odgovarajući broj uz " + (j+1) + ". artikl: ", "Neispravan unos artikla, molimo pokušajte ponovno!", NUM_ITEM);
                    checkArticle(choice, j);
                }
                catch (NotInt ex) {
                    repeatInput = true;
                    logger.error("Wrong data type entered!", ex);
                }
                catch (InvalidInt ex) {
                    repeatInput = true;
                    logger.error("Wrong number!", ex);
                }
                catch (SameArticle ex) {
                    repeatInput = true;
                    logger.error("Item was already entered!", ex);
                }
            } while (repeatInput);
            factoryItems.add(items.get(choice[j]-1));
        }
        input.nextLine();
        return new Factory(name, address, factoryItems);
    }

    /**
     * Used to add new factories to the corresponding array (stores)
     *
     * @param input Scanner object used to get numbers and strings for all arguments of the Store class
     * @param i Integer used to number the outputs
     * @param items Used to print out all the items to assign a desired amount to each store
     * @return Returns new object of type Store to be stored in the stores array
     */
    public static Store inputStores(Scanner input, Integer i, List<Item> items) {
        System.out.print("-Unesite naziv " + (i+1) + ". trgovine: ");
        String name = input.nextLine();

        System.out.print("-Unesite WEB adresu " + (i+1) + ". trgovine: ");
        String webAddress = input.nextLine();

        boolean repeatInput;
        int count = 0;
        logger.info("Store item count input");
        do {
            repeatInput = false;
            try {
                count = inputInt(input, "-Odaberite koliko artikla prodaje " + (i + 1) + ". trgovina: ", "Neispravan unos artikla, molimo pokušajte ponovno!", NUM_ITEM);
            }
            catch (NotInt ex) {
                repeatInput = true;
                logger.error("Wrong data type entered!", ex);
            }
            catch (InvalidInt ex) {
                repeatInput = true;
                logger.error("Wrong number!", ex);
            }
        } while (repeatInput);
        Set<Item> storeItems = new HashSet<>();

        System.out.println("-Odaberite koja " + count + " artikla prodaje " + (i + 1) + ". trgovina: ");
        for (int j = 0; j < NUM_ITEM; j++)
            System.out.println((j + 1) + ". - " + items.get(j).getName() + " Kategorija: " + items.get(j).getCategory().getName());

        int[] choice = new int[count];
        logger.info("Store item input");
        for (int j = 0; j < count; j++) {
            do {
                repeatInput = false;
                try {
                    choice[j] = inputInt(input, "Unesite odgovarajući broj uz " + (j+1) + ". artikl: ", "Neispravan unos artikla, molimo pokušajte ponovno!", NUM_ITEM);
                    checkArticle(choice, j);
                }
                catch (NotInt ex) {
                    repeatInput = true;
                    logger.error("Wrong data type entered!", ex);
                }
                catch (InvalidInt ex) {
                    repeatInput = true;
                    logger.error("Wrong number!", ex);
                }
                catch (SameArticle ex) {
                    repeatInput = true;
                    logger.error("Item was already entered!", ex);
                }
            } while (repeatInput);
            storeItems.add(items.get(choice[j]-1));
        }
        input.nextLine();
        return new Store(name, webAddress, storeItems);
    }

    /**
     * Used to throw exceptions if the input value is not a int or not within the given parameters
     *
     * @param input Scanner object used to get the input number
     * @param mess1 String used to describe what the input should be
     * @param mess2 String used to let the user know that an error had occurred
     * @param num Int used to define the highest input value
     * @return Returns int for various uses
     * @throws InvalidInt Throws if the input is not within the given parameters
     */
    public static int inputInt(Scanner input, String mess1, String mess2, int num) throws InvalidInt{
        int choice;
        System.out.print(mess1);
        try {
            choice = input.nextInt();
            logger.info("Entered number " + choice);
            if (mess1.contains("popust")) {
                if (choice < 0 || choice > num) {
                    System.out.println(mess2);
                    throw new InvalidInt();
                }
            }
            else
                if (choice <= 0 || choice > num) {
                    System.out.println(mess2);
                    throw new InvalidInt();
                }
        }
        catch (InputMismatchException ex) {
            input.nextLine();
            System.out.println("Krivi unos, morate unijeti broj!");
            throw new NotInt(ex);
        }
        return choice;
    }

    /**
     * Used to throw exceptions if the input value is not a BigDecimal or not within the given parameters
     *
     * @param input Scanner object used to get the input number
     * @param mess1 String used to describe what the input should be
     * @param mess2 String used to let the user know that an error had occurred
     * @return Returns BigDecimal for various uses
     * @throws InvalidBigDecimal Throws if the input is not within the given parameters
     */
    public static BigDecimal inputBigDecimal(Scanner input, String mess1, String mess2) throws InvalidBigDecimal{
        BigDecimal choice;
        BigDecimal test = BigDecimal.valueOf(0);

        System.out.print(mess1);
        try {
            choice = input.nextBigDecimal();
            logger.info("Entered number " + choice);
            if (choice.compareTo(test) <= 0) {
                System.out.println(mess2);
                throw new InvalidBigDecimal();
            }
        }
        catch (InputMismatchException ex) {
            input.nextLine();
            System.out.println("Krivi unos, morate unijeti broj!");
            throw new NotBigDecimal(ex);
        }
        return choice;
    }

    /**
     * Used to throw an exception if an item has already been entered to stores or factories arrays
     *
     * @param array Array that represents stores or factories used to read previously added items
     * @param j Int used to represent current item that we want to add
     * @throws SameArticle Throws if the item that we want to add already exists in the array
     */
    public static void checkArticle(int[] array, int j) throws SameArticle {
        for (int i = 0; i < j; i++)
            if(array[i] == array[j]) {
                System.out.println("Artikl je već unesen!");
                throw new SameArticle();
            }
    }

    /**
     * Used to find the highest volume item in the factories array
     *
     * @param factories Used to have the ability to get all the items for each factory
     */
    public static void biggestItem(Factory[] factories) {
        BigDecimal[] tmp = new BigDecimal[NUM_FACT];
        BigDecimal max = BigDecimal.valueOf(0);
        int res;
        int count = 0;

        for (int i = 0; i < NUM_FACT; i++)
            tmp[i] = factories[i].factoryBiggestItem();

        for (int i = 0; i < NUM_FACT - 1; i++) {
            res = tmp[i].compareTo(tmp[i + 1]);
            if (res ==  1) {
                max = tmp[i];
            }
            else {
                max = tmp[i + 1];
                count = i+1;
            }
        }
        System.out.println("\nNajveći volumen proizvoda iznosi: " + max + "\nTvornica koja ga proizvodi: " + factories[count].getName());
    }

    /**
     * Used to find the cheapest item in the stores array
     *
     * @param stores Used to have the ability to get all the items for each store
     */
    public static void cheapestIteam(Store[] stores) {
        BigDecimal[] tmp = new BigDecimal[NUM_STORE];
        BigDecimal min = BigDecimal.valueOf(0);
        int res;
        int count = 0;

        for (int i = 0; i < NUM_STORE; i++)
            tmp[i] = stores[i].storeCheapestItem();

        for (int i = 0; i < NUM_STORE - 1; i++) {
            res = tmp[i].compareTo(tmp[i + 1]);
            if (res ==  -1) {
                min = tmp[i];
            }
            else {
                min = tmp[i + 1];
                count = i+1;
            }
        }
        System.out.println("Najjeftiniji proizvod košta: " + min + " kn\nTrgovina koja ga prodaje: " + stores[count].getName());
    }

    /**
     * Used to find the item of type Food or Drink that has the highest calorie count based on weight and highest price
     *
     * @param foods Used to have the ability to calculate the price and calories for each food
     * @param drinks Used to have the ability to calculate the price and calories for each drink
     */
    public static void bestGroceries(List<Food> foods, List<Drink> drinks) {
        int maxCal = 0;
        float maxPrice = 0;
        int count1 = 0;
        int count2 = 0;
        int[] tmp = new int[br1+br2];
        float[] tmp1 = new float[br1+br2];

        for (int i = 0; i < br1; i++) {
            tmp[i] = foods.get(i).calculateKilocalories();
            tmp1[i] = foods.get(i).calculatePrice();
        }
        for (int i = br1; i < (br1 + br2); i++) {
            tmp[i] = drinks.get(i-br1).calculateKilocalories();
            tmp1[i] = drinks.get(i-br1).calculatePrice();
        }
        for (int i = 0; i < (br1 + br2) - 1; i++) {
            if (tmp[i] <= tmp[i+1]) {
                maxCal = tmp[i+1];
                count1 = i+1;
            }
            else {
                maxCal = tmp[i];
                count1 = i;
            }
        }
        for (int i = 0; i < (br1 + br2) - 1; i++) {
            if (tmp1[i] <= tmp1[i+1]) {
                maxPrice = tmp1[i+1];
                count2 = i+1;
            }
            else {
                maxPrice = tmp1[i];
                count2 = i;
            }
        }

        if (count1 <= br1-1)
            System.out.println("Najviše kilokalorija ima proizvod: " + foods.get(count1).getName() + "\nIznos: " + maxCal + " kcal");
        else if (count1 > br1-1)
            System.out.println("Najviše kilokalorija ima proizvod: " + drinks.get(count1-br1).getName() + "\nIznos: " + maxCal + " kcal");
        if (count2 <= br1-1)
            System.out.println("Najskuplji proizvod: " + foods.get(count2).getName() + "\nIznos: " + maxPrice + " kn");
        else if (count2 > br1-1)
            System.out.println("Najskuplji proizvod: " + drinks.get(count2-br1).getName() + "\nIznos: " + maxPrice + " kn");
    }

    /**
     * Used to find the item of type Laptop that has the shortest warranty period
     *
     * @param laptops Used to have the ability to calculate the warranty period for each laptop
     */
    public static void shortestWarranty(List<Laptop> laptops) {
        int minWarranty = 0;
        int count1 = 0;
        int[] tmp = new int[br3];

        for (int i = 0; i < br3; i++) {
            tmp[i] = laptops.get(i).warrantyPeriod();
        }
        for (int i = 0; i < br3-1; i++) {
            if (tmp[i] <= tmp[i+1]) {
                minWarranty = tmp[i];
                count1 = i;
            }
            else {
                minWarranty = tmp[i+1];
                count1 = i+1;
            }
        }
        System.out.println("Najkraću garanciju ima proizvod: " + laptops.get(count1).getName() + "\nTrajanje: " + minWarranty + " mjeseci");
    }

    /**
     * Used to find the cheapest and most expensive items for each category
     *
     * @param items List of items used to copy each item of a certain category to it's respective list
     * @param categories Array of categories used to assign each key
     * @param choice Int used to determine how the list of items will be sorted
     */
    public static void cheapAndExpensive(List<Item> items, Category[] categories, int choice) {
        Map<String, List<Item>> mapOfItemsPerCategory = new HashMap<>();
        for (Category category : categories) {
            List<Item> tmpItems = new ArrayList<>();
            for (Item item : items)
                if (category.getName().equals(item.getCategory().getName()))
                    tmpItems.add(item);
            switch (choice) {
                case 1 -> mapOfItemsPerCategory.put(category.getName(), tmpItems.stream().sorted(new ProductionSorter()).collect(Collectors.toList()));
                case 2 -> mapOfItemsPerCategory.put(category.getName(), tmpItems.stream().sorted(new ProductionSorter().reversed()).collect(Collectors.toList()));
            }
        }

        for (Category category : categories) {
            int categorySize = mapOfItemsPerCategory.get(category.getName()).size();
            if (categorySize > 0) {
                if (mapOfItemsPerCategory.get(category.getName()).get(0) instanceof Technical)
                    printMap(choice, categorySize, category, mapOfItemsPerCategory);
                else if (mapOfItemsPerCategory.get(category.getName()).get(0) instanceof Edible)
                    printMap(choice, categorySize, category, mapOfItemsPerCategory);
            }
        }
    }

    /**
     * Used to print out the cheapest and most expensive items that implement Technical and Edible interfaces
     *
     * @param choice Int used to determine ascending or descending order
     * @param categorySize Int that describes how many items a certain category contains
     * @param category Object of type Category used to get the name of a certain category
     * @param mapOfItemsPerCategory Map that contains names of categories as keys and a list of items in those categories of that category
     */
    public static void printMap(int choice, int categorySize, Category category, Map<String, List<Item>> mapOfItemsPerCategory) {
        switch (choice) {
            case 1 -> {
                System.out.println("Najjeftiniji proizvod u kategoriji " + category.getName() + " je: " + mapOfItemsPerCategory.
                        get(category.getName()).get(0).getName() + " (" + mapOfItemsPerCategory.
                        get(category.getName()).get(0).getSellingPrice() + " kn)");
                System.out.println("Najskuplji proizvod u kategoriji " + category.getName() + " je: " + mapOfItemsPerCategory.
                        get(category.getName()).get(categorySize - 1).getName() + " (" + mapOfItemsPerCategory.
                        get(category.getName()).get(categorySize - 1).getSellingPrice() + " kn)");
            }
            case 2 -> {
                System.out.println("Najskuplji proizvod u kategoriji " + category.getName() + " je: " + mapOfItemsPerCategory.
                        get(category.getName()).get(0).getName() + " (" + mapOfItemsPerCategory.
                        get(category.getName()).get(0).getSellingPrice() + " kn)");
                System.out.println("Najjeftiniji proizvod u kategoriji " + category.getName() + " je: " + mapOfItemsPerCategory.
                        get(category.getName()).get(categorySize - 1).getName() + " (" + mapOfItemsPerCategory.
                        get(category.getName()).get(categorySize - 1).getSellingPrice() + " kn)");
            }
        }
    }

    public static FoodStore addFoodStore(String name, String webAddress, List<Food> foods, List<Drink> drinks) {
        List<Edible> edibles = new ArrayList<>();
        edibles.addAll(foods);
        edibles.addAll(drinks);
        return new FoodStore<>(name, webAddress, edibles);
    }

    public static TechnicalStore addTechStore(String name, String webAddress, List<Laptop> laptops) {
        return new TechnicalStore(name, webAddress, laptops);
    }

    public static List<Item> sortByVolume(List<Item> items) {
        List<Item> sortedByVolume = items.stream()
                .sorted((o1, o2) -> Integer.compare(o1.calculateVolume().compareTo(o2.calculateVolume()), 0))
                .collect(Collectors.toList());

        System.out.println("\nProizvodi sortirani po volumenu:");
        sortedByVolume.forEach(o -> System.out.println("Naziv proizvoda: " + o.getName() + " - Volumen (" + o.calculateVolume() + ")"));

        return sortedByVolume;
    }

    public static void sortByPrice(List<Item> sortedByVolume) {
        BigDecimal averageVolume;
        BigDecimal tmpVolume = BigDecimal.valueOf(0);
        for (Item item : sortedByVolume)
            tmpVolume = tmpVolume.add(item.calculateVolume());

        averageVolume=tmpVolume.divide(BigDecimal.valueOf(sortedByVolume.size()), RoundingMode.CEILING);

        System.out.println("\nProizvodi koji imaju volumen veći od " + averageVolume + ": ");
        sortedByVolume.stream()
                .filter(o -> o.calculateVolume().compareTo(averageVolume) >= 1)
                .forEach(o -> System.out.println("Naziv proizvoda: " + o.getName() + " - Volumen (" + o.calculateVolume() + ")"));

        Double avgPrice = sortedByVolume.stream()
                .filter(o -> o.calculateVolume().compareTo(averageVolume) >= 1)
                .mapToDouble(Item::calculatePrice)
                .average().getAsDouble();

        System.out.println("Prosječna cijena: " + avgPrice + " kn");
    }

    public static List<Store> filteredStores(Store[] stores, TechnicalStore Links, FoodStore Lidl) {
        List<Store> allStores = new ArrayList<>();
        long tmp = 0;
        long count;
        for (Store store : stores) {
            allStores.add(store);
            tmp += store.getItems().size();
        }
        allStores.add(Lidl);
        allStores.add(Links);
        List<Store> tmpList = new ArrayList<>(allStores);

        tmp += Lidl.getListOfEdibles().size();
        tmp += Links.getListOfTechnic().size();
        count = tmp/4;

        System.out.println("\nTrgovine koje imaju više od " + count + " artikla:");

        allStores.stream()
                .filter(o -> o.getItems().size() > count)
                .forEach(o -> System.out.println("Naziv trgovine: " + o.getName()));

        return tmpList;
    }

    public static List<Item> sortByVolumeNoLambda(List<Item> items) {
        List<Item> tmpItems = new ArrayList<>(items);
        tmpItems.sort(new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                if (o1.calculateVolume().compareTo(o2.calculateVolume()) > 0)
                    return 1;
                else if (o1.calculateVolume().compareTo(o2.calculateVolume()) < 0)
                    return -1;
                else
                    return 0;
            }
        });
        System.out.println("\nProizvodi sortirani po volumenu:");
        for (Item item : items)
            System.out.println("Naziv proizvoda: " + item.getName() + " - Volumen (" + item.calculateVolume() + ")");

        return tmpItems;
    }

    public static void sortByPriceNoLambda(List<Item> sortedByVolume) {
        BigDecimal averageVolume;
        BigDecimal tmpVolume = BigDecimal.valueOf(0);
        for (Item item : sortedByVolume)
            tmpVolume = tmpVolume.add(item.calculateVolume());

        averageVolume = tmpVolume.divide(BigDecimal.valueOf(sortedByVolume.size()), RoundingMode.CEILING);

        System.out.println("\nProizvodi koji imaju volumen veći od " + averageVolume + ": ");

        List<Item> tmpList = new ArrayList<>();
        for (Item item : sortedByVolume)
            if (item.calculateVolume().compareTo(averageVolume) >= 1)
                tmpList.add(item);

        Double avgPrice = 0.0;
        for (Item item : tmpList) {
            System.out.println("Naziv proizvoda: " + item.getName() + " - Volumen (" + item.calculateVolume() + ")");
            avgPrice += item.calculatePrice();
        }
        System.out.println("Prosječna cijena: " + avgPrice/tmpList.size() + " kn");
    }

    public static void filteredStoresNoLambda(Store[] stores, TechnicalStore Links, FoodStore Lidl) {
        List<Store> allStores = new ArrayList<>();
        long tmp = 0;
        long count;
        for (Store store : stores) {
            allStores.add(store);
            tmp += store.getItems().size();
        }
        allStores.add(Lidl);
        allStores.add(Links);

        tmp += Lidl.getListOfEdibles().size();
        tmp += Links.getListOfTechnic().size();
        count = tmp/4;

        System.out.println("\nTrgovine koje imaju više od " + count + " artikla:");

        for (Store store : allStores)
            if(store.getItems().size() > count)
                System.out.println("Naziv trgovine: " + store.getName());
    }

    public static Optional<List<Item>> filterDiscount(List<Item> items) {
        List<Item> tmpList = new ArrayList<>(items);

        System.out.println("\nProizvodi koji imaju popust: ");
        tmpList.stream()
                .filter(o -> o.getDiscount().discountAmount() > 0)
                .forEach(o -> System.out.println("Naziv proizvoda: " + o.getName() + " - Popust (" + o.getDiscount().discountAmount() + ")"));

        return Optional.of(tmpList);
    }

    public static void itemsPerStore(List<Store> allStores) {
        List<Store> tmpStores = new ArrayList<>(allStores);

        System.out.println("\nBroj artikla po trgovini: ");
        tmpStores.stream()
                .map(o -> o)
                .forEach(System.out::println);
    }
}