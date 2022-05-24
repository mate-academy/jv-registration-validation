package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceTest {
    private static User newUser;
    private static RegistrationService registrationService;
    private static StorageDao storageDao;

    @BeforeAll
    static void beforeAll() {
        newUser = new User();
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @AfterAll
    static void afterAll() {
        Storage.people.clear();
    }

    @BeforeEach
    void setUp() {
        newUser.setAge(19);
        newUser.setLogin("Djon");
        newUser.setPassword("qwerty123");
    }

    @Test
    void register_nullValue_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_ageValueNull_NotOk() {
        newUser.setAge(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @Test
    void register_valueAgeAdult_NotOK() {
        newUser.setAge(16);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @Test
    void register_passwordValueNull_NotOk() {
        newUser.setPassword(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @Test
    void register_passwordValueIsBlank_NotOk() {
        newUser.setPassword("");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @Test
    void register_passwordValueIsShort_NotOk() {
        newUser.setPassword("short");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(newUser);
        });

    }

    @Test
    void register_loginValueNull_NotOk() {
        newUser.setLogin(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @Test
    void register_loginValueIsBlank_NotOk() {
        newUser.setLogin("");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @Test
    void register_existentLoginValue_NotOk() {
        newUser.setLogin("Djon");
        storageDao.add(newUser);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(newUser);
        });
    }
}
