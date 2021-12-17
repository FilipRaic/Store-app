package hr.java.production.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Class used for creating an object that stores information for name and description for a category
 * Extends class NamedEntity
 */
public class Category extends NamedEntity implements Serializable {
    private String description;

    /**
     * Constructor for Category object
     *
     * @param name Used to invoke the NamedEntity constructor and store the name of the category in the object
     * @param description Used to store a description of the category in the object
     */
    public Category(Long id, String name, String description) {
        super(id, name);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Category category = (Category) o;
        return Objects.equals(description, category.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), description);
    }
}
