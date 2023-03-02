package core.basesyntax.service;

import core.basesyntax.RegistrationException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
    }

    @Test
    void addUser_Ok() {
        user = new User(1238383L, "world12", "userworld123", 20);
        User userTestOne = registrationService.register(user);
        user = new User(2323233L, "world13", "userworld1543", 25);
        User userTestTwo = registrationService.register(user);
        user = new User(2323233L, "world14", "userworld95884", 19);
        User userTestThree = registrationService.register(user);
        assertEquals(storageDao.get("world12"), userTestOne);
        assertEquals(storageDao.get("world13"), userTestTwo);
        assertEquals(storageDao.get("world14"), userTestThree);
        assertEquals(3, Storage.people.size());
    }

    @Test
    void addExistUser_NotOk() {
        assertThrows(RegistrationException.class, () -> {
            StorageDaoImpl storageDao = new StorageDaoImpl();
            user = new User(12333L, "world1266", "userw23023", 21);
            storageDao.add(user);
            registrationService.register(user);
        }, "You can not add users with same login");
    }

    @Test
    void addUserWithNullPassword_NotOk() {
        user = new User(12333L, "world126333", null, 21);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        }, "We can not add user with null password");
    }

    @Test
    void addUserWithNullLogin_NotOk() {
        user = new User(12333L, null, "jdhfhd83", 21);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        }, "We can not add user with null login");
    }

    @Test
    void addUserWithForbiddenAge_NotOk() {
        user = new User(12333L, "newLogin1029", "jdhfhd83", 17);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        }, "You can not add use with age under 18");
    }

    @Test
    void addUserWithForbiddenPassword_NotOk() {
        user = new User(12333L, "newLogin10329", "2828", 18);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        }, "You can not add user with password length less then 8");
    }

    @Test
    void addUserWithNullAge_NotOk() {
        user = new User(12333L, "newLogin10329", "2828333333", null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        }, " You can not add user with null age");
    }
}
