package core.basesyntax.service;

import core.basesyntax.exeption.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationServiceImpl registrationService = new RegistrationServiceImpl();
    private User user;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        user = new User();
        user.setLogin("Daedrus");
        user.setAge(25);
        user.setPassword("Daedrus123");

    }

    @Test
    void nullUser_NotOk() {
        Assertions.assertThrows(RegistrationException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void nullLogin_NotOk() {
        Assertions.assertThrows(RegistrationException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void nullPassword_NotOk() {
        Assertions.assertThrows(RegistrationException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void nullAge_NotOk() {
        Assertions.assertThrows(RegistrationException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void user_Age_NotOk() {
        user.setAge(17);
        Assertions.assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void user_shortLogin_NotOk() {
        user.setLogin("Jonn");
        Assertions.assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void user_short_password_NotOk() {
        user.setPassword("aksk1");
        Assertions.assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }
}
