package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.AuthorizationError;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    private static final String VALID_LOGIN = "login";
    private static final String VALID_PASSWORD = "password";
    private static final String SHORT_PASSWORD = "pasik";
    private static final Integer VALID_AGE = 18;
    private static final Integer INVALID_AGE = 10;
    private static final String EMPTY_LOGIN = "";

    private static final User userIsNull = null;
    private static User validUser;
    private static User userInvalidAge;
    private static User userNullLogin;
    private static User userNullPassword;
    private static User userNullAge;
    private static User userEmptyLogin;
    private static User userShortPassword;
    private static RegistrationService registrationService;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        validUser = new User();
        validUser.setLogin(VALID_LOGIN);
        validUser.setPassword(VALID_PASSWORD);
        validUser.setAge(VALID_AGE);
        userInvalidAge = new User();
        userInvalidAge.setLogin(VALID_LOGIN);
        userInvalidAge.setPassword(VALID_PASSWORD);
        userInvalidAge.setAge(INVALID_AGE);
        userNullAge = new User();
        userNullAge.setLogin(VALID_LOGIN);
        userNullAge.setPassword(VALID_PASSWORD);
        userNullAge.setAge(null);
        userNullPassword = new User();
        userNullPassword.setLogin(VALID_LOGIN);
        userNullPassword.setPassword(null);
        userNullPassword.setAge(VALID_AGE);
        userNullLogin = new User();
        userNullLogin.setLogin(null);
        userNullLogin.setPassword(VALID_PASSWORD);
        userNullLogin.setAge(VALID_AGE);
        userNullLogin = new User();
        userNullLogin.setLogin(null);
        userNullLogin.setPassword(VALID_PASSWORD);
        userNullLogin.setAge(VALID_AGE);
        userEmptyLogin = new User();
        userEmptyLogin.setLogin(EMPTY_LOGIN);
        userEmptyLogin.setPassword(VALID_PASSWORD);
        userEmptyLogin.setAge(VALID_AGE);
        userShortPassword = new User();
        userShortPassword.setLogin(VALID_LOGIN);
        userShortPassword.setPassword(SHORT_PASSWORD);
        userShortPassword.setAge(VALID_AGE);
    }

    @BeforeEach
    void setUp() {
        Storage.people.clear();
    }

    @Test
    void registerNullUserNotOK() {
        try {
            registrationService.register(userIsNull);
        } catch (AuthorizationError e) {
            return;
        }
        fail("Object User  is empty ");
    }

    @Test
    void registerNullLoginNotOK() {
        try {
            registrationService.register(userNullLogin);
        } catch (AuthorizationError e) {
            return;
        }
        fail("Login must be not null ");
    }

    @Test
    void registerNullPasswordNotOK() {
        assertThrows(AuthorizationError.class, () -> {
            registrationService.register(userNullPassword);
        });
    }

    @Test
    void registerNullAgedNotOK() {
        assertThrows(AuthorizationError.class, () -> {
            registrationService.register(userNullAge);
        });
    }

    @Test
    void registerInvalidAgeNotOK() {
        assertThrows(AuthorizationError.class, () -> {
            registrationService.register(userInvalidAge);
        });
    }

    @Test
    void registerValidUserOk() {
        User expected = null;
        try {
            expected = registrationService.register(validUser);
        } catch (AuthorizationError e) {
            fail("User is Valid and must be added");
        }
        assertEquals(validUser,expected,"User is valid");
    }

    @Test
    void registerCloneUserNotOk() {
        try {
            registrationService.register(validUser);
        } catch (AuthorizationError e) {
            fail("users are valid and must be added");
        }
        try {
            registrationService.register(validUser);
        } catch (AuthorizationError e) {
            return;
        }
        fail("Two record whit same login -> exception ");
    }

    @Test
    void registerEmptyLoginNotOk() {
        try {
            registrationService.register(userEmptyLogin);
        } catch (AuthorizationError e) {
            return;
        }
        fail("Login must have a value");
    }

    @Test
    void registerShortPasswordNotOk() {
        try {
            registrationService.register(userShortPassword);
        } catch (AuthorizationError e) {
            return;
        }
        fail("User password is at least 6 characters, mast have");
    }

}