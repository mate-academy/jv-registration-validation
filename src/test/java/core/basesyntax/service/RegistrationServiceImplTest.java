package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

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
    void register_validUser_ok() {
        String login = "testuser_" + System.currentTimeMillis();
        String password = "123456";
        int age = 25;
        User user = new User(login, password, age);
        User registeredUser = registrationService.register(user);
        assertNotNull(registeredUser);
        assertEquals(login, registeredUser.getLogin());
        assertEquals(password, registeredUser.getPassword());
        assertEquals(age, registeredUser.getAge());
    }

    @Test
    void register_duplicateUser_notOk() {
        String login = "testuser";
        String password = "123456";
        int age = 25;
        User user = new User(login, password, age);
        registrationService.register(user);
        Assertions.assertThrows(RegistrationException.class, () -> registrationService
                .register(new User(login, "different_password", 30)));
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
    void register_maxLengthPasswordPlusOne_notOk() {
        String login = "testuser";
        String password = "1234567";
        int age = 25;
        Assertions.assertThrows(RegistrationException.class, () -> registrationService
                .register(new User(login, password, age)));
    }

    @Test
    void register_underAge_notOk() {
        String login = "testuser";
        String password = "123456";
        int age = 15;
        Assertions.assertThrows(RegistrationException.class, () -> registrationService
                .register(new User(login, password, age)));
    }
}
