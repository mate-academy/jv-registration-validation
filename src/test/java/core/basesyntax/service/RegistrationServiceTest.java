package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.*;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceTest {
    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    public void register_validUser_ok() {
        User user = new User("testUser", "password123", 25);
        User registeredUser = registrationService.register(user);
        assertNotNull(registeredUser);
        assertEquals("testUser", registeredUser.getLogin());
    }

    @Test
    public void register_duplicateName_throwsException() {
        User user1 = new User("username", "password123", 25);
        User user2 = new User("username", "anotherPassword", 30);

        registrationService.register(user1);

        RegistrationException exception = assertThrows(
                RegistrationException.class,
                () -> registrationService.register(user2)
        );

        assertEquals("User with the same login already exists", exception.getMessage());
    }

    @Test
    void register_shortLogin_throwsException() {
        User user = new User("short", "password123", 25);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortPassword_throwsException() {
        User userWithShortPassword = new User("username", "short", 25);
        RegistrationException exception = assertThrows(RegistrationException.class, () ->
                registrationService.register(userWithShortPassword));
    }

    @Test
    void register_underAge_throwsException() {
        User user = new User("username", "password123", 17);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_negativeAge_throwsException() {
        User user = new User("username", "password123", -5);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullLogin_throwsException() {
        User user = new User(null, "password123", 25);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_throwsException() {
        User user = new User("username", null, 25);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }
}
