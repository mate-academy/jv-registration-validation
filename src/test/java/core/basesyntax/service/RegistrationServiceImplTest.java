package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        Storage.people.clear();
    }

    @Test
    void nullValue_NotOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_ValidUser_Ok() {
        User user = new User(1L, "qwerty1", "qwerty1", 21);
        User addedUser = registrationService.register(user);
        assertEquals(user, addedUser);
        assertNotNull(addedUser.getId());
    }

    @Test
    void register_NullValues_notOk() {
        User user = new User(null, "qwerty1", "qwerty1", 21);
        User user1 = new User(1L, null, "qwerty1", 21);
        User user2 = new User(1L, "qwerty1", null, 21);

        RegistrationException invalidUserExceptionById = assertThrows(
                RegistrationException.class, () -> registrationService.register(user));
        assertEquals("User id, login, password or age can't be null",
                invalidUserExceptionById.getMessage());
        RegistrationException invalidUserExceptionByLogin = assertThrows(
                RegistrationException.class, () -> registrationService.register(user1));
        assertEquals("User id, login, password or age can't be null",
                invalidUserExceptionByLogin.getMessage());
        RegistrationException invalidUserExceptionByPassword = assertThrows(
                RegistrationException.class, () -> registrationService.register(user2));
        assertEquals("User id, login, password or age can't be null",
                invalidUserExceptionByPassword.getMessage());
        User user3 = new User(1L, "qwerty1", "qwerty1", null);
        RegistrationException invalidUserExceptionByAge = assertThrows(
                RegistrationException.class, () -> registrationService.register(user3));
        assertEquals("User id, login, password or age can't be null",
                invalidUserExceptionByAge.getMessage());
    }

    @Test
    void register_ExistingUser_NotOk() {
        User existingUser = new User(1L, "qwerty1", "qwerty1", 21);
        registrationService.register(existingUser);
        User user = new User(1L, "qwerty1", "qwerty1", 21);
        RegistrationException invalidUserException = assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
        assertEquals("Invalid data, user with this already exist",
                invalidUserException.getMessage());
    }

    @Test
    void register_UserWithExistingId_NotOk() {
        User existingUser = new User(1L, "qwerty1", "qwerty1", 21);
        registrationService.register(existingUser);
        User user = new User(1L, "qwerty", "qwerty1", 21);
        RegistrationException invalidUserException = assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
        assertEquals("Invalid data, user with this id already exist",
                invalidUserException.getMessage());
    }

    @Test
    void register_LoginCharactersLessThanSix_NotOk() {
        User user = new User(1L, "qwe", "qwerty1", 21);
        RegistrationException invalidUserException = assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
        assertEquals("Invalid data, login length must be greater than zero",
                invalidUserException.getMessage());
    }

    @Test
    void register_AgeLessThanEighteen_NotOk() {
        User user = new User(1L, "qwe", "qwerty1", 17);
        RegistrationException invalidUserException = assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
        assertEquals("Invalid data, age must be greater than 18",
                invalidUserException.getMessage());
    }

    @Test
    void register_PasswordCharactersLessThanSix_NotOk() {
        User user = new User(1L, "qwerty1", "qwe", 21);
        RegistrationException invalidUserException = assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
        assertEquals("Invalid data, password length must be greater than zero",
                invalidUserException.getMessage());
    }

}
