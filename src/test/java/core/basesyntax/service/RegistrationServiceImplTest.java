package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private final RegistrationService registrationService = new RegistrationServiceImpl();

    @Test
    void register() {
        User user = new User("Bobusun", "bob123", 18);
        User actualUser = registrationService.register(user);
        assertEquals(user, actualUser);
    }

    @Test
    void registerWithNullUser() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void registerWithNullLogin() {
        User user = new User(null, "bob123", 18);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void registerWithNullPassword() {
        User user = new User("Bobusun", null, 18);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void registerWithNullAge() {
        User user = new User("Bobusun", "bob123", null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void registerWithEmptyLogin() {
        User user = new User("", "bob123", 18);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void registerWithEmptyPassword() {
        User user = new User("Bobusun", "", 18);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
        void registerWithNegativeAge() {
        User user = new User("Bobusun", "bob123", -1);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void registerLoginLessThan6() {
        User user = new User("Bob", "bob123", 18);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void registerPasswordLessThen6() {
        User user = new User("Bobusun", "bob", 18);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }
}
