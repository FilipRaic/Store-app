package hr.java.production.model;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Class used for creating an object that can has more information than an Item object
 * Extends class Item and implements interface Technical
 */
public final class Laptop extends Item implements Technical {
    private int warranty;

    /**
     *
     *
     * @param name Inherited parameter, used to give the object a name
     * @param category Inherited parameter, used to give the object a category
     * @param width Inherited parameter, used to give the object width
     * @param height Inherited parameter, used to give the object height
     * @param length Inherited parameter, used to give the object length
     * @param productionCost Inherited parameter, used to give the object a production cost
     * @param sellingPrice Inherited parameter, used to give the object a selling price
     * @param discount Inherited parameter, used to give the object a discount
     * @param warranty Used to add a warranty period to an object
     */
    public Laptop(Long id, String name, Category category, BigDecimal width, BigDecimal height, BigDecimal length, BigDecimal productionCost, BigDecimal sellingPrice, Discount discount, int warranty) {
        super(id, name, category, width, height, length, productionCost, sellingPrice, discount);
        this.warranty = warranty;
    }

    public int getWarranty() {
        return warranty;
    }

    public void setWarranty(int warranty) {
        this.warranty = warranty;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Laptop laptop = (Laptop) o;
        return warranty == laptop.warranty;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), warranty);
    }

    /**
     * Used to return a warranty period
     *
     * @return Returns an int that represents a warranty period in months
     */
    @Override
    public int warrantyPeriod() {
        return warranty;
    }
}
