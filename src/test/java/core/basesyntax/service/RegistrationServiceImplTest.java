package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationServiceImpl testRegistrationService = new RegistrationServiceImpl();
    private StorageDao storageDao = new StorageDaoImpl();

    @BeforeEach
    void setUp() {
        testRegistrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
        Storage.people.clear();
    }

    @Test
    void register_nullLogin_notOk() {
        User testUser = new User(null, "TestPassword", 18);
        assertThrows(InvalidDataException.class, () -> testRegistrationService.register(testUser));
    }

    @Test
    void register_nullPassword_notOk() {
        User testUser = new User("TestUser", null, 18);
        assertThrows(InvalidDataException.class, () -> testRegistrationService.register(testUser));
    }

    @Test
    void register_nullAge_notOk() {
        User testUser = new User("TestUser", "TestPassword", null);
        assertThrows(InvalidDataException.class, () -> testRegistrationService.register(testUser));
    }

    @Test
    void register_uniqueLogin_Ok() {
        User testUser = new User("TestUser", "TestPassword", 18);
        assertEquals(testRegistrationService.register(testUser), testUser);
        assertEquals(storageDao.get(testUser.getLogin()), testUser);
    }

    @Test
    void register_duplicateLogin_notOk() {
        User testUser = new User("TestUser", "TestPassword", 18);
        Storage.people.add(testUser);
        User testUser2 = new User("TestUser", "TestPassword", 18);
        assertThrows(InvalidDataException.class, () -> testRegistrationService.register(testUser2));
    }

    @Test
    void register_loginIsLongEnough_Ok() {
        User testUser = new User("TestUser", "TestPassword", 18);
        assertEquals(testRegistrationService.register(testUser), testUser);
        assertEquals(storageDao.get(testUser.getLogin()), testUser);
    }

    @Test
    void register_loginIsNotLongEnough_notOk() {
        User testUser = new User("Test", "TestPassword", 18);
        assertThrows(InvalidDataException.class, () -> testRegistrationService.register(testUser));
    }

    @Test
    void register_passwordIsNotLongEnough_notOk() {
        User testUser = new User("TestUser", "Test", 18);
        assertThrows(InvalidDataException.class, () -> testRegistrationService.register(testUser));
    }

    @Test
    void register_passwordIsLongEnough_Ok() {
        User testUser = new User("TestUser", "12345678", 18);
        assertEquals(testRegistrationService.register(testUser), testUser);
        assertEquals(storageDao.get(testUser.getLogin()), testUser);
    }

    @Test
    void register_passwordIsZero_notOk() {
        User testUser = new User("TestUser", "", 18);
        assertThrows(InvalidDataException.class, () -> testRegistrationService.register(testUser));
    }

    @Test
    void register_passwordIsThree_notOk() {
        User testUser = new User("TestUser", "123", 18);
        assertThrows(InvalidDataException.class, () -> testRegistrationService.register(testUser));
    }

    @Test
    void register_passwordIsFive_notOk() {
        User testUser = new User("TestUser", "12345", 18);
        assertThrows(InvalidDataException.class, () -> testRegistrationService.register(testUser));
    }

    @Test
    void register_passwordIsSix_Ok() {
        User testUser = new User("TestUser", "123456", 18);
        assertEquals(testRegistrationService.register(testUser), testUser);
        assertEquals(storageDao.get(testUser.getLogin()), testUser);
    }

    @Test
    void register_userIsAdult_Ok() {
        User testUser = new User("TestUser", "TestPassword", 18);
        assertEquals(testRegistrationService.register(testUser), testUser);
        assertEquals(storageDao.get(testUser.getLogin()), testUser);
    }

    @Test
    void register_userIsChild_notOk() {
        User testUser = new User("TestUser", "TestPassword", 13);
        assertThrows(InvalidDataException.class, () -> testRegistrationService.register(testUser));
    }

    @Test
    void register_negativeAge_notOk() {
        User testUser = new User("TestUser", "TestPassword", -13);
        assertThrows(InvalidDataException.class, () -> testRegistrationService.register(testUser));
    }

}
