package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private static final String CORRECT_PASSWORD = "qwerty";
    private static final int CORRECT_AGE = 23;
    private static final User VALID1_LOGIN_USER = new User("gabriel",
            CORRECT_PASSWORD, CORRECT_AGE);
    private static final User VALID2_LOGIN_USER = new User("kate2006",
            CORRECT_PASSWORD, CORRECT_AGE);
    private static final User INVALID1_LOGIN_USER = new User("max",
            CORRECT_PASSWORD, CORRECT_AGE);
    private static final User INVALID2_LOGIN_USER = new User("",
            CORRECT_PASSWORD, CORRECT_AGE);
    private static final User NULL_LOGIN_USER = new User(null,
            CORRECT_PASSWORD, CORRECT_AGE);
    private static final User INVALID1_PASSWORD_USER = new User("Oleksandr",
            "ghabf", CORRECT_AGE);
    private static final User INVALID2_PASSWORD_USER = new User("Volodymyr",
            "abc", CORRECT_AGE);
    private static final User INVALID3_PASSWORD_USER = new User("Yevhen",
            "", CORRECT_AGE);
    private static final User VALID1_PASSWORD_USER = new User("patrick",
            "tyfhhfaa", CORRECT_AGE);
    private static final User VALID2_PASSWORD_USER = new User("Denius",
            "lalakfjg", CORRECT_AGE);
    private static final User NULL_PASSWORD_USER = new User("max",
            null, CORRECT_AGE);
    private static final User INVALID1_AGE_USER = new User("Viktoriya",
            CORRECT_PASSWORD, 16);
    private static final User INVALID2_AGE_USER = new User("max",
            CORRECT_PASSWORD, -7);
    private static final User VALID1_AGE_USER = new User("engine768",
            CORRECT_PASSWORD, CORRECT_AGE);
    private static final User VALID2_AGE_USER = new User("car123456",
            CORRECT_PASSWORD, CORRECT_AGE);
    private static final User NULL_USER = null;
    private static final User VALID_USER = new User("Machine",
            CORRECT_PASSWORD, CORRECT_AGE);
    private static final User EXISTING_USER = new User("Machine",
            CORRECT_PASSWORD, CORRECT_AGE);

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_invalidLogin_NotOk() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(INVALID1_LOGIN_USER);
            registrationService.register(INVALID2_LOGIN_USER);
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
        User actual1 = registrationService.register(VALID1_LOGIN_USER);
        User actual2 = registrationService.register(VALID2_LOGIN_USER);
        assertEquals(VALID1_LOGIN_USER, actual1);
        assertEquals(VALID2_LOGIN_USER, actual2);
    }

    @Test
    void register_invalidPassword_NotOk() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(INVALID1_PASSWORD_USER);
            registrationService.register(INVALID2_PASSWORD_USER);
            registrationService.register(INVALID3_PASSWORD_USER);
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
        User actual1 = registrationService.register(VALID1_PASSWORD_USER);
        User actual2 = registrationService.register(VALID2_PASSWORD_USER);
        assertEquals(VALID1_PASSWORD_USER, actual1);
        assertEquals(VALID2_PASSWORD_USER, actual2);
    }

    @Test
    void register_invalidAge_NotOk() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(INVALID1_AGE_USER);
            registrationService.register(INVALID2_AGE_USER);
        });
    }

    @Test
    void register_validAge_Ok() {
        User actual1 = registrationService.register(VALID1_AGE_USER);
        User actual2 = registrationService.register(VALID2_AGE_USER);
        assertEquals(VALID1_AGE_USER, actual1);
        assertEquals(VALID2_AGE_USER, actual2);
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
