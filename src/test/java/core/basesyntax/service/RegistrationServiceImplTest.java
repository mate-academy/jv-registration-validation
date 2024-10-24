package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationServiceImpl registrationService;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void shouldThrowExceptionWhenUserAlreadyExists() {
        User existingUser = new User();
        existingUser.setLogin("existingLogin");
        existingUser.setPassword("password");
        existingUser.setAge(25);
        Storage.people.add(existingUser);

        User newUser = new User();
        newUser.setLogin("existingLogin");
        newUser.setPassword("password123");
        newUser.setAge(30);

        assertThrows(RegistrationException.class, () -> registrationService.register(newUser));
    }

    @Test
    void shouldThrowExceptionWhenUserIsNull() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(null), "User can't be null");
    }

    @Test
    void shouldThrowExceptionWhenLoginIsNull() {
        User newUser = new User();
        newUser.setLogin(null);
        newUser.setPassword("password123");
        newUser.setAge(25);

        assertThrows(RegistrationException.class,
                () -> registrationService.register(newUser), "Login can't be null");
    }

    @Test
    void shouldThrowExceptionWhenLoginIsTooShort() {
        User newUser = new User();
        newUser.setLogin("abc");
        newUser.setPassword("password123");
        newUser.setAge(25);

        assertThrows(RegistrationException.class,
                () -> registrationService.register(newUser), "Login can't be shorter than 6");
    }

    @Test
    void shouldThrowExceptionWhenPasswordIsNull() {
        User newUser = new User();
        newUser.setLogin("validLogin");
        newUser.setPassword(null);
        newUser.setAge(25);

        assertThrows(RegistrationException.class,
                () -> registrationService.register(newUser), "Password can't be null");
    }

    @Test
    void shouldThrowExceptionWhenPasswordIsTooShort() {
        User newUser = new User();
        newUser.setLogin("validLogin");
        newUser.setPassword("short");
        newUser.setAge(25);

        assertThrows(RegistrationException.class,
                () -> registrationService.register(newUser), "Password can't be shorter than 6");
    }

    @Test
    void shouldThrowExceptionWhenAgeIsTooLow() {
        User user = new User();
        user.setLogin("dana123");
        user.setPassword("valanesa");
        user.setAge(16);

        assertThrows(RegistrationException.class,
                () -> registrationService.register(user), "Not valid age: " + user.getAge()
                        + ". Min allowed age is 18");
    }

    @Test
    void shouldRegisterValidUserSuccessfully() {
        User newUser = new User();
        newUser.setLogin("validLogin");
        newUser.setPassword("validPassword");
        newUser.setAge(25);

        User registeredUser = registrationService.register(newUser);

        assertEquals(newUser, registeredUser);
        assertTrue(Storage.people.contains(newUser));
    }
}
