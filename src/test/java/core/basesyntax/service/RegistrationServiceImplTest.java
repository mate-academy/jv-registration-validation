package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;

    @BeforeAll
    static void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @AfterEach
    void clearList() {
        if (!Storage.people.isEmpty()) {
            Storage.people.clear();
        }
    }

    @Test
    void register_validUser_storageNotEmpty() {
        User user = new User("loginn", "password", 18);
        User actual = registrationService.register(user);
        assertEquals(actual, user);
    }

    @Test
    void register_nullUser_throwsRegistrationException() {
        User testNullUser = null;
        assertThrows(RegistrationException.class, () -> registrationService.register(testNullUser));
    }

    @Test
    void register_ageJustBelowMinAge_notOk() {
        User user = new User("Loginn", "password", 17);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginTooShort_throwsRegistrationException() {
        User user = new User("login", "password", 18);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordBelowMinLength_throwsRegistrationException() {
        User user = new User("loginOk", "pas-d", 18);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_duplicateLogin_throwsRegistrationException() {
        User user = new User("login1", "password", 18);
        Storage.people.add(user);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_negativeAge_throwsRegistrationException() {
        User user = new User("login1", "password", -34);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_redundantTestMethod_multipleUsers_storageContainsAllUsers() {
        User user2 = new User("login2", "password", 18);
        User user3 = new User("login3", "password2",22);
        registrationService.register(user2);
        registrationService.register(user3);
        int actual = Storage.people.size();
        int expected = 2;
        assertEquals(expected, actual);
        assertTrue(Storage.people.contains(user2));
        assertTrue(Storage.people.contains(user3));
    }
}
