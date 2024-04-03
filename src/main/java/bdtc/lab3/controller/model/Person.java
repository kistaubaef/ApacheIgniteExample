package bdtc.lab3.controller.model;

import java.util.Objects;

public final class Person {
    private String name;

    /**
     * Constructs Person with empty name.
     */
    public Person() {
    }

    /**
     * Constructs Person with the given name.
     * @param name Name
     */
    public Person(final String name) {
        this.name = name;
    }

    /**
     * Returns person's name.
     * @return Name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets person's name.
     * @param name Name
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

        Person person = (Person) o;

        return Objects.equals(name, person.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Person{"
             + "name='" + name + '\''
             + '}';
    }
}
