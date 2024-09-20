package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.RegistrationValidationException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.Test;

class RegistrationServiceTest {
    private final RegistrationService registrationService = new RegistrationServiceImpl();

    @Test
    void register_ValidUser_Ok() {
        User user = new User();
        user.setLogin("testlogin");
        user.setPassword("password123");
        user.setAge(25);

        User registeredUser = registrationService.register(user);

        assertNotNull(registeredUser);
        assertEquals("testlogin", registeredUser.getLogin());
        assertEquals("password123", registeredUser.getPassword());
        assertEquals(25, registeredUser.getAge());
        assertNotNull(registeredUser.getId());
    }

    @Test
    void validLogin_Not_Ok() {
        User user = new User();
        user.setLogin("log");
        user.setPassword("password123");
        user.setAge(25);

        assertThrows(RegistrationValidationException.class, () -> {
            registrationService.register(user);
        }, "RegistrationValidationException "
                + "should be thrown if the login has less than 6 characters");
    }

    @Test
    void validPassword_Not_Ok() {
        User user = new User();
        user.setLogin("testlogin");
        user.setPassword("pass");
        user.setAge(25);

        assertThrows(RegistrationValidationException.class, () -> {
            registrationService.register(user);
        }, "RegistrationValidationException "
                + "should be thrown if the password has less than 6 characters");
    }

    @Test
    void validAge_Not_Ok() {
        User user = new User();
        user.setLogin("testlogin");
        user.setPassword("password");
        user.setAge(12);

        assertThrows(RegistrationValidationException.class, () -> {
            registrationService.register(user);
        }, "RegistrationValidationException "
                + "should be thrown if age is less than 18");

    }

    @Test
    void loginAuthentication() {
        User user1 = new User();
        user1.setLogin("testlogin");
        user1.setPassword("password");
        user1.setAge(25);

        registrationService.register(user1);

        User user2 = new User();
        user2.setLogin("testlogin");
        user2.setPassword("password123");
        user2.setAge(30);

        assertThrows(RegistrationValidationException.class, () -> {
            registrationService.register(user2);
        }, "RegistrationValidationException "
                + "should be thrown if the login already exists");
    }

    @Test
    void checkingRepository() {
        User user1 = new User();
        user1.setLogin("testlogin");
        user1.setPassword("password");
        user1.setAge(25);
        User registeredUser1 = registrationService.register(user1);
        assertEquals(registeredUser1, registrationService.get(user1.getLogin()));
        User user2 = new User();
        user2.setLogin("testlogin123");
        user2.setPassword("password12345");
        user2.setAge(30);
        User registeredUser2 = registrationService.register(user2);
        assertEquals(registeredUser2, registrationService.get(user2.getLogin()));
    }

    @Test
    void borderline_cases() {
        User user = new User();
        user.setLogin("login1");
        user.setPassword("passwo");
        user.setAge(18);
        User userRegistered = registrationService.register(user);
        assertNotNull(userRegistered);
        assertEquals("login1", userRegistered.getLogin());
    }
}
