package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String DEFAULT_CORRECT_LOGIN = "login";
    private static final String DEFAULT_CORRECT_PASSWORD = "password";
    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_nullAge_NotOk() {
        User user = new User(DEFAULT_CORRECT_LOGIN, DEFAULT_CORRECT_PASSWORD, null);
        detectionCheckException(user);
    }

    @Test
    void register_nullPassword_NotOk() {
        User user = new User(DEFAULT_CORRECT_LOGIN, null, 18);
        detectionCheckException(user);
    }

    @Test
    void register_nullLogin_NotOk() {
        User user = new User(null, DEFAULT_CORRECT_PASSWORD, 18);
        detectionCheckException(user);
    }

    @Test
    void register_checkingForTheSameLogins_NotOk() {
        User user1 = new User(DEFAULT_CORRECT_LOGIN, DEFAULT_CORRECT_PASSWORD, 18);
        User user2 = new User(DEFAULT_CORRECT_LOGIN, "passwords", 18);
        registrationService.register(user1);
        detectionCheckException(user2);
    }

    @Test
    void register_ageLess18_NotOk() {
        User actual = new User(DEFAULT_CORRECT_LOGIN, DEFAULT_CORRECT_PASSWORD, 17);
        detectionCheckException(actual);
    }

    @Test
    void register_ageMore18_Ok() {
        User actual = new User(DEFAULT_CORRECT_LOGIN, DEFAULT_CORRECT_PASSWORD, 20);
        User expected = registrationService.register(actual);
        assertEquals(actual, expected);
    }

    @Test
    void register_age18_Ok() {
        User expected = new User(DEFAULT_CORRECT_LOGIN, DEFAULT_CORRECT_PASSWORD, 18);
        User actual = registrationService.register(expected);
        assertEquals(expected, actual);
    }

    @Test
    void register_negativeAge_NotOk() {
        User user = new User(DEFAULT_CORRECT_LOGIN, DEFAULT_CORRECT_PASSWORD, -5);
        detectionCheckException(user);
    }

    @Test
    void register_passwordLengthless6_NotOk() {
        User user = new User(DEFAULT_CORRECT_LOGIN, "pass", 18);
        detectionCheckException(user);
    }

    @Test
    void register_addTwoDifferenceUsers_Ok() {
        User expected1 = new User(DEFAULT_CORRECT_LOGIN, DEFAULT_CORRECT_PASSWORD, 18);
        User expected2 = new User(DEFAULT_CORRECT_LOGIN + "s", DEFAULT_CORRECT_PASSWORD, 20);
        User actual1 = registrationService.register(expected1);
        User actual2 = registrationService.register(expected2);
        assertEquals(expected1, actual1);
        assertEquals(expected2, actual2);
    }

    @Test
    void register_addNullUser_NotOk() {
        detectionCheckException(null);
    }

    private void detectionCheckException(User actual) {
        assertThrows(RegistrationServiceException.class,
                () -> registrationService.register(actual));
    }
}
