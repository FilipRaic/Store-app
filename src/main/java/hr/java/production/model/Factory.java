package hr.java.production.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

/**
 * Class used for creating an object that stores information about a factory and which items that factory makes
 * Extends class NamedEntity
 */
public class Factory extends NamedEntity implements Serializable {
    private Address address;
    private Set<Item> items;

    /**
     * Constructor for Factory object
     *
     * @param name Used to invoke the NamedEntity constructor and store the name of the factory in the object
     * @param address Used to store an address of the factory in the object
     * @param items Used to store an array of items of the factory in the object
     */
    public Factory(Long id, String name, Address address, Set<Item> items){
        super(id, name);
        this.address = address;
        this.items = items;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
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
        Factory factory = (Factory) o;
        return Objects.equals(address, factory.address) && Objects.equals(items, factory.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), address, items);
    }

    /**
     * Used to calculate what is the largest volume item in the factory
     *
     * @return Returns a BigDecimal value that represents the volume of the largest item in this factory
     */
    public BigDecimal factoryBiggestItem() {
        BigDecimal[] tmp = new BigDecimal[items.size()];
        BigDecimal tmp1 = BigDecimal.valueOf(1);

        int i=0;
        for (Item element : items) {
            tmp[i] = tmp1.multiply(element.getWidth());
            tmp[i] = tmp[i].multiply(element.getHeight());
            tmp[i] = tmp[i].multiply(element.getLength());
            i++;
        }
        BigDecimal maximum = BigDecimal.valueOf(1);
        for (i = 0; i < tmp.length-1; i++)
            maximum = tmp[i].max(tmp[i + 1]);

        return maximum;
    }

    @Override
    public String toString() {
        String list = "";

        for (Item item : items)
            list += item.getName() + "\n";

        return list;
    }
}