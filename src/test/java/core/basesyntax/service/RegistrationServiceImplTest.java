package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User(0L, "ihor-91", "123456", 33);
    }

    @Test
    void userLoginNotNull_Ok() {
        assertNotNull(registrationService.register(user).getLogin());
    }

    @Test
    void userLoginNull_NotOk() {
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void userLoginLess6Characters_NotOk() {
        user.setLogin("Ihor1");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void userLoginGreater6Characters_Ok() {
        int actual = registrationService.register(user).getLogin().length();
        assertEquals(7, actual);
    }

    @Test
    void passwordNotNull_Ok() {
        String password = registrationService.register(user).getPassword();
        assertNotNull(password);
    }

    @Test
    void passwordNull_NotOk() {
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void passwordLess6Character_NotOk() {
        user.setPassword("12345");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void passwordGreater6Characters_Ok() {
        user.setPassword("1234567");
        int actual = registrationService.register(user).getPassword().length();
        assertEquals(7, actual);
    }

    @Test
    void userLoginAlreadyExist_NotOk() {
        Storage.people.add(user);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void userAgeNotNull_Ok() {
        assertNotNull(registrationService.register(user).getAge());
    }

    @Test
    void userAgeNull_NotOk() {
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void userAgeLessMinAge_NotOk() {
        user.setAge(17);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void userAgeGreaterMinAge_Ok() {
        Integer actual = registrationService.register(user).getAge();
        assertEquals(33, actual);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
