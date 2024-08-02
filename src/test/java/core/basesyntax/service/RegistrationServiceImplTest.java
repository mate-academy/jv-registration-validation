package core.basesyntax.service;

import core.basesyntax.exception.UserValidationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static User user;
    private final RegistrationService registrationService = new RegistrationServiceImpl();

    @BeforeAll
    static void createValidUser() {
        user = new User();
        user.setLogin("validLogin");
        user.setPassword("validPassword");
        user.setAge(20);
    }

    @Test
    void register_nullUser_notOk() {
        Assertions.assertThrows(UserValidationException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_nullUserLogin_notOk() {
        user.setLogin(null);
        Assertions.assertThrows(UserValidationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullUserPassword_notOk() {
        user.setPassword(null);
        Assertions.assertThrows(UserValidationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullUserAge_notOk() {
        user.setAge(null);
        Assertions.assertThrows(UserValidationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_underAge_notOk() {
        user.setAge(17);
        Assertions.assertThrows(UserValidationException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_shortLogin_notOk() {
        user.setLogin("short");
        Assertions.assertThrows(UserValidationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_shortPassword_notOk() {
        user.setPassword("short");
        Assertions.assertThrows(UserValidationException.class, () -> {
            registrationService.register(user);
        });
    }
}
