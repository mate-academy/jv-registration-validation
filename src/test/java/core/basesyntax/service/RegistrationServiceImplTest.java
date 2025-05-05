package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
    void register_nullUser_NotOk() {
        assertThrows(RegistrationUserException.class, () -> registrationService.register(null));
    }

    @Test
    void register_nullLogin_NotOk() {
        User user = new User(null, "wqr32r23r", 40);
        assertThrows(RegistrationUserException.class, () -> registrationService.register(user));
    }

    @Test
    void register_checkLogin_Ok() {
        User user = new User("qwe", "qewrqt23", 20);
        assertThrows(RegistrationUserException.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginAlreadyExists_NotOk() {
        User user = new User("user345", "password", 20);
        registrationService.register(user);
        assertThrows(RegistrationUserException.class, () -> registrationService.register(user));
    }

    @Test
    void register_checkPassword_Ok() {
        User user = new User("user135", "qwe", 25);
        assertThrows(RegistrationUserException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_NotOk() {
        User user = new User("user567", null, 40);
        assertThrows(RegistrationUserException.class, () -> registrationService.register(user));
    }

    @Test
    void register_checkAge_NotOk() {
        User user = new User("user246", "password", 12);
        assertThrows(RegistrationUserException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullAge_NotOk() {
        User user = new User("user678", "password", null);
        assertThrows(RegistrationUserException.class, () -> registrationService.register(user));
    }

    @Test
    void register_successfulRegistration_Ok() {
        User user = new User("user456", "password", 20);
        User registeredUser = registrationService.register(user);
        assertNotNull(registeredUser.getId());
        assertEquals(user.getLogin(), registeredUser.getLogin());
        assertEquals(user.getPassword(), registeredUser.getPassword());
        assertEquals(user.getAge(), registeredUser.getAge());
    }

    @Test
    void register_invalidAge_Ok() {
        User user = new User("user234", "password", 16);
        assertThrows(RegistrationUserException.class, () -> registrationService.register(user));
    }

    @Test
    void register_invalidLogin_Ok() {
        User user = new User("a", "password", 20);
        assertThrows(RegistrationUserException.class, () -> registrationService.register(user));
    }

    @Test
    void register_invalidPassword_Ok() {
        User user = new User("user123", "pass", 20);
        assertThrows(RegistrationUserException.class, () -> registrationService.register(user));
    }
}
