package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exeption.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static User userValid;
    private static final String MESSAGE = "RegistrationException should be thrown in this case.";

    @BeforeAll
    public static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        Storage.people.clear();
        userValid = new User("goodlog", "password", 18);
    }

    @Test
    public void register_validUser_Ok() {
        User registeredUser = registrationService.register(userValid);
        assertEquals(userValid, registeredUser, "Users should be equals.");
    }

    @Test
    public void register_existingUser_NotOk() {
        Storage.people.add(userValid);
        User duplicateUser = new User("goodlog", "newpassword", 25);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(duplicateUser), MESSAGE);
    }

    @Test
    public void register_userLoginTooShort_NotOk() {
        User user = new User("abc", "password", 20);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user), MESSAGE);
    }

    @Test
    public void register_userPasswordTooShort_NotOk() {
        User user = new User("johnsmith", "abc", 20);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user), MESSAGE);
    }

    @Test
    public void register_userInvalidAge_NotOk() {
        User user = new User("johnsmith", "password", 16);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user), MESSAGE);
    }

    @Test
    public void register_userNullLogin_NotOk() {
        User user = new User(null, "password", 20);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user), MESSAGE);
    }

    @Test
    public void register_userNullPassword_NotOk() {
        User userWithNullPassword = new User("johnsmith", null, 20);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(userWithNullPassword), MESSAGE);
    }

    @Test
    public void register_userNullAge_NotOk() {
        User user = new User("johnsmith", "password", null);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user), MESSAGE);
    }

    @Test
    public void register_userUnder18_NotOk() {
        User user = new User("johnsmith", "password", 17);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user), MESSAGE);
    }

    @Test
    public void register_userExactly18_Ok() {
        User user = new User("johnsmith", "password", 18);
        User registeredUser = registrationService.register(user);
        assertEquals(user, registeredUser, "Incorrect age.");
    }

    @Test
    public void register_userOver18_Ok() {
        User user = new User("johnsmith", "password", 19);
        User registeredUser = registrationService.register(user);
        assertEquals(user, registeredUser, "Incorrect age.");
    }

    @Test
    public void register_userNegativeAge_NotOk() {
        User user = new User("johnsmith", "password", -20);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user), MESSAGE);
    }

    @AfterEach
    void clear() {
        Storage.people.clear();
    }
}
