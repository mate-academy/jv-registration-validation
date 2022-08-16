package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    public static final String DEFAULT_LOGIN = "login";
    public static final String DEFAULT_PASSWORD = "password";
    public static final int DEFAULT_AGE = 30;
    private static RegistrationService registrationService;
    private User user;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        user = new User();
        user.setLogin(DEFAULT_LOGIN);
        user.setPassword(DEFAULT_PASSWORD);
        user.setAge(DEFAULT_AGE);
    }

    @Test
    void register_validData_Ok() {
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_TwoUsers_Ok() {
        User user2 = new User();
        user2.setLogin("user2");
        user2.setPassword(DEFAULT_PASSWORD);
        user2.setAge(DEFAULT_AGE);
        registrationService.register(user);
        registrationService.register(user2);
        assertTrue(Storage.people.contains(user));
        assertTrue(Storage.people.contains(user2));
    }

    @Test
    void register_emptyLogin_NotOk() {
        user.setLogin("");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_invalidLogin_NotOk() {
        User user2 = new User();
        user2.setLogin(DEFAULT_LOGIN);
        user2.setPassword(DEFAULT_PASSWORD);
        user2.setAge(DEFAULT_AGE);
        registrationService.register(user2);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_invalidAge_NotOk() {
        user.setAge(16);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_invalidPassword_NotOk() {
        user.setPassword("pass");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullUser_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_nullLogin_NotOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullAge_NotOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullPassword_NotOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
