package core.basesyntax.validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.InvalidDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserValidatorTest {
    private static final String DEFAULT_LOGIN = "login";
    private static final String DEFAULT_PASSWORD = "password";
    private static final int DEFAULT_AGE = 25;
    private static final int MIN_ALLOWED_AGE = 18;
    private static final int MAX_ALLOWED_AGE = 100;
    private static final int NEGATIVE_AGE = -19;
    private static final String INVALID_LOGIN_CHARACTERS =
            "'@' ',' '#' '[]' '()' '&' '{}' '\\' '/' '*' ';' 'white space inside' '. at the end'";
    private static final String INVALID_PASSWORD_CHARACTERS =
            "'\\' '/' '.' '<>' '^' 'white space inside'";
    private static final String[] VALID_LOGINS = {
            "login12345", "tRyThisLoGIn", " my_login   ", "YOURLOGIN", "log.in.with.dots"};
    private static final String[] INVALID_LOGINS = {
            "log@in", "log,in", "lo#gin", "login]", "login[", "logi(n)", "&login",
            "login{}", "lo\\gin/", "login*", "login;", "log in", "login."};
    private static final String[] VALID_PASSWORDS = {
            "pass@word", " password ", "password12345"};
    private static final String[] INVALID_PASSWORDS = {
            "pass\\word", "pass/word", "pass.word", "pass<word>", "pass^word", "pass word"};
    private static final String INVALID_PASSWORD_LENGTH = "pass";
    private static final String EMPTY_STRING = "    ";
    private static Validator validator;
    private static StorageDao storage;
    private static User defaultUser;

    @BeforeAll
    static void setUp() {
        validator = new UserValidator();
        storage = new StorageDaoImpl();
        defaultUser = new User();
    }

    @BeforeEach
    void setDefaultUser() {
        defaultUser.setLogin(DEFAULT_LOGIN);
        defaultUser.setPassword(DEFAULT_PASSWORD);
        defaultUser.setAge(DEFAULT_AGE);
    }

    @AfterEach
    void clearStorage() {
        Storage.people.clear();
    }

    @Test
    void isValid_userWithValidLogin_ok() {
        for (String login : VALID_LOGINS) {
            defaultUser.setLogin(login);
            assertTrue(validator.isValid(defaultUser),
                    "Test failed! User '" + defaultUser + "' has correct data");
        }
    }

    @Test
    void isValid_userNull_notOk() {
        assertThrows(InvalidDataException.class, () -> {
            validator.isValid(null);
        }, "Test failed! User can't be Null");
    }

    @Test
    void isValid_loginNull_notOk() {
        defaultUser.setLogin(null);
        assertThrows(InvalidDataException.class, () -> {
            validator.isValid(defaultUser);
        }, "Test failed! Login can't be Null");
    }

    @Test
    void isValid_loginWithInvalidCharacter_notOk() {
        for (String invalidLogin : INVALID_LOGINS) {
            defaultUser.setLogin(invalidLogin);
            assertThrows(InvalidDataException.class, () -> {
                validator.isValid(defaultUser);
            }, "Test failed! Login '" + invalidLogin
                    + "' contains invalid character.\n Characters: "
                    + INVALID_LOGIN_CHARACTERS + " are not allowed in login");
        }
    }

    @Test
    void isValid_emptyLogin_notOk() {
        defaultUser.setLogin(EMPTY_STRING);
        assertThrows(InvalidDataException.class, () -> {
            validator.isValid(defaultUser);
        }, "Test failed! Login is empty");
    }

    @Test
    void isValid_existingLogin_notOk() {
        storage.add(defaultUser);
        User newUser = new User();
        newUser.setLogin(DEFAULT_LOGIN);
        newUser.setPassword(DEFAULT_PASSWORD);
        newUser.setAge(DEFAULT_AGE);
        assertFalse(validator.isValid(newUser),
                "Test failed! User with Login ='" + DEFAULT_LOGIN
                        + "' is already exist");
    }

    @Test
    void isValid_existingLoginSurroundedSpaces_notOk() {
        storage.add(defaultUser);
        User newUser = new User();
        newUser.setLogin("   " + DEFAULT_LOGIN + "  ");
        newUser.setPassword(DEFAULT_PASSWORD);
        newUser.setAge(DEFAULT_AGE);
        assertFalse(validator.isValid(newUser),
                "Test failed! User with Login '   " + DEFAULT_LOGIN
                        + "   ' is already exist");
    }

    @Test
    void isValid_ageMinimal_ok() {
        defaultUser.setAge(MIN_ALLOWED_AGE);
        assertTrue(validator.isValid(defaultUser),
                "Test failed! Age '" + MIN_ALLOWED_AGE + "' is valid");
    }

    @Test
    void isValid_ageMaximal_ok() {
        defaultUser.setAge(MAX_ALLOWED_AGE);
        assertTrue(validator.isValid(defaultUser),
                "Test failed! Age '" + MAX_ALLOWED_AGE + "' is valid");
    }

    @Test
    void isValid_ageLessThanMinimal_notOk() {
        defaultUser.setAge(MIN_ALLOWED_AGE - 1);
        assertFalse(validator.isValid(defaultUser),
                "Test failed! Age '" + (MIN_ALLOWED_AGE - 1) + "' is less than allowed");
    }

    @Test
    void isValid_ageBiggerThanMaximal_notOk() {
        defaultUser.setAge(MAX_ALLOWED_AGE + 1);
        assertFalse(validator.isValid(defaultUser),
                "Test failed! Age '" + (MAX_ALLOWED_AGE + 1) + "' is bigger than allowed");
    }

    @Test
    void isValid_ageNegative_notOk() {
        defaultUser.setAge(NEGATIVE_AGE);
        assertFalse(validator.isValid(defaultUser),
                "Test failed! Age '" + NEGATIVE_AGE + "' is negative");
    }

    @Test
    void isValid_ageNull_notOk() {
        defaultUser.setAge(null);
        assertThrows(InvalidDataException.class, () -> {
            validator.isValid(defaultUser);
        }, "Test failed! Age can't be Null");
    }

    @Test
    void isValid_passwordValid_Ok() {
        for (String password : VALID_PASSWORDS) {
            defaultUser.setPassword(password);
            assertTrue(validator.isValid(defaultUser),
                    "Test failed! The password ='" + password + "' is correct");
        }
    }

    @Test
    void isValid_passwordNull_notOk() {
        defaultUser.setPassword(null);
        assertThrows(InvalidDataException.class, () -> {
            validator.isValid(defaultUser);
        }, "Test failed! Password can't be Null");
    }

    @Test
    void isValid_emptyPassword_notOk() {
        defaultUser.setPassword(EMPTY_STRING);
        assertThrows(InvalidDataException.class, () -> {
            validator.isValid(defaultUser);
        }, "Test failed! Password is empty");
    }

    @Test
    void isValid_passwordInvalidLength_notOk() {
        defaultUser.setPassword(INVALID_PASSWORD_LENGTH);
        assertFalse(validator.isValid(defaultUser),
                "Test failed! Password '" + INVALID_PASSWORD_LENGTH
                        + "' is less than allowed");
    }

    @Test
    void isValid_passwordWithInvalidCharacter_notOk() {
        for (String invalidPassword : INVALID_PASSWORDS) {
            defaultUser.setPassword(invalidPassword);
            assertThrows(InvalidDataException.class, () -> {
                validator.isValid(defaultUser);
            }, "Test failed! Password '" + invalidPassword
                    + "' contains invalid character.\n"
                    + "Characters: " + INVALID_PASSWORD_CHARACTERS
                    + " are not allowed in password");
        }
    }
}
