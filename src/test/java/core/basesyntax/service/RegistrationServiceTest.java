package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.InvalidRegistrationData;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceTest {
    private static final String VALID_LOGIN = "valid_login";
    private static final String VALID_LOGIN_2 = "valid_login2";
    private static final String VALID_LOGIN_3 = "valid_login3";
    private static final String VALID_PASSWORD = "1234qwer";
    private static final String ANOTHER_VALID_PASSWORD = "QWERTY12";
    private static final int VALID_AGE = 20;
    private static final int ANOTHER_VALID_AGE = 31;
    private static final String INVALID_LOGIN = "abc";
    private static final String INVALID_PASSWORD = "12qw";
    private static final int INVALID_AGE = 10;

    private static RegistrationService registrationService;

    private static User validUser;
    private static User validUser2;
    private static User validUser3;
    private static User validUserSameLogin;
    private static User invalidLoginUser;
    private static User invalidPasswordUser;
    private static User invalidAgeUser;
    private static User emptyUser;
    private static User nullLoginUser;
    private static User nullPasswordUser;
    private static User nullAgeUser;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        validUser = new User(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);
        validUser2 = new User(VALID_LOGIN_2, VALID_PASSWORD, VALID_AGE);
        validUser3 = new User(VALID_LOGIN_3, ANOTHER_VALID_PASSWORD, ANOTHER_VALID_AGE);
        validUserSameLogin = new User(VALID_LOGIN, ANOTHER_VALID_PASSWORD, ANOTHER_VALID_AGE);
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

        assertNotNull(actualUser);
        assertNotNull(actualUser.getId());
        assertEquals(VALID_LOGIN, actualUser.getLogin());
        assertEquals(VALID_PASSWORD, actualUser.getPassword());
        assertEquals(VALID_AGE, actualUser.getAge());
    }

    @Test
    void register_invalidLoginUser_NotOK() {
        assertThrows(InvalidRegistrationData.class, () -> {
            registrationService.register(invalidLoginUser);
        });
    }

    @Test
    void register_invalidPasswordUser_NotOK() {
        assertThrows(InvalidRegistrationData.class, () -> {
            registrationService.register(invalidPasswordUser);
        });
    }

    @Test
    void register_invalidAgeUser_NotOK() {
        assertThrows(InvalidRegistrationData.class, () -> {
            registrationService.register(invalidAgeUser);
        });
    }

    @Test
    void register_userWithSameLogin_NotOK() {
        registrationService.register(validUser);
        assertThrows(InvalidRegistrationData.class, () -> {
            registrationService.register(validUserSameLogin);
        });
    }

    @Test
    void register_nullUser_NotOk() {
        assertThrows(InvalidRegistrationData.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_emptyUser_NotOk() {
        assertThrows(InvalidRegistrationData.class, () -> {
            registrationService.register(emptyUser);
        });
    }

    @Test
    void register_nullLoginUser_NotOk() {
        assertThrows(InvalidRegistrationData.class, () -> {
            registrationService.register(nullLoginUser);
        });
    }

    @Test
    void register_nullPasswordUser_NotOk() {
        assertThrows(InvalidRegistrationData.class, () -> {
            registrationService.register(nullPasswordUser);
        });
    }

    @Test
    void register_nullAgeUser_NotOk() {
        assertThrows(InvalidRegistrationData.class, () -> {
            registrationService.register(nullAgeUser);
        });
    }

    @Test
    void register_multipleUsers_Ok() {
        User registeredUser = registrationService.register(validUser);
        User registeredUser2 = registrationService.register(validUser2);
        User registeredUser3 = registrationService.register(validUser3);

        assertNotNull(registeredUser);
        assertNotNull(registeredUser2);
        assertNotNull(registeredUser3);

        assertNotNull(registeredUser.getId());
        assertNotNull(registeredUser2.getId());
        assertNotNull(registeredUser3.getId());

        assertNotEquals(registeredUser.getId(), registeredUser2.getId());
        assertNotEquals(registeredUser2.getId(), registeredUser3.getId());
        assertNotEquals(registeredUser.getId(), registeredUser3.getId());
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
