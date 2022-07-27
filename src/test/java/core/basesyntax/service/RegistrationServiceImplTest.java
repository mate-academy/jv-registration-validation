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
    void register_correctPassword_Ok() {
        User user = new User();
        user.setPassword("1234567");
        user.setAge(20);
        user.setLogin("alex123");
        User actual = registrationService.register(user);
        User expected = new User();
        expected.setPassword("1234567");
        assertEquals(expected.getPassword(), actual.getPassword());
    }

    @Test
    void register_correctLogin_Ok() {
        User user = new User();
        user.setPassword("1234567");
        user.setAge(20);
        user.setLogin("ron123");
        User actual = registrationService.register(user);
        User expected = new User();
        expected.setLogin("ron123");
        assertEquals(expected.getLogin(), actual.getLogin());
    }

    @Test
    void register_correctAge_Ok() {
        User user = new User();
        user.setPassword("1234567");
        user.setAge(30);
        user.setLogin("aiv123");
        User actual = registrationService.register(user);
        int expected = 30;
        assertEquals(expected, actual.getAge());
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
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullAge_NotOk() {
        User user = new User();
        user.setPassword("qwerty");
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_incorrectPassword_NotOk() {
        User user = new User();
        user.setPassword("qwert");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_invalidAge_NotOk() {
        User user = new User();
        user.setPassword("qwerty");
        user.setAge(12);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_sameLogin_NotOk() {
        User expected = new User();
        expected.setLogin("chill09");
        expected.setPassword("qwerty");
        expected.setAge(20);
        registrationService.register(expected);
        User actual = new User();
        actual.setLogin("chill09");
        actual.setPassword("qwerty");
        actual.setAge(20);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(actual);
        });
    }

    @Test
    void register_nullUser_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(null);
        });
    }
}
