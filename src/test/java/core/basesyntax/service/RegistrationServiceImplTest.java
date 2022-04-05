package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static StorageDao storageDao;
    private static User testUser;
    private User validUser = new User("GreatLogin", "secure_password123", 20);

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
        testUser = new User();
    }

    @BeforeEach
    void setUp() {
        testUser = new User();
    }

    @AfterEach
    void cleanUp() {
        Storage.people.clear();
    }

    @Test
    void register_UserIsNull_NotOk() {
        assertThrows(NullPointerException.class, () -> registrationService.register(null));
    }

    @Test
    void register_LoginIsNull_NotOk() {
        testUser.setLogin(null);
        assertThrows(NullPointerException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_AgeIsNull_NotOk() {
        testUser.setAge(null);
        assertThrows(NullPointerException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_PasswordIsNull_NotOk() {
        testUser.setPassword(null);
        assertThrows(NullPointerException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_ValidUserReturnsSameUser_Ok() {
        assertEquals(validUser, registrationService.register(validUser));
    }

    @Test
    void register_ValidUserAddedOnlyOneTime_Ok() {
        registrationService.register(validUser);
        Storage.people.remove(validUser);
        assertTrue(Storage.people.isEmpty());
    }

    @Test
    void register_ValidUserAddedToDatabaseIsNotModified_Ok() {
        registrationService.register(validUser);
        assertEquals(validUser, storageDao.get(validUser.getLogin()));
    }

    @Test
    void register_UserIsAlreadyInDatabase_NotOk() {
        registrationService.register(validUser);
        assertThrows(RuntimeException.class, () -> registrationService.register(validUser));
    }

    @Test
    void register_AgeLessThanMinimum_NotOk() {
        testUser.setAge(17);
        assertThrows(RuntimeException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_NegativeAge_NotOk() {
        testUser.setAge(-15);
        assertThrows(RuntimeException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_MinimumAge_Ok() {
        testUser = validUser;
        testUser.setAge(18);
        assertEquals(testUser, registrationService.register(testUser));
        assertNotNull(storageDao.get(testUser.getLogin()));
    }

    @Test
    void register_AgeGreaterThanMin_Ok() {
        testUser = validUser;
        testUser.setAge(42);
        assertEquals(testUser, registrationService.register(testUser));
        assertNotNull(storageDao.get(testUser.getLogin()));
    }

    @Test
    void register_PasswordEmptyString_NotOk() {
        testUser = validUser;
        testUser.setPassword("");
        assertThrows(RuntimeException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_InvalidPassword_NotOk() {
        testUser = validUser;
        testUser.setPassword("bad12");
        assertThrows(RuntimeException.class, () -> registrationService.register(testUser));
    }
}
