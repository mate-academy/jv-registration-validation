package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.RegistrationException;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceTest {
    private RegistrationService registrationService = new RegistrationServiceImpl();

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_ValidUser_Ok() {
        User user = new User("testlogin", "password123", 25);

        User registeredUser = registrationService.register(user);

        assertNotNull(registeredUser);
        assertEquals("testlogin", registeredUser.getLogin());
        assertEquals("password123", registeredUser.getPassword());
        assertEquals(25, registeredUser.getAge());
        assertNotNull(registeredUser.getId());
    }

    @Test
    void register_loginShorterThanRequired_notOk() {
        User user = new User("log", "password123", 25);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        }, "RegistrationException "
                + "should be thrown if the login has less than 6 characters");
    }

    @Test
    void register_passwordShorterThanRequired_notOk() {
        User user = new User("testlogin", "pass", 25);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        }, "RegistrationException "
                + "should be thrown if the password has less than 6 characters");
    }

    @Test
    void register_ageLessThanRequired_notOk() {
        User user = new User("testlogin", "password", 12);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        }, "RegistrationException "
                + "should be thrown if age is less than 18");

    }

    @Test
    void register_duplicateLogin_notOk() {
        User user = new User("testlogin", "password", 25);
        Storage.people.add(user);
        User newUser = new User("testlogin", "password123", 30);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(newUser);
        }, "RegistrationException "
                + "should be thrown if the login already exists");
    }

    @Test
    void register_minimumLoginLength_ok_() {
        User user = new User("login1", "password", 20);
        User userRegistered = registrationService.register(user);
        assertNotNull(userRegistered);
        assertEquals("login1", userRegistered.getLogin());
    }

    @Test
    void register_minimumPasswordLength_ok() {
        User user = new User("logintest", "passwo", 20);
        User userRegistered = registrationService.register(user);
        assertNotNull(userRegistered);
        assertEquals("passwo", userRegistered.getPassword());
    }

    @Test
    void register_minimumAge_ok() {
        User user = new User("logintest", "password123", 18);
        User userRegistered = registrationService.register(user);
        assertNotNull(userRegistered);
        assertEquals(18, userRegistered.getAge());
    }

    @Test
    void registe_nullUser_notOk() {
        User user = null;
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        }, "RegistrationException should be thrown if null user");
    }

    @Test
    void register_nullLogin_notOk() {
        User user = new User(null, "password123", 20);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        }, "RegistrationException should be thrown if null login");
    }

    @Test
    void register_nullPassword_notOk() {
        User user = new User("logintest", null, 20);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        }, "RegistrationException should be thrown if null password");
    }

    @Test
    void register_nullAge_notOk() {
        User user = new User("logintest", "password123", 0);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        }, "RegistrationException should be thrown if null age");
    }
}
