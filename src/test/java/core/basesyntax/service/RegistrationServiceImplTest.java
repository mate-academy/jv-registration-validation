package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private static final String VALID_LOGIN = "Denis123";
    private static final String VALID_PASSWORD = "qwerty";
    private static final int VALID_AGE = 23;
    private static final String SHORT_LOGIN = "Den";
    private static final String SHORT_PASSWORD = "ytr";
    private static final int NEGATIVE_AGE = -7;
    private static final int BELOW_MIN_AGE = 17;
    private static final User NULL_USER = null;
    private static final User NULL_LOGIN_USER = new User(null,
            VALID_PASSWORD, VALID_AGE);
    private static final User NULL_PASSWORD_USER = new User("max",
            null, VALID_AGE);
    private static final User USER_WITH_SHORT_LOGIN = new User(SHORT_LOGIN, VALID_PASSWORD,
            VALID_AGE);
    private static final User USER_WITH_SHORT_PASSWORD = new User("Maksim1",
            SHORT_PASSWORD, VALID_AGE);
    private static final User USER_WITH_NEGATIVE_AGE = new User("Oleksandr",
            VALID_PASSWORD, NEGATIVE_AGE);
    private static final User USER_BELOW_MIN_AGE = new User("roman2", VALID_PASSWORD,
            BELOW_MIN_AGE);
    private static final User VALID_USER_1 = new User("bohdan783",
            VALID_PASSWORD, VALID_AGE);
    private static final User VALID_USER_2 = new User("Yevheniy", VALID_PASSWORD,
            VALID_AGE);
    private static final User EXISTING_USER = new User("Yevheniy",
            VALID_PASSWORD, VALID_AGE);

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_validUser_ok() {
        User registeredUser = registrationService.register(VALID_USER_1);
        assertEquals(VALID_USER_1, registeredUser);
    }

    @Test
    void register_invalidLogin_notOk() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(USER_WITH_SHORT_LOGIN);
        });
    }

    @Test
    void register_nullLogin_notOk() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(NULL_LOGIN_USER);
        });
    }

    @Test
    void register_invalidPassword_notOk() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(USER_WITH_SHORT_PASSWORD);
        });
    }

    @Test
    void register_nullPassword_notOk() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(NULL_PASSWORD_USER);
        });
    }

    @Test
    void register_invalidAge_notOk() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(USER_BELOW_MIN_AGE);
            registrationService.register(USER_WITH_NEGATIVE_AGE);
        });
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(NULL_USER);
        });
    }

    @Test
    void register_existingUser_notOk() {
        Storage.people.add(VALID_USER_2);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(EXISTING_USER);
        });
    }
}
