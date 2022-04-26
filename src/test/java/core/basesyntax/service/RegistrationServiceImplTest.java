package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService userRegistrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        userRegistrationService = new RegistrationServiceImpl();
        User userJohn = new User();
        userJohn.setLogin("John");
        userJohn.setAge(18);
        userJohn.setPassword("site7tJohn");
        userRegistrationService.register(userJohn);
        User userBob = new User();
        userBob.setLogin("Bob");
        userBob.setAge(20);
        userBob.setPassword("site7tBob");
        userRegistrationService.register(userBob);
        User userAlice = new User();
        userAlice.setLogin("Alice");
        userAlice.setAge(19);
        userAlice.setPassword("site7tAlice");
        userRegistrationService.register(userAlice);
    }

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @Test
    void register_validUserData_Ok() {
        user.setLogin("Tom");
        user.setAge(18);
        user.setPassword("tom7t@gmail.com");
        User actual = userRegistrationService.register(user);
        User expected = user;
        assertEquals(expected, actual);
    }

    @Test
    void register_userAge_notOk() {
        user.setAge(17);
        assertThrows(RuntimeException.class, () -> {
            userRegistrationService.register(user);
        });
    }

    @Test
    void register_userAgeNegative_notOk() {
        user.setAge(-17);
        assertThrows(RuntimeException.class, () -> {
            userRegistrationService.register(user);
        });
    }

    @Test
    void register_userAgeNull_notOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> {
            userRegistrationService.register(user);
        });
    }

    @Test
    void register_userPasswordLengthLess_notOk() {
        user.setPassword("short");
        assertThrows(RuntimeException.class, () -> {
            userRegistrationService.register(user);
        });
    }

    @Test
    void register_userPasswordNull_notOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> {
            userRegistrationService.register(user);
        });
    }

    @Test
    void register_userPasswordIsEmpty_notOk() {
        user.setPassword("");
        assertThrows(RuntimeException.class, () -> {
            userRegistrationService.register(user);
        });
    }

    @Test
    void register_userLoginNull_notOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> {
            userRegistrationService.register(user);
        });
    }

    @Test
    void register_userLoginIsEmpty_notOk() {
        user.setLogin("");
        assertThrows(RuntimeException.class, () -> {
            userRegistrationService.register(user);
        });
    }

    @Test
    void register_userLoginExisted_notOk() {
        user.setLogin("Alice");
        assertThrows(RuntimeException.class, () -> {
            userRegistrationService.register(user);
        });
    }
}
