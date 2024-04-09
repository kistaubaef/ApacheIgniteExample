package bdtc.lab3.controller.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
public class TestServicePersonTest {

    @Test
    public void constructor_setsNameToEmpty() {
        // Arrange and Act
        Person person = new Person();

        // Assert
        assertNull(person.getName());
    }

    @Test
    public void constructor_setsName() {
        // Arrange
        String name = "John";

        // Act
        Person person = new Person(name);

        // Assert
        assertEquals(name, person.getName());
    }

    @Test
    public void getName_returnsName() {
        // Arrange
        String name = "John";
        Person person = new Person(name);

        // Act
        String actualName = person.getName();

        // Assert
        assertEquals(name, actualName);
    }

    @Test
    public void setName_setsName() {
        // Arrange
        String name = "John";
        Person person = new Person();

        // Act
        person.setName(name);
        String actualName = person.getName();

        // Assert
        assertEquals(name, actualName);
    }

    @Test
    public void equals_returnsTrueIfSameObject() {
        // Arrange
        Person person = new Person();

        // Act
        boolean result = person.equals(person);

        // Assert
        assertTrue(result);
    }

    @Test
    public void equals_returnsTrueIfSameName() {
        // Arrange
        Person person1 = new Person("John");
        Person person2 = new Person("John");

        // Act
        boolean result = person1.equals(person2);

        // Assert
        assertTrue(result);
    }

    @Test
    public void equals_returnsFalseIfDifferentName() {
        // Arrange
        Person person1 = new Person("John");
        Person person2 = new Person("Jane");

        // Act
        boolean result = person1.equals(person2);

        // Assert
        assertFalse(result);
    }

    @Test
    public void equals_returnsFalseIfDifferentTypes() {
        // Arrange
        Person person = new Person();
        String otherString = "John";

        // Act
        boolean result = person.equals(otherString);

        // Assert
        assertFalse(result);
    }

    @Test
    public void equals_returnsFalseIfNull() {
        // Arrange
        Person person = new Person();

        assertFalse(person.equals(null));
    }

    @Test
    public void hashCode_returnsSameValueForSameObject() {
        // Arrange
        Person person = new Person();

        // Act
        int result = person.hashCode();

        // Assert
        assertEquals(person.hashCode(), result);
    }

    @Test
    public void hashCode_returnsSameValueForSameName() {
        // Arrange
        Person person1 = new Person("John");
        Person person2 = new Person("John");

        // Act
        int result1 = person1.hashCode();
        int result2 = person2.hashCode();

        // Assert
        assertEquals(result1, result2);
    }

    @Test
    public void hashCode_returnsDifferentValueForDifferentName() {
        // Arrange
        Person person1 = new Person("John");
        Person person2 = new Person("Jane");

        // Act
        int result1 = person1.hashCode();
        int result2 = person2.hashCode();

        // Assert
        assertNotEquals(result1, result2);
    }

    @Test
    public void toString_includesName() {
        // Arrange
        String name = "John";
        Person person = new Person(name);

        // Act
        String actualString = person.toString();

        // Assert
        assertTrue(actualString.contains(name));
    }
}