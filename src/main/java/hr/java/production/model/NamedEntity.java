package hr.java.production.model;

import java.util.Objects;

/**
 * Abstract class that is extended to every class whose objects need a name
 */
public abstract class NamedEntity {
    private String name;

    /**
     * Constructor for name
     *
     * @param name Used to allow objects to have a name
     */
    public NamedEntity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NamedEntity that = (NamedEntity) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
