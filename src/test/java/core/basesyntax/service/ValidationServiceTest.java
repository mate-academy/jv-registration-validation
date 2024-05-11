package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ValidationServiceTest {
    private static final String TEST_FAILED_EXPECTED_FALSE =
            "The test failed, expected false but actual ";
    private static final String TEST_FAILED_EXPECTED_FALSE_FOR =
            "The test failed, expected false for ";
    private static final String TEST_FAILED_EXPECTED_TRUE =
            "The test failed, expected true for ";
    private static final int MIN_LENGTH = 6;
    private static final int ZERO_AGE = 0;
    private static final int MIN_AGE = 18;
    private static final int NEGATIVE_AGE = -18;
    private static ValidationService validationService;

    @BeforeEach
    void setUp() {
        validationService = new ValidationService();
    }

    @Test
    void isValidLogin_nullLogin_notOk() {
        boolean actual = validationService.isValidLogin(null);
        assertFalse(actual, TEST_FAILED_EXPECTED_FALSE + actual);
    }

    @Test
    void isValidLogin_fromEmptyToMinLengthLogin_notOk() {
        String login = "";
        for (int i = 0; i < MIN_LENGTH; i++) {
            boolean actual = validationService.isValidLogin(login);
            assertFalse(actual, TEST_FAILED_EXPECTED_FALSE_FOR + login
                    + " actual " + actual);
            login += "a";
        }
    }

    @Test
    void isValidLogin_login_ok() {
        String[] logins = new String[]{"username", "username123", "123username"};
        for (String login : logins) {
            boolean actual = validationService.isValidLogin(login);
            assertTrue(actual, TEST_FAILED_EXPECTED_TRUE + login
                    + " actual " + actual);
        }
    }

    @Test
    void isValidLogin_specialCharLogin_notOk() {
        String[] logins = new String[]{"user@name", "?username", "user name"};
        for (String login : logins) {
            boolean actual = validationService.isValidLogin(login);
            assertFalse(actual, TEST_FAILED_EXPECTED_FALSE_FOR + login
                    + " actual " + actual);
        }
    }

    @Test
    void iisValidPassword_nullPassword_notOk() {
        boolean actual = validationService.isValidPassword(null);
        assertFalse(actual, TEST_FAILED_EXPECTED_FALSE + actual);
    }

    @Test
    void isValidPassword_fromEmptyToPasswordLimit_notOk() {
        String password = "";
        for (int i = 0; i < MIN_LENGTH; i++) {
            boolean actual = validationService.isValidPassword(password);
            assertFalse(actual, TEST_FAILED_EXPECTED_FALSE_FOR + password
                    + " actual " + actual);
            password += "a";
        }
    }

    @Test
    void isValidPassword_password_ok() {
        String[] passwords = new String[]{"password", "password123", "123password"};
        for (String password : passwords) {
            boolean actual = validationService.isValidPassword(password);
            assertTrue(actual, TEST_FAILED_EXPECTED_TRUE + password
                    + " actual " + actual);
        }
    }

    @Test
    void isValidPassword_specialCharPassword_notOk() {
        String[] passwords = new String[]{"pass@word", "?password", " password"};
        for (String password : passwords) {
            boolean actual = validationService.isValidPassword(password);
            assertFalse(actual, TEST_FAILED_EXPECTED_FALSE_FOR + password
                    + " actual " + actual);
        }
    }

    @Test
    void isValidAge_nullAge_notOK() {
        boolean actual = validationService.isValidAge(null);
        assertFalse(actual, TEST_FAILED_EXPECTED_FALSE + actual);
    }

    @Test
    void isValidAge_fromZeroToLimitAge_notOk() {
        for (int age = ZERO_AGE; age < MIN_AGE; age++) {
            boolean actual = validationService.isValidAge(age);
            assertFalse(actual, TEST_FAILED_EXPECTED_FALSE_FOR + age
                    + " actual " + actual);
        }
    }

    @Test
    void isValidAge_negativeAge_notOk() {
        boolean actual = validationService.isValidAge(NEGATIVE_AGE);
        assertFalse(actual, TEST_FAILED_EXPECTED_FALSE_FOR + NEGATIVE_AGE
                + " actual " + actual);
    }

    @Test
    void isValidAge_limitAge_ok() {
        boolean actual = validationService.isValidAge(MIN_AGE);
        assertTrue(actual, TEST_FAILED_EXPECTED_TRUE + MIN_AGE
                + " actual " + actual);
    }

}
