package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceTest {

    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin("user");
        user.setPassword("password");
        user.setAge(18);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_nullLogin_notOK() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_EmptyLogin_notOK() {
        user.setLogin("");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_ExistLogin_notOk() {
        registrationService.register(user);
        User newUser = new User();
        newUser.setLogin(user.getLogin());
        newUser.setAge(18);
        newUser.setPassword("ddsdsds");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @Test
    void register_user_OK() {
        User registerUser = registrationService.register(user);
        assertEquals(user.getLogin(), registerUser.getLogin());
        assertEquals(user.getPassword(), registerUser.getPassword());
        assertEquals(user.getAge(), registerUser.getAge());
    }

    @Test
    void register_AgeLessThan18_notOK() {
        user.setAge(5);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_passwordLess_Length_notOK() {
        user.setPassword("12345");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullPassword_notOK() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_emptyPassword_notOK() {
        user.setPassword("");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }
}
