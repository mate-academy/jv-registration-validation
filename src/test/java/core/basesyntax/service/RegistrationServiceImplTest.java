package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exeption.InvalidUserException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static User validUser;

    @AfterEach
    void clear() {
        Storage.people.clear();
    }

    @BeforeAll
    static void setUp() {
        registrationService = new RegistrationServiceImpl();
        validUser = new User("validlogin", "validpassword", 18);
    }

    @Test
    void register_ValidUser_Ok() throws InvalidUserException {
        User actual = registrationService.register(validUser);
        assertEquals(validUser, actual, "Users should be equals");
    }

    @Test
    public void register_UserAgeExactly18_Ok() throws InvalidUserException {
        User actual = new User("johnsmith", "password", 18);
        User registeredUser = registrationService.register(actual);
        assertEquals(actual, registeredUser, "Invalid age: " + actual.getAge());
    }

    @Test
    public void register_UserAgeOver18_Ok() throws InvalidUserException {
        User actual = new User("johnsmith", "password", 20);
        User registeredUser = registrationService.register(actual);
        assertEquals(actual, registeredUser, "Invalid age: " + actual.getAge());
    }

    @Test
    public void register_UserNullAge_NotOk() {
        User actual = new User("johnsmith", "password", null);
        assertThrows(InvalidUserException.class, () ->
                registrationService.register(actual),
                "Invalid age: " + actual.getAge());
    }

    @Test
    public void register_userNegativeAge_NotOk() {
        User actual = new User("johnsmith", "password", -20);
        assertThrows(InvalidUserException.class, () ->
                registrationService.register(actual),
                "Invalid password" + actual.getPassword());
    }

    @Test
    public void register_UserNullPassword_NotOk() {
        User actual = new User("johnsmith", null, 20);
        assertThrows(InvalidUserException.class, () ->
                registrationService.register(actual),
                "Invalid password: " + actual.getPassword());
    }

    @Test
    public void register_UserPasswordTooShort_NotOk() {
        User actual = new User("johnsmith", "abc", 20);
        assertThrows(InvalidUserException.class, () ->
                registrationService.register(actual),
                "Invalid password" + actual.getPassword());
    }

    @Test
    public void register_existingUser_NotOk() {
        Storage.people.add(validUser);
        User actual = new User("validlogin", "newpassword", 20);
        assertThrows(InvalidUserException.class, () ->
                registrationService.register(actual),
                "This user already in storage: " + actual.getLogin());
    }

    @Test
    public void register_userNullLogin_NotOk() {
        User actual = new User(null, "password", 20);
        assertThrows(InvalidUserException.class, () ->
                registrationService.register(actual),
                "Invalid user: " + actual.getLogin());
    }
}
