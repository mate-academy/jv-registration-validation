package core.basesyntax.service;

import static core.basesyntax.service.RegistrationServiceImpl.AGE;
import static core.basesyntax.service.RegistrationServiceImpl.EXIST_MESSAGE;
import static core.basesyntax.service.RegistrationServiceImpl.LESS_MESSAGE;
import static core.basesyntax.service.RegistrationServiceImpl.LOGIN;
import static core.basesyntax.service.RegistrationServiceImpl.LOGIN_PASSWORD_MIN_LENGTH;
import static core.basesyntax.service.RegistrationServiceImpl.MIN_AGE;
import static core.basesyntax.service.RegistrationServiceImpl.NULL_MESSAGE;
import static core.basesyntax.service.RegistrationServiceImpl.PASSWORD;
import static core.basesyntax.service.RegistrationServiceImpl.RegistrationException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
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
    private static final String EMPTY_STRING = "pass";
    private static RegistrationServiceImpl registrationService;
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
    void registerProperUserOk() {
        User registerdUser = registrationService.register(user);
        assertTrue(Storage.people.contains(registerdUser));
    }

    @Test
    void registerNullUserNotOk() {
        assertException(null, "User can`t be null!");
    }

    @Test
    void registerSameLoginNotOk() {
        Storage.people.add(user);
        assertException(user, EXIST_MESSAGE);
    }

    @Test
    void registerLoginNullNotOk() {
        user.setLogin(null);
        assertException(user, String.format(NULL_MESSAGE, LOGIN));
    }

    @Test
    void registerPasswordNullNotOk() {
        user.setPassword(null);
        assertException(user, String.format(NULL_MESSAGE, PASSWORD));
    }

    @Test
    void registerAgeNullNotOk() {
        user.setAge(null);
        assertException(user, String.format(NULL_MESSAGE, AGE));
    }

    @Test
    void registerLoginShortNotOk() {
        user.setLogin(LOGIN_NOT_OK);
        assertException(
                user,
                String.format(
                        LESS_MESSAGE,
                        LOGIN,
                        LOGIN_PASSWORD_MIN_LENGTH,
                        user.getLogin().length()));
        user.setLogin(EMPTY_STRING);
        assertException(
                user,
                String.format(
                        LESS_MESSAGE,
                        LOGIN,
                        LOGIN_PASSWORD_MIN_LENGTH,
                        user.getLogin().length()));
    }

    @Test
    void registerPasswordShortNotOk() {
        user.setPassword(PASSWORD_NOT_OK);
        assertException(
                user,
                String.format(
                        LESS_MESSAGE,
                        PASSWORD,
                        LOGIN_PASSWORD_MIN_LENGTH,
                        user.getPassword().length()));
        user.setPassword(EMPTY_STRING);
        assertException(
                user,
                String.format(
                        LESS_MESSAGE,
                        PASSWORD,
                        LOGIN_PASSWORD_MIN_LENGTH,
                        user.getPassword().length()));
    }

    @Test
    void registerAgeLessNotOk() {
        user.setAge(AGE_NOT_OK);
        assertException(
                user,
                String.format(LESS_MESSAGE, AGE, MIN_AGE, user.getAge()));
        user.setAge(AGE_ZERO);
        assertException(
                user,
                String.format(LESS_MESSAGE, AGE, MIN_AGE, user.getAge()));
    }

    private void assertException(User user, String expected) {
        Exception exception = assertThrows(
                RegistrationException.class,
                () -> registrationService.register(user));
        String actual = exception.getMessage();
        assertEquals(expected, actual);
    }
}
