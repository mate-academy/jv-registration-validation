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
    void userIsOlder_Ok() {
        User expected = new User(29892234L, "test_user22", "test_password1", 20);
        Storage.people.add(expected);
        assertThrows(RegistrationException.class, () -> reg.register(expected));
    }

    @Test
    void userTooYoung_NotOk() {
        User expected = new User(1276534L, "test_user", "test_password", 17);
        assertThrows(RegistrationException.class, () -> reg.register(expected));
    }

    @Test
    void userLoginLength_Ok() {
        User expected = new User(123411234L, "test_user0", "test_password2", 22);
        Storage.people.add(expected);
        assertThrows(RegistrationException.class, () -> reg.register(expected));
    }

    @Test
    void userShortLogin_NotOk() {
        User expected = new User(9183744L, "u", "test_password07", 25);
        assertThrows(RegistrationException.class, () -> reg.register(expected));
    }

    @Test
    void userPasswordLength_Ok() {
        User expected = new User(16544235L, "test_user7", "test_password3", 19);
        Storage.people.add(expected);
        assertThrows(RegistrationException.class, () -> reg.register(expected));
    }

    @Test
    void userShortPassword_NotOk() {
        User expected = new User(1234L, "test_user009", "pass", 23);
        assertThrows(RegistrationException.class, () -> reg.register(expected));
    }

    @Test
    void userWithExactLogin_Ok() {
        User expected = new User(1453233L, "test_user8", "test_password4", 35);
        Storage.people.add(expected);
        User actual = new User(987654L, "test_user8", "test_password5", 25);
        assertThrows(RegistrationException.class, () -> reg.register(actual));
    }

    @Test
    void userIdMaxValue_NotOk() {
        User expected = new User(Long.MAX_VALUE, "test_user1", "test_password6", 33);
        assertThrows(RegistrationException.class, () -> reg.register(expected));
    }

    @Test
    void userNullLogin_NotOk() {
        User expected = new User(4321L, null, "test_password7", 33);
        assertThrows(RegistrationException.class, () -> reg.register(expected));
    }

    @Test
    void userNullAge_NotOk() {
        User expected = new User(4321L, "test_user16", "test_password8", null);
        assertThrows(RegistrationException.class, () -> reg.register(expected));
    }

    @Test
    void userLoginMaxLength_NotOk() {
        String tooLongUsername = "toolongusernameabcdefghi1234567890";
        User expected = new User(121297L, tooLongUsername, "test_password9", 33);
        RegistrationException exception = assertThrows(RegistrationException.class,
                () -> reg.register(expected));
        assertEquals("User login is not within the allowed range", exception.getMessage());
    }

    @Test
    void userNullPassword_NotOk() {
        User expected = new User(12597L, "test_user12", null, 33);
        assertThrows(RegistrationException.class, () -> reg.register(expected));
    }

    @Test
    void userWeakPassword_NotOk() {
        User expected = new User(129227L, "test_user11", "123456", 33);
        assertThrows(RegistrationException.class, () -> reg.register(expected));
    }

    @Test
    void userZeroAge_NotOk() {
        User expected = new User(4321L, "test_user90", "test_password01", 0);
        assertThrows(RegistrationException.class, () -> reg.register(expected));
    }

    @Test
    void userAgeBelowZero_NotOk() {
        User expected = new User(4321L, "test_user09", "test_password02", -5);
        assertThrows(RegistrationException.class, () -> reg.register(expected));
    }

    @Test
    void userMinLoginAndPassLength_Ok() {
        String minLogin = "user12";
        String minPassword = "pass12";
        User expected = new User(1234L, minLogin, minPassword, 25);
        assertDoesNotThrow(() -> {
            User registeredUser = reg.register(expected);
            assertNotNull(registeredUser);
            assertEquals(expected, registeredUser);
        });
    }

    @Test
    void userRegistration_Successful() {
        User expected = new User(1234L, "test_user", "test_password", 25);

        assertDoesNotThrow(() -> {
            User registeredUser = reg.register(expected);
            assertNotNull(registeredUser);
            assertEquals(expected, registeredUser);
        });
    }
}
