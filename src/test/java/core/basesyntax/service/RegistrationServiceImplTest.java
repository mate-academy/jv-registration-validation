package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
    private static final User FIRST_VALID_USER = new User("john8989", "maximus", 20);
    private static final User SECOND_VALID_USER = new User("ann_25", "sweet_angel", 18);
    private static final int SIZE_OF_STORAGE_WITH_VALID_USERS = 2;
    private static final String VALID_PASSWORD = "lviv4_4";
    private static final String VALID_LOGIN = "bob_255";
    private static final int VALID_AGE = 25;
    private static final String INVALID_PASSWORD = "hfgeu";
    private static final String LOGIN_ALREADY_EXISTS = "ann_25";
    private static final int AGE_LESS_THEN_18 = 17;

    private static final int AGE_MORE_THEN_120 = 125;
    private static final int NEGATIVE_AGE = - 5;
    private static final String EMPTY_STRING = "";
    private static RegistrationService registrationService;
    private static StorageDao storageDao;
    private User testUser;
    
    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        storageDao.add(FIRST_VALID_USER);
        storageDao.add(SECOND_VALID_USER);
        testUser = new User(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);

    }

    @Test
    void register_validUser_ok() {
        User registeredValidUser = registrationService.register(testUser);
        assertEquals(3, Storage.people.size());
        assertTrue(Storage.people.contains(registeredValidUser));
        assertEquals(registeredValidUser, testUser);
    }

    @Test
    void register_ageLessThen18_notOk() {
        testUser.setAge(AGE_LESS_THEN_18);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(testUser);
        });
        assertEquals(SIZE_OF_STORAGE_WITH_VALID_USERS, Storage.people.size());
        assertFalse(Storage.people.contains(testUser));
    }

    @Test
    void register_ageMoreThen120_notOk() {
        testUser.setAge(AGE_MORE_THEN_120);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(testUser);
        });
        assertEquals(SIZE_OF_STORAGE_WITH_VALID_USERS, Storage.people.size());
        assertFalse(Storage.people.contains(testUser));
    }

    @Test
    void register_negativeAge_notOk() {
        testUser.setAge(NEGATIVE_AGE);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(testUser);
        });
        assertEquals(SIZE_OF_STORAGE_WITH_VALID_USERS, Storage.people.size());
        assertFalse(Storage.people.contains(testUser));
    }

    @Test
    void register_nullAge_notOk() {
        testUser.setAge(null);
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            registrationService.register(testUser);
        });
        assertEquals("Age can't be null", thrown.getMessage());
        assertEquals(SIZE_OF_STORAGE_WITH_VALID_USERS, Storage.people.size());
        assertFalse(Storage.people.contains(testUser));
    }

    @Test
    void register_passwordLessThenSixCharacters_notOk() {
        testUser.setPassword(INVALID_PASSWORD);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(testUser);
        });
        assertEquals(SIZE_OF_STORAGE_WITH_VALID_USERS, Storage.people.size());
        assertFalse(Storage.people.contains(testUser));
    }

    @Test
    void register_nullPassword_notOk() {
        testUser.setPassword(null);
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            registrationService.register(testUser);
        });
        assertEquals("Password can't be null", thrown.getMessage());
        assertEquals(SIZE_OF_STORAGE_WITH_VALID_USERS, Storage.people.size());
        assertFalse(Storage.people.contains(testUser));
    }

    @Test
    void register_emptyPassword_notOk() {
        testUser.setPassword(EMPTY_STRING);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(testUser);
        });
        assertEquals(SIZE_OF_STORAGE_WITH_VALID_USERS, Storage.people.size());
        assertFalse(Storage.people.contains(testUser));
    }

    @Test
    void register_loginExist_notOk() {
        testUser.setLogin(LOGIN_ALREADY_EXISTS);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(testUser);
        });
        assertEquals(SIZE_OF_STORAGE_WITH_VALID_USERS, Storage.people.size());
        assertFalse(Storage.people.contains(testUser));
    }

    @Test
    void register_nullLogin_notOk() {
        testUser.setLogin(null);
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            registrationService.register(testUser);
        });
        assertEquals("Login can't be null", thrown.getMessage());
        assertEquals(SIZE_OF_STORAGE_WITH_VALID_USERS, Storage.people.size());
        assertFalse(Storage.people.contains(testUser));
    }

    @Test
    void register_emptyLogin_notOk() {
        testUser.setLogin(EMPTY_STRING);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(testUser);
        });
        assertEquals(SIZE_OF_STORAGE_WITH_VALID_USERS, Storage.people.size());
        assertFalse(Storage.people.contains(testUser));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
