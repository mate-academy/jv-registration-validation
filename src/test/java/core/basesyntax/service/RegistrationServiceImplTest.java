package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.InvalidAgeException;
import core.basesyntax.exceptions.InvalidLoginException;
import core.basesyntax.exceptions.InvalidPasswordException;
import core.basesyntax.exceptions.NullUserException;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String LOGIN_OK = "userLogin";
    private static final String LOGIN_NOT_OK = "login";
    private static final String PASSWORD_OK = "userPassword";
    private static final String PASSWORD_NOT_OK = "pass";
    private static final int AGE_OK = 18;
    private static final int AGE_NOT_OK = 17;
    private static final int AGE_ZERO = 0;
    private static final String EMPTY_STRING = "";
    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User(LOGIN_OK, PASSWORD_OK, AGE_OK);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_ProperUser_Ok() {
        User registeredUser = registrationService.register(user);
        assertTrue(Storage.people.contains(registeredUser));
    }

    @Test
    void register_SameLogin_NotOk() {
        Storage.people.add(user);
        assertException(user, "A user with this login already exists!");
    }

    @Test
    void register_LoginNull_NotOk() {
        user.setLogin(null);
        assertException(user, "Login can't be null or empty!");
    }

    @Test
    void register_PasswordNull_NotOk() {
        user.setPassword(null);
        assertException(user, "Password can't be null or empty!");
    }

    @Test
    void register_AgeNull_NotOk() {
        user.setAge(null);
        assertException(user, "Age can't be null!");
    }

    @Test
    void register_LoginShort_NotOk() {
        user.setLogin(LOGIN_NOT_OK);
        assertException(
                user,
                "Login less than minimum length: 6 actual length: " + user.getLogin().length());
        user.setLogin(EMPTY_STRING);
        assertException(
                user,
                "Login can't be null or empty!");
    }

    @Test
    void register_PasswordShort_NotOk() {
        user.setPassword(PASSWORD_NOT_OK);
        assertException(
                user,
                "Password less than minimum length: 6 actual length: "
                        + user.getPassword().length());
        user.setPassword(EMPTY_STRING);
        assertException(
                user,
                "Password can't be null or empty!");
    }

    @Test
    void register_AgeLess_NotOk() {
        user.setAge(AGE_NOT_OK);
        assertException(
                user,
                "Age less than minimum: 18");
        user.setAge(AGE_ZERO);
        assertException(
                user,
                "Age less than minimum: 18");
    }

    @Test
    void register_NullUser_NotOk() {
        assertException(null, "User cannot be null");
    }

    private void assertException(User user, String expected) {
        Exception exception = assertThrows(
                RegistrationException.class,
                () -> registrationService.register(user));
        String actual = exception.getMessage();
        assertEquals(expected, actual);
        if (expected.contains("User")) {
            assertTrue(exception instanceof NullUserException);
        } else if (expected.contains("Login")) {
            assertTrue(exception instanceof InvalidLoginException);
        } else if (expected.contains("Password")) {
            assertTrue(exception instanceof InvalidPasswordException);
        } else if (expected.contains("Age")) {
            assertTrue(exception instanceof InvalidAgeException);
        }
    }
}
