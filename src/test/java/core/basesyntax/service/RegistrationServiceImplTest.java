package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static final User USER_VALID = new User("Badcannon", "password1", 39);

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_nullUser_notOK() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_validUser_OK() {
        User actual = registrationService.register(USER_VALID);
        assertTrue(Storage.people.contains(actual));
        assertEquals(USER_VALID, actual);
    }

    @Test
    void register_userWithAgeLessRequired_NotOK() {
        User user = new User("login", "1234266", 12);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_userWithPassLessThanSixSymbol_notOK() {
        User user = new User("login", "1234", 39);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_userWithEmptyLogin_notOk() {
        User user = new User("", "123456", 39);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });

    }

    @Test
    void register_userWithNullLogin_NotOk() {
        User user = new User(null, "123456", 39);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_userWithSameLogin_NotOK() {
        Storage.people.add(USER_VALID);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(USER_VALID);
        });
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
