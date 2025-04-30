package core.basesyntax.service;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RegistrationServiceImplTest {

    private RegistrationServiceImpl registrationService;

    @BeforeEach
    public void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_nullUser_notOk() {
        registrationService.register(null);
    }

    @Test
    void register_nullLogin_notOk() {
        User user = new User();
        user.setLogin(null);
        user.setPassword("password");
        user.setAge(20);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword(null);
        user.setAge(20);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_emptyPassword_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("");
        user.setAge(20);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("validPassword");
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userUnderAge_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("validPassword");
        user.setAge(17);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userWithAge18_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("validPassword");
        user.setAge(18);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userGreater18_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("validPassword");
        user.setAge(19);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userNegativeAge_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("validPassword");
        user.setAge(-1);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordTooShort_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("abc");
        user.setAge(20);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordLength5_notOK() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("abcef");
        user.setAge(20);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordLengthMinimum_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("abcefg");
        user.setAge(20);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordLengthMoreMinimum_Ok() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("abcefgh");
        user.setAge(20);
        User registeredUser = registrationService.register(user);
        assertNotNull(registeredUser);
    }

    @Test
    void register_shortLogin_notOk() {
        User user = new User();
        user.setLogin("abc");
        user.setPassword("password");
        user.setAge(20);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_duplicateLogin_notOk() {
        User user1 = new User();
        user1.setLogin("uniqueLogin");
        user1.setPassword("password");
        user1.setAge(20);
        registrationService.register(user1);

        User user2 = new User();
        user2.setLogin("uniqueLogin");
        user2.setPassword("password123");
        user2.setAge(21);
        registrationService.register(user2);

        assertThrows(RegistrationException.class, () -> registrationService.register(user2));
    }

    @Test
    void register_validUser_Ok() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("validPassport");
        user.setAge(20);

        User registeredUser = registrationService.register(user);
        assertNotNull(registeredUser);
    }
}
