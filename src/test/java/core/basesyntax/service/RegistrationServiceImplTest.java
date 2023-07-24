package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.UserRegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int LOGIN_MIN_LENGTH = 6;
    private static final int PASSWORD_MIN_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private static final String VALID_LOGIN = "Valid_login";
    private static final String VALID_PASSWORD = "Valid_password";
    private static RegistrationService registrationService;

    @BeforeAll
    static void beforeAll() {
        if (VALID_LOGIN.length() < LOGIN_MIN_LENGTH) {
            throw new RuntimeException("Valid login should has length at least "
                    + LOGIN_MIN_LENGTH);
        }
        if (VALID_PASSWORD.length() < PASSWORD_MIN_LENGTH) {
            throw new RuntimeException("Valid password should has length at least "
                    + PASSWORD_MIN_LENGTH);
        }
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        Storage.people.clear();
    }

    @Test
    void validUserCase() {
        User expected = User.of(VALID_LOGIN, VALID_PASSWORD, 37);
        User actual = registrationService.register(expected);
        assertEquals(expected, actual);
        assertTrue(Storage.people.contains(actual));

    }

    @Test
    void validUserCase_minLengthLogin() {
        String minLengthLogin = VALID_LOGIN.substring(0, LOGIN_MIN_LENGTH);
        User expected = User.of(minLengthLogin, VALID_PASSWORD, 56);
        User actual = registrationService.register(expected);
        assertEquals(expected, actual);
        assertTrue(Storage.people.contains(actual));
    }

    @Test
    void validUserCase_minAge() {
        User expected = User.of(VALID_LOGIN, VALID_PASSWORD, MIN_AGE);
        User actual = registrationService.register(expected);
        assertEquals(expected, actual);
        assertTrue(Storage.people.contains(actual));
    }

    @Test
    void validUserCase_minLengthPassword() {
        String minLengthPassword = VALID_PASSWORD.substring(0, PASSWORD_MIN_LENGTH);
        User expected = User.of(VALID_LOGIN, minLengthPassword, 75);
        User actual = registrationService.register(expected);
        assertEquals(expected, actual);
        assertTrue(Storage.people.contains(actual));
    }

    @Test
    void invalidUserCase_existingLogin() {
        User validUser = User.of(VALID_LOGIN, VALID_PASSWORD, MIN_AGE);
        registrationService.register(validUser);
        User existingLoginUser = User.of(VALID_LOGIN, "else_password", 19);
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(existingLoginUser);
        }, "This login already exist - " + validUser.getLogin());
    }

    @Test
    void invalidUserCase_nullUser() {
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(null);
        }, "User can`t be null");
    }

    @Test
    void invalidUserCase_invalidLoginByLength() {
        String invalidLogin = VALID_LOGIN.substring(0, LOGIN_MIN_LENGTH - 1);
        User invalidLoginUser = User.of(invalidLogin, VALID_PASSWORD, 46);
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(invalidLoginUser);
        }, "Login " + invalidLogin + " with length " + invalidLogin.length()
                + " is invalid by min length " + LOGIN_MIN_LENGTH);
        assertFalse(Storage.people.contains(invalidLoginUser));
    }

    @Test
    void invalidUserCase_nullLogin() {
        User nullLoginUser = User.of(null, VALID_PASSWORD, MIN_AGE);
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(nullLoginUser);
        }, "Login can`t be null");
        assertFalse(Storage.people.contains(nullLoginUser));
    }

    @Test
    void invalidUserCase_invalidPasswordByLength() {
        String invalidPassword = VALID_PASSWORD.substring(0, PASSWORD_MIN_LENGTH - 1);
        User invalidPasswordUser = User.of(VALID_LOGIN, invalidPassword, 89);
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(invalidPasswordUser);
        }, invalidPassword.length() + " password length is invalid for min length "
                + PASSWORD_MIN_LENGTH);
        assertFalse(Storage.people.contains(invalidPasswordUser));
    }

    @Test
    void invalidUserCase_nullPassword() {
        User nullPasswordUser = User.of(VALID_LOGIN, null, 76);
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(nullPasswordUser);
        }, "Password can`t be null");
        assertFalse(Storage.people.contains(nullPasswordUser));
    }

    @Test
    void invalidUserCase_ageLessThenMinAge() {
        Integer invalidAge = MIN_AGE - 1;
        User invalidAgeUser = User.of(VALID_LOGIN, VALID_PASSWORD, invalidAge);
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(invalidAgeUser);
        }, invalidAge + " years is less then min age " + MIN_AGE);
        assertFalse(Storage.people.contains(invalidAgeUser));
    }

    @Test
    void invalidUserCase_negativeAge() {
        Integer negativeAge = -100;
        User negativeAgeUser = User.of(VALID_LOGIN, VALID_PASSWORD,
                negativeAge);
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(negativeAgeUser);
        }, negativeAge + " years is less then min age " + MIN_AGE);
        assertFalse(Storage.people.contains(negativeAgeUser));
    }

    @Test
    void invalidUserCase_nullAge() {
        User nullAgeUser = User.of(VALID_LOGIN, VALID_PASSWORD, null);
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(nullAgeUser);
        }, "Age can`t be null");
        assertFalse(Storage.people.contains(nullAgeUser));
    }
}
