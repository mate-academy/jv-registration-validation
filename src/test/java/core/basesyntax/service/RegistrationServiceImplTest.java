package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.exeption.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int MIN_LOGIN = 6;
    private static final int MIN_PASSWORD = 6;
    private static final int MIN_AGE = 18;
    private static final int BAD_AGE = 16;
    private static final int GOOD_AGE = 20;
    private static final String LOGIN = "Oleksandr";
    private static final String BAD_LOGIN = "Osa";
    private static final String GOOD_LOGIN = "CorrectLogin";
    private static final String PASSWORD = "rytop12Qt";
    private static final String BAD_PASSWORD = "123";
    private static final String GOOD_PASSWORD = "qwerty987";

    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin(LOGIN);
        user.setPassword(PASSWORD);
        user.setAge(MIN_AGE);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(null);
        }, "User can't be null");
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        }, "Login can't be null");
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        }, "Password can't be null");
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        }, "Age can't be null");
    }

    @Test
    void register_minLogin_notOk() {
        user.setLogin(BAD_LOGIN);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        }, "Login must be more then " + MIN_LOGIN + "characters");
    }

    @Test
    void register_minPassword_notOk() {
        user.setPassword(BAD_PASSWORD);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        }, "Password must be more then " + MIN_PASSWORD + "characters");
    }

    @Test
    void register_minAge_notOk() {
        user.setAge(BAD_AGE);
        assertThrows(RegistrationException.class,() -> {
            registrationService.register(user);
        }, "You age should be at least " + MIN_AGE);
    }

    @Test
    void register_existingUser_notOk() {
        Storage.people.add(user);
        assertThrows(RegistrationException.class,() -> {
            registrationService.register(user);
        },"User already exist in storage");
    }

    @Test
    void register_correctLogin_ok() {
        user.setLogin(GOOD_LOGIN);
        registrationService.register(user);
        assertTrue(Storage.people.contains(user));
    }

    @Test
    void register_correctPassword_ok() {
        user.setPassword(GOOD_PASSWORD);
        registrationService.register(user);
        assertTrue(Storage.people.contains(user));
    }

    @Test
    void register_correctAge_ok() {
        user.setAge(GOOD_AGE);
        registrationService.register(user);
        assertTrue(Storage.people.contains(user));
    }
}
