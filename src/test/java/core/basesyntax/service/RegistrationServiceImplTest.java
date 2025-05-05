package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String VALID_PASSWORD = "1234567";
    private static final String VALID_PASSWORD_MIN_LENGTH = "123456";
    private static final String INVALID_PASSWORD = "12345";
    private static final String EMPTY_PASSWORD = "";
    private static final String VALID_LOGIN = "userLogin";
    private static final String VALID_LOGIN_MIN_LENGTH = "userLo";
    private static final String INVALID_LOGIN = "userL";
    private static final String EMPTY_LOGIN = "";
    private static final int MIN_AGE = 18;
    private static final int VALID_AGE = 19;
    private static final int INVALID_AGE = 17;
    private static final int ZERO_AGE = 0;
    private static final int MIN_LENGTH_PASSWORD = 6;
    private static final int MIN_LENGTH_LOGIN = 6;
    private static final String ERROR_MESSAGE_INVALID_LOGIN
            = "Login can't be null or must be more than " + MIN_LENGTH_LOGIN + " characters";
    private static final String ERROR_MESSAGE_INVALID_PASSWORD
            = "Password can't be null or must be more than " + MIN_LENGTH_PASSWORD + " characters";
    private static RegistrationServiceImpl registrationService;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_nullUser_notOk() {
        var nullUser = assertThrows(RegistrationException.class,
                () -> registrationService.register(null));
        assertEquals("User can`t be a null", nullUser.getMessage());
    }

    @Test
    void register_nullLogin_notOk() {
        var nullLogin = assertThrows(RegistrationException.class,
                () -> registrationService.register(
                new User(null, VALID_PASSWORD, VALID_AGE)));
        assertEquals(ERROR_MESSAGE_INVALID_LOGIN, nullLogin.getMessage());
    }

    @Test
    void register_nullPassword_notOk() {
        var nullPassword = assertThrows(RegistrationException.class,
                () -> registrationService.register(
                new User(VALID_LOGIN, null, VALID_AGE)));
        assertEquals(ERROR_MESSAGE_INVALID_PASSWORD, nullPassword.getMessage());
    }

    @Test
    void register_nullAge_notOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(
                new User(VALID_LOGIN, VALID_PASSWORD, null)));
    }

    @Test
    void register_invalidLogin_notOK() {
        var invalidDataLogin = assertThrows(RegistrationException.class,
                () -> registrationService.register(
                new User(INVALID_LOGIN, VALID_PASSWORD, VALID_AGE)));
        assertEquals(ERROR_MESSAGE_INVALID_LOGIN, invalidDataLogin.getMessage());
    }

    @Test
    void register_emptyLogin_notOK() {
        var emptyLogin = assertThrows(RegistrationException.class,
                () -> registrationService.register(
                new User(EMPTY_LOGIN, VALID_PASSWORD, VALID_AGE)));
        assertEquals(ERROR_MESSAGE_INVALID_LOGIN, emptyLogin.getMessage());
    }

    @Test
    void register_invalidPassword_notOK() {
        var invalidDataPassword = assertThrows(RegistrationException.class,
                () -> registrationService.register(
                new User(VALID_LOGIN, INVALID_PASSWORD, VALID_AGE)));
        assertEquals(ERROR_MESSAGE_INVALID_PASSWORD, invalidDataPassword.getMessage());
    }

    @Test
    void register_emptyPassword_notOK() {
        var emptyPassword = assertThrows(RegistrationException.class,
                () -> registrationService.register(
                        new User(VALID_LOGIN, EMPTY_PASSWORD, VALID_AGE)));
        assertEquals(ERROR_MESSAGE_INVALID_PASSWORD, emptyPassword.getMessage());
    }

    @Test
    void register_invalidAge_notOk() {
        User user = new User(VALID_LOGIN, VALID_PASSWORD, INVALID_AGE);
        var invalidAge = assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
        assertEquals("Not valid age: " + user.getAge() + ". Min allowed age is "
                + MIN_AGE, invalidAge.getMessage());
    }

    @Test
    void register_zeroAge_notOk() {
        User user = new User(VALID_LOGIN, VALID_PASSWORD, ZERO_AGE);
        var invalidAge = assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
        assertEquals("Not valid age: " + user.getAge() + ". Min allowed age is "
                + MIN_AGE, invalidAge.getMessage());
    }

    @Test
    void register_validUser_ok() {
        User expectedUser1 = new User(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);
        User expectedUser2 = new User(VALID_LOGIN_MIN_LENGTH, VALID_PASSWORD_MIN_LENGTH, MIN_AGE);
        User actualUser1 = registrationService.register(expectedUser1);
        User actualUser2 = registrationService.register(expectedUser2);
        assertEquals(expectedUser1, actualUser1);
        assertEquals(expectedUser2, actualUser2);
    }

    @Test
    void register_userExist_notOk() {
        User user = new User(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);
        Storage.people.add(user);
        var existUser = assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
        assertEquals("This login already used: " + user.getLogin(), existUser.getMessage());
    }
}
