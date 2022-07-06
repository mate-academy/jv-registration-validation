package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static User firstUser;
    private static User secondUser;
    private static User threeUser;
    private static User fourthUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        firstUser = new User();
        secondUser = new User();
        threeUser = new User();
        fourthUser = new User();
    }

    @Test
    void register_TwoUser_Ok() {
        firstUser.setAge(20);
        firstUser.setLogin("Bob");
        firstUser.setPassword("123456");
        secondUser.setAge(24);
        secondUser.setLogin("Henry");
        secondUser.setPassword("654321");
        registrationService.register(firstUser);
        registrationService.register(secondUser);
        boolean actual = Storage.people.size() == 2;
        assertTrue(actual);
    }

    @Test
    void register_PasswordShortLength_NotOk() {
        threeUser.setAge(20);
        threeUser.setLogin("Alice");
        threeUser.setPassword("1234");
        assertThrows(RuntimeException.class, () -> registrationService.register(threeUser));
    }

    @Test
    void register_ClonedUser_NotOk() {
        fourthUser.setAge(20);
        fourthUser.setLogin("Bob");
        fourthUser.setPassword("123456");
        assertThrows(RuntimeException.class, () -> registrationService.register(fourthUser));
    }

    @Test
    void storageDao_getUser_Ok() {
        StorageDao storageDao = new StorageDaoImpl();
        boolean actual = firstUser.equals(storageDao.get("Bob"));
        assertTrue(actual);
    }

    @Test
    void storageDao_getUser_notOk() {
        StorageDao storageDao = new StorageDaoImpl();
        boolean actual = threeUser.equals(storageDao.get("Bob"));
        assertFalse(actual);
    }
}
