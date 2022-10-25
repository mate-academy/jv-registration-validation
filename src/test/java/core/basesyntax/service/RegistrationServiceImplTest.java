package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static StorageDao storageDao;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        user = new User();
        user.setLogin("Tretiak");
        user.setPassword("123456");
        user.setAge(20);
    }

    @Test
    void register_noExistUserLogin_ok() {
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void register_successfulRegistration_ok() {
        registrationService.register(user);
        assertEquals(user, storageDao.get(user.getLogin()));
    }

    @Test
    void register_containsUserLogin_notOk() {
        Storage.people.add(user);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullUserLogin_notOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullUser_notOk() {
        user = null;
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_smallPassword_notOk() {
        user.setPassword("123");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_lowAge_notOk() {
        user.setAge(15);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }
}
