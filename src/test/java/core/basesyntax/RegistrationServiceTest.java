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
        User user1 = new User(null, "validp", 21);
        User user2 = new User(null, "validp", MIN_AGE);
        User user3 = new User(null, "validp", 17);
        assertThrows(RegistrationException.class, () -> registrationService.register(user1));
        assertThrows(RegistrationException.class, () -> registrationService.register(user2));
        assertThrows(RegistrationException.class, () -> registrationService.register(user3));
    }

    @Test
    public void register_NullPassword_NotOk() {
        User user1 = new User("validp", null, 14);
        User user2 = new User("validp", null, MIN_AGE);
        User user3 = new User("validp", null, 22);
        assertThrows(RegistrationException.class, () -> registrationService.register(user1));
        assertThrows(RegistrationException.class, () -> registrationService.register(user2));
        assertThrows(RegistrationException.class, () -> registrationService.register(user3));
    }

    @Test
    public void register_NullAge_NotOk() {
        User user1 = new User("", "", null);
        User user2 = new User("abcde", "abcde", null);
        User user3 = new User("validp", "validp", null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user1));
        assertThrows(RegistrationException.class, () -> registrationService.register(user2));
        assertThrows(RegistrationException.class, () -> registrationService.register(user3));
    }

    @Test
    public void register_UserUnder18_NotOk() {
        User user1 = new User("", "", 13);
        User user2 = new User("abcde", "abcde", 14);
        User user3 = new User("validp", "validp", 15);
        assertThrows(RegistrationException.class, () -> registrationService.register(user1));
        assertThrows(RegistrationException.class, () -> registrationService.register(user2));
        assertThrows(RegistrationException.class, () -> registrationService.register(user3));
    }

    @Test
    public void register_NegativeAge_NotOk() {
        User user1 = new User("", "", -1);
        User user2 = new User("abcde", "abcde", -3);
        User user3 = new User("validp", "validpas", -3);
        assertThrows(RegistrationException.class, () -> registrationService.register(user1));
        assertThrows(RegistrationException.class, () -> registrationService.register(user2));
        assertThrows(RegistrationException.class, () -> registrationService.register(user3));

    }

    @Test
    public void register_ShortLogin_NotOk() {
        User user1 = new User("", "validp", MIN_AGE);
        User user2 = new User("abc", "validpas", MIN_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user1));
        assertThrows(RegistrationException.class, () -> registrationService.register(user2));
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
        User registerPasswordWithSixCharacter = new User("valid1", "abcdef", MIN_AGE);
        User registerPasswordWithEightCharacter = new User("valid2", "abcdefgh", MIN_AGE);
        User userOverMinAge1 = new User("valid3", "validp", 19);
        User registeredUser1 = registrationService.register(registerPasswordWithSixCharacter);
        User registeredUser2 = registrationService.register(registerPasswordWithEightCharacter);
        User registeredUser3 = registrationService.register(userOverMinAge1);
        assertNotNull(registeredUser1.getId());
        assertNotNull(registeredUser2.getId());
        assertNotNull(registeredUser3.getId());
    }
}
