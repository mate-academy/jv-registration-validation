package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String VALID_LOGIN = "abcdefg";
    private static final String VALID_PASSWORD = "123456";
    private static final String LOGIN_LESS_THAN_6_CHARACTERS = "abcde";
    private static final String PASSWORD_LESS_THAN_6_CHARACTERS = "12345";
    private static final int AGE_LESS_THAN_MIN = 10;
    private static final int MIN_AGE = 18;
    private static RegistrationService registrationService;
    private User user = new User(VALID_LOGIN, VALID_PASSWORD, MIN_AGE);

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_minLogin_notOk() {
        user.setLogin(LOGIN_LESS_THAN_6_CHARACTERS);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_minPassword_notOk() {
        user.setPassword(PASSWORD_LESS_THAN_6_CHARACTERS);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_minAge_notOk() {
        user.setAge(AGE_LESS_THAN_MIN);
        assertThrows(RegistrationException.class,() -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_existingUser_notOk() {
        Storage.people.add(user);
        assertThrows(RegistrationException.class,() -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_validLogin_ok() {
        user.setLogin(VALID_LOGIN);
        registrationService.register(user);
        assertTrue(Storage.people.contains(user));
    }

    @Test
    void register_validPassword_ok() {
        user.setPassword(VALID_LOGIN);
        registrationService.register(user);
        assertTrue(Storage.people.contains(user));
    }

    @Test
    void register_validAge_ok() {
        user.setAge(MIN_AGE);
        registrationService.register(user);
        assertTrue(Storage.people.contains(user));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
