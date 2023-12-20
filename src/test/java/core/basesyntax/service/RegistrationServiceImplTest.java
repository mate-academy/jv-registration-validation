package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static List<User> users;
    private RegistrationService registrationService;

    @BeforeAll
    static void beforeAll() {
        users = new ArrayList<>();
        User userNullId = new User(null, "qwerty1", "qwerty1", 21);
        User userNullLogin = new User(1L, null, "qwerty1", 21);
        User userNullPassword = new User(1L, "qwerty1", null, 21);
        User userNullAge = new User(1L, "qwerty1", "qwerty1", null);
        users.add(userNullId);
        users.add(userNullLogin);
        users.add(userNullPassword);
        users.add(userNullAge);

    }

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        Storage.people.clear();
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_ValidUser_Ok() {
        User userOk = new User(1L, "qwerty1", "qwerty1", 21);
        User addedUser = registrationService.register(userOk);
        assertEquals(userOk, addedUser);
        assertNotNull(addedUser.getId());
    }

    @Test
    void register_NullValues_notOk() {
        assertEquals("User id can't be null",
                assertThrows(RegistrationException.class,
                        () -> registrationService.register(users.get(0))).getMessage());
        assertEquals("User login can't be null",
                assertThrows(RegistrationException.class,
                        () -> registrationService.register(users.get(1))).getMessage());
        assertEquals("User password can't be null",
                assertThrows(RegistrationException.class,
                        () -> registrationService.register(users.get(2))).getMessage());
        assertEquals("User age can't be null",
                assertThrows(RegistrationException.class,
                        () -> registrationService.register(users.get(3))).getMessage());

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
