package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String CORRECT_LOGIN = "login";
    private static final String CORRECT_PASSWORD = "password";
    private RegistrationServiceImpl registrationService;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_nullAge_notOk() {
        User actual = new User(CORRECT_LOGIN, CORRECT_PASSWORD, null);
        checkException(actual);
    }

    @Test
    void register_nullPassword_notOk() {
        User actual = new User(CORRECT_LOGIN, null, 18);
        checkException(actual);
    }

    @Test
    void register_nullLogin_notOk() {
        User actual = new User(null, CORRECT_PASSWORD, 18);
        checkException(actual);
    }

    @Test
    void register_ageLess18_NotOk() {
        User actual = new User(CORRECT_LOGIN, CORRECT_PASSWORD, 17);
        checkException(actual);
    }

    @Test
    void register_ageLess0_NotOk() {
        User actual = new User(CORRECT_LOGIN, CORRECT_PASSWORD, -1);
        checkException(actual);
    }

    @Test
    void register_age18_Ok() {
        User expected = new User(CORRECT_LOGIN, CORRECT_PASSWORD, 18);
        User actual = registrationService.register(expected);
        assertEquals(expected, actual);
    }

    @Test
    void register_loginLengthLess6_NotOk() {
        User actual = new User(CORRECT_LOGIN, "passw", 18);
        checkException(actual);
    }

    @Test
    void register_loginAlreadyExists_NotOk() {
        User expect = new User(CORRECT_LOGIN, CORRECT_PASSWORD, 18);
        User actual = new User(CORRECT_LOGIN, CORRECT_PASSWORD, 18);
        assertEquals(expect, actual);
    }

    @Test
    void register_addNullUser_NotOk() {
        checkException(null);
    }

    private void checkException(User actual) {
        assertThrows(RegistrationServiceException.class, () -> {
            registrationService.register(actual);
        });
    }
}
