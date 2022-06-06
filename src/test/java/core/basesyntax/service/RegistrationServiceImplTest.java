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
        user = new User();
    }

    @Test
    void registerUserNull_NotOK() {
        user = null;
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void registerUserLoginNull_NotOk() {
        user.setPassword("password");
        user.setAge(20);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void registerUserLoginEmpty_NotOk() {
        user.setLogin("");
        user.setPassword("password");
        user.setAge(20);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void registerUserPasswordNull_NotOk() {
        user.setLogin("Login");
        user.setAge(20);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void registerUserLoginAndPasswordNull_NotOk() {
        user.setAge(20);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register17Year_NotOk() {
        user.setPassword("password");
        user.setLogin("Login");
        user.setAge(17);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register18Year_Ok() {
        user.setPassword("password");
        user.setLogin("Login");
        user.setAge(18);
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void register19Year_Ok() {
        user.setPassword("password");
        user.setLogin("Login");
        user.setAge(19);
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void registerPassword4len_NotOk() {
        user.setLogin("Login");
        user.setPassword("pass");
        user.setAge(21);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void registerPassword6len_Ok() {
        user.setLogin("Login");
        user.setPassword("passwd");
        user.setAge(21);
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void registerPassword7len_Ok() {
        user.setLogin("Login");
        user.setPassword("passwor");
        user.setAge(21);
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void registerSameUser_NotOk() {
        user.setLogin("Login");
        user.setPassword("password");
        user.setAge(21);
        User user2 = new User();
        user2.setLogin("Login");
        user2.setPassword("545485");
        user2.setAge(56);
        registrationService.register(user2);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }
}
