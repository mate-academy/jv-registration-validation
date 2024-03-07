package core.basesyntax.service;

import static core.basesyntax.service.RegistrationServiceImpl.AGE;
import static core.basesyntax.service.RegistrationServiceImpl.EXIST_MSG;
import static core.basesyntax.service.RegistrationServiceImpl.LESS_MSG;
import static core.basesyntax.service.RegistrationServiceImpl.LOGIN;
import static core.basesyntax.service.RegistrationServiceImpl.MIN_AGE;
import static core.basesyntax.service.RegistrationServiceImpl.NULL_MSG;
import static core.basesyntax.service.RegistrationServiceImpl.PASSWORD;
import static core.basesyntax.service.RegistrationServiceImpl.RegistrationException;
import static core.basesyntax.service.RegistrationServiceImpl.USER_DATA_MIN_LENGTH;
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
    private static RegistrationServiceImpl registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User("userLogin", "userPassword", 18);
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
        User second = new User(user.getLogin(), "321321", 25);
        assertException(second, EXIST_MSG);
    }

    @Test
    void registerLoginNullNotOk() {
        user.setLogin(null);
        assertException(user, String.format(NULL_MSG, LOGIN));
    }

    @Test
    void registerPasswordNullNotOk() {
        user.setPassword(null);
        assertException(user, String.format(NULL_MSG, PASSWORD));
    }

    @Test
    void registerAgeNullNotOk() {
        user.setAge(null);
        assertException(user, String.format(NULL_MSG, AGE));
    }

    @Test
    void registerLoginShortNotOk() {
        user.setLogin("0123");
        assertException(
                user,
                String.format(LESS_MSG, LOGIN, USER_DATA_MIN_LENGTH, user.getLogin().length()));
        user.setLogin("");
        assertException(
                user,
                String.format(LESS_MSG, LOGIN, USER_DATA_MIN_LENGTH, user.getLogin().length()));
    }

    @Test
    void registerPasswordShortNotOk() {
        user.setPassword("0123");
        assertException(
                user,
                String.format(
                        LESS_MSG,
                        PASSWORD,
                        USER_DATA_MIN_LENGTH,
                        user.getPassword().length()));
        user.setPassword("");
        assertException(
                user,
                String.format(
                        LESS_MSG,
                        PASSWORD,
                        USER_DATA_MIN_LENGTH,
                        user.getPassword().length()));
    }

    @Test
    void registerAgeLessNotOk() {
        user.setAge(17);
        assertException(
                user,
                String.format(LESS_MSG, AGE, MIN_AGE, user.getAge()));
        user.setAge(0);
        assertException(
                user,
                String.format(LESS_MSG, AGE, MIN_AGE, user.getAge()));
    }

    private void assertException(User user, String expected) {
        Exception exception = assertThrows(
                RegistrationException.class,
                () -> registrationService.register(user));
        String actual = exception.getMessage();
        assertEquals(expected, actual);
    }
}
