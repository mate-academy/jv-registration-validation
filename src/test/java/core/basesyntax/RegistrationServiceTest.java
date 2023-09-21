package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        assertNotNull(registeredUser.getId());
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
    public void register_UserWithExistingLogin_ThrowsException() {
        User existingUser = new User("existinguser", "password", 30);
        registrationService.register(existingUser);

        User userWithSameLogin = new User("existinguser", "newpassword", 35);
        assertThrows(RegistrationException.class, () -> registrationService.register(userWithSameLogin));
    }

    @Test
    public void isAdult_WhenAgeIsNull_ReturnsFalse() {
        User user = new User();
        assertFalse(user.isAdult());
    }

    @Test
    public void isAdult_WhenAgeIsLessThan18_ReturnsFalse() {
        User user = new User("user123", "password123", 17);
        assertFalse(user.isAdult());
    }

    @Test
    public void isAdult_WhenAgeIsEqualTo18_ReturnsTrue() {
        User user = new User("user123", "password123", 18);
        assertTrue(user.isAdult());
    }

    @Test
    public void hasValidLoginAndPassword_WhenLoginIsNull_ReturnsFalse() {
        User user = new User();
        user.setPassword("password");
        assertFalse(user.hasValidLoginAndPassword());
    }

    @Test
    public void hasValidLoginAndPassword_WhenPasswordIsNull_ReturnsFalse() {
        User user = new User();
        user.setLogin("username");
        assertFalse(user.hasValidLoginAndPassword());
    }

    @Test
    public void hasValidLoginAndPassword_WhenLoginAndPasswordAreValid_ReturnsTrue() {
        User user = new User("username", "password123", 18);
        assertTrue(user.hasValidLoginAndPassword());
    }

    @Test
    public void equals_SameUser_ReturnsTrue() {
        User user1 = new User("user123", "password123", 18);
        User user2 = new User("user123", "password123", 18);
        assertTrue(user1.equals(user2));
    }

    @Test
    public void equals_DifferentUsers_ReturnsFalse() {
        User user1 = new User("user123", "password123", 18);
        User user2 = new User("differentUser", "differentPassword", 25);
        assertFalse(user1.equals(user2));
    }
}





