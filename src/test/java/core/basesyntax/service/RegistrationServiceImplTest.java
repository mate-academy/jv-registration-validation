package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        Storage.people.add(new User("Bob Visconti", "password", 20));
        Storage.people.add(new User("Alice Armstrong", "drowssap", 25));
        Storage.people.add(new User("John Stuard", "qwertyui", 27));
    }

    @Test
    void register_SameLogin_NotOk() {
        assertThrows(RegistrationException.class, () ->
                registrationService.register(new User("Bob Visconti", "gdsjhhbgvbh", 30)));
        assertThrows(RegistrationException.class, () ->
                registrationService.register(new User("Alice Armstrong", "gdsjhhbgvbh", 30)));
        assertThrows(RegistrationException.class, () ->
                registrationService.register(new User("John Stuard", "gdsjhhbgvbh", 30)));
    }

    @Test
    void register_NullData_NotOk() {
        assertThrows(RegistrationException.class, () ->
                registrationService.register(new User("Petro Stuard", null, 30)));
        assertThrows(RegistrationException.class, () ->
                registrationService.register(new User(null, "gdsjhhbgvbh", 30)));
    }

    @Test
    void register_AgeLessThanMinAge_NotOk() {
        assertThrows(RegistrationException.class, () ->
                registrationService.register(new User("Victoria Markevych", "gdsjhhbgvbh", 10)));
        assertThrows(RegistrationException.class, () ->
                registrationService.register(new User("Anna Chen", "gdsjhhbgvbh", 13)));
        assertThrows(RegistrationException.class, () ->
                registrationService.register(new User("Petro Bosy", "gdsjhhbgvbh", 17)));
    }

    @Test
    void register_NotValidLogin_NotOk() {
        assertThrows(RegistrationException.class, () ->
                registrationService.register(new User("Bob", "gdsjhhbgvbh", 18)));
        assertThrows(RegistrationException.class, () ->
                registrationService.register(new User("Anna", "gdsjhhbgvbh", 23)));
        assertThrows(RegistrationException.class, () ->
                registrationService.register(new User("Petro", "gdsjhhbgvbh", 19)));
        assertThrows(RegistrationException.class, () ->
                registrationService.register(new User("", "gdsjhhbgvbh", 19)));
    }

    @Test
    void registre_NotValidPassword_NotOk() {
        assertThrows(RegistrationException.class, () ->
                registrationService.register(new User("Victoria Markevych", "gdsj", 20)));
        assertThrows(RegistrationException.class, () ->
                registrationService.register(new User("Anna Chen", "gdsjh", 23)));
        assertThrows(RegistrationException.class, () ->
                registrationService.register(new User("Petro Bosy", "", 19)));
    }

    @Test
    void register_ValidData_Ok() {
        assertEquals(new User("Victoria Markevych", "gdsjjefbfb", 20),
                registrationService.register(new User("Victoria Markevych", "gdsjjefbfb", 20)));
        assertEquals(new User("Anna Chen", "gdsjhu", 23),
                registrationService.register(new User("Anna Chen", "gdsjhu", 23)));
        assertEquals(new User("Jan Co", "gdsjh5", 18),
                registrationService.register(new User("Jan Co", "gdsjh5", 18)));
    }

}



