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
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin("User@gmail.com");
        user.setPassword("password123");
        user.setAge(19);
    }

    @Test
    void register_validUser_Ok() {
        User actual = registrationService.register(user);
        assertEquals(actual, user, "Your registration was failed");
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_loginAlreadyExists_notOk() {
        user.setLogin("login@gmail.com");
        Storage.people.add(user);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_notValidAge_notOk() {
        user.setAge(-1);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
        user.setAge(16);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_notValidPassword_notOk() {
        user.setPassword("");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
        user.setPassword("3w@68");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }
}
