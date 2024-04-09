package bdtc.lab3.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;


import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

@RunWith(SpringRunner.class)
public class TestServicePersonEntityTest {

    @Test
    public void testConstructor() {
        PersonEntity person = new PersonEntity("John Doe");
        assertNotNull(person.getId());
        assertEquals("John Doe", person.getName());
    }

    @Test
    public void testSetId() {
        UUID id = UUID.randomUUID();
        PersonEntity person = new PersonEntity("John Doe");
        person.setId(id);
        assertEquals(id, person.getId());
    }

    @Test
    public void testSetName() {
        PersonEntity person = new PersonEntity("John Doe");
        person.setName("Jane Doe");
        assertEquals("Jane Doe", person.getName());
    }

    @Test
    public void equals_returnsTrueIfSameNameAndId() {
        UUID id = UUID.randomUUID();
        PersonEntity person1 = new PersonEntity("John Doe");
        person1.setId(id);
        PersonEntity person2 = new PersonEntity("John Doe");
        person2.setId(id);
        assertTrue(person1.equals(person2));
    }

    @Test
    public void equals_returnsFalseIfDifferentNames() {
        PersonEntity person1 = new PersonEntity("John Doe");
        PersonEntity person2 = new PersonEntity("Jahn Dog");
        assertFalse(person1.equals(person2));
    }

    @Test
    public void equals_returnsFalseIfDifferentIds() {
        PersonEntity person1 = new PersonEntity("John Doe");
        PersonEntity person2 = new PersonEntity("John Doe");
        assertFalse(person1.equals(person2));
    }

    @Test
    public void equals_returnsFalseIfDifferentNamesButSameId() {
        PersonEntity person1 = new PersonEntity("John Doe");
        PersonEntity person2 = new PersonEntity("Jahn Dog");
        UUID id = UUID.randomUUID();
        person1.setId(id);
        person2.setId(id);
        assertFalse(person1.equals(person2));
    }

    @Test
    public void equals_returnsTrueIfSameObject() {
        // Arrange
        PersonEntity personeEntity = new PersonEntity("John Doe");

        // Act
        boolean result = personeEntity.equals(personeEntity);

        // Assert
        assertTrue(result);
    }

    @Test
    public void equals_returnsFalseIfDifferentTypes() {
        // Arrange
        PersonEntity personeEntity = new PersonEntity("John");
        String otherString = "John";

        // Act
        boolean result = personeEntity.equals(otherString);

        // Assert
        assertFalse(result);
    }

    @Test
    public void equals_returnsFalseIfNull() {
        // Arrange
        PersonEntity personEntity = new PersonEntity("John");

        assertFalse(personEntity.equals(null));
    }

    @Test
    public void testNotEquals() {
        PersonEntity person1 = new PersonEntity("John Doe");
        PersonEntity person2 = new PersonEntity("Jane Doe");
        assertFalse(person1.equals(person2));
    }

    @Test
    public void testHashCode() {
        UUID id = UUID.randomUUID();
        PersonEntity person1 = new PersonEntity("John Doe");
        person1.setId(id);
        PersonEntity person2 = new PersonEntity("John Doe");
        person2.setId(id);
        assertEquals(person1.hashCode(), person2.hashCode());
    }

    @Test
    public void testToString() {
        UUID id = UUID.randomUUID();
        PersonEntity person = new PersonEntity("John Doe");
        person.setId(id);
        String expected = "PersonEntity{id=" + id + ", name='John Doe'}";
        assertEquals(expected, person.toString());
    }
}
