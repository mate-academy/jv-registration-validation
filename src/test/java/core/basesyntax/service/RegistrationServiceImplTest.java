package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService reg;

    @BeforeEach
    void setUp() {
        reg = new RegistrationServiceImpl();
    }

    @Test
    void register_userIsOlder_Ok() {
        User expected = new User("test_user22", "test_password1", 20);
        Storage.people.add(expected);
        assertThrows(RegistrationException.class, () -> reg.register(expected));
    }

    @Test
    void register_userTooYoung_notOk() {
        User expected = new User("test_user", "test_password", 17);
        assertThrows(RegistrationException.class, () -> reg.register(expected));
    }

    @Test
    void register_userLoginLength_ok() {
        User expected = new User("test_user0", "test_password2", 22);
        Storage.people.add(expected);
        assertThrows(RegistrationException.class, () -> reg.register(expected));

        String minUsername = "user12";
        User minUsernameUser = new User(minUsername, "test_password15", 22);
        assertDoesNotThrow(() -> {
            User registeredUser = reg.register(minUsernameUser);
            assertNotNull(registeredUser);
            assertEquals(minUsernameUser, registeredUser);
        });

        String validUsername = "user123";
        User validUsernameUser = new User(validUsername, "test_password16", 25);
        assertDoesNotThrow(() -> {
            User registeredUser = reg.register(validUsernameUser);
            assertNotNull(registeredUser);
            assertEquals(validUsernameUser, registeredUser);
        });

        String maxUsername = "maxusername123456789zxcvbnmasd";
        User maxUsernameUser = new User(maxUsername, "test_password17", 28);
        assertDoesNotThrow(() -> {
            User registeredUser = reg.register(maxUsernameUser);
            assertNotNull(registeredUser);
            assertEquals(maxUsernameUser, registeredUser);
        });

        String specialCharsUsername = "user@name_456";
        User specialCharsUsernameUser = new User(specialCharsUsername, "test_password18", 23);
        assertDoesNotThrow(() -> {
            User registeredUser = reg.register(specialCharsUsernameUser);
            assertNotNull(registeredUser);
            assertEquals(specialCharsUsernameUser, registeredUser);
        });
    }

    @Test
    void register_userShortLogin_notOk() {
        User expected = new User("u", "test_password07", 25);
        assertThrows(RegistrationException.class, () -> reg.register(expected));
    }

    @Test
    void register_userPasswordLength_ok() {
        User expected = new User("test_user7", "test_password3", 19);
        Storage.people.add(expected);
        assertThrows(RegistrationException.class, () -> reg.register(expected));
    }

    @Test
    void register_userShortPassword_notOk() {
        User expected = new User("test_user009", "pass", 23);
        assertThrows(RegistrationException.class, () -> reg.register(expected));
    }

    @Test
    void register_userWithExactLogin_ok() {
        User expected = new User("test_user8", "test_password4", 35);
        Storage.people.add(expected);
        User actual = new User("test_user8", "test_password5", 25);
        assertThrows(RegistrationException.class, () -> reg.register(actual));
    }

    @Test
    void register_userNullLogin_notOk() {
        User expected = new User(null, "test_password7", 33);
        assertThrows(RegistrationException.class, () -> reg.register(expected));
    }

    @Test
    void register_userNullAge_notOk() {
        User expected = new User("test_user16", "test_password8", null);
        assertThrows(RegistrationException.class, () -> reg.register(expected));
    }

    @Test
    void register_userLoginMaxLength_notOk() {
        String tooLongUsername = "toolongusernameabcdefghi1234567890";
        User expected = new User(tooLongUsername, "test_password9", 33);
        RegistrationException exception = assertThrows(RegistrationException.class,
                () -> reg.register(expected));
        assertEquals("User login is not within the allowed range", exception.getMessage());
    }

    @Test
    void register_userNullPassword_notOk() {
        User expected = new User("test_user12", null, 33);
        assertThrows(RegistrationException.class, () -> reg.register(expected));
    }

    @Test
    void register_userZeroAge_notOk() {
        User expected = new User("test_user90", "test_password01", 0);
        assertThrows(RegistrationException.class, () -> reg.register(expected));
    }

    @Test
    void register_userAgeBelowZero_notOk() {
        User expected = new User("test_user09", "test_password02", -5);
        assertThrows(RegistrationException.class, () -> reg.register(expected));
    }

    @Test
    void register_userMinLoginAndPassLength_ok() {
        String minLogin = "12user";
        String minPassword = "pass12";
        User expected = new User(minLogin, minPassword, 25);
        assertDoesNotThrow(() -> {
            User registeredUser = reg.register(expected);
            assertNotNull(registeredUser);
            assertEquals(expected, registeredUser);
        });
    }

    @Test
    void register_userRegistration_successful() {
        User expected = new User("test_user", "test_password", 25);

        assertDoesNotThrow(() -> {
            User registeredUser = reg.register(expected);
            assertNotNull(registeredUser);
            assertEquals(expected, registeredUser);
        });
    }
}
