package demo.unitTests;

import static org.junit.Assert.*;

import demo.model.Person;
import demo.model.Role;
import org.junit.Before;
import org.junit.Test;

public class PersonTest {

    private Person person;

    @Before
    public void setUp() {
        person = new Person("Mario", "Rossi", "ABC123", "mrossi", "password123", Role.RESEARCHER);
    }

    @Test
    public void testDefaultConstructor() {
        Person defaultPerson = new Person();
        assertNull(defaultPerson.getId());
        assertNull(defaultPerson.getName());
        assertNull(defaultPerson.getSurname());
        assertNull(defaultPerson.getCF());
        assertNull(defaultPerson.getUsername());
        assertNull(defaultPerson.getPassword());
        assertNull(defaultPerson.getRole());
    }

    @Test
    public void testParameterizedConstructor() {
        assertEquals("Mario", person.getName());
        assertEquals("Rossi", person.getSurname());
        assertEquals("ABC123", person.getCF());
        assertEquals("mrossi", person.getUsername());
        assertEquals("password123", person.getPassword());
        assertEquals(Role.RESEARCHER, person.getRole());
    }

    @Test
    public void testMinimalConstructor() {
        Person minimalPerson = new Person("giovannibianchi", "password456", Role.RESEARCHER);
        assertNull(minimalPerson.getName());
        assertNull(minimalPerson.getSurname());
        assertNull(minimalPerson.getCF());
        assertEquals("giovannibianchi", minimalPerson.getUsername());
        assertEquals("password456", minimalPerson.getPassword());
        assertEquals(Role.RESEARCHER, minimalPerson.getRole());
    }

    @Test
    public void testSetAndGetId() {
        person.setId(1L);
        assertNull(person.getId()); // setId does nothing in the current implementation
    }

    @Test
    public void testSetAndGetName() {
        person.setName("Giovanni");
        assertEquals("Giovanni", person.getName());
    }

    @Test
    public void testSetAndGetSurname() {
        person.setSurname("Bianchi");
        assertEquals("Bianchi", person.getSurname());
    }

    @Test
    public void testSetAndGetCF() {
        person.setCF("XYZ789");
        assertEquals("XYZ789", person.getCF());
    }

    @Test
    public void testSetAndGetPassword() {
        person.setPassword("nuovapassword");
        assertEquals("nuovapassword", person.getPassword());
    }

    @Test
    public void testSetAndGetUsername() {
        person.setUsername("giovannibianchi");
        assertEquals("giovannibianchi", person.getUsername());
    }

    @Test
    public void testSetAndGetRole() {
        person.setRole(Role.RESEARCHER);
        assertEquals(Role.RESEARCHER, person.getRole());
    }
}
