package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private RegistrationService registrationService;
    private User validUser;

    @BeforeEach
    public void setUp() {
        registrationService = new RegistrationServiceImpl();
        String login = "john_doe_" + System.currentTimeMillis();
        String password = "strong_password";
        int age = 25;
        validUser = new User(login, password, age);
    }

    @Test
    public void register_validUser_ok() {
        User registeredUser = assertDoesNotThrow(() -> registrationService.register(validUser));
    }

    @Test
    public void register_nullLogin_notOk() {
        validUser.setLogin(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(validUser));
    }

    @Test
    public void register_shortLogin_notOk() {
        validUser.setLogin("abc");
        assertThrows(RegistrationException.class, () -> registrationService.register(validUser));
    }

    @Test
    public void register_nullPassword_notOk() {
        validUser.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(validUser));
    }

    @Test
    public void register_shortPassword_notOk() {
        validUser.setPassword("abc");
        assertThrows(RegistrationException.class, () -> registrationService.register(validUser));
    }

    @Test
    public void register_shortPassword5_notOk() {
        validUser.setPassword("abcdf");
        assertThrows(RegistrationException.class, () -> registrationService.register(validUser));
    }

    @Test
    public void register_minLengthPassword_ok() {
        validUser.setPassword("abcdef");
        assertDoesNotThrow(() -> registrationService.register(validUser));
    }

    @Test
    public void register_maxLengthPassword_ok() {
        validUser.setPassword("abcdefgh");
        assertDoesNotThrow(() -> registrationService.register(validUser));
    }

    @Test
    public void register_emptyPassword_notOk() {
        validUser.setPassword("");
        assertThrows(RegistrationException.class, () -> registrationService.register(validUser));
    }

    @Test
    public void register_blankPassword_notOk() {
        validUser.setPassword("   ");
        assertThrows(RegistrationException.class, () -> registrationService.register(validUser));
    }

    @Test
    public void register_nullAge_notOk() {
        validUser.setAge(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(validUser));
    }

    @Test
    public void register_underAge_notOk() {
        validUser.setAge(15);
        assertThrows(RegistrationException.class, () -> registrationService.register(validUser));
    }
}
