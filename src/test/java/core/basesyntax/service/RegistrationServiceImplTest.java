package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.RegistrationException;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int VALID_AGE = 18;
    private static final long VALID_ID = 123883022L;
    private static final String VALID_LOGIN = "UserName";
    private static final String VALID_PASSWORD = "BestPasswordEver123";
    private final RegistrationService registrationService = new RegistrationServiceImpl();
    private User user = new User();

    @BeforeEach
    void setUp() {
        user = new User();
        user.setAge(VALID_AGE);
        user.setId(VALID_ID);
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
    }

    @Test
    void register_lowerThanExpectedAge_NotOk() {
        int age = 17;
        user.setAge(age);
        RegistrationException exception =
                assertThrows(RegistrationException.class, () -> registrationService.register(user));
        String expected = "Not valid age: " + age + ". Minimal allowed age is 18";
        String actual = exception.getMessage();
        assertEquals(expected, actual);
    }

    @Test
    void register_equalsToExpectedAge_Ok() {
        User actualUser = registrationService.register(user);
        assertEquals(actualUser, user);
    }

    @Test
    void register_negativeAge_notOk() {
        int age = -18;
        user.setAge(age);
        RegistrationException exception =
                assertThrows(RegistrationException.class, () -> registrationService.register(user));
        String expected = "Not valid age: " + age + ". Minimal allowed age is 18";
        String actual = exception.getMessage();
        assertEquals(expected, actual);
    }

    @Test
    void register_nullPassword_NotOk() {
        user.setPassword(null);
        RegistrationException exception =
                assertThrows(RegistrationException.class, () -> registrationService.register(user));
        String expected = "Password can't be null";
        String actual = exception.getMessage();
        assertEquals(expected, actual);
    }

    @Test
    void register_emptyPassword_NotOk() {
        String password = "";
        user.setPassword(password);
        Exception exception =
                assertThrows(RegistrationException.class, () -> registrationService.register(user));
        String expected = "Password can't be empty";
        String actual = exception.getMessage();
        assertTrue(actual.contains(expected));
    }

    @Test
    void register_invalidLengthOfPassword3_NotOk() {
        String password = "asd";
        user.setPassword(password);
        Exception exception =
                assertThrows(RegistrationException.class, () -> registrationService.register(user));
        String expected = "Password can't be less than 6 symbols";
        String actual = exception.getMessage();
        assertTrue(actual.contains(expected));
    }

    @Test
    void register_invalidLengthOfPassword5_NotOk() {
        String password = "small";
        user.setPassword(password);
        Exception exception =
                assertThrows(RegistrationException.class, () -> registrationService.register(user));
        String expected = "Password can't be less than 6 symbols";
        String actual = exception.getMessage();
        assertTrue(actual.contains(expected));
    }

    @Test
    void register_NullLogin_NotOk() {
        user.setLogin(null);
        Exception exception =
                assertThrows(RegistrationException.class, () -> registrationService.register(user));
        String expected = "Login can't be null";
        String actual = exception.getMessage();
        assertTrue(actual.contains(expected));
    }

    @Test
    void register_emptyLogin_NotOk() {
        user.setLogin("");
        Exception exception =
                assertThrows(RegistrationException.class, () -> registrationService.register(user));
        String expected = "Login can't be empty";
        String actual = exception.getMessage();
        assertTrue(actual.contains(expected));
    }

    @Test
    void register_lengthOfLoginIs3_NotOk() {
        String login = "asd";
        user.setLogin(login);
        Exception exception =
                assertThrows(RegistrationException.class, () -> registrationService.register(user));
        String expected = "Login can't be less than 6 symbols";
        String actual = exception.getMessage();
        assertTrue(actual.contains(expected));
    }

    @Test
    void register_lengthOfLoginIs5_NotOk() {
        String login = "small";
        user.setLogin(login);
        Exception exception =
                assertThrows(RegistrationException.class, () -> registrationService.register(user));
        String expected = "Login can't be less than 6 symbols";
        String actual = exception.getMessage();
        assertTrue(actual.contains(expected));
    }

    @Test
    void register_nullAge_NotOk() {
        user.setAge(null);
        Exception exception =
                assertThrows(RegistrationException.class, () -> registrationService.register(user));
        String expected = "Age can't be null";
        String actual = exception.getMessage();
        assertTrue(actual.contains(expected));
    }

    @Test
    void register_userWithExistingLogin_NotOk() {
        User newUser = new User();
        newUser.setLogin("someLogin");
        newUser.setPassword("goodPassword");
        newUser.setAge(22);
        Storage.people.add(newUser);
        assertThrows(RegistrationException.class, () -> registrationService.register(newUser));
    }
}
