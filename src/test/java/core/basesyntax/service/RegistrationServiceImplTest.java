package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.exceptions.RegistrationServiceException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String DEFAULT_CORRECT_LOGIN = "login";
    private static final String DEFAULT_CORRECT_PASSWORD = "password";
    private static final int DEFAULT_CORRECT_AGE = 18;
    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.getAll().clear();
    }

    @Test
    void register_addNullUser_NotOk() {
        detectionCheckException(null);
    }

    @Test
    void register_nullAge_notOk() {
        User user = new User(DEFAULT_CORRECT_LOGIN, DEFAULT_CORRECT_PASSWORD, null);
        detectionCheckException(user);
    }

    @Test
    void register_nullPassword_notOK() {
        User user = new User(DEFAULT_CORRECT_LOGIN, null, DEFAULT_CORRECT_AGE);
        detectionCheckException(user);
    }

    @Test
    void  register_nullLogin_notOK() {
        User user = new User(null, DEFAULT_CORRECT_PASSWORD, DEFAULT_CORRECT_AGE);
        detectionCheckException(user);
    }

    @Test
    void register_smallPassword_notOK() {
        User actual = new User(DEFAULT_CORRECT_LOGIN, "12", DEFAULT_CORRECT_AGE);
        detectionCheckException(actual);
    }

    @Test
    void register_user_ageLess18_notOK() {
        User user = new User(DEFAULT_CORRECT_LOGIN, DEFAULT_CORRECT_PASSWORD, 13);
        detectionCheckException(user);
    }

    @Test
    void register_ageIsNegative_notOK() {
        User user = new User(DEFAULT_CORRECT_LOGIN, DEFAULT_CORRECT_PASSWORD, -9);
        detectionCheckException(user);
    }

    @Test
    void register_two_users_sameLogin_notOK() {
        User user1 = new User(DEFAULT_CORRECT_LOGIN,
                DEFAULT_CORRECT_PASSWORD, DEFAULT_CORRECT_AGE);
        User user2 = new User(DEFAULT_CORRECT_LOGIN, DEFAULT_CORRECT_PASSWORD
                + "s", DEFAULT_CORRECT_AGE);
        registrationService.register(user1);
        detectionCheckException(user2);
    }

    @Test
    void register_correctAge_more18_OK() {
        User expected = new User(DEFAULT_CORRECT_LOGIN, DEFAULT_CORRECT_PASSWORD, 19);
        User actual = registrationService.register(expected);
        assertEquals(expected, actual);
    }

    @Test
    void register_age18_OK() {
        User expected = new User(DEFAULT_CORRECT_LOGIN, DEFAULT_CORRECT_PASSWORD, DEFAULT_CORRECT_AGE);
        User actual = registrationService.register(expected);
        assertEquals(expected, actual);
    }

    @Test
    void register_two_users_OK() {
        User expected1 = new User(DEFAULT_CORRECT_LOGIN,
                DEFAULT_CORRECT_PASSWORD, DEFAULT_CORRECT_AGE);
        User expected2 = new User(DEFAULT_CORRECT_LOGIN
                + "s", DEFAULT_CORRECT_PASSWORD, DEFAULT_CORRECT_AGE);
        User actual1 = registrationService.register(expected1);
        User actual2 = registrationService.register(expected2);
        assertEquals(expected1, actual1);
        assertEquals(expected2, actual2);
    }

    private void detectionCheckException(User actual) {
        assertThrows(RegistrationServiceException.class,
                () -> registrationService.register(actual));
    }
}
