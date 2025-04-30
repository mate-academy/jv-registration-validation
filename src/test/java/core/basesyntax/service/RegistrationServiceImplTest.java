package core.basesyntax.service;

import static core.basesyntax.db.Storage.people;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    public static final int NEGATIVE_AGE = -10;
    public static final int ZERO_AGE = 0;
    public static final int BELOW_MINIMUM_AGE = 17;
    public static final int MINIMUM_AGE = 18;
    public static final int OVER_MINIMUM_AGE = 21;
    public static final String CORRECT_LOGIN = "qwerty";
    public static final String INCORRECT_LOGIN = "log";
    public static final String CORRECT_PASSWORD = "123456";
    public static final String INCORRECT_PASSWORD = "qwer";
    public static final String EMPTY_LINE = "";

    private static RegistrationService registrationService;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @AfterEach
    void tearDown() {
        people.clear();
    }

    @Test
    void register_nullLogin_NotOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(
                        new User(null, CORRECT_PASSWORD, MINIMUM_AGE)),
                "Expected RegistrationException was not thrown for null login.");
    }

    @Test
    void register_nullPassword_NotOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(
                        new User(CORRECT_LOGIN, null, MINIMUM_AGE)),
                "Expected RegistrationException was not thrown for null password.");
    }

    @Test
    void register_nullAge_NotOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(
                        new User(CORRECT_LOGIN, CORRECT_PASSWORD, null)),
                "Expected RegistrationException was not thrown for null age.");
    }

    @Test
    void register_correctUser_Ok() {
        User expected = new User(CORRECT_LOGIN, CORRECT_PASSWORD, MINIMUM_AGE);
        User actual = registrationService.register(expected);
        assertEquals(actual, expected);
    }

    @Test
    void register_correctUserOverMinimumAge_Ok() {
        User expected = new User(CORRECT_LOGIN, CORRECT_PASSWORD, OVER_MINIMUM_AGE);
        User actual = registrationService.register(expected);
        assertEquals(actual, expected);
    }

    @Test
    void register_incorrectAge_NotOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(
                        new User(CORRECT_LOGIN, CORRECT_PASSWORD, NEGATIVE_AGE)),
                "Expected RegistrationException was not thrown for negative age.");
        assertThrows(RegistrationException.class, () -> registrationService.register(
                        new User(CORRECT_LOGIN, CORRECT_PASSWORD, ZERO_AGE)),
                "Expected RegistrationException was not thrown for age equal to zero.");
        assertThrows(RegistrationException.class, () -> registrationService.register(
                        new User(CORRECT_LOGIN, CORRECT_PASSWORD, BELOW_MINIMUM_AGE)),
                "Expected RegistrationException was not thrown for age below minimum allowed.");
    }

    @Test
    void register_incorrectLogin_NotOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(
                        new User(INCORRECT_LOGIN, CORRECT_PASSWORD, MINIMUM_AGE)),
                "Expected RegistrationException was not thrown for for an incorrect login"
                        + " of less than six characters.");
        assertThrows(RegistrationException.class, () -> registrationService.register(
                        new User(EMPTY_LINE, CORRECT_PASSWORD, MINIMUM_AGE)),
                "Expected RegistrationException was not thrown for incorrect login(empty line).");
    }

    @Test
    void register_incorrectPassword_NotOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(
                        new User(CORRECT_LOGIN, INCORRECT_PASSWORD, MINIMUM_AGE)),
                "Expected RegistrationException was not thrown for for an incorrect password"
                        + " of less than six characters.");
        assertThrows(RegistrationException.class, () -> registrationService.register(
                        new User(CORRECT_LOGIN, EMPTY_LINE, MINIMUM_AGE)),
                "Expected RegistrationException was not thrown for"
                        + " incorrect password(empty line).");
    }

    @Test
    void register_userAlreadyExists_NotOk() {
        User newUser = new User(CORRECT_LOGIN, CORRECT_PASSWORD, MINIMUM_AGE);
        people.add(newUser);
        assertThrows(RegistrationException.class, () -> registrationService.register(newUser));
    }
}
