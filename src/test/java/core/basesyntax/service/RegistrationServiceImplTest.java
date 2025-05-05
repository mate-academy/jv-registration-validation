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
    void register_setNullUser_notOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_setNullLogin_notOk() {
        testUser.setLogin(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_setNullAge_notOk() {
        testUser.setAge(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_setNullPassword_notOk() {
        testUser.setPassword(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_userAlreadyExistWithSuchLogin_notOk() {
        storageDao.add(testUser);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(new User("Mark", "654321", 20));
        });
    }

    @Test
    void register_userNotExistWithSuchLogin_ok() {
        User expected = new User("Mark", "asdq123", 20);
        User actual = registrationService.register(testUser);
        assertNotEquals(expected, actual);
    }

    @Test
    void register_setNotValidAge_notOk() {
        testUser.setAge(17);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_setNegativeAge_notOk() {
        testUser.setAge(-18);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_setAgeEqualsAllowAge_ok() {
        testUser.setAge(18);
        storageDao.add(testUser);
        assertEquals(testUser, storageDao.get(testUser.getLogin()));
    }

    @Test
    void register_setAgeBiggerAgeThanAllow_ok() {
        testUser.setAge(20);
        storageDao.add(testUser);
        assertEquals(testUser, storageDao.get(testUser.getLogin()));
    }

    @Test
    void register_setPasswordWithLengthLessThanAllow_notOk() {
        testUser.setPassword("12345");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_setPasswordWithLengthMoreThanAllow_ok() {
        testUser.setPassword("tooLong123435132412346");
        assertEquals(testUser, registrationService.register(testUser));
    }

    @Test
    void register_setPasswordWithLengthAllow_ok() {
        testUser.setPassword("123456");
        assertEquals(testUser, registrationService.register(testUser));
    }

    @Test
    void register_setPasswordWithSymbols_ok() {
        testUser.setPassword("!@#@$#$!&*^*_");
        assertEquals(testUser, registrationService.register(testUser));
    }

    @Test
    void register_setUserWithValidParameters_ok() {
        storageDao.add(testUser);
        assertEquals(testUser, storageDao.get(testUser.getLogin()));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
