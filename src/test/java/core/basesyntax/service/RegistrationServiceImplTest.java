package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static StorageDao storageDao;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @Test
    void correctDataForRegistration_Ok() throws RegistrationUserException {
        User user = new User("Good_boy28", "qwerty101", 29);
        User user1 = new User("user123456", "user12", 99);
        User user2 = new User("11user", "password", 18);
        assertTrue(Storage.people.contains(registrationService.register(user)));
        assertTrue(Storage.people.contains(registrationService.register(user1)));
        assertTrue(Storage.people.contains(registrationService.register(user2)));
    }

    @Test
    void successfulRegistration_Ok() throws RegistrationUserException {
        User user = new User("Good_boy29", "qwerty101", 29);
        User registeredUser = registrationService.register(user);
        assertNotNull(registeredUser.getId());
        assertEquals(user.getLogin(), registeredUser.getLogin());
        assertEquals(user.getPassword(), registeredUser.getPassword());
        assertEquals(user.getAge(), registeredUser.getAge());
    }

    @Test
    void successfulAddedToList_Ok() {
        User user = new User("Good_boy30", "qwerty101", 29);
        storageDao.add(user);
        assertTrue(Storage.people.contains(user));
    }

    @Test
    void checkIfUserIsNull_NotOk() {
        User user = null;
        assertThrows(RegistrationUserException.class, () -> registrationService.register(user));
    }

    @Test
    void checkNullValues_NotOk() {
        User user = new User(null, "qwerty101", 29);
        User user1 = new User("user123456", null, 65);
        User user2 = new User("11user", "password", 0);
        assertThrows(RegistrationUserException.class, () -> registrationService.register(user));
        assertThrows(RegistrationUserException.class, () -> registrationService.register(user1));
        assertThrows(RegistrationUserException.class, () -> registrationService.register(user2));
    }

    @Test
    void checkUsersWithSameLogin_NotOk() throws RegistrationUserException {
        User user = new User("Good_boy22", "qwerty101", 26);
        registrationService.register(user);
        assertThrows(RegistrationUserException.class, () -> registrationService.register(user));
    }

    @Test
    void checkLoginForMinCountSymbols_NotOk() {
        User user = new User("Hey12", "qwerty101", 26);
        assertThrows(RegistrationUserException.class, () -> registrationService.register(user));
    }

    @Test
    void checkPasswordForMinCountSymbols_NotOk() {
        User user = new User("Good_boy22", "qwert", 26);
        assertThrows(RegistrationUserException.class, () -> registrationService.register(user));
    }

    @Test
    void checkAgeLessThanMin_NotOk() {
        User user = new User("Good_boy22", "qwerty101", 9);
        assertThrows(RegistrationUserException.class, () -> registrationService.register(user));
    }

    @Test
    void checkForIncorrectAge_NotOk() {
        User user = new User("Good_boy22", "qwerty101", -5);
        User user1 = new User("Good_boy22", "qwerty101", 105);
        assertThrows(RegistrationUserException.class, () -> registrationService.register(user));
        assertThrows(RegistrationUserException.class, () -> registrationService.register(user1));
    }
}
