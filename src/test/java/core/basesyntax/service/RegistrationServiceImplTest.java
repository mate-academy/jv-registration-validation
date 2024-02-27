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
    private static final int VALID_MIN_AGE = 18;
    private static final int INVALID_AGE = 17;
    private static final int VALID_AGE = 18;

    private static final String LOGIN = "Oleksandr";
    private static final String INVALID_LOGIN = "Osano";
    private static final String VALID_LOGIN = "Correc";
    private static final String PASSWORD = "rytop1";
    private static final String INVALID_PASSWORD = "12345";
    private static final String VALID_PASSWORD = "123456";

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
        user.setAge(VALID_MIN_AGE);
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
        user.setLogin(INVALID_LOGIN);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        }, "Login must be more then " + MIN_LOGIN + "characters");
    }

    @Test
    void register_minPassword_notOk() {
        user.setPassword(INVALID_PASSWORD);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        }, "Password must be more then " + MIN_PASSWORD + "characters");
    }

    @Test
    void register_minAge_notOk() {
        user.setAge(INVALID_AGE);
        assertThrows(RegistrationException.class,() -> {
            registrationService.register(user);
        }, "You age should be at least " + INVALID_AGE);
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
        user.setLogin(VALID_LOGIN);
        registrationService.register(user);
        assertTrue(Storage.people.contains(user));
    }

    @Test
    void register_correctPassword_ok() {
        user.setPassword(VALID_PASSWORD);
        registrationService.register(user);
        assertTrue(Storage.people.contains(user));
    }

    @Test
    void register_correctAge_ok() {
        user.setAge(VALID_AGE);
        registrationService.register(user);
        assertTrue(Storage.people.contains(user));
    }
}
