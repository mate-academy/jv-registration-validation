package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exeptions.ValidationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {

    private Storage storage = new Storage();
    private StorageDao storageDao = new StorageDaoImpl();
    private User testUser = new User();
    private RegistrationServiceImpl registrationService = new RegistrationServiceImpl();

    @BeforeEach
    void setUp() {
        storage.people.clear();
        User user1 = new User();
        user1.setLogin("Bob");
        user1.setAge(18);
        user1.setPassword("123456");
        storageDao.add(user1);
        User user2 = new User();
        user2.setLogin("Lola");
        user2.setAge(21);
        user2.setPassword("123456");
        storageDao.add(user2);
        testUser.setLogin("TestUser");
        testUser.setAge(18);
        testUser.setPassword("123456");
    }

    @Test
    void registerTest_AddValidNewUser_isOk() {
        assertEquals(testUser, registrationService.register(testUser));
        assertTrue(storage.people.contains(testUser));
    }

    @Test
    void registerTest_UserPresentInBase_notOkException() {
        storageDao.add(testUser);
        assertThrows(ValidationException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void registerTest_UserAgeLess18_notOkException() {
        testUser.setAge(17);
        assertThrows(ValidationException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void registerTest_UserPasswordLessMinValue_notOkException() {
        testUser.setPassword("12345");
        assertThrows(ValidationException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void registerTest_UserIsNull_notOkException() {
        testUser = null;
        assertThrows(ValidationException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void registerTest_UserFieldsNull_notOkException() {
        testUser.setLogin("");
        assertThrows(ValidationException.class, () -> {
            registrationService.register(testUser);
        });
        testUser.setLogin(null);
        assertThrows(ValidationException.class, () -> {
            registrationService.register(testUser);
        });
        testUser.setLogin("name");
        testUser.setAge(0);
        assertThrows(ValidationException.class, () -> {
            registrationService.register(testUser);
        });
        testUser.setPassword(null);
        assertThrows(ValidationException.class, () -> {
            registrationService.register(testUser);
        });
    }
}
