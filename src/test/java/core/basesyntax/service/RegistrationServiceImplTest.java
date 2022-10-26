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
    private User mark;

    @BeforeAll
    static void beforeAll() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        mark = new User("Mark","123456", 18);
    }

    @Test
    void register_setNullUser_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_setNullLogin_NotOk() {
        mark.setLogin(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(mark);
        });
    }

    @Test
    void register_setNullAge_NotOk() {
        mark.setAge(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(mark);
        });
    }

    @Test
    void register_setNullPassword_NotOk() {
        mark.setPassword(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(mark);
        });
    }

    @Test
    void register_userAlreadyExistWithSuchLogin_NotOk() {
        storageDao.add(mark);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(new User("Mark", "654321", 20));
        });
    }

    @Test
    void register_userNotExistWithSuchLogin_Ok() {
        User expected = new User("Mark", "asdq123", 20);
        User actual = registrationService.register(mark);
        assertNotEquals(expected, actual);
    }

    @Test
    void register_setNotValidAge_NotOk() {
        mark.setAge(17);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(mark);
        });
    }

    @Test
    void register_setNegativeAge_NotOk() {
        mark.setAge(-18);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(mark);
        });
    }

    @Test
    void register_setAgeEqualsAllowAge_Ok() {
        mark.setAge(18);
        storageDao.add(mark);
        assertEquals(mark, storageDao.get(mark.getLogin()));
    }

    @Test
    void register_setAgeBiggerAgeThanAllow_Ok() {
        mark.setAge(20);
        storageDao.add(mark);
        assertEquals(mark, storageDao.get(mark.getLogin()));
    }

    @Test
    void register_setPasswordWithLengthLessThanAllow_NotOk() {
        mark.setPassword("12345");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(mark);
        });
    }

    @Test
    void register_setPasswordWithLengthMoreThanAllow_Ok() {
        mark.setPassword("tooLong123435132412346");
        assertEquals(mark, registrationService.register(mark));
    }

    @Test
    void register_setPasswordWithLengthAllow_Ok() {
        mark.setPassword("123456");
        assertEquals(mark, registrationService.register(mark));
    }

    @Test
    void register_setPasswordWithSymbols_Ok() {
        mark.setPassword("!@#@$#$!&*^*_");
        assertEquals(mark, registrationService.register(mark));
    }

    @Test
    void register_setUserWithValidParameters_Ok() {
        storageDao.add(mark);
        assertEquals(mark, storageDao.get(mark.getLogin()));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
