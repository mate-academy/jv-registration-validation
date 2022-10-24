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
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        user = new User();
        user.setLogin("Tretiak");
        user.setPassword("123456");
        user.setAge(20);
    }

    @Test
    void register_noExistUserLogin_Ok() {
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void register_containsUserLogin_NotOk() {
        Storage.people.add(user);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullUserLogin_NotOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullUser_NotOk() {
        user = null;
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_smallPassword_NotOk() {
        user.setPassword("123");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullPassword_NotOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_lowAge_NotOk() {
        user.setAge(15);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }
}
