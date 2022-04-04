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
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static int MIN_AGE = 18;
    private static int NEGATIVE_AGE = -23;
    private static int LESS_THAN_MIN_AGE = 12;
    private static int GREATER_THAN_MIN_AGE = 2056;
    private static int VALID_AGE = 20;
    private static String INVALID_PASSWORD = "bad12";
    private static String VALID_PASSWORD = "secure_password123";
    private static String VALID_LOGIN = "GreatLogin";
    private static String EMPTY_STRING = "";
    private static RegistrationService registrationService;
    private static StorageDao storageDao;
    private static User testUser;
    private User validUser = new User(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
        testUser = new User();
    }

    @AfterEach
    void cleanUp() {
        testUser.setId(null);
        testUser.setLogin(null);
        testUser.setPassword(null);
        testUser.setAge(null);
        Storage.people.clear();
    }

    @Test
    void register_UserIsNull_NotOk() {
        assertThrows(NullPointerException.class, () -> registrationService.register(null));
    }

    @Test
    void register_loginIsNull_NotOk() {
        testUser.setLogin(null);
        assertThrows(NullPointerException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_ageIsNull_NotOk() {
        testUser.setAge(null);
        assertThrows(NullPointerException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_passwordIsNull_NotOk() {
        testUser.setPassword(null);
        assertThrows(NullPointerException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_ValidUser_ReturnsSameUser() {
        assertEquals(validUser, registrationService.register(validUser));
    }

    @Test
    void register_ValidUser_UserAddedOnlyOneTime() {
        registrationService.register(validUser);
        Storage.people.remove(validUser);
        assertTrue(Storage.people.isEmpty());
    }

    @Test
    void register_ValidUser_UserAddedToDatabaseIsNotModified() {
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
        testUser.setAge(LESS_THAN_MIN_AGE);
        assertThrows(RuntimeException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_NegativeAge_NotOk() {
        testUser.setAge(NEGATIVE_AGE);
        assertThrows(RuntimeException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_MinimumAge_Ok() {
        testUser = validUser;
        testUser.setAge(MIN_AGE);
        assertEquals(testUser, registrationService.register(testUser));
        assertNotNull(storageDao.get(testUser.getLogin()));
    }

    @Test
    void register_AgeGreaterThanMin_Ok() {
        testUser = validUser;
        testUser.setAge(GREATER_THAN_MIN_AGE);
        assertEquals(testUser, registrationService.register(testUser));
        assertNotNull(storageDao.get(testUser.getLogin()));
    }

    @Test
    void register_PasswordEmptyString_NotOk() {
        testUser = validUser;
        testUser.setPassword(EMPTY_STRING);
        assertThrows(RuntimeException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_InvalidPassword_NotOk() {
        testUser = validUser;
        testUser.setPassword(INVALID_PASSWORD);
        assertThrows(RuntimeException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_ValidUser_Ok() {
        testUser = validUser;
        assertEquals(validUser, registrationService.register(validUser));
        assertEquals(validUser, storageDao.get(validUser.getLogin()));
        Storage.people.remove(validUser);
        assertTrue(Storage.people.isEmpty());
    }
}
