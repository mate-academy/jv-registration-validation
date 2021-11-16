package core.basesyntax.service;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RegistrationServiceImplTest {
    static User expected;
    static User exist;
    static RegistrationServiceImpl registrationService;
    User actual;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        exist = new User();
        exist.setPassword("123456");
        exist.setAge(65);
        exist.setLogin("lkjhg@gmail.com");
        registrationService.register(exist);
        expected = new User();
        expected.setAge(65);
        expected.setLogin("qwerty@gmail.com");
        expected.setPassword("123456");
    }

    @BeforeEach
    void setUp() {
        actual = new User();
    }

    @Test
    void dataIsTrue_Ok() {
        actual.setAge(65);
        actual.setLogin("qwerty@gmail.com");
        actual.setPassword("123456");
        assertEquals(expected, actual);
    }

    @Test
    void isNull_NotOK() {
        actual.setPassword(null);
        actual.setAge(null);
        actual.setLogin(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(actual);
        });
    }

    @Test
    void ageTooSmall_NotOk() {
        actual.setAge(17);
        actual.setLogin("asdfqwer@gmail.com");
        actual.setPassword("123456");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(actual);
        });
    }

    @Test
    void passwordTooSmall_NotOk() {
        actual.setPassword("12345");
        actual.setAge(18);
        actual.setLogin("asdfqwer@gmail.com");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(actual);
        });
    }

    @Test
    void loginExist_NotOK() {
        actual.setLogin("lkjhg@gmail.com");
        actual.setAge(18);
        actual.setPassword("123456");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(actual);
        });
    }

    @Test
    void idNotNull_NotOK() {
        actual.setId(123456L);
        actual.setAge(18);
        actual.setLogin("asdfqwer@gmail.com");
        actual.setPassword("123456");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(actual);
        });
    }
}
