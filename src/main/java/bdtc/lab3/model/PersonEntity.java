package bdtc.lab3.model;

import java.util.Objects;
import java.util.UUID;
public final class PersonEntity {
    private UUID id;
    private String name;

    /**
     * Constructs PersonEntity with the given name.
     * @param name Person name
     */
    public PersonEntity(final String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }

    /**
     * Returns persons's id.
     * @return id
     */
    public UUID getId() {
        return id;
    }

    /**
     * Sets new persons's id.
     * @param id New id
     */
    public void setId(final UUID id) {
        this.id = id;
    }

    /**
     * Returns person's name.
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets person's name.
     * @param name New name
     */
    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PersonEntity personEntity = (PersonEntity) o;

        return Objects.equals(id, personEntity.id)
                && Objects.equals(name, personEntity.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "PersonEntity{"
             + "id=" + id
             + ", name='" + name + '\''
             + '}';
    }
}
