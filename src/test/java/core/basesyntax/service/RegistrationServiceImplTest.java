package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private RegistrationService registrationService;
    private User user;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        user = new User();
        user.setPassword("123456");
        user.setAge(18);
        user.setLogin("test@mail");
    }

    @Test
    void age_isUnder_18() {
        user.setAge(17);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void age_isNegative() {
        user.setAge(-17);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void age_isMore_18() {
        user.setAge(19);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void age_is_18() {
        user.setAge(18);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void age_isNull() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void password_isShort() {
        user.setAge(20);
        user.setPassword("123");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void password_Ok() {
        user.setPassword("qwerty");
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void password_isNull() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void login_isNull() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void userAlreadyInStorage() {
        User user2 = new User();
        user2.setLogin("test@mail");
        user2.setPassword("123456");
        user2.setAge(18);
        Storage.people.add(user2);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @AfterEach
    void cleanStorage() {
        Storage.people.clear();
    }
}
