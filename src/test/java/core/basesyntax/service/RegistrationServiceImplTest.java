package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_nullUser() {
        assertThrows(RegistrationUserException.class, () -> registrationService.register(null));
    }

    @Test
    void nullLogin_NotOk() {
        User user = new User(null, "wqr32r23r", 40);
        assertThrows(RegistrationUserException.class, () -> registrationService.register(user));
    }

    @Test
    void checkLogin_Ok() {
        User user = new User("qwe", "qewrqt23", 20);
        assertThrows(RegistrationUserException.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginAlreadyExists() {
        User user = new User("user345", "password", 20);
        registrationService.register(user);
        assertThrows(RegistrationUserException.class, () -> registrationService.register(user));
    }

    @Test
    void checkPassword_Ok() {
        User user = new User("user135", "qwe", 25);
        assertThrows(RegistrationUserException.class, () -> registrationService.register(user));
    }

    @Test
    void nullPassword_NotOk() {
        User user = new User("user567", null, 40);
        assertThrows(RegistrationUserException.class, () -> registrationService.register(user));
    }

    @Test
    void checkAge_NotOk() {
        User user = new User("user246", "password", 12);
        assertThrows(RegistrationUserException.class, () -> registrationService.register(user));
    }

    @Test
    void nullAge_NotOk() {
        User user = new User("user678", "password", null);
        assertThrows(RegistrationUserException.class, () -> registrationService.register(user));
    }

    @Test
    void register_successfulRegistration() {
        User user = new User("user456", "password", 20);
        User registeredUser = registrationService.register(user);
        assertNotNull(registeredUser.getId());
        assertEquals(user.getLogin(), registeredUser.getLogin());
        assertEquals(user.getPassword(), registeredUser.getPassword());
        assertEquals(user.getAge(), registeredUser.getAge());
    }

    @Test
    void register_invalidAge() {
        User user = new User("user234", "password", 16);
        assertThrows(RegistrationUserException.class, () -> registrationService.register(user));
    }

    @Test
    void register_invalidLogin() {
        User user = new User("a", "password", 20);
        assertThrows(RegistrationUserException.class, () -> registrationService.register(user));
    }

    @Test
    void register_invalidPassword() {
        User user = new User("user123", "pass", 20);
        assertThrows(RegistrationUserException.class, () -> registrationService.register(user));
    }
}
