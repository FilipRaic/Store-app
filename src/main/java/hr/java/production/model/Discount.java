package hr.java.production.model;

import java.util.Objects;

/**
 * Record for adding a discount object to an item
 */
public record Discount(int discountAmount) {

    /**
     * Used to create a record of a discount
     *
     * @param discountAmount Used to create an instance of Discount, not used here
     */
    public Discount(int discountAmount) {
        this.discountAmount = discountAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Discount discount = (Discount) o;
        return discountAmount == discount.discountAmount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(discountAmount);
    }
}
