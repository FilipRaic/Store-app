package hr.java.production.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Abstract class that is extended to every class whose objects need a name
 */
public abstract class NamedEntity implements Serializable {
    private Long id;
    private String name;

    /**
     * Constructor for NamedEntity taking id and name
     *
     * @param id Used to allow objects to have an id
     * @param name Used to allow objects to have a name
     */
    public NamedEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
