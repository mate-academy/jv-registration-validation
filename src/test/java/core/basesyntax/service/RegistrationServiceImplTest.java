package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    static final int VALID_AGE = 45;
    static final int INVALID_AGE1 = 15;
    static final int INVALID_AGE2 = 180;
    static final String VALID_PASSWORD = "qwerty";
    static final String INVALID_PASSWORD = "1234";
    static final String LOGIN = "login";
    static final String LOGIN2 = "login2";
    private RegistrationService registrationService;
    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        user1 = new User();
        user1.setAge(VALID_AGE);
        user1.setPassword(VALID_PASSWORD);
        user1.setLogin(LOGIN);
        user2 = new User();
        user2.setAge(VALID_AGE);
        user2.setPassword(VALID_PASSWORD);
        user2.setLogin(LOGIN2);
    }

    @Test
    void register_validData_Ok() {
        User actual = registrationService.register(user2);
        assertEquals(user2, actual);
    }

    @Test
    void register_nullAge_notOk() {
        user1.setAge(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user1);
        });
    }

    @Test
    void register_invalidAge_notOk() {
        user1.setAge(INVALID_AGE1);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user1);
        });
        user1.setAge(INVALID_AGE2);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user1);
        });
    }

    @Test
    void register_nullPassword_notOk() {
        user1.setPassword(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user1);
        });
    }

    @Test
    void register_invalidPassword_notOk() {
        user1.setPassword(INVALID_PASSWORD);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user1);
        });
    }

    @Test
    void register_nullLogin_notOk() {
        user1.setLogin(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user1);
        });
    }

    @Test
    void register_userAlreadyExist_notOk() {
        User userClone = user1;
        registrationService.register(user1);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(userClone);
        });
    }
}
