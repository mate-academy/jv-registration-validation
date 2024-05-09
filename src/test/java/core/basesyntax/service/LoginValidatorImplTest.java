package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.interfaces.LoginValidator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class LoginValidatorImplTest {
    private static final String TEST_FAILED_EXPECTED_FALSE =
            "The test failed, expected false but actual ";
    private static final String TEST_FAILED_BY_LOGIN_FALSE =
            "The test failed, expected false for ";
    private static final String TEST_FAILED_EXPECTED_TRUE =
            "The test failed, expected true for ";
    private static final int MIN_LOGIN_LENGTH = 6;
    private static LoginValidator loginValidator;

    @BeforeAll
    static void setUp() {
        loginValidator = new LoginValidatorImpl();
    }

    @Test
    void isValid_nullLogin_notOk() {
        boolean actual = loginValidator.isValid(null);
        assertFalse(actual, TEST_FAILED_EXPECTED_FALSE + actual);
    }

    @Test
    void isValid_fromEmptyToMinLengthLogin_notOk() {
        String login = "";
        for (int i = 0; i < MIN_LOGIN_LENGTH; i++) {
            boolean actual = loginValidator.isValid(login);
            assertFalse(actual, TEST_FAILED_BY_LOGIN_FALSE + login + " actual " + actual);
            login += "a";
        }
    }

    @Test
    void isValid_login_ok() {
        String[] logins = new String[]{"username", "username123", "123username"};
        for (String login : logins) {
            boolean actual = loginValidator.isValid(login);
            assertTrue(actual, TEST_FAILED_EXPECTED_TRUE + login + " actual " + actual);
        }
    }

    @Test
    void isValid_specialCharLogin_notOk() {
        String[] logins = new String[]{"user@name", "?username", "user name"};
        for (String login : logins) {
            boolean actual = loginValidator.isValid(login);
            assertFalse(actual, TEST_FAILED_BY_LOGIN_FALSE + login + " actual " + actual);
        }
    }
}
