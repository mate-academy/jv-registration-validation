package core.basesyntax;

import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Feel free to remove this class and create your own.
 */
public class RegistrationServiceTest {
    private RegistrationService registrationService;

    @BeforeEach
    public void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    public void register_ValidUser_Success() {
        User user = new User();
        user.setLogin("user123");
        user.setPassword("password123");
        user.setAge(18);
    }

    @Test
    public void register_UserWitShortLogin_ThrowsException() {
        User user = new User();
        user.setLogin("u");
        user.setPassword("password123");
        user.setAge(18);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_UserWithSortPassword_ThrowsException() {
        User user = new User();
        user.setLogin("user123");
        user.setPassword("pass");
        user.setAge(18);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_underageUser_registrationFailed() {
        User user = new User();
        user.setLogin("validuser");
        user.setPassword("validpassword");
        user.setAge(17);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }
    @Test
    public void register_shortPassword_registrationFailed() {
        User user = new User();
        user.setLogin("validuser");
        user.setPassword("short");
        user.setAge(20);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }
    @Test
    public void register_shortLogin_registrationFailed() {
        User user = new User();
        user.setLogin("short");
        user.setPassword("validpassword");
        user.setAge(25);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }
    @Test
    public void register_existingLogin_registrationFailed() {
        User existingUser = new User();
        existingUser.setLogin("existinguser");
        existingUser.setPassword("password");
        existingUser.setAge(30);
        registrationService.register(existingUser);
        User userWithSameLogin = new User();
        userWithSameLogin.setLogin("existinguser");
        userWithSameLogin.setPassword("newpassword");
        userWithSameLogin.setAge(35);
        assertThrows(RegistrationException.class, () -> registrationService.register(userWithSameLogin));
    }
    @Test
    public void isAdult_WhenAgeIsNull_ReturnsFalse() {
        User user = new User();
        assertFalse(user.isAdult());
    }

    @Test
    public void isAdult_WhenAgeIsLessThan18_ReturnsFalse() {
        User user = new User();
        user.setAge(17);
        assertFalse(user.isAdult());
    }

    @Test
    public void isAdult_WhenAgeIsEqualTo18_ReturnsTrue() {
        User user = new User();
        user.setAge(18);
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
        User user = new User();
        user.setLogin("username");
        user.setPassword("password123");
        assertTrue(user.hasValidLoginAndPassword());
    }

    @Test
    public void constructor_DefaultConstructor() {
        User user = new User();
        assertNotNull(user);
    }

    @Test
    public void constructor_ParameterizedConstructor() {
        User user = new User();
        assertNotNull(user);
    }

    @Test
    public void equals_SameUser_ReturnsTrue() {
        User user1 = new User();
        User user2 = new User();
        assertTrue(user1.equals(user2));
    }

    /*@Test
    public void equals_DifferentUsers_ReturnsFalse() {
        User user1 = new User();
        User user2 = new User();
        assertFalse(user1.equals(user2));
    }
*/
    @Test
    public void equals_DifferentUsers_ReturnsFalse() {
        User user1 = new User();
        user1.setLogin("user123");
        user1.setPassword("password123");
        user1.setAge(18);

        User user2 = new User();
        user2.setLogin("differentUser");
        user2.setPassword("differentPassword");
        user2.setAge(25);

        assertFalse(user1.equals(user2));
    }
    @Test
    public void hashCode_EqualUsers_HaveSameHashCode() {
        User user1 = new User();
        User user2 = new User();
        assertEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    public void isAdult_NullAge_ReturnsFalse() {
        User user = new User();
        assertFalse(user.isAdult());
    }

    @Test
    public void hasValidLoginAndPassword_NullLogin_ReturnsFalse() {
        User user = new User();
        assertFalse(user.hasValidLoginAndPassword());
    }
}





