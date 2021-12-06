package hr.java.production.model;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Class used for creating an object that can has more information than an Item object
 * Implements interface Edible
 */
public class Food extends Item implements Edible{
    private final int calPerKg = 1000;
    private BigDecimal weight;

    /**
     * Constructor for Food objects
     *
     * @param name Inherited parameter, used to give the object a name
     * @param category Inherited parameter, used to give the object a category
     * @param width Inherited parameter, used to give the object width
     * @param height Inherited parameter, used to give the object height
     * @param length Inherited parameter, used to give the object length
     * @param productionCost Inherited parameter, used to give the object a production cost
     * @param sellingPrice Inherited parameter, used to give the object a selling price
     * @param discount Inherited parameter, used to give the object a discount
     * @param weight Used to give the object weight
     */
    public Food(String name, Category category, BigDecimal width, BigDecimal height, BigDecimal length, BigDecimal productionCost, BigDecimal sellingPrice, Discount discount, BigDecimal weight) {
        super(name, category, width, height, length, productionCost, sellingPrice, discount);
        this.weight = weight;
    }

    public int getCalPerKg() {
        return calPerKg;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Food food = (Food) o;
        return calPerKg == food.calPerKg && Objects.equals(weight, food.weight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), calPerKg, weight);
    }

    /**
     * Overrides a method in Edible interface that is used to calculate how many kilocalories a food contains
     *
     * @return Returns an int that represents calories
     */
    @Override
    public int calculateKilocalories() {
        BigDecimal calories = BigDecimal.valueOf(calPerKg);
        calories = weight.multiply(calories);

        return calories.intValue();
    }

    /**
     * Overrides a method in Edible interface and is used to calculate the price of a food based on selling price, weight and discount
     *
     * @return Returns a float that represents the price
     */
    @Override
    public float calculatePrice() {
        BigDecimal totalCost = weight;
        totalCost = getSellingPrice().multiply(totalCost);

        return totalCost.floatValue() * ((100 - (float) getDiscount().discountAmount()) / 100);
    }
}
