package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.RegistrationValidationException;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceTest {
    private final RegistrationService registrationService = new RegistrationServiceImpl();

    @BeforeEach
    void clearStorage() {
        Storage.people.clear();
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

        assertThrows(RegistrationValidationException.class, () -> {
            registrationService.register(user);
        }, "RegistrationValidationException "
                + "should be thrown if the login has less than 6 characters");
    }

    @Test
    void register_passwordShorterThanRequired_notOk() {
        User user = new User("testlogin", "pass", 25);

        assertThrows(RegistrationValidationException.class, () -> {
            registrationService.register(user);
        }, "RegistrationValidationException "
                + "should be thrown if the password has less than 6 characters");
    }

    @Test
    void register_ageLessThanRequired_notOk() {
        User user = new User("testlogin", "password", 12);

        assertThrows(RegistrationValidationException.class, () -> {
            registrationService.register(user);
        }, "RegistrationValidationException "
                + "should be thrown if age is less than 18");

    }

    @Test
    void register_duplicateLogin_notOk() {
        User user1 = new User("testlogin", "password", 25);
        Storage.people.add(user1);

        User user2 = new User("testlogin", "password123", 30);

        assertThrows(RegistrationValidationException.class, () -> {
            registrationService.register(user2);
        }, "RegistrationValidationException "
                + "should be thrown if the login already exists");
    }

    @Test
    void register_userAddedToStorage_Ok() {
        User user1 = new User("testlogin", "password", 25);
        User registeredUser1 = registrationService.register(user1);
        assertEquals(registeredUser1, registrationService.get(user1.getLogin()));
        User user2 = new User("testlogin123", "password12345", 30);
        User registeredUser2 = registrationService.register(user2);
        assertEquals(registeredUser2, registrationService.get(user2.getLogin()));
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
}
