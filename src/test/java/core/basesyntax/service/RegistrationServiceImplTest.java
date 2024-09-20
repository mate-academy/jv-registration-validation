package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static StorageDao storageDao;

    @BeforeAll
    static void setUp() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @AfterEach
    void clearList() {
        if (!Storage.people.isEmpty()) {
            Storage.people.clear();
        }
    }

    @Test
    void register_validUser_storageNotEmpty() {
        User testUser1 = new User("loginn", "password", 18);
        registrationService.register(testUser1);
        assertFalse(Storage.people.isEmpty());
    }

    @Test
    void register_multipleUsers_storageContainsAllUsers() {
        User testUser2 = new User("login2", "password", 18);
        User testUser3 = new User("login3", "password2",22);
        registrationService.register(testUser2);
        registrationService.register(testUser3);
        int actual = Storage.people.size();
        int expected = 2;
        assertEquals(expected, actual);
        assertTrue(Storage.people.contains(testUser2));
        assertTrue(Storage.people.contains(testUser3));
    }

    @Test
    void register_nullUser_throwsRegistrationException() {
        User testNullUser = null;
        assertThrows(RegistrationException.class, () -> registrationService.register(testNullUser));
    }

    @Test
    void register_ageJustBelowMinAge_notOk() {
        User testUser = new User("Loginn", "password", 17);
        assertThrows(RegistrationException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_loginTooShort_throwsRegistrationException() {
        User testUser = new User("login", "password", 18);
        assertThrows(RegistrationException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_passwordBelowMinLength_throwsRegistrationException() {
        User testUser = new User("loginOk", "pas-d", 18);
        assertThrows(RegistrationException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_duplicateLogin_throwsRegistrationException() {
        User testUser = new User("login1", "password", 18);
        Storage.people.add(testUser);
        assertThrows(RegistrationException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_validUser_userIsRetrievable() {
        User testUser = new User("login1", "password", 18);
        registrationService.register(testUser);
        String testUserLogin = testUser.getLogin();
        User expected = testUser;
        User actual = storageDao.get(testUserLogin);
        assertEquals(expected, actual);
    }
}
