package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private RegistrationService registrationService;
    private User validUser;

    @BeforeEach
    public void setUp() {
        registrationService = new RegistrationServiceImpl();
        validUser = new User();
        validUser.setLogin("validLogin");
        validUser.setPassword("validPassword");
        validUser.setAge(25);
        Storage.people.clear(); // Clear storage before each test
    }

    @Test
    public void register_validUser_ok() {
        User registeredUser = registrationService.register(validUser);
        assertNotNull(registeredUser.getId(), "User ID should be set after registration");
        assertEquals("validLogin", registeredUser.getLogin());
        assertEquals("validPassword", registeredUser.getPassword());
        assertEquals(25, registeredUser.getAge());
    }

    @Test
    public void register_nullUser_notOk() {
        assertThrows(RegistrationException.class, ()
                -> registrationService.register(null), "User cannot be null");
    }

    @Test
    public void register_shortLogin_notOk() {
        validUser.setLogin("abc");
        assertThrows(RegistrationException.class, () -> registrationService.register(validUser),
                "Login must be at least 6 characters long");
    }

    @Test
    public void register_existingLogin_notOk() {
        registrationService.register(validUser);
        User anotherUser = new User();
        anotherUser.setLogin("validLogin");
        anotherUser.setPassword("anotherPassword");
        anotherUser.setAge(30);
        assertThrows(RegistrationException.class, () -> registrationService.register(anotherUser),
                "Duplicate login should throw RegistrationException");
    }

    @Test
    public void register_shortPassword_notOk() {
        validUser.setPassword("123");
        assertThrows(RegistrationException.class, () -> registrationService.register(validUser),
                "Password must be at least 6 characters long");
    }

    @Test
    public void register_underageUser_notOk() {
        validUser.setAge(17);
        assertThrows(RegistrationException.class, () -> registrationService.register(validUser),
                "User must be at least 18 years old");
    }

    @Test
    public void register_blankLogin_notOk() {
        validUser.setLogin("      ");
        assertThrows(RegistrationException.class, () -> registrationService.register(validUser),
                "Blank login should throw RegistrationException");
    }

    @Test
    public void register_blankPassword_notOk() {
        validUser.setPassword("      ");
        assertThrows(RegistrationException.class, ()
                -> registrationService.register(validUser), "Blank password"
                + " should throw RegistrationException");
    }

    @Test
    public void register_nullPassword_notOk() {
        validUser.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(validUser),
                "Null password should throw RegistrationException");
    }

    @Test
    public void register_nullAge_notOk() {
        validUser.setAge(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(validUser),
                "Null age should throw RegistrationException");
    }

    @Test
    public void register_userJustOfAge_ok() {
        validUser.setAge(18);
        User registeredUser = registrationService.register(validUser);
        assertNotNull(registeredUser.getId(), "User ID should be set for a valid registration");
        assertEquals(18, registeredUser.getAge(), "Age should be registered as set");
    }
}
