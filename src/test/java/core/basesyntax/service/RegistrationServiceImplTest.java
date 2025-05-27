package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static User user;
    private static RegistrationServiceImpl registrationService = new RegistrationServiceImpl();

    @BeforeAll
    static void beforeAll() {
        User user1 = new User(1L, "alex123", "password1", 25);
        User user2 = new User(2L, "johnDoe", "123456", 30);
        User user3 = new User(3L, "tester99", "qwerty", 19);
        User user4 = new User(4L, "maria87", "securepass", 28);
        User user5 = new User(5L, "bobsmith", "mypwd789", 35);
        registrationService.register(user1);
        registrationService.register(user2);
        registrationService.register(user3);
        registrationService.register(user4);
        registrationService.register(user5);
    }

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @Test
    void register_userAlready_notOK() {
        user = new User(10L, "alex123", "passwo222", 26);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_userNull_notOK() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_passwordNull_notOK() {
        user.setLogin("shshshsh");
        user.setAge(20);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_loginNull_notOK() {
        user.setPassword("wwwwwww");
        user.setAge(20);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_ageNull_notOK() {
        user.setPassword("sssssss");
        user.setLogin("1233421");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_passwordShort_notOK() {
        user.setLogin("sssssss");
        user.setPassword("123");
        user.setAge(20);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_loginShort_notOK() {
        user.setPassword("sssssss");
        user.setLogin("123");
        user.setAge(20);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_ageLow_notOK() {
        user.setPassword("sssssss");
        user.setLogin("1233421");
        user.setAge(17);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }
}
