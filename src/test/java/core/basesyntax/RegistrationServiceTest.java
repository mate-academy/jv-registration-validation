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
        User user1 = new User(null, "", 21);
        User user2 = new User(null, "val", 21);
        User user3 = new User(null, "notvd", 21);
        User user4 = new User(null, "validp", 21);
        User user5 = new User(null, "validpas", 21);
        User user6 = new User(null, "", MIN_AGE);
        User user7 = new User(null, "val", MIN_AGE);
        User user8 = new User(null, "notvd", MIN_AGE);
        User user9 = new User(null, "validp", MIN_AGE);
        User user10 = new User(null, "validpas", MIN_AGE);
        User user11 = new User(null, "", 17);
        User user12 = new User(null, "abc", 17);
        User user13 = new User(null, "abcde", 17);
        User user14 = new User(null, "validp", 17);
        User user15 = new User(null, "validpas", 17);
        assertThrows(RegistrationException.class, () -> registrationService.register(user1));
        assertThrows(RegistrationException.class, () -> registrationService.register(user2));
        assertThrows(RegistrationException.class, () -> registrationService.register(user3));
        assertThrows(RegistrationException.class, () -> registrationService.register(user4));
        assertThrows(RegistrationException.class, () -> registrationService.register(user5));
        assertThrows(RegistrationException.class, () -> registrationService.register(user6));
        assertThrows(RegistrationException.class, () -> registrationService.register(user7));
        assertThrows(RegistrationException.class, () -> registrationService.register(user8));
        assertThrows(RegistrationException.class, () -> registrationService.register(user9));
        assertThrows(RegistrationException.class, () -> registrationService.register(user10));
        assertThrows(RegistrationException.class, () -> registrationService.register(user11));
        assertThrows(RegistrationException.class, () -> registrationService.register(user12));
        assertThrows(RegistrationException.class, () -> registrationService.register(user13));
        assertThrows(RegistrationException.class, () -> registrationService.register(user14));
        assertThrows(RegistrationException.class, () -> registrationService.register(user15));
    }

    @Test
    public void register_NullPassword_NotOk() {
        User user1 = new User("", null, 14);
        User user2 = new User("abc", null, 14);
        User user3 = new User("abcde", null, 14);
        User user4 = new User("validp", null, 14);
        User user5 = new User("validpas", null, 14);
        User user6 = new User("", null, MIN_AGE);
        User user7 = new User("abc", null, MIN_AGE);
        User user8 = new User("abcde", null, MIN_AGE);
        User user9 = new User("validp", null, MIN_AGE);
        User user10 = new User("validpas", null, MIN_AGE);
        User user11 = new User("", null, 22);
        User user12 = new User("abc", null, 22);
        User user13 = new User("abcde", null, 22);
        User user14 = new User("validp", null, 22);
        User user15 = new User("validpas", null, 22);
        assertThrows(RegistrationException.class, () -> registrationService.register(user1));
        assertThrows(RegistrationException.class, () -> registrationService.register(user2));
        assertThrows(RegistrationException.class, () -> registrationService.register(user3));
        assertThrows(RegistrationException.class, () -> registrationService.register(user4));
        assertThrows(RegistrationException.class, () -> registrationService.register(user5));
        assertThrows(RegistrationException.class, () -> registrationService.register(user6));
        assertThrows(RegistrationException.class, () -> registrationService.register(user7));
        assertThrows(RegistrationException.class, () -> registrationService.register(user8));
        assertThrows(RegistrationException.class, () -> registrationService.register(user9));
        assertThrows(RegistrationException.class, () -> registrationService.register(user10));
        assertThrows(RegistrationException.class, () -> registrationService.register(user11));
        assertThrows(RegistrationException.class, () -> registrationService.register(user12));
        assertThrows(RegistrationException.class, () -> registrationService.register(user13));
        assertThrows(RegistrationException.class, () -> registrationService.register(user14));
        assertThrows(RegistrationException.class, () -> registrationService.register(user15));
    }

    @Test
    public void register_NullAge_NotOk() {
        User user1 = new User("", "", null);
        User user2 = new User("", "abc", null);
        User user3 = new User("abc", "", null);
        User user4 = new User("abcde", "", null);
        User user5 = new User("", "abcde", null);
        User user6 = new User("abc", "abcde", null);
        User user7 = new User("abcde", "abc", null);
        User user8 = new User("abcde", "abcde", null);
        User user9 = new User("validp", "abcde", null);
        User user10 = new User("adcde", "validp", null);
        User user11 = new User("validp", "validp", null);
        User user12 = new User("validpas", "validp", null);
        User user13 = new User("validp", "validpas", null);
        User user14 = new User("validpas", "validpas", null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user1));
        assertThrows(RegistrationException.class, () -> registrationService.register(user2));
        assertThrows(RegistrationException.class, () -> registrationService.register(user3));
        assertThrows(RegistrationException.class, () -> registrationService.register(user4));
        assertThrows(RegistrationException.class, () -> registrationService.register(user5));
        assertThrows(RegistrationException.class, () -> registrationService.register(user6));
        assertThrows(RegistrationException.class, () -> registrationService.register(user7));
        assertThrows(RegistrationException.class, () -> registrationService.register(user8));
        assertThrows(RegistrationException.class, () -> registrationService.register(user9));
        assertThrows(RegistrationException.class, () -> registrationService.register(user10));
        assertThrows(RegistrationException.class, () -> registrationService.register(user11));
        assertThrows(RegistrationException.class, () -> registrationService.register(user12));
        assertThrows(RegistrationException.class, () -> registrationService.register(user13));
        assertThrows(RegistrationException.class, () -> registrationService.register(user14));
    }

    @Test
    public void register_UserUnder18_NotOk() {
        User user1 = new User("", "", 13);
        User user2 = new User("", "abc", 14);
        User user3 = new User("abc", "", 15);
        User user4 = new User("abcde", "", 16);
        User user5 = new User("", "abcde", 17);
        User user6 = new User("abc", "abcde", 16);
        User user7 = new User("abcde", "abc", 15);
        User user8 = new User("abcde", "abcde", 14);
        User user9 = new User("validp", "abcde", 13);
        User user10 = new User("adcde", "validp", 14);
        User user11 = new User("validp", "validp", 15);
        User user12 = new User("validpas", "validp", 16);
        User user13 = new User("validp", "validpas", 17);
        User user14 = new User("validpas", "validpas", 16);
        assertThrows(RegistrationException.class, () -> registrationService.register(user1));
        assertThrows(RegistrationException.class, () -> registrationService.register(user2));
        assertThrows(RegistrationException.class, () -> registrationService.register(user3));
        assertThrows(RegistrationException.class, () -> registrationService.register(user4));
        assertThrows(RegistrationException.class, () -> registrationService.register(user5));
        assertThrows(RegistrationException.class, () -> registrationService.register(user6));
        assertThrows(RegistrationException.class, () -> registrationService.register(user7));
        assertThrows(RegistrationException.class, () -> registrationService.register(user8));
        assertThrows(RegistrationException.class, () -> registrationService.register(user9));
        assertThrows(RegistrationException.class, () -> registrationService.register(user10));
        assertThrows(RegistrationException.class, () -> registrationService.register(user11));
        assertThrows(RegistrationException.class, () -> registrationService.register(user12));
        assertThrows(RegistrationException.class, () -> registrationService.register(user13));
        assertThrows(RegistrationException.class, () -> registrationService.register(user14));
    }

    @Test
    public void register_NegativeAge_NotOk() {
        User user1 = new User("", "", -1);
        User user2 = new User("", "abc", -2);
        User user3 = new User("abc", "", -3);
        User user4 = new User("abcde", "", -3);
        User user5 = new User("", "abcde", -3);
        User user6 = new User("abc", "abcde", -3);
        User user7 = new User("abcde", "abc", -3);
        User user8 = new User("abcde", "abcde", -3);
        User user9 = new User("validp", "abcde", -3);
        User user10 = new User("adcde", "validp", -3);
        User user11 = new User("validp", "validp", -3);
        User user12 = new User("validpas", "validp", -3);
        User user13 = new User("validp", "validpas", -3);
        User user14 = new User("validpas", "validpas", -3);
        assertThrows(RegistrationException.class, () -> registrationService.register(user1));
        assertThrows(RegistrationException.class, () -> registrationService.register(user2));
        assertThrows(RegistrationException.class, () -> registrationService.register(user3));
        assertThrows(RegistrationException.class, () -> registrationService.register(user4));
        assertThrows(RegistrationException.class, () -> registrationService.register(user5));
        assertThrows(RegistrationException.class, () -> registrationService.register(user6));
        assertThrows(RegistrationException.class, () -> registrationService.register(user7));
        assertThrows(RegistrationException.class, () -> registrationService.register(user8));
        assertThrows(RegistrationException.class, () -> registrationService.register(user9));
        assertThrows(RegistrationException.class, () -> registrationService.register(user10));
        assertThrows(RegistrationException.class, () -> registrationService.register(user11));
        assertThrows(RegistrationException.class, () -> registrationService.register(user12));
        assertThrows(RegistrationException.class, () -> registrationService.register(user13));
        assertThrows(RegistrationException.class, () -> registrationService.register(user14));
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
        User userOverMinAge2 = new User("valid4", "validpas", 19);
        User registeredUser1 = registrationService.register(registerPasswordWithSixCharacter);
        User registeredUser2 = registrationService.register(registerPasswordWithEightCharacter);
        User registeredUser3 = registrationService.register(userOverMinAge1);
        User registeredUser4 = registrationService.register(userOverMinAge2);
        assertNotNull(registeredUser1.getId());
        assertNotNull(registeredUser2.getId());
        assertNotNull(registeredUser3.getId());
        assertNotNull(registeredUser4.getId());
    }
}
