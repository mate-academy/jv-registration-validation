package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static Storage storage;
    private static User actual;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storage = new Storage();
    }

    @BeforeEach
    void beforeEach() {
        actual = new User(1234L, "login254", "1234567", 18);
    }

    @Test
    void duplicateLoginIs_notOk() {
        Storage.people.add(actual);
        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(actual);
        });
    }

    @Test
    void userLoginIs_NotOk() {
        actual.setLogin("login");
        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(actual);
        });
    }

    @Test
    void userPasswordIs_NotOk() {
        actual.setPassword("12345");
        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(actual);
        });
    }

    @Test
    void userAgeIs_NotOk() {
        actual.setAge(17);
        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(actual);
        });
    }

    @Test
    void userLoginIs_Ok() {
        actual.setLogin("login111");
        assertEquals(registrationService.register(actual), actual);
    }

    @Test
    void userPasswordIs_Ok() {
        actual.setLogin("login222");
        assertEquals(registrationService.register(actual),
                actual);
    }

    @Test
    void userAgeIs_Ok() {
        actual.setLogin("login333");
        assertEquals(registrationService.register(actual),
                actual);
    }

    @Test
    void userIsNull_Ok() {
        User actual = null;
        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(actual);
        });
    }
}
