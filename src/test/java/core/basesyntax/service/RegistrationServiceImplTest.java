package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private static final String VALID_PASSWORD = "qwerty";
    private static final int VALID_AGE = 23;
    private static final User VALID_LOGIN_1_USER = new User("gabriel",
            VALID_PASSWORD, VALID_AGE);
    private static final User VALID_LOGIN_2_USER = new User("kate2006",
            VALID_PASSWORD, VALID_AGE);
    private static final User INVALID_LOGIN_1_USER = new User("max",
            VALID_PASSWORD, VALID_AGE);
    private static final User INVALID_LOGIN_2_USER = new User("",
            VALID_PASSWORD, VALID_AGE);
    private static final User NULL_LOGIN_USER = new User(null,
            VALID_PASSWORD, VALID_AGE);
    private static final User INVALID_PASSWORD_1_USER = new User("Oleksandr",
            "ghabf", VALID_AGE);
    private static final User INVALID_PASSWORD_2_USER = new User("Volodymyr",
            "abc", VALID_AGE);
    private static final User INVALID_PASSWORD_3_USER = new User("Yevhen",
            "", VALID_AGE);
    private static final User VALID_PASSWORD_1_USER = new User("patrick",
            "tyfhhfaa", VALID_AGE);
    private static final User VALID_PASSWORD_2_USER = new User("Denius",
            "lalakfjg", VALID_AGE);
    private static final User NULL_PASSWORD_USER = new User("max",
            null, VALID_AGE);
    private static final User INVALID_AGE_1_USER = new User("Viktoriya",
            VALID_PASSWORD, 16);
    private static final User INVALID_AGE_2_USER = new User("max",
            VALID_PASSWORD, -7);
    private static final User VALID_AGE_1_USER = new User("engine768",
            VALID_PASSWORD, VALID_AGE);
    private static final User VALID_AGE_2_USER = new User("car123456",
            VALID_PASSWORD, VALID_AGE);
    private static final User NULL_USER = null;
    private static final User VALID_USER = new User("Machine",
            VALID_PASSWORD, VALID_AGE);
    private static final User EXISTING_USER = new User("Machine",
            VALID_PASSWORD, VALID_AGE);

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_invalidLogin_NotOk() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(INVALID_LOGIN_1_USER);
            registrationService.register(INVALID_LOGIN_2_USER);
        });
    }

    @Test
    void register_nullLogin_NotOk() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(NULL_LOGIN_USER);
        });
    }

    @Test
    void register_validLogin_Ok() {
        User actual1 = registrationService.register(VALID_LOGIN_1_USER);
        User actual2 = registrationService.register(VALID_LOGIN_2_USER);
        assertEquals(VALID_LOGIN_1_USER, actual1);
        assertEquals(VALID_LOGIN_2_USER, actual2);
    }

    @Test
    void register_invalidPassword_NotOk() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(INVALID_PASSWORD_1_USER);
            registrationService.register(INVALID_PASSWORD_2_USER);
            registrationService.register(INVALID_PASSWORD_3_USER);
        });
    }

    @Test
    void register_nullPassword_NotOk() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(NULL_PASSWORD_USER);
        });
    }

    @Test
    void register_validPassword_Ok() {
        User actual1 = registrationService.register(VALID_PASSWORD_1_USER);
        User actual2 = registrationService.register(VALID_PASSWORD_2_USER);
        assertEquals(VALID_PASSWORD_1_USER, actual1);
        assertEquals(VALID_PASSWORD_2_USER, actual2);
    }

    @Test
    void register_invalidAge_NotOk() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(INVALID_AGE_1_USER);
            registrationService.register(INVALID_AGE_2_USER);
        });
    }

    @Test
    void register_validAge_Ok() {
        User actual1 = registrationService.register(VALID_AGE_1_USER);
        User actual2 = registrationService.register(VALID_AGE_2_USER);
        assertEquals(VALID_AGE_1_USER, actual1);
        assertEquals(VALID_AGE_2_USER, actual2);
    }

    @Test
    void register_nullUser_NotOk() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(NULL_USER);
        });
    }

    @Test
    void register_alreadyExistingUser_NotOk() {
        Storage.people.add(VALID_USER);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(EXISTING_USER);
        });
    }
}
