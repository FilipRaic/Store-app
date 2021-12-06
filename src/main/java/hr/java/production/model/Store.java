package hr.java.production.model;

import java.math.BigDecimal;
import java.util.*;

/**
 * Class used for creating an object that stores information about a store and which items that store sells
 * Extends class NamedEntity
 */
public class Store extends NamedEntity{
    private String webAddress;
    private Set<Item> items;

    /**
     * Constructor for Store object
     *
     * @param name Used to invoke the NamedEntity constructor and store the name of the store in the object
     * @param webAddress Used to store a string that represents a web address
     * @param items Used to store an array of items of the store in the object
     */
    public Store(String name, String webAddress, Set<Item> items) {
        super(name);
        this.webAddress = webAddress;
        this.items = items;
    }

    public Store(String name, String webAddress) {
        super(name);
        this.webAddress = webAddress;
    }

    public String getWebAddress() {
        return webAddress;
    }

    public void setWebAddress(String webAddress) {
        this.webAddress = webAddress;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Store store = (Store) o;
        return Objects.equals(webAddress, store.webAddress) && Objects.equals(items, store.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), webAddress, items);
    }

    /**
     * Used to calculate what is the cheapest item in the store
     *
     * @return Returns a BigDecimal value that represents the price of the cheapest item in this store
     */
    public BigDecimal storeCheapestItem() {
        BigDecimal[] tmp = new BigDecimal[items.size()];

        int i=0;
        for (Item element : items) {
            tmp[i] = element.getSellingPrice();
            i++;
        }
        BigDecimal min = BigDecimal.valueOf(1);
        for (i = 0; i < tmp.length-1; i++)
            min = tmp[i].min(tmp[i+1]);
        return min;
    }

    @Override
    public String toString() {
        return "Naziv trgovine: " + this.getName() +
                " - " + items.size();
    }
}
