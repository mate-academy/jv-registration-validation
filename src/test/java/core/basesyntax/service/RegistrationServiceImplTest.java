package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exeption.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;

    @BeforeAll
    static void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void shouldThrowRegistrationExceptionWhenUserIsNull() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void shouldThrowRegistrationExceptionWhenLoginIsNull() {
        User user = initialize();
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void shouldThrowRegistrationExceptionWhenPasswordIsNull() {
        User user = initialize();
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void shouldThrowRegistrationExceptionWhenAgeIsNull() {
        User user = initialize();
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void shouldThrowRegistrationExceptionWhenAgeIsNegative() {
        User user = initialize();
        user.setAge(-1);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void shouldThrowRegistrationExceptionWhenAgeNotOk() {
        User user = initialize();
        user.setAge(17);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void shouldThrowRegistrationExceptionWhenLoginIsShort() {
        User user = initialize();
        user.setLogin("Jonn");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void shouldThrowRegistrationExceptionWhenPasswordIsShort() {
        User user = initialize();
        user.setPassword("aksk1");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void shouldThrowRegistrationExceptionWhenUserIsExist() {
        User user = initialize();
        Storage.people.add(user);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void shouldValidUser() {
        User user = initialize();
        Assertions.assertDoesNotThrow(() -> {
            registrationService.register(user);
        });
    }

    private User initialize() {
        User user = new User();
        user.setLogin("Daedrus");
        user.setAge(25);
        user.setPassword("Daedrus123");
        return user;
    }
}
