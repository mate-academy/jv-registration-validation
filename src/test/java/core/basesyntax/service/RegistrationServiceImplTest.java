package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exeptions.ValidationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private Storage storage = new Storage();
    private StorageDao storageDao = new StorageDaoImpl();
    private User testUser = new User();
    private RegistrationServiceImpl registrationService = new RegistrationServiceImpl();

    @AfterEach
    void tearDown() {
        storage.people.clear();
    }

    @BeforeEach
    void setUp() {
        testUser.setLogin("TestUser");
        testUser.setAge(18);
        testUser.setPassword("123456");
    }

    @Test
    void register_AddValidNewUser_Ok() {
        assertEquals(testUser, registrationService.register(testUser));
        assertTrue(storage.people.contains(testUser));
    }

    @Test
    void register_UserPresentInBase_notOk() {
        storageDao.add(testUser);
        assertThrows(ValidationException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_LowAge_notOk() {
        testUser.setAge(17);
        assertThrows(ValidationException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_ShortPassword_notOk() {
        testUser.setPassword("12345");
        assertThrows(ValidationException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_UserIsNull_notOk() {
        testUser = null;
        assertThrows(ValidationException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_UserFieldsNull_notOk() {
        testUser.setLogin(null);
        assertThrows(ValidationException.class, () -> {
            registrationService.register(testUser);
        });
        testUser.setPassword(null);
        assertThrows(ValidationException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_UserFieldsEmpty_notOk() {
        testUser.setLogin("");
        assertThrows(ValidationException.class, () -> {
            registrationService.register(testUser);
        });
    }
}
