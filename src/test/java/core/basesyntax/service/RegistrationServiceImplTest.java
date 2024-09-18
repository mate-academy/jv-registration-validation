package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exeption.RegistrationException;
import core.basesyntax.model.User;
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
        User user = initializeUser();
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void shouldThrowRegistrationExceptionWhenPasswordIsNull() {
        User user = initializeUser();
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void shouldThrowRegistrationExceptionWhenAgeIsNull() {
        User user = initializeUser();
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void shouldThrowRegistrationExceptionWhenAgeIsNegative() {
        User user = initializeUser();
        user.setAge(-1);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void shouldThrowRegistrationExceptionWhenAgeNotOk() {
        User user = initializeUser();
        user.setAge(17);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void shouldThrowRegistrationExceptionWhenLoginIsShort() {
        User user = initializeUser();
        user.setLogin("Jonn");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void shouldThrowRegistrationExceptionWhenPasswordIsShort() {
        User user = initializeUser();
        user.setPassword("aksk1");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void shouldThrowRegistrationExceptionWhenUserIsExist() {
        User user = initializeUser();
        Storage.people.add(user);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void shouldValidUser() throws RegistrationException {
        User user = initializeUser();
        User actual = registrationService.register(user);
        assertEquals(actual, user);
    }

    private User initializeUser() {
        User user = new User();
        user.setLogin("Daedrus");
        user.setAge(25);
        user.setPassword("Daedrus123");
        return user;
    }
}
