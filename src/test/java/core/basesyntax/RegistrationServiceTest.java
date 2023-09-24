package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceTest {
    private RegistrationService registrationService;

    @BeforeEach
    public void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    public void register_ValidUser_Success() {
        User user = new User("user123", "password123", 18);
        User registeredUser = registrationService.register(user);
        assertNotNull(registeredUser);
        assertEquals(user, registeredUser);
    }

    @Test
    public void register_UserWithShortLogin_ThrowsException() {
        User user = new User("u", "password123", 18);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_UserWithShortPassword_ThrowsException() {
        User user = new User("user123", "pass", 18);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_UnderageUser_ThrowsException() {
        User user = new User("validuser", "validpassword", 17);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_UserWithValidParameters_ReturnsRegisteredUser() {
        User user = new User("validuser", "validpassword", 18);
        User registeredUser = registrationService.register(user);
        assertNotNull(registeredUser);
        assertEquals(user, registeredUser);
    }

    @Test
    public void register_UserWithExistingLogin_ThrowsException() {
        User existingUser = new User("existinguser", "password", 30);
        registrationService.register(existingUser);

        User userWithSameLogin = new User("existinguser", "newpassword", 35);
        assertThrows(RegistrationException.class, () -> registrationService
                .register(userWithSameLogin));
    }
}
