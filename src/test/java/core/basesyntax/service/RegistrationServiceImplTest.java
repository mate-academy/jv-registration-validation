package core.basesyntax.service;

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
    void checkIsNotEnoughAge_NotOK() {
        User user = new User("Loginnir", "Password", 5);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        }, "Expected RegistrationException for user with insufficient age.");
    }

    @Test
    void checkIsShortPassword_NotOK() {
        User user = new User(null, "Pass", 19);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        }, "Expected RegistrationException for user with short password.");
    }

    @Test
    void checkIsShortLogin_NotOK() {
        User user = new User("Log", "Passwordius", 35);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        }, "Expected RegistrationException for user with short login.");
    }

    @Test
    void checkIsLoginAlreadyRegistered_NotOK() {
        User user = new User("Loginius", "Passwordius", 35);
        registrationService.register(user);

        User userWithSameLogin = new User("Loginius", "Passw1231", 25);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userWithSameLogin);
        }, "Expected RegistrationException for user with duplicate login.");
    }
}
