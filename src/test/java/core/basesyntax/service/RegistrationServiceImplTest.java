package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String DEFAULT_LOGIN = "user_Lili";
    private static final String VALID_PASSWORD = "password";
    private static final int VALID_AGE = 24;
    private RegistrationService registrationService;
    private User user;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        user = new User();
        user.setLogin(DEFAULT_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
    }

    @Test
    void register_validData_registerOk() {
        User actual = user;
        assertEquals(registrationService.register(user), actual);
    }

    @Test
    void register_notValidAge_callException() {
        user.setAge(13);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
        user.setAge(0);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
        user.setAge(145);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_notValidPassword_callException() {
        user.setPassword("123");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullPassword_callException() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_existingLogin_callException() {
        User newUser = new User();
        newUser.setLogin(user.getLogin());
        newUser.setPassword("12345678");
        newUser.setAge(24);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @Test
    void register_nullLogin_callException() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }
}
