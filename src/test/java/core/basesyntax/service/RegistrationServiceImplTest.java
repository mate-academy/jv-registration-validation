package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.UserValidationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl service;

    @BeforeAll
    static void beforeAll() {
        service = new RegistrationServiceImpl();
    }

    @Test
    void register_firstValidUser_Ok() {
        User expected = new User("Markus", "121212", 18);
        User actual = service.register(expected);
        assertEquals(expected, actual);
        assertTrue(Storage.people.contains(expected));
    }

    @Test
    void register_twoUniqueLogins_Ok() {
        User firstUser = new User("Markus", "121212", 18);
        service.register(firstUser);
        User secondUser = new User("Tormund", "121212", 18);
        service.register(secondUser);
        assertTrue(Storage.people.contains(secondUser));
    }

    @Test
    void register_identicalLogins_NotOk() {
        User user = new User("Markus", "121212", 18);
        service.register(user);
        User repeatedLoginUser = new User("Markus", "323232", 30);
        assertThrows(UserValidationException.class, () -> {
            service.register(repeatedLoginUser);
        });
        assertFalse(Storage.people.contains(repeatedLoginUser));
    }

    @Test
    void register_nullLogin_NotOk() {
        User userWithNullLogin = new User(null, "232323", 20);
        assertThrows(UserValidationException.class, () -> {
            service.register(userWithNullLogin);
        });
        assertFalse(Storage.people.contains(userWithNullLogin));
    }

    @Test
    void register_nullPassword_NotOk() {
        User userWithNullPassword = new User("Markus", null, 20);
        assertThrows(UserValidationException.class, () -> {
            service.register(userWithNullPassword);
        });
        assertFalse(Storage.people.contains(userWithNullPassword));
    }

    @Test
    void register_nullAge_NotOk() {
        User userWithNullAge = new User("Markus", "3x3x3x", null);
        assertThrows(UserValidationException.class, () -> {
            service.register(userWithNullAge);
        });
        assertFalse(Storage.people.contains(userWithNullAge));
    }

    @Test
    void register_nullUser_NotOk() {
        assertThrows(UserValidationException.class, () -> {
            service.register(null);
        });
    }

    @Test
    void register_tooShortLogin_NotOk() {
        User userWith5LengthLogin = new User("Johny", "3x3x3x", 20);
        assertThrows(UserValidationException.class, () -> {
            service.register(userWith5LengthLogin);
        });
        assertFalse(Storage.people.contains(userWith5LengthLogin));
    }

    @Test
    void register_tooShortPassword_NotOk() {
        User userWith5LengthPassword = new User("Markus", "5x5x", 20);
        assertThrows(UserValidationException.class, () -> {
            service.register(userWith5LengthPassword);
        });
        assertFalse(Storage.people.contains(userWith5LengthPassword));
    }

    @Test
    void register_tooYoungAge_NotOk() {
        User userOfAge17 = new User("Markus", "5x5x5xx", 17);
        assertThrows(UserValidationException.class, () -> {
            service.register(userOfAge17);
        });
        assertFalse(Storage.people.contains(userOfAge17));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
