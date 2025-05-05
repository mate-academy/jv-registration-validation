package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static User user;

    @BeforeAll
    public static void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    public void createUser() {
        user = new User();
        user.setPassword("123456");
        user.setAge(18);
    }

    @Test
    public void register_validUser_ok() {
        user.setLogin("valid1@email.com");
        User actual = registrationService.register(user);
        assertEquals(user, actual, "Test failed! Registered user should"
                + " be returned after registration!");
    }

    @Test
    public void register_existingLogin_notOk() {
        user.setLogin("valid2@email.com");
        registrationService.register(user);
        final User existingUser = new User();
        user.setLogin("valid2@email.com");
        user.setPassword("12345678");
        user.setAge(34);
        assertThrows(RegistrationException.class, () -> registrationService.register(existingUser),
                "Test failed! Exception should be thrown if user already exists!");
    }

    @Test
    public void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "Test failed! Exception should be thrown if login is null!");
    }

    @Test
    public void register_nullPassword_notOk() {
        user.setLogin("valid3@email.com");
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "Test failed! Exception should be thrown if password is null!");
    }

    @Test
    public void register_nullAge_notOk() {
        user.setLogin("valid4@email.com");
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "Test failed! Exception should be thrown if age is null!");
    }

    @Test
    public void register_invalidLogin_notOk() {
        user.setLogin("");
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "Test failed! Exception should be thrown if login is too short!");

        user.setLogin("short");
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "Test failed! Exception should be thrown if login is too short!");
    }

    @Test
    public void register_invalidPassword_notOk() {
        user.setLogin("valid5@email.com");
        user.setPassword("");
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "Test failed! Exception should be thrown if password is too short!");

        user.setLogin("valid6@email.com");
        user.setPassword("short");
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "Test failed! Exception should be thrown if password is too short!");
    }

    @Test
    public void register_invalidAge_notOk() {
        user.setLogin("valid7@email.com");
        user.setAge(12);
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "Test failed! Exception should be thrown if age is too low!");

        user.setLogin("valid8@email.com");
        user.setAge(17);
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "Test failed! Exception should be thrown if age is too low!");
    }
}
