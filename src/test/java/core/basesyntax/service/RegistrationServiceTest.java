package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceTest {
    private static final String DEFAULT_LOGIN = "login";
    private static final String DEFAULT_PASSWORD = "password";
    private static final Integer DEFAULT_AGE = 20;
    private static User DEFAULT_USER;
    private RegistrationService service;

    @BeforeEach
    void setUp() {
        service = new RegistrationServiceImpl();
        DEFAULT_USER = new User(DEFAULT_LOGIN, DEFAULT_PASSWORD, DEFAULT_AGE);
        Storage.people.clear();
    }

    @Test
    void passwordNull_notOk() {
        DEFAULT_USER.setPassword(null);
        assertThrows(RuntimeException.class, () -> {
            service.register(DEFAULT_USER);
        });
    }

    @Test
    void loginNull_notOk() {
        DEFAULT_USER.setLogin(null);
        assertThrows(RuntimeException.class, () -> {
            service.register(DEFAULT_USER);
        });
    }

    @Test
    void checkedRegister_ok() {
        User actual = service.register(DEFAULT_USER);
        assertEquals(DEFAULT_USER, actual);
    }

    @Test
    void checkedAge_notOk() {
        DEFAULT_USER.setAge(15);
        assertThrows(RuntimeException.class, () -> {
            service.register(DEFAULT_USER);
        });
        DEFAULT_USER.setAge(0);
        assertThrows(RuntimeException.class, () -> {
            service.register(DEFAULT_USER);
        });
        DEFAULT_USER.setAge(null);
        assertThrows(RuntimeException.class, () -> {
            service.register(DEFAULT_USER);
        });
        DEFAULT_USER.setAge(5000);
        assertThrows(RuntimeException.class, () -> {
            service.register(DEFAULT_USER);
        });
    }

    @Test
    void checkedUserIsExist_notOk() {
        service.register(DEFAULT_USER);
        assertThrows(RuntimeException.class, () -> {
            service.register(DEFAULT_USER);
        });
    }

    @Test
    void passwordValid_NotOk() {
        DEFAULT_USER.setPassword("4aaaa");
        assertThrows(RuntimeException.class, () -> {
            service.register(DEFAULT_USER);
        });
    }

    @Test
    void register1000Users_Ok() {
        for (int i = 1; i <= 1000; i++) {
            User user = new User(DEFAULT_LOGIN + i, DEFAULT_PASSWORD, DEFAULT_AGE);
            service.register(user);
        }
        assertEquals(1000, Storage.people.size());
    }
}
