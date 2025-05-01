package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.RegistrationException;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private static User userWithWrongLogin;
    private static User userWithWrongPassword;
    private static User userWithWrongAge;
    private static User existUser;
    private static User userWithSameLogin;

    @BeforeAll
    public static void setUp() {
        registrationService = new RegistrationServiceImpl();
        userWithWrongLogin = new User("Misha", "password", 25);
        userWithWrongPassword = new User("Viktor", "pass", 37);
        userWithWrongAge = new User("Marianna", "password", 12);
        existUser = new User("Mikhaylo", "password", 20);
        userWithSameLogin = new User("Mikhaylo", "password", 20);
    }

    @Test
    void register_sameLogin_notOk() {
        Storage.people.add(existUser);
        RegistrationException exceptionWithSimilarity = assertThrows(RegistrationException
                .class, () -> registrationService.register(userWithSameLogin));
        assertEquals("Users with same login already exist", exceptionWithSimilarity.getMessage());
    }

    @Test
    void register_loginLengthLessThanMin_notOk() {
        RegistrationException exceptionWithWrongPassword = assertThrows(RegistrationException
                .class, () -> registrationService.register(userWithWrongLogin));
        assertEquals("User's login must be at least 6 characters",
                exceptionWithWrongPassword.getMessage());
    }

    @Test
    void register_passwordLengthLessThanMin_notOk() {
        RegistrationException exceptionWithWrongPassword = assertThrows(RegistrationException
                .class, () -> registrationService.register(userWithWrongPassword));
        assertEquals("User's password must be at least 6 characters",
                exceptionWithWrongPassword.getMessage());
    }

    @Test
    void register_ageLessThanMin_notOk() {
        RegistrationException exceptionWithWrongAge = assertThrows(RegistrationException
                .class, () -> registrationService.register(userWithWrongAge));
        assertEquals("User's age must be at least 18 years", exceptionWithWrongAge.getMessage());
    }

    @Test
    void register_userNullValues_notOK() {
        User userWithNullLogin = new User(null, "123456", 20);

        RegistrationException exceptionWithNullLogin = assertThrows(RegistrationException
                .class, () -> registrationService.register(userWithNullLogin));
        assertEquals("Login cannot be null", exceptionWithNullLogin.getMessage());

        User userWithNullPassword = new User("qwerty", null, 20);

        RegistrationException exceptionWithNullPassword = assertThrows(RegistrationException
                .class, () -> registrationService.register(userWithNullPassword));
        assertEquals("Password cannot be null", exceptionWithNullPassword.getMessage());

        User userWithNullAge = new User("qwerty", "123456", null);

        RegistrationException exceptionWithNullAge = assertThrows(RegistrationException
                .class, () -> registrationService.register(userWithNullAge));
        assertEquals("Age cannot be null", exceptionWithNullAge.getMessage());
    }

    @Test
    void register_userValid_ok() {
        User validUser = new User("Romana", "password", 18);
        User actual = registrationService.register(validUser);
        assertNotNull(actual, "User should be successfully registered");
        assertEquals(actual, validUser);
    }
}
