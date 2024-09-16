package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

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
        User user = getUser();
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void shouldThrowRegistrationExceptionWhenPasswordIsNull() {
        User user = getUser();
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void shouldThrowRegistrationExceptionWhenAgeIsNull() {
        User user = getUser();
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void shouldThrowRegistrationExceptionWhenAgeIsNegative() {
        User user = getUser();
        user.setAge(-1);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void shouldThrowRegistrationExceptionWhenAgeNotOk() {
        User user = getUser();
        user.setAge(17);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void shouldThrowRegistrationExceptionWhenLoginIsShort() {
        User user = getUser();
        user.setLogin("Jonn");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void shouldThrowRegistrationExceptionWhenPasswordIsShort() {
        User user = getUser();
        user.setPassword("aksk1");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void shouldThrowRegistrationExceptionWhenUserIsExist() throws RegistrationException {
        User user = getUser();
        registrationService.register(user);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    private User getUser() {
        User user = new User();
        user.setLogin("Daedrus");
        user.setAge(25);
        user.setPassword("Daedrus123");
        return user;
    }
}
