package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;

    @BeforeAll
    public static void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    public void register_validUser_ok() {
        User validUser = new User();
        validUser.setLogin("valid1@email.com");
        validUser.setPassword("123456");
        validUser.setAge(18);
        User actual = registrationService.register(validUser);
        assertEquals(validUser, actual, "Test failed! Registered user should"
                + " be returned after registration!");
    }

    @Test
    public void register_existingUser_notOk() {
        User validUser = new User();
        validUser.setLogin("valid2@email.com");
        validUser.setPassword("123456");
        validUser.setAge(18);
        registrationService.register(validUser);
        final User existingUser = new User();
        validUser.setLogin("valid2@email.com");
        validUser.setPassword("12345678");
        validUser.setAge(34);
        assertThrows(RegistrationException.class, () -> registrationService.register(existingUser),
                "Test failed! Exception should be thrown if user already exists!");
    }

    @Test
    public void register_nullLogin_notOk() {
        User newUser = new User();
        newUser.setLogin(null);
        newUser.setPassword("123456");
        newUser.setAge(18);

        assertThrows(RegistrationException.class, () -> registrationService.register(newUser),
                "Test failed! Exception should be thrown if login is null!");
    }

    @Test
    public void register_nullPassword_notOk() {
        User newUser = new User();
        newUser.setLogin("valid3@email.com");
        newUser.setPassword(null);
        newUser.setAge(18);
        assertThrows(RegistrationException.class, () -> registrationService.register(newUser),
                "Test failed! Exception should be thrown if password is null!");
    }

    @Test
    public void register_nullAge_notOk() {
        User newUser = new User();
        newUser.setLogin("valid3@email.com");
        newUser.setPassword("123456");
        newUser.setAge(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(newUser),
                "Test failed! Exception should be thrown if age is null!");
    }

    @Test
    public void register_invalidLogin_notOk() {
        User newUser = new User();
        newUser.setLogin("short");
        newUser.setPassword("123456");
        newUser.setAge(18);
        assertThrows(RegistrationException.class, () -> registrationService.register(newUser),
                "Test failed! Exception should be thrown if login is too short!");
    }

    @Test
    public void register_invalidPassword_notOk() {
        User newUser = new User();
        newUser.setLogin("valid4@email.com");
        newUser.setPassword("short");
        newUser.setAge(18);
        assertThrows(RegistrationException.class, () -> registrationService.register(newUser),
                "Test failed! Exception should be thrown if password is too short!");
    }

    @Test
    public void register_invalidAge_notOk() {
        User newUser = new User();
        newUser.setLogin("valid5@email.com");
        newUser.setPassword("123456");
        newUser.setAge(12);
        assertThrows(RegistrationException.class, () -> registrationService.register(newUser),
                "Test failed! Exception should be thrown if age is too low!");
    }
}
