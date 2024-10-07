package core.basesyntax;

import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

/**
 * Feel free to remove this class and create your own.
 */
public class RegistrationServiceTest {
    private RegistrationService registrationService = new RegistrationServiceImpl();
    private User user = new User();

    @Test
    void registerNullUser_NotOk() {
        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(null);
        });

        assertEquals("User is null.", exception.getMessage());
    }

    @Test
    void registerLoginNull_NotOk() {
        user.setLogin(null);
        user.setPassword("validPassword");
        user.setAge(20);

        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
        assertEquals("Login can't be null.", exception.getMessage());
    }

    @Test
    void registerPasswordNull_NotOk() {
        user.setLogin("validLogin");
        user.setPassword(null);
        user.setAge(20);

        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
        assertEquals("Password can't be null.", exception.getMessage());
    }

    @Test
    void registerUserAgeNull_NotOk() {
        user.setLogin("validLogin");
        user.setPassword("validPassword");
        user.setAge(null);

        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
        assertEquals("Age can't be null.", exception.getMessage());
    }

    @Test
    void registerLogin_NotOk() {
        String[] invalidLogins = {"1", "ab", "son", "hell", "user"};

        for (String login : invalidLogins) {
            user.setLogin(login);
            user.setPassword("validPassword");
            user.setAge(20);

            RegistrationException exception = assertThrows(RegistrationException.class, () -> {
                registrationService.register(user);
            });

            assertEquals("Login length must be at least 6 characters.", exception.getMessage());
        }
    }

    @Test
    void registerPassword_NotOk() {
        String[] invalidPasswords = {"hello", "hell", "user"};

        for (String password : invalidPasswords) {
            user.setLogin("validLogin");
            user.setPassword(password);
            user.setAge(20);

            RegistrationException exception = assertThrows(RegistrationException.class, () -> {
                registrationService.register(user);
            });

            assertEquals("Password length must be at least 6 characters.", exception.getMessage());
        }
    }

    @Test
    void registerUserAge_NotOk() {
        for (int i = 0; i < 18; i++) {
            user.setLogin("validLogin");
            user.setPassword("validPassword");
            user.setAge(i);

            RegistrationException exception = assertThrows(RegistrationException.class, () -> {
                registrationService.register(user);
            });

            assertEquals("Age must be at least 18 years old.", exception.getMessage());
        }
    }

    @Test
    void register_Ok() {
        user.setLogin("validUser");
        user.setPassword("validPassword123");
        user.setAge(20);

        User registeredUser = assertDoesNotThrow(() -> {
            return registrationService.register(user);
        });

        assertNotNull(registeredUser);
        assertEquals("validUser", registeredUser.getLogin());
        assertEquals("validPassword123", registeredUser.getPassword());
        assertEquals(20, registeredUser.getAge());
    }
}
