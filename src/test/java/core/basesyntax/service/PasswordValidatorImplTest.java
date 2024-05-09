package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.interfaces.PasswordValidator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PasswordValidatorImplTest {
    private static final String TEST_FAILED_EXPECTED_FALSE =
            "The test failed, expected false but actual ";
    private static final String TEST_FAILED_BY_PASSWORD_FALSE =
            "The test failed, expected false for ";
    private static final String TEST_FAILED_EXPECTED_TRUE =
            "The test failed, expected true for ";
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static PasswordValidator passwordValidator;

    @BeforeAll
    static void setUp() {
        passwordValidator = new PasswordValidatorImpl();
    }

    @Test
    void isValid_nullPassword_notOk() {
        boolean actual = passwordValidator.isValid(null);
        assertFalse(actual, TEST_FAILED_EXPECTED_FALSE + actual);
    }

    @Test
    void isValid_fromEmptyToPasswordLimit_notOk() {
        String password = "";
        for (int i = 0; i < MIN_PASSWORD_LENGTH; i++) {
            boolean actual = passwordValidator.isValid(password);
            assertFalse(actual, TEST_FAILED_BY_PASSWORD_FALSE + password + " actual " + actual);
            password += "a";
        }
    }

    @Test
    void isValid_password_ok() {
        String[] passwords = new String[]{"password", "password123", "123password"};
        for (String password : passwords) {
            boolean actual = passwordValidator.isValid(password);
            assertTrue(actual, TEST_FAILED_EXPECTED_TRUE + password + " actual " + actual);
        }
    }

    @Test
    void isValid_specialCharLogin_notOk() {
        String[] passwords = new String[]{"pass@word", "?password", " password"};
        for (String password : passwords) {
            boolean actual = passwordValidator.isValid(password);
            assertFalse(actual, TEST_FAILED_BY_PASSWORD_FALSE + password + " actual " + actual);
        }
    }
}
