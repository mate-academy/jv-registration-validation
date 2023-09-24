package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class RegistrationServiceTest {
    private static RegistrationService registrationService;
    private static final int MIN_AGE = 18;

    @BeforeAll
    public static void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    public void register_NullLogin_ThrowsException() {
        User user = new User(null, "validpassword", MIN_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_NullPassword_ThrowsException() {
        User user = new User("validuser", null, MIN_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_NullAge_ThrowsException() {
        User user = new User("validuser", "validpassword", null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_UserUnder18_ThrowsException() {
        User user = new User("validuser", "validpassword", 17);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_UserOver18_Success() {
        User user = new User("validuser", "validpassword", 19);
        User registeredUser = registrationService.register(user);
        assertNotNull(registeredUser.getId());
    }

    @Test
    public void register_NegativeAge_ThrowsException() {
        User user = new User("validuser", "validpassword", -5);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_EmptyLogin_ThrowsException() {
        User user = new User("", "validpassword", MIN_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_ShortLogin_ThrowsException() {
        User user = new User("u", "validpassword", MIN_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_NotValidPassword_NotOk() {
        User userWithEmptyPassword = new User("validuser", "", MIN_AGE);
        User userWithShortPassword = new User("validuser", "abc", MIN_AGE);
        User userWithFiveCharacterPassword = new User("validuser", "abcdf", MIN_AGE);
        assertThrows(RegistrationException.class, () -> registrationService
                .register(userWithEmptyPassword));
        assertThrows(RegistrationException.class, () -> registrationService
                .register(userWithShortPassword));
        assertThrows(RegistrationException.class, () -> registrationService
                .register(userWithFiveCharacterPassword));
    }

    @Test
    public void registerPassword_Ok() throws RegistrationException {
        User registerPasswordWithSixCharacter = new User("validuser", "abcdef", MIN_AGE);
        User registerPasswordWithEightCharacter = new User("validuser", "abcdefgh", MIN_AGE);
        assertThrows(RegistrationException.class, () -> registrationService
                .register(registerPasswordWithSixCharacter));
        assertThrows(RegistrationException.class, () -> registrationService
                .register(registerPasswordWithEightCharacter));

    }
}
