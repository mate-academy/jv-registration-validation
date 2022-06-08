package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        user = new User("Login", "password", 21);
    }

    @Test
    void registerUserNull_NotOK() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void registerUserLoginNull_NotOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void registerUserLoginEmpty_NotOk() {
        user.setLogin("");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void registerUserPasswordNull_NotOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void registerUserLoginAndPasswordNull_NotOk() {
        user.setLogin(null);
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register17Year_NotOk() {
        user.setAge(17);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register18Year_Ok() {
        user.setAge(18);
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void register19Year_Ok() {
        user.setAge(19);
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void registerPassword4len_NotOk() {
        user.setPassword("pass");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void registerPassword6len_Ok() {
        user.setPassword("passwd");
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void registerPassword7len_Ok() {
        user.setPassword("passwor");
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void registerSameUser_NotOk() {
        User user2 = new User(user.getLogin(), "anotherpassword", 56);
        registrationService.register(user2);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }
}
