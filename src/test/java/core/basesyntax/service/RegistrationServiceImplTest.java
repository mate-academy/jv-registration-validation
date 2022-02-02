package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
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
        user.setLogin("niteris");
        user.setPassword("qwerty123");
        user.setAge(18);
    }

    @Test
    void register_NullLogin_NotOK() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_User_OK() {
        User registerUser = registrationService.register(user);
        assertEquals(user.getLogin(), registerUser.getLogin());
        assertEquals(user.getPassword(), registerUser.getPassword());
        assertEquals(user.getAge(), registerUser.getAge());
    }

    @Test
    void register_AgeLessThan18_NotOK() {
        user.setAge(5);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_NullAge_NotOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_PasswordLess_Length_NotOK() {
        user.setPassword("12345");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_NullPassword_NotOK() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_ExistLogin_NotOk() {
        registrationService.register(user);
        User newUser = new User();
        newUser.setLogin(user.getLogin());
        newUser.setAge(18);
        newUser.setPassword("testpassword");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @AfterEach
    void clearStorage() {
        Storage.people.clear();
    }
}
