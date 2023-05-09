package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private User user1;
    private User user2;
    private User user3;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user1 = new User("Bob Visconti", "lkwNJOHAGHGJG", 30);
        user2 = new User("Alice Armstrong", "gdsjhhbgvbh", 20);
        user3 = new User("John Stuard", "snkcbu3637y2hrf", 25);
    }

    @Test
    void register_UserIsNull_NotOk() {
        assertThrows(RegistrationException.class, () ->
                registrationService.register(null));
    }

    @Test
    void register_SameLogin_NotOk() {
        Storage.people.add(user1);
        Storage.people.add(user2);
        Storage.people.add(user3);
        User userWithSameLogin1 = new User("Bob Visconti", "gdsjhhbgvbh", 30);
        User userWithSameLogin2 = new User("Alice Armstrong", "gdsjhhbgvbh", 30);
        User userWithSameLogin3 = new User("John Stuard", "gdsjhhbgvbh", 30);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(userWithSameLogin1));
        assertThrows(RegistrationException.class, () ->
                registrationService.register(userWithSameLogin2));
        assertThrows(RegistrationException.class, () ->
                registrationService.register(userWithSameLogin3));
    }

    @Test
    void register_NullData_NotOk() {
        User userWithNullPassword = new User("Petro Stuard", null, 30);
        User userWithNullLogin = new User(null, "gdsjhhbgvbh", 30);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(userWithNullPassword));
        assertThrows(RegistrationException.class, () ->
                registrationService.register(userWithNullLogin));
    }

    @Test
    void register_AgeLessThanMinAge_NotOk() {
        User userWithLessAge1 = new User("Victoria Markevych", "gdsjhhbgvbh", 10);
        User userWuthLessAge2 = new User("Anna Chen", "gdsjhhbgvbh", 13);
        User userWithLessAge3 = new User("Petro Bosy", "gdsjhhbgvbh", 17);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(userWithLessAge1));
        assertThrows(RegistrationException.class, () ->
                registrationService.register(userWuthLessAge2));
        assertThrows(RegistrationException.class, () ->
                registrationService.register(userWithLessAge3));
    }

    @Test
    void register_ShortLogin_NotOk() {
        User userWithShortLogin1 = new User("Bob", "gdsjhhbgvbh", 18);
        User userWithShortLogin2 = new User("Petro", "gdsjhhbgvbh", 19);
        User userWithShortLogin3 = new User("", "gdsjhhbgvbh", 19);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(userWithShortLogin1));
        assertThrows(RegistrationException.class, () ->
                registrationService.register(userWithShortLogin2));
        assertThrows(RegistrationException.class, () ->
                registrationService.register(userWithShortLogin3));
    }

    @Test
    void registre_ShortPassword_NotOk() {
        User userWithShortPassword1 = new User("Victoria Markevych", "gdsj", 20);
        User userWithShortPassword2 = new User("Anna Chen", "gdsjh", 23);
        User userWithShortPassword3 = new User("Petro Bosy", "", 19);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(userWithShortPassword1));
        assertThrows(RegistrationException.class, () ->
                registrationService.register(userWithShortPassword2));
        assertThrows(RegistrationException.class, () ->
                registrationService.register(userWithShortPassword3));
    }

    @Test
    void register_ValidData_Ok() {
        assertEquals(user1, registrationService.register(user1));
        assertEquals(user2, registrationService.register(user2));
        assertEquals(user3, registrationService.register(user3));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
