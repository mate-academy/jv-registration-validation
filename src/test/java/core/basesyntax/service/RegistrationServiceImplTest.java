package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationError.class, () -> registrationService.register(null));
    }

    @Test
    void register_nullName_notOk() {
        User user = new User(null, "password", 25);
        assertThrows(RegistrationError.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortName_notOk() {
        // Length 0
        User user1 = new User("", "password", 25);
        assertThrows(RegistrationError.class, () -> registrationService.register(user1));
        // Length 3
        User user2 = new User("Ben", "password", 25);
        assertThrows(RegistrationError.class, () -> registrationService.register(user2));
        // Length 5
        User user3 = new User("Alice", "password", 25);
        assertThrows(RegistrationError.class, () -> registrationService.register(user3));
    }

    @Test
    void register_validName_ok() {
        // Length 6
        User user1 = new User("Robert", "asdgfw45t45", 20);
        User actual = registrationService.register(user1);
        assertEquals(user1, actual);
        // Length 8
        User user2 = new User("Robert20", "vcewjfjew", 20);
        actual = registrationService.register(user2);
        assertEquals(user2, actual);
    }

    @Test
    void register_nullPassword_notOk() {
        User user = new User("Benjamin", null, 25);
        assertThrows(RegistrationError.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortPassword_notOk() {
        // Length 0
        User user1 = new User("Benjamin", "", 25);
        assertThrows(RegistrationError.class, () -> registrationService.register(user1));
        // Length 3
        User user2 = new User("Benjamin", "123", 25);
        assertThrows(RegistrationError.class, () -> registrationService.register(user2));
        // Length 5
        User user3 = new User("Benjamin", "12345", 25);
        assertThrows(RegistrationError.class, () -> registrationService.register(user3));
    }

    @Test
    void register_validPassword_ok() {
        // Length 6
        User user1 = new User("Estelle27", "sw2de3", 27);
        User actual = registrationService.register(user1);
        assertEquals(user1, actual);
        // Length 8
        User user2 = new User("Estelle25", "nmhjyu67", 25);
        actual = registrationService.register(user2);
        assertEquals(user2, actual);
    }

    @Test
    void register_nullAge_notOk() {
        User user = new User("Benjamin", "123456", null);
        assertThrows(RegistrationError.class, () -> registrationService.register(user));
    }

    @Test
    void register_zeroAge_notOk() {
        User user = new User("Benjamin", "123456", 0);
        assertThrows(RegistrationError.class, () -> registrationService.register(user));
    }

    @Test
    void register_negativeAge_notOk() {
        // Age -10
        User user1 = new User("Benjamin", "123456", -10);
        assertThrows(RegistrationError.class, () -> registrationService.register(user1));
        // Min age
        User user2 = new User("Benjamin", "123456", Integer.MIN_VALUE);
        assertThrows(RegistrationError.class, () -> registrationService.register(user2));
    }

    @Test
    void register_youngUser_notOk() {
        // Age 0
        User user1 = new User("Benjamin", "123456", 0);
        assertThrows(RegistrationError.class, () -> registrationService.register(user1));
        // Age 10
        User user2 = new User("Benjamin", "123456", 10);
        assertThrows(RegistrationError.class, () -> registrationService.register(user2));
        // Age 15
        User user3 = new User("Benjamin", "123456", 15);
        assertThrows(RegistrationError.class, () -> registrationService.register(user3));
        // Age 17
        User user4 = new User("Benjamin", "123456", 17);
        assertThrows(RegistrationError.class, () -> registrationService.register(user4));
    }

    @Test
    void register_tooBigAge_ok() {
        // Age 999
        User user1 = new User("Ted999", "grewtg3", 999);
        User actual = registrationService.register(user1);
        assertEquals(user1, actual);
        // Max age
        User user2 = new User("Odin777", "graeger6", Integer.MAX_VALUE);
        actual = registrationService.register(user2);
        assertEquals(user2, actual);
    }

    @Test
    void register_validAge_ok() {
        // Length 18
        User user1 = new User("Bobby27", "gresg5w4e5", 18);
        User actual = registrationService.register(user1);
        assertEquals(user1, actual);
        // Length 99
        User user2 = new User("John99", "zdfgvb5w453", 99);
        actual = registrationService.register(user2);
        assertEquals(user2, actual);
    }

    @Test
    void register_doubleRegistrationSameUser_notOk() {
        User user = new User("Alexander", "qwerty", 27);
        Storage.people.add(user);
        assertThrows(RegistrationError.class, () -> registrationService.register(user));
    }

    @Test
    void register_twoUsersWithSameLogin_notOk() {
        User user1 = new User("Benjamin", "123456", 25);
        Storage.people.add(user1);
        User user2 = new User("Benjamin", "654321", 35);
        assertThrows(RegistrationError.class, () -> registrationService.register(user2));
    }

    @Test
    void register_validUsers_ok() {
        User user1 = new User("Roberto", "nbgt%^&", 20);
        User actual = registrationService.register(user1);
        assertEquals(user1, actual);
        User user2 = new User("Montgomery", "befdaser", 40);
        actual = registrationService.register(user2);
        assertEquals(user2, actual);
        User user3 = new User("Giselle", "fastfast", 19);
        actual = registrationService.register(user3);
        assertEquals(user3, actual);
        User user4 = new User("Esmeralda", "987654321", 55);
        actual = registrationService.register(user4);
        assertEquals(user4, actual);
    }
}
