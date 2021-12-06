package hr.java.production.model;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Class used for creating an object that stores information of what  a certain item is
 * Extends class NamedEntity
 */
public class Item extends NamedEntity {
    private Category category;
    private BigDecimal width;
    private BigDecimal height;
    private BigDecimal length;
    private BigDecimal productionCost;
    private BigDecimal sellingPrice;
    private Discount discount;

    /**
     * Constructor for Item objects
     *
     * @param name Inherited parameter, used to give the object a name
     * @param category Used to give the object a category
     * @param width Used to give the object width
     * @param height Used to give the object height
     * @param length Used to give the object lenght
     * @param productionCost Used to give the object production cost
     * @param sellingPrice Used to give the object selling price
     * @param discount Used to give the object a discount
     */
    public Item(String name, Category category, BigDecimal width, BigDecimal height, BigDecimal length, BigDecimal productionCost, BigDecimal sellingPrice, Discount discount) {
        super(name);
        this.category = category;
        this.width = width;
        this.height = height;
        this.length = length;
        this.productionCost = productionCost;
        this.sellingPrice = sellingPrice;
        this.discount = discount;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public BigDecimal getWidth() {
        return width;
    }

    public void setWidth(BigDecimal width) {
        this.width = width;
    }

    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    public BigDecimal getLength() {
        return length;
    }

    public void setLength(BigDecimal length) {
        this.length = length;
    }

    public BigDecimal getProductionCost() {
        return productionCost;
    }

    public void setProductionCost(BigDecimal productionCost) {
        this.productionCost = productionCost;
    }

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public Discount getDiscount() { return discount; }

    public void setDiscount(Discount discount) { this.discount = discount; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Item item = (Item) o;
        return Objects.equals(category, item.category) && Objects.equals(width, item.width) && Objects.equals(height, item.height) && Objects.equals(length, item.length) && Objects.equals(productionCost, item.productionCost) && Objects.equals(sellingPrice, item.sellingPrice) && Objects.equals(discount, item.discount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), category, width, height, length, productionCost, sellingPrice, discount);
    }

    /**
     * Used to calculate the price of an item based on selling price and discount
     *
     * @return Float that represents the final price
     */
    public float calculatePrice() {
        return sellingPrice.floatValue() * ((100 - (float) getDiscount().discountAmount()) / 100);
    }

    public BigDecimal calculateVolume() {
        return width.multiply(height.multiply(length));
    }

    @Override
    public String toString() {
        return "Naziv proizvoda: " + this.getName() +
                " - " + this.calculatePrice();
    }
}
