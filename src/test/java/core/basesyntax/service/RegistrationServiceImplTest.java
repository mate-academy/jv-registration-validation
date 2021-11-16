package core.basesyntax.service;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RegistrationServiceImplTest {
    private static User expected;
    private static RegistrationServiceImpl registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        expected = new User();
        expected.setAge(65);
        expected.setLogin("qwerty@gmail.com");
        expected.setPassword("123456");
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin("qwerty@gmail.com");
        user.setAge(65);
        user.setPassword("123456");
    }

    @Test
    void register_userValid_ok() {
        user.setAge(65);
        user.setLogin("qwerty@gmail.com");
        user.setPassword("123456");
        assertEquals(expected, user);
    }

    @Test
    void register_passwordIsNull_notOK() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_ageIsNull_notOK() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_loginIsNull_notOK() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_ageInvalid_notOk() {
        user.setAge(17);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_passwordInvalid_notOk() {
        user.setPassword("12345");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_loginExist_notOK() {
        registrationService.register(user);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_idNotNull_notOK() {
        user.setId(123456L);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }
}
