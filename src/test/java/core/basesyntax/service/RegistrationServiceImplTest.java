package core.basesyntax.service;

import core.basesyntax.exception.UserValidationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int VALID_AGE = 20;
    private static final int UNDER_AGE = 17;
    private static final String VALID_LOGIN = "validLogin";
    private static final String VALID_PASSWORD = "validPassword";
    private static final String SHORT_INPUT = "short";
    private User user;
    private final RegistrationService registrationService = new RegistrationServiceImpl();

    @BeforeEach
    void createValidUser() {
        user = new User();
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
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
        user.setAge(UNDER_AGE);
        Assertions.assertThrows(UserValidationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_shortLogin_notOk() {
        user.setLogin(SHORT_INPUT);
        Assertions.assertThrows(UserValidationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_shortPassword_notOk() {
        user.setPassword(SHORT_INPUT);
        Assertions.assertThrows(UserValidationException.class, () -> {
            registrationService.register(user);
        });
    }
}
