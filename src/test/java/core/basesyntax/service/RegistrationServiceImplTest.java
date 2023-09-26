package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    void register_nullValue_notOk() {
        assertThrows(RegistrationError.class, () -> registrationService.register(null));
    }

    @Test
    void register_shortName_notOk() {
        User user = new User(0L, "Ben", "password", 25);
        assertThrows(RegistrationError.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortPassword_notOk() {
        User user = new User(0L, "Benjamin", "1234", 25);
        assertThrows(RegistrationError.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        User user = new User(0L, "Benjamin", "123456", null);
        assertThrows(RegistrationError.class, () -> registrationService.register(user));
    }

    @Test
    void register_youngUser_notOk() {
        User user = new User(0L, "Benjamin", "123456", 15);
        assertThrows(RegistrationError.class, () -> registrationService.register(user));
    }

    @Test
    void register_doubleRegistration_notOk() {
        User user = new User(0L, "Alexander", "qwerty", 27);
        registrationService.register(user);
        assertThrows(RegistrationError.class, () -> registrationService.register(user));
    }

    @Test
    void register_twoUsersWithSameLogin_notOk() {
        User user1 = new User(1L, "Benjamin", "123456", 25);
        registrationService.register(user1);
        User user2 = new User(2L, "Benjamin", "654321", 35);
        assertThrows(RegistrationError.class, () -> registrationService.register(user2));
    }

    @Test
    void register_validUsers_ok() {
        User user1 = new User(3L, "Roberto", "nbgt%^&", 20);
        User actual = registrationService.register(user1);
        assertEquals(user1, actual);
        User user2 = new User(4L, "Montgomery", "befdaser", 40);
        actual = registrationService.register(user2);
        assertEquals(user2, actual);
        User user3 = new User(5L, "Giselle", "fastfast", 19);
        actual = registrationService.register(user3);
        assertEquals(user3, actual);
        User user4 = new User(6L, "Esmeralda", "987654321", 55);
        actual = registrationService.register(user4);
        assertEquals(user4, actual);
    }
}
