package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String DEFAULT_VALID_LOGIN = "Yaroslav";
    private static final String DEFAULT_VALID_PASSWORD = "123456";
    private static final int DEFAULT_VALID_AGE = 18;
    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_loginNull_NotOk() {
        User user = new User(null, DEFAULT_VALID_PASSWORD, DEFAULT_VALID_AGE);
        checkRegistrationException(user);
    }

    @Test
    void register_passwordNull_NotOk() {
        User user = new User(DEFAULT_VALID_LOGIN, null, DEFAULT_VALID_AGE);
        checkRegistrationException(user);
    }

    @Test
    void register_ageNull_NotOk() {
        User user = new User(DEFAULT_VALID_LOGIN, DEFAULT_VALID_PASSWORD, null);
        checkRegistrationException(user);
    }

    @Test
    void register_ageLess18_NotOk() {
        User user = new User(DEFAULT_VALID_LOGIN, DEFAULT_VALID_PASSWORD, 5);
        checkRegistrationException(user);
    }

    @Test
    void register_addUserWithTheSameLogin_NotOk() {
        User user1 = new User(DEFAULT_VALID_LOGIN,
                DEFAULT_VALID_PASSWORD, DEFAULT_VALID_AGE);
        User user2 = new User(DEFAULT_VALID_LOGIN,
                DEFAULT_VALID_PASSWORD + "23", DEFAULT_VALID_AGE + 5);
        registrationService.register(user1);
        checkRegistrationException(user2);
    }

    @Test
    void register_ageNegativeNumber_NotOk() {
        User user = new User(DEFAULT_VALID_LOGIN, DEFAULT_VALID_PASSWORD, -10);
        checkRegistrationException(user);
    }

    @Test
    void register_ageMoreThen18_Ok() {
        User expected = new User(DEFAULT_VALID_LOGIN, DEFAULT_VALID_PASSWORD, 95);
        User actual = registrationService.register(expected);
        assertEquals(expected, actual);
    }

    @Test
    void register_passwordLessThen6_NotOk() {
        User user = new User(DEFAULT_VALID_LOGIN, "123", DEFAULT_VALID_AGE);
        checkRegistrationException(user);
    }

    @Test
    void register_allValidData_Ok() {
        User expected = new User(DEFAULT_VALID_LOGIN, DEFAULT_VALID_PASSWORD, DEFAULT_VALID_AGE);
        User actual = registrationService.register(expected);
        assertEquals(expected, actual);
    }

    @Test
    void register_userNull_NotOk() {
        checkRegistrationException(null);
    }

    @Test
    void register_twoDifferentUsersValidData_Ok() {
        User expected1 = new User(DEFAULT_VALID_LOGIN, DEFAULT_VALID_PASSWORD, DEFAULT_VALID_AGE);
        User expected2 = new User("Bogdan", "774523234", 38);
        User actual1 = registrationService.register(expected1);
        User actual2 = registrationService.register(expected2);
        assertEquals(expected1, actual1);
        assertEquals(expected2, actual2);
    }

    private void checkRegistrationException(User actual) {
        assertThrows(RegistrationException.class, () -> registrationService.register(actual));
    }

}
