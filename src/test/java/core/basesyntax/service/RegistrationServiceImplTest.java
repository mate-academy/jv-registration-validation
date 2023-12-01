package core.basesyntax.service;

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
    void userAlreadyExists_NotOk() {
        User user = new User("Bobusun", "bob123", 18);
        registrationService.register(user);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void nullValue_NotOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void nullLogin_NotOk() {
        User user = new User(null, "bob123", 18);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void nullPassword_NotOk() {
        User user = new User("Bobusun", null, 18);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void nullAge_NotOk() {
        User user = new User("Bobusun", "bob123", null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void emptyLogin_NotOk() {
        User user = new User("", "bob123", 18);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void emptyPassword_NotOk() {
        User user = new User("Bobusun", "", 18);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
        void negativeAge_NotOk() {
        User user = new User("Bobusun", "bob123", -1);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void loginLessThan6_NotOk() {
        User user = new User("Bob", "bob123", 18);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void passwordLessThen6_NotOk() {
        User user = new User("Bobusun", "bob", 18);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void ageLessThen18_NotOk() {
        User user = new User("Bobusun", "bob123", 17);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }
}
