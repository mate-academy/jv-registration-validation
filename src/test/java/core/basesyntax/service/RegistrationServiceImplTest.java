package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exeption.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static User validUser;
    private static final String EXCEPTION_MESSAGE =
            "RegistrationException should be thrown in this case.";

    @BeforeAll
    public static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        validUser = new User("goodlog", "password", 18);
    }

    @BeforeEach
    public void preparing() {
        Storage.people.clear();
    }

    @Test
    public void register_validUser_ok() {
        User registeredUser = registrationService.register(validUser);
        assertEquals(validUser, registeredUser, "Users should be equal.");
    }

    @Test
    public void register_existingUser_notOk() {
        Storage.people.add(validUser);
        User duplicateUser = new User("goodlog", "newpassword", 25);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(duplicateUser), EXCEPTION_MESSAGE);
    }

    @Test
    public void register_userLoginTooShort_notOk() {
        User user = new User("abc", "password", 20);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user), EXCEPTION_MESSAGE);
    }

    @Test
    public void register_userNullLogin_notOk() {
        User user = new User(null, "password", 20);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user), EXCEPTION_MESSAGE);
    }

    @Test
    public void register_userPasswordTooShort_notOk() {
        User user = new User("johnsmith", "abc", 20);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user), EXCEPTION_MESSAGE);
    }

    @Test
    public void register_userNullPassword_notOk() {
        User userWithNullPassword = new User("johnsmith", null, 20);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(userWithNullPassword), EXCEPTION_MESSAGE);
    }

    @Test
    public void register_userUnder18_notOk() {
        User user = new User("johnsmith", "password", 17);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user), EXCEPTION_MESSAGE);
    }

    @Test
    public void register_userValidData_Ok() {
        User user = new User("johnsmith", "password", 19);
        User registeredUser = registrationService.register(user);
        assertEquals(user, registeredUser, "Users should be equals to each other");
    }

    @Test
    public void register_userNegativeAge_notOk() {
        User user = new User("johnsmith", "password", -20);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user), EXCEPTION_MESSAGE);
    }

    @Test
    public void register_userNullAge_notOk() {
        User user = new User("johnsmith", "password", null);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user), EXCEPTION_MESSAGE);
    }
}
