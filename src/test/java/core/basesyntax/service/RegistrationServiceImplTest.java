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
        userRegistrationService.register(new User("John", 18, "site7t@gmail.com"));
        userRegistrationService.register(new User("Bob", 20, "bob@gmail.com"));
        userRegistrationService.register(new User("Alice", 19, "alice4@gmail.com"));
    }

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @Test
    void register_validUserAllData_Ok() {
        user = new User("Tom", 18, "tom7t@gmail.com");
        User actual = userRegistrationService.register(user);
        User expected = new User("Tom", 18, "tom7t@gmail.com");
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
    void add_ToTheEmptyStorage_Ok() {
        userRegistrationService.reset();
        user = new User("John", 18, "site7t@gmail.com");
        userRegistrationService.register(user);
        int actual = userRegistrationService.getSize();
        int expected = 1;
        assertEquals(expected, actual);
    }

    @Test
    void register_userLoginExisted_notOk() {
        user = new User("Alice", 26, "alice2@gmail.com");
        assertThrows(RuntimeException.class, () -> {
            userRegistrationService.register(user);
        });
        String actual = user.getLogin();
        String expected = "Alice";
        assertEquals(expected, actual);
    }
}
