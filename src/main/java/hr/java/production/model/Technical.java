package hr.java.production.model;

/**
 * Used to describe class Laptop
 */
public sealed interface Technical permits Laptop {
    int warrantyPeriod();
}
