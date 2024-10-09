package core.basesyntax.service;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exception.InvalidUserDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



class RegistrationServiceImplTest {
    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_userIsNull_notOk() {
        Exception exception = assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(null);
        });
        assertEquals("User cannot be null", exception.getMessage());
    }

    @Test
    void register_userWithExistingLogin_notOk() {
        User user1 = new User();
        user1.setLogin("testuser");
        user1.setPassword("password");
        user1.setAge(20);
        registrationService.register(user1);

        User user2 = new User();
        user2.setLogin("testuser");
        user2.setPassword("newpassword");
        user2.setAge(22);

        Exception exception = assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(user2);
        });
        assertEquals("User with this login already exists", exception.getMessage());
    }

    @Test
    void register_loginTooShort_notOk() {
        User user = new User();
        user.setLogin("short");
        user.setPassword("password");
        user.setAge(20);

        Exception exception = assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(user);
        });
        assertEquals("Login must be at least 6 characters long", exception.getMessage());
    }

    @Test
    void register_passwordTooShort_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("short");
        user.setAge(20);

        Exception exception = assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(user);
        });
        assertEquals("Password must be at least 6 characters long", exception.getMessage());
    }

    @Test
    void register_ageTooYoung_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("validPassword");
        user.setAge(17);

        Exception exception = assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(user);
        });
        assertEquals("User must be at least 18 years old", exception.getMessage());
    }

    @Test
    void register_validUser_ok() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("validPassword");
        user.setAge(20);

        User registeredUser = registrationService.register(user);
        assertNotNull(registeredUser);
        assertEquals(user.getLogin(), registeredUser.getLogin());
    }
}
