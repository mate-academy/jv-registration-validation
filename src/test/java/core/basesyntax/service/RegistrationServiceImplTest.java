package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final User NULL_USER = null;
    private static final String NULL_LOGIN = null;
    private static final Integer NULL_AGE = null;
    private static final String NULL_PASSWORD = null;
    private static final String VALID_LOGIN1 = "Andrew";
    private static final String VALID_LOGIN2 = "Penelopa";
    private static final String VALID_PWD = "1234567";
    private static final int VALID_AGE = 20;
    private static final String INVALID_LOGIN = "Andr";
    private static final String INVALID_PWD = "12345";
    private static final int INVALID_AGE = 17;
    private static final int MIN_AGE = 18;
    private static final int MIN_LENGTH = 6;
    private static final User INVALID_LOGIN_USER = new User(INVALID_LOGIN, VALID_PWD, VALID_AGE);
    private static final User INVALID_PWD_USER = new User(VALID_LOGIN1, INVALID_PWD, VALID_AGE);
    private static final User INVALID_AGE_USER = new User(VALID_LOGIN1, VALID_PWD, INVALID_AGE);
    private static final User VALID_USER = new User(VALID_LOGIN2, VALID_PWD, VALID_AGE);
    private static final User VALID_USER2 = new User("Alexandr", "password", 50);
    private static final User VALID_USER3 = new User("Leopold", "qwerty", 33);
    private static RegistrationServiceImpl regService;

    @BeforeAll
    static void beforeAll() {
        regService = new RegistrationServiceImpl();
    }

    @Test
    void register_nullUser_Exception_notOk() {
        RegistrationException invalidDataException = assertThrows(RegistrationException.class,
                () -> {
                regService.register(NULL_USER);
                });
        assertEquals("User can't be null", invalidDataException.getMessage());
    }

    @Test
    void register_nullPassword_Exception_notOk() {
        RegistrationException invalidDataException = assertThrows(RegistrationException.class,
                () -> {
                regService.register(new User(VALID_LOGIN1, NULL_PASSWORD, VALID_AGE));
                });
        assertEquals("Password can't be null", invalidDataException.getMessage());
    }

    @Test
    void register_nullLogin_Exception_notOk() {
        RegistrationException invalidDataException = assertThrows(RegistrationException.class,
                () -> {
                regService.register(new User(NULL_LOGIN, VALID_PWD, VALID_AGE));
                });
        assertEquals("Login can't be null", invalidDataException.getMessage());
    }

    @Test
    void register_nullAge_Exception_notOk() {
        RegistrationException invalidDataException = assertThrows(RegistrationException.class,
                () -> {
                regService.register(new User(VALID_LOGIN1, VALID_PWD, NULL_AGE));
                });
        assertEquals("Age can't be null", invalidDataException.getMessage());
    }

    @Test
    void register_invalidLogin_Exception_notOk() {
        RegistrationException invalidDataException = assertThrows(RegistrationException.class,
                () -> {
                regService.register(INVALID_LOGIN_USER);
                });
        assertEquals("Not valid login length: "
                + INVALID_LOGIN_USER.getLogin().length()
                + ". Min allowed login length is " + MIN_LENGTH, invalidDataException.getMessage());
    }

    @Test
    void register_existUser_Exception_notOk() {
        Storage.people.add(VALID_USER);
        RegistrationException invalidDataException = assertThrows(RegistrationException.class,
                () -> {
                    regService.register(VALID_USER);
                });
        assertEquals("User with this login already exists", invalidDataException.getMessage());
    }

    @Test
    void register_invalidPassword_Exception_notOk() {
        RegistrationException invalidDataException = assertThrows(RegistrationException.class,
                () -> {
                regService.register(INVALID_PWD_USER);
                });
        assertEquals("Not valid password length: "
                + INVALID_PWD_USER.getPassword().length()
                + ". Min allowed password length is "
                + MIN_LENGTH, invalidDataException.getMessage());
    }

    @Test
    void register_invalidAge_Exception_notOk() {
        RegistrationException invalidDataException = assertThrows(RegistrationException.class,
                () -> {
                regService.register(INVALID_AGE_USER);
                });
        assertEquals("Not valid age: "
                + INVALID_AGE_USER.getAge()
                + ". Min allowed age is " + MIN_AGE, invalidDataException.getMessage());
    }

    @Test
    void register_oneUser_Ok() {
        User validUser = regService.register(VALID_USER);
        assertEquals(validUser, VALID_USER);
    }

    @Test
    void register_twoUsers_Ok() {
        User validUser2 = regService.register(VALID_USER2);
        User validUser3 = regService.register(VALID_USER3);
        assertEquals(validUser2, VALID_USER2);
        assertEquals(validUser3, VALID_USER3);
    }

    @AfterAll
    static void afterAll() {
        Storage.people.clear();
    }
}
