package core.basesyntax.service;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.ValidationException;
import core.basesyntax.model.User;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    User defaultOkUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        defaultOkUser = new User();
        defaultOkUser.setAge(40);
        defaultOkUser.setLogin("1234566666");
        defaultOkUser.setPassword("43434343");
    }

    @Test
    void containsValidUser_Ok() {
        User expected = defaultOkUser;
        User actual = registrationService.register(defaultOkUser);
        assertEquals(expected, actual);
    }

    @Test
    void userIsAlreadyInStorage_NotOk() {
        Storage.people.add(defaultOkUser);
        assertThrows(ValidationException.class, () -> {
            registrationService.register(defaultOkUser);
        });
    }

    @Test
    void nullValue_NotOk() {
        assertThrows(ValidationException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void loginIsMoreThan6Letters_NotOk() {
        defaultOkUser.setLogin("1");
        assertThrows(ValidationException.class, () -> {
            registrationService.register(defaultOkUser);
        });
    }

    @Test
    void loginIsMoreThan6Letters_Ok() {
        defaultOkUser.setLogin("1010100909");
        int expected = 10;
        int actual = registrationService.register(defaultOkUser).getLogin().length();
        assertEquals(expected, actual);
    }

    @Test
    void passwordIsMoreThan6Letters_NotOk() {
        defaultOkUser.setPassword("1");
        assertThrows(ValidationException.class, () -> {
            registrationService.register(defaultOkUser);
        });
    }

    @Test
    void passwordIsMoreThan6Letters_Ok() {
        defaultOkUser.setLogin("303030303030");
        int expected = 8;
        int actual = registrationService.register(defaultOkUser).getPassword().length();
        assertEquals(expected, actual);
    }

    @Test
    void userAge_Ok() {
        defaultOkUser.setLogin("909090909090");
        int expected = 40;
        int actual = registrationService.register(defaultOkUser).getAge();
        assertEquals(expected, actual);
    }

    @Test
    void userAge_NotOk() {
        defaultOkUser.setAge(10);
        assertThrows(ValidationException.class, () -> {
            registrationService.register(defaultOkUser);
        });
    }

    @Test
    void passwordNullCheck_NotOk() {
        defaultOkUser.setPassword(null);
        assertThrows(ValidationException.class, () -> {
            registrationService.register(defaultOkUser);
        });
    }

    @Test
    void loginNullCheck_NotOk() {
        defaultOkUser.setLogin(null);
        assertThrows(ValidationException.class, () -> {
            registrationService.register(defaultOkUser);
        });
    }
}
