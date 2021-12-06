package hr.java.production.sort;

import hr.java.production.model.Item;
import java.util.Comparator;

/**
 * Used to sort items in a list
 */
public class ProductionSorter implements Comparator<Item> {
    /**
     * Used to compare items based on price and sort them in the list from lowest to highest
     *
     * @param o1 Object of type Item
     * @param o2 Object of type Item
     * @return Returns an int that is used to sort the list
     */
    @Override
    public int compare(Item o1, Item o2) {
        if (o1.getSellingPrice().compareTo(o2.getSellingPrice()) > 0)
            return 1;
        else if (o1.getSellingPrice().compareTo(o2.getSellingPrice()) < 0)
            return -1;
        else
            return 0;
    }

    /**
     * Used to reverse the order of a list
     *
     * @return Returns a reveresed order of a list
     */
    @Override
    public Comparator<Item> reversed() {
        return Comparator.super.reversed();
    }
}
