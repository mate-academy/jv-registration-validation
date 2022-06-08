package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private User userBob;
    private User user;
    private RegistrationServiceImpl registrationService;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        user = new User("Alice", "Johnson", 63);
        userBob = new User("Bob", "SomeLength", 22);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void user_is_null_notOK() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void login_is_null_notOK() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void check_if_login_exists_notOk() {
        user.setLogin(userBob.getLogin());
        registrationService.register(user);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void check_if_login_exists_is_ok() {
        user.setLogin("NotBob");
        User expected = user;
        User actual = registrationService.register(userBob);
        assertNotEquals(expected, actual);
    }

    @Test
    void login_is_ok() {
        user.setLogin("SomeSmartName");
        User expected = user;
        User actual = registrationService.register(user);
        assertEquals(expected, actual);
    }

    @Test
    void password_is_null_notOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void password_is_short_notOk() {
        user.setPassword("three");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void password_is_ok() {
        user.setPassword("ThisPasswordIsLongEnough");
        User expected = user;
        User actual = registrationService.register(user);
        assertEquals(expected, actual);
    }

    @Test
    void password_minimum_required_length_isOk() {
        user.setPassword("123456");
        User expected = user;
        User actual = registrationService.register(user);
        assertEquals(expected, actual);
    }

    @Test
    void age_is_null_notOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void age_is_lower_notOk() {
        user.setAge(17);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void age_minimum_value_ok() {
        user.setAge(18);
        User expected = user;
        User actual = registrationService.register(user);
        assertEquals(expected, actual);
    }

    @Test
    void age_is_ok() {
        user.setAge(19);
        User expected = user;
        User actual = registrationService.register(user);
        assertEquals(expected, actual);
    }
}
