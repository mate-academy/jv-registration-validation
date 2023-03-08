package core.basesyntax.service;

import core.basesyntax.exceptions.AuthorisationError;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    private static final String VALID_LOGIN = "login";
    private static final String VALID_PASSWORD = "password";
    private static final Integer VALID_AGE = 18;
    private static final Integer INVALID_AGE = 10;

    private RegistrationService registrationService;
    private static User validUser;
    private static User userInvalidAge;
    private static User userIsNull;
    private static User userNullLogin;
    private static User userNullPassword;
    private static User userNullAge;


    @BeforeAll
    static void beforeAll() {

        validUser = new User();
        validUser.setLogin(VALID_LOGIN);
        validUser.setPassword(VALID_PASSWORD);
        validUser.setAge(VALID_AGE);
    }

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void registerNullUserNotOK() {
        try {
            registrationService.register(userIsNull);
        }
        catch (AuthorisationError e) {
            return;
        }
        fail("User Object is empty ");
    }

    @Test
    void registerNullLoginNotOK() {
        userNullLogin = validUser;
        userNullLogin.setLogin(null);
        try {
            registrationService.register(userNullLogin);
        }
        catch (AuthorisationError e) {
            return;
        }
        fail("Login must be not null ");
    }

    @Test
    void registerNullPasswordNotOK() {
        userNullPassword = validUser;
        userNullPassword.setPassword(null);
        assertThrows(AuthorisationError.class, () -> {
            registrationService.register(userNullPassword);
        });
    }

    @Test
    void registerNullAgedNotOK() {
        userNullAge = validUser;
        userNullAge.setAge(INVALID_AGE);
        assertThrows(AuthorisationError.class, () -> {
            registrationService.register(userNullAge);
        });
    }

    @Test
    void registerInvalidAgedNotOK() {
        userInvalidAge = validUser;
        userInvalidAge.setAge(INVALID_AGE);
        assertThrows(AuthorisationError.class, () -> {
            registrationService.register(userInvalidAge);
        });
    }

    @Test
    void registerValidUserOk() {
        User expected = null;
        try {
            expected = registrationService.register(validUser);
        } catch (AuthorisationError e) {
            fail("User is Valid");
        }
        assertTrue(validUser.equals(expected), "User is valid");
    }

    @Test
    void registerCloneUserNotOk() {
        User expected = null;
        try {
            registrationService.register(validUser);
            expected = registrationService.register(validUser);
        } catch (AuthorisationError e) {
          fail("users are valid");
        }
        assertNull(expected);
    }

}