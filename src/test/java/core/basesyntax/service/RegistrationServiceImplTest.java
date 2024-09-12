package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;

    @BeforeAll
    static void init() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_validUser_Ok() {
        User expected = new User();
        expected.setPassword("1234567");
        expected.setAge(20);
        expected.setLogin("alex123");
        User actual = registrationService.register(expected);
        assertEquals(expected, actual);
    }

    @Test
    void register_nullLogin_NotOk() {
        User user = new User();
        user.setPassword("qwerty");
        user.setAge(20);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });

    }

    @Test
    void register_nullPassword_NotOk() {
        User user = new User();
        user.setAge(20);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullAge_NotOk() {
        User user = new User();
        user.setLogin("alex123");
        user.setAge(null);
        user.setPassword("qwerty");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_incorrectPassword_NotOk() {
        User user = new User();
        user.setLogin("alex123");
        user.setAge(20);
        user.setPassword("qwert");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_invalidAge_NotOk() {
        User user = new User();
        user.setLogin("alex123");
        user.setAge(12);
        user.setPassword("qwerty");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_sameLogin_NotOk() {
        User user = new User();
        user.setLogin("chill09");
        user.setPassword("qwerty");
        user.setAge(20);
        registrationService.register(user);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullUser_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(null);
        });
    }
}
