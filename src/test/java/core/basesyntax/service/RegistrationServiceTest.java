package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceTest {
    private static final String VALID_LOGIN = "valid_login";
    private static final String MIN_LENGTH_LOGIN = "qwerty";
    private static final String MIN_LENGTH_PASSWORD = "ytrewq";
    private static final String VALID_PASSWORD = "1234qwer";
    private static final int VALID_AGE = 25;
    private static final int MIN_AGE = 18;
    private static final String INVALID_LOGIN = "abc";
    private static final String INVALID_PASSWORD = "12qw";
    private static final int INVALID_AGE = 10;

    private static RegistrationService registrationService;

    private static User validUser;
    private static User minAgeUser;
    private static User minLengthLoginUser;
    private static User minLengthPasswordUser;
    private static User validUserSameLogin;
    private static User invalidLoginUser;
    private static User invalidPasswordUser;
    private static User invalidAgeUser;
    private static User emptyUser;
    private static User nullLoginUser;
    private static User nullPasswordUser;
    private static User nullAgeUser;

    @BeforeAll
    static void setUp() {
        registrationService = new RegistrationServiceImpl();
        validUser = new User(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);
        minLengthLoginUser = new User(MIN_LENGTH_LOGIN, VALID_PASSWORD, VALID_AGE);
        minLengthPasswordUser = new User(VALID_LOGIN, MIN_LENGTH_PASSWORD, VALID_AGE);
        minAgeUser = new User(VALID_LOGIN, VALID_PASSWORD, MIN_AGE);
        validUserSameLogin = new User(VALID_LOGIN, MIN_LENGTH_PASSWORD, MIN_AGE);
        invalidLoginUser = new User(INVALID_LOGIN, VALID_PASSWORD, VALID_AGE);
        invalidPasswordUser = new User(VALID_LOGIN, INVALID_PASSWORD, VALID_AGE);
        invalidAgeUser = new User(VALID_LOGIN, VALID_PASSWORD, INVALID_AGE);
        emptyUser = new User();
        nullLoginUser = new User(null, VALID_PASSWORD, VALID_AGE);
        nullPasswordUser = new User(VALID_LOGIN, null, VALID_AGE);
        nullAgeUser = new User(VALID_LOGIN, VALID_PASSWORD, null);
    }

    @Test
    void register_validUser_Ok() {
        User actualUser = registrationService.register(validUser);
        testRegisteredUser(actualUser, VALID_LOGIN, VALID_PASSWORD, VALID_AGE);
    }

    @Test
    void register_minLengthLoginUser_Ok() {
        User actualUser = registrationService.register(minLengthLoginUser);
        testRegisteredUser(actualUser, MIN_LENGTH_LOGIN, VALID_PASSWORD, VALID_AGE);
    }

    @Test
    void register_minLengthPasswordUser_Ok() {
        User actualUser = registrationService.register(minLengthPasswordUser);
        testRegisteredUser(actualUser, VALID_LOGIN, MIN_LENGTH_PASSWORD, VALID_AGE);
    }

    @Test
    void register_minAgeUser_Ok() {
        User actualUser = registrationService.register(minAgeUser);
        testRegisteredUser(actualUser, VALID_LOGIN, VALID_PASSWORD, MIN_AGE);
    }

    @Test
    void register_invalidLoginUser_NotOK() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(invalidLoginUser);
        });
    }

    @Test
    void register_invalidPasswordUser_NotOK() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(invalidPasswordUser);
        });
    }

    @Test
    void register_invalidAgeUser_NotOK() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(invalidAgeUser);
        });
    }

    @Test
    void register_userWithSameLogin_NotOK() {
        Storage.people.add(validUser);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(validUserSameLogin);
        });
    }

    @Test
    void register_nullUser_NotOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_emptyUser_NotOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(emptyUser);
        });
    }

    @Test
    void register_nullLoginUser_NotOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(nullLoginUser);
        });
    }

    @Test
    void register_nullPasswordUser_NotOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(nullPasswordUser);
        });
    }

    @Test
    void register_nullAgeUser_NotOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(nullAgeUser);
        });
    }

    private void testRegisteredUser(User registeredUser, String expectedLogin,
                                    String expectedPassword, Integer expectedAge) {
        assertNotNull(registeredUser);
        assertNotNull(registeredUser.getId());
        assertEquals(expectedLogin, registeredUser.getLogin());
        assertEquals(expectedPassword, registeredUser.getPassword());
        assertEquals(expectedAge, registeredUser.getAge());
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
