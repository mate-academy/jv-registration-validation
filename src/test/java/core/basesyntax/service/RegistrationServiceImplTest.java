package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private static User userWithWrongLogin;
    private static User userWithWrongPassword;
    private static User userWithWrongAge;
    private static User sameUser1;
    private static User sameUser2;

    @BeforeAll
    public static void setUp() {
        registrationService = new RegistrationServiceImpl();
        userWithWrongLogin = new User("Misha", "password", 25);
        userWithWrongPassword = new User("Viktor", "pass", 37);
        userWithWrongAge = new User("Marianna", "password", 12);
        sameUser1 = new User("Mikhaylo", "password", 20);
        sameUser2 = new User("Mikhaylo", "password", 20);
    }

    @Test
    void register_sameLogin_notOk() {
        registrationService.register(sameUser1);
        RegistrationException exceptionWithSimilarity = assertThrows(RegistrationException
                .class, () -> registrationService.register(sameUser2));
        assertEquals("Users with same login already exist", exceptionWithSimilarity.getMessage());
    }

    @Test
    void register_loginLength_notOk() {
        RegistrationException exceptionWithWrongPassword = assertThrows(RegistrationException
                .class, () -> registrationService.register(userWithWrongLogin));
        assertEquals("User's login must be at least 6 characters",
                exceptionWithWrongPassword.getMessage());
    }

    @Test
    void register_passwordLength_notOk() {
        RegistrationException exceptionWithWrongPassword = assertThrows(RegistrationException
                .class, () -> registrationService.register(userWithWrongPassword));
        assertEquals("User's password must be at least 6 characters",
                exceptionWithWrongPassword.getMessage());
    }

    @Test
    void register_age_notOk() {
        RegistrationException exceptionWithWrongAge = assertThrows(RegistrationException
                .class, () -> registrationService.register(userWithWrongAge));
        assertEquals("User's age must be at least 18 years", exceptionWithWrongAge.getMessage());
    }

    @Test
    void register_userNullValues_notOK() {
        User userWithNullLogin = new User(null, "123456", 20);

        RegistrationException exceptionWithNullLogin = assertThrows(RegistrationException
                .class, () -> registrationService.register(userWithNullLogin));
        assertEquals("Login cannot be null or empty", exceptionWithNullLogin.getMessage());

        User userWithNullPassword = new User("qwerty", null, 20);

        RegistrationException exceptionWithNullPassword = assertThrows(RegistrationException
                .class, () -> registrationService.register(userWithNullPassword));
        assertEquals("Password cannot be null or empty", exceptionWithNullPassword.getMessage());

        User userWithNullAge = new User("qwerty", "123456", null);

        RegistrationException exceptionWithNullAge = assertThrows(RegistrationException
                .class, () -> registrationService.register(userWithNullAge));
        assertEquals("Age cannot be null or empty", exceptionWithNullAge.getMessage());

    }
}
