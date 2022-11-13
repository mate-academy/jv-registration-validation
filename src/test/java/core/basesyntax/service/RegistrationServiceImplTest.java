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
    private static final User VALID_USER = new User("ann_25", "maximus", 20);
    private static final String VALID_PASSWORD = "lviv4_4";
    private static final String VALID_LOGIN = "bob_255";
    private static final int VALID_AGE = 18;
    private static final String INVALID_PASSWORD_LESS_THEN_SIX_CHARACTERS = "hfgeu";
    private static final String LOGIN_ALREADY_EXISTS = "ann_25";
    private static final int AGE_LESS_THEN_MIN = 17;

    private static final int AGE_MORE_THEN_MAX = 125;
    private static final int NEGATIVE_AGE = - 5;
    private static final String EMPTY_STRING = "";
    private static final String SEVERAL_WHITE_SPACES = "      ";
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
        storageDao.add(VALID_USER);
        testUser = new User(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);
    }

    @Test
    void register_validUser_ok() {
        User registeredValidUser = registrationService.register(testUser);
        assertEquals(registeredValidUser, testUser);
        assertEquals(2, Storage.people.size());
        assertTrue(Storage.people.contains(registeredValidUser));
    }

    @Test
    void register_nullUser_notOk() {
        testUser = null;
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            registrationService.register(testUser);
        });
        assertEquals("User cant be null", thrown.getMessage());
        assertEquals(1, Storage.people.size());
        assertFalse(Storage.people.contains(testUser));
    }

    @Test
    void register_ageLessThenMin_notOk() {
        testUser.setAge(AGE_LESS_THEN_MIN);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(testUser);
        });
        assertEquals(1, Storage.people.size());
        assertFalse(Storage.people.contains(testUser));
    }

    @Test
    void register_ageMoreThenMax_notOk() {
        testUser.setAge(AGE_MORE_THEN_MAX);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(testUser);
        });
        assertEquals(1, Storage.people.size());
        assertFalse(Storage.people.contains(testUser));
    }

    @Test
    void register_negativeAge_notOk() {
        testUser.setAge(NEGATIVE_AGE);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(testUser);
        });
        assertEquals(1, Storage.people.size());
        assertFalse(Storage.people.contains(testUser));
    }

    @Test
    void register_nullAge_notOk() {
        testUser.setAge(null);
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            registrationService.register(testUser);
        });
        assertEquals("Age can't be null", thrown.getMessage());
        assertEquals(1, Storage.people.size());
        assertFalse(Storage.people.contains(testUser));
    }

    @Test
    void register_invalidPassword_notOk() {
        testUser.setPassword(INVALID_PASSWORD_LESS_THEN_SIX_CHARACTERS);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(testUser);
        });
        assertEquals(1, Storage.people.size());
        assertFalse(Storage.people.contains(testUser));
    }

    @Test
    void register_nullPassword_notOk() {
        testUser.setPassword(null);
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            registrationService.register(testUser);
        });
        assertEquals("Password can't be null", thrown.getMessage());
        assertEquals(1, Storage.people.size());
        assertFalse(Storage.people.contains(testUser));
    }

    @Test
    void register_emptyPassword_notOk() {
        testUser.setPassword(EMPTY_STRING);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(testUser);
        });
        assertEquals(1, Storage.people.size());
        assertFalse(Storage.people.contains(testUser));
    }

    @Test
    void register_invalidPasswordWhiteSpaces_notOk() {
        testUser.setPassword(SEVERAL_WHITE_SPACES);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(testUser);
        });
        assertEquals(1, Storage.people.size());
        assertFalse(Storage.people.contains(testUser));
    }

    @Test
    void register_loginExist_notOk() {
        testUser.setLogin(LOGIN_ALREADY_EXISTS);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(testUser);
        });
        assertEquals(1, Storage.people.size());
        assertFalse(Storage.people.contains(testUser));
    }

    @Test
    void register_nullLogin_notOk() {
        testUser.setLogin(null);
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            registrationService.register(testUser);
        });
        assertEquals("Login can't be null", thrown.getMessage());
        assertEquals(1, Storage.people.size());
        assertFalse(Storage.people.contains(testUser));
    }

    @Test
    void register_emptyLogin_notOk() {
        testUser.setLogin(EMPTY_STRING);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(testUser);
        });
        assertEquals(1, Storage.people.size());
        assertFalse(Storage.people.contains(testUser));
    }

    @Test
    void register_LoginWhiteSpaces_notOk() {
        testUser.setLogin(SEVERAL_WHITE_SPACES);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(testUser);
        });
        assertEquals(1, Storage.people.size());
        assertFalse(Storage.people.contains(testUser));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
