package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceTest {
    private static final String DEFAULT_LOGIN = "login";
    private static final String DEFAULT_PASSWORD = "password";
    private static final Integer DEFAULT_AGE = 20;
    private static RegistrationService service;
    private User defaultUser;

    @BeforeEach
    void setUp() {
        defaultUser = new User(DEFAULT_LOGIN, DEFAULT_PASSWORD, DEFAULT_AGE);
        Storage.people.clear();
    }

    @BeforeAll
    static void beforeAll() {
        service = new RegistrationServiceImpl();
    }

    @Test
    void register_passwordNull_notOk() {
        defaultUser.setPassword(null);
        assertThrows(RuntimeException.class, () -> {
            service.register(defaultUser);
        });
    }

    @Test
    void register_passwordNotValid_notOk() {
        defaultUser.setPassword("4aaaa");
        assertThrows(RuntimeException.class, () -> {
            service.register(defaultUser);
        });
    }

    @Test
    void register_1000Users_Ok() {
        for (int i = 1; i <= 1000; i++) {
            User user = new User(DEFAULT_LOGIN + i, DEFAULT_PASSWORD, DEFAULT_AGE);
            service.register(user);
        }
        assertEquals(1000, Storage.people.size());
    }

    @Test
    void register_checkedRegister_ok() {
        User actual = service.register(defaultUser);
        assertEquals(defaultUser, actual);
    }

    @Test
    void register_loginNull_notOk() {
        defaultUser.setLogin(null);
        assertThrows(RuntimeException.class, () -> {
            service.register(defaultUser);
        });
    }

    @Test
    void register_userExists_notOk() {
        service.register(defaultUser);
        assertThrows(RuntimeException.class, () -> {
            service.register(defaultUser);
        });
    }

    @Test
    void register_checkedAge_notOk() {
        defaultUser.setAge(17);
        assertThrows(RuntimeException.class, () -> {
            service.register(defaultUser);
        });
        defaultUser.setAge(0);
        assertThrows(RuntimeException.class, () -> {
            service.register(defaultUser);
        });
    }

    @Test
    void register_checkedNullAge_notOk() {
        defaultUser.setAge(null);
        assertThrows(RuntimeException.class, () -> {
            service.register(defaultUser);
        });
    }
}
