package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private User validUser;

    @BeforeAll
    public static void setUpClass() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    public void setUp() {
        String login = "john_doe_" + System.currentTimeMillis();
        String password = "strong_password";
        int age = 25;
        validUser = new User(login, password, age);
    }

    @AfterEach
    public void tearDown() {
        Storage.people.clear();
    }

    @Test
    void registerValidUser_ok() {
        User registeredUser = registrationService.register(validUser);
        assertNotNull(registeredUser);
    }

    @Test
    void registerDuplicateUser_notOk() {
        String login = "testuser";
        String password = "123456";
        int age = 25;
        User user = new User(login, password, age);
        registrationService.register(user);
        Assertions.assertThrows(RegistrationException.class, () -> registrationService
                .register(new User(login, "different_password", 30)));
    }

    @Test
    public void registerNullLogin_notOk() {
        validUser.setLogin(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(validUser));
    }

    @Test
    public void registerShortLogin_notOk() {
        validUser.setLogin("abc");
        assertThrows(RegistrationException.class, () -> registrationService.register(validUser));
    }

    @Test
    public void registerNullPassword_notOk() {
        validUser.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(validUser));
    }

    @Test
    void registerShortPassword_notOk() {
        validUser.setPassword("a");
        assertThrows(RegistrationException.class, () -> registrationService.register(validUser));
        validUser.setPassword("ab");
        assertThrows(RegistrationException.class, () -> registrationService.register(validUser));
        validUser.setPassword("abc");
        assertThrows(RegistrationException.class, () -> registrationService.register(validUser));
        validUser.setPassword("abcd");
        assertThrows(RegistrationException.class, () -> registrationService.register(validUser));
        validUser.setPassword("abcdf");
        assertThrows(RegistrationException.class, () -> registrationService.register(validUser));
    }

    @Test
    public void registerMinLengthPassword_ok() {
        validUser.setPassword("abcdef");
        assertDoesNotThrow(() -> registrationService.register(validUser));
    }

    @Test
    public void registerMaxLengthPassword_ok() {
        validUser.setPassword("abcdefgh");
        assertDoesNotThrow(() -> registrationService.register(validUser));
    }

    @Test
    public void registerEmptyPasswordNot_ok() {
        validUser.setPassword("");
        assertThrows(RegistrationException.class, () -> registrationService.register(validUser));
    }

    @Test
    public void registerBlankPasswordNot_ok() {
        validUser.setPassword("   ");
        assertThrows(RegistrationException.class, () -> registrationService.register(validUser));
    }

    @Test
    public void registerNullAge_notOk() {
        validUser.setAge(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(validUser));
    }

    @Test
    void registerUnderAge_notOk() {
        String login = "testuser";
        int age = 15;
        Assertions.assertThrows(RegistrationException.class, () -> registrationService
                .register(new User(login, "123456", age)));
    }
}
