package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static StorageDao storageDao;
    private static RegistrationService registrationService;
    private User testUser;

    @BeforeAll
    static void beforeAll() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        testUser = new User("Mark","123456", 18);
    }

    @Test
    void register_SetNullUser_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_SetNullLogin_NotOk() {
        testUser.setLogin(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_SetNullAge_NotOk() {
        testUser.setAge(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_SetNullPassword_NotOk() {
        testUser.setPassword(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_UserAlreadyExistWithSuchLogin_NotOk() {
        storageDao.add(testUser);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(new User("Mark", "654321", 20));
        });
    }

    @Test
    void register_UserNotExistWithSuchLogin_Ok() {
        User expected = new User("Mark", "asdq123", 20);
        User actual = registrationService.register(testUser);
        assertNotEquals(expected, actual);
    }

    @Test
    void register_SetNotValidAge_NotOk() {
        testUser.setAge(17);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_SetNegativeAge_NotOk() {
        testUser.setAge(-18);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_SetAgeEqualsAllowAge_Ok() {
        testUser.setAge(18);
        storageDao.add(testUser);
        assertEquals(testUser, storageDao.get(testUser.getLogin()));
    }

    @Test
    void register_SetAgeBiggerAgeThanAllow_Ok() {
        testUser.setAge(20);
        storageDao.add(testUser);
        assertEquals(testUser, storageDao.get(testUser.getLogin()));
    }

    @Test
    void register_SetPasswordWithLengthLessThanAllow_NotOk() {
        testUser.setPassword("12345");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_SetPasswordWithLengthMoreThanAllow_Ok() {
        testUser.setPassword("tooLong123435132412346");
        assertEquals(testUser, registrationService.register(testUser));
    }

    @Test
    void register_SetPasswordWithLengthAllow_Ok() {
        testUser.setPassword("123456");
        assertEquals(testUser, registrationService.register(testUser));
    }

    @Test
    void register_SetPasswordWithSymbols_Ok() {
        testUser.setPassword("!@#@$#$!&*^*_");
        assertEquals(testUser, registrationService.register(testUser));
    }

    @Test
    void register_SetUserWithValidParameters_Ok() {
        storageDao.add(testUser);
        assertEquals(testUser, storageDao.get(testUser.getLogin()));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
