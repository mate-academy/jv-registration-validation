package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registration = new RegistrationServiceImpl();
    private static StorageDao storageDao = new StorageDaoImpl();
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin("Alice");
        user.setAge(22);
        user.setPassword("1234567");
        storageDao.add(user);
    }

    @Test
    void userIsNull_NotOK() {
        assertThrows(NullPointerException.class, () -> registration.register(null));
    }

    @Test
    void userLoginIsNull_NotOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> registration.register(user));
    }

    @Test
    void userLoginIsEmpty_NotOk() {
        user.setLogin("");
        assertThrows(RuntimeException.class, () -> registration.register(user));
    }

    @Test
    void userByLoginIsAlreadyExist_NotOK() {
        user = new User();
        user.setLogin("Alice");
        user.setAge(22);
        user.setPassword("1234567");
        assertThrows(RuntimeException.class, () -> registration.register(user));
    }

    @Test
    void ageOfUserIsNull_NotOk() {
        user = new User();
        user.setLogin("Bob");
        user.setAge(null);
        user.setPassword("123456");
        assertThrows(RuntimeException.class, () -> registration.register(user));
    }

    @Test
    void ageOfUser_NotOk() {
        user = new User();
        user.setLogin("Bob");
        user.setAge(12);
        user.setPassword("123456");
        assertThrows(RuntimeException.class, () -> registration.register(user));
    }

    @Test
    void ageOfUser_Ok() {
        user = new User();
        user.setLogin("Bob");
        user.setAge(18);
        user.setPassword("123456");
        registration.register(user);
        assertTrue(Storage.people.contains(user));
        assertEquals(2, Storage.people.size(), "Test failed! Size of Storage should be " + 2
                + " but it is " + Storage.people.size());
    }

    @Test
    void passwordIsNull_NotOk() {
        user = new User();
        user.setLogin("Bob");
        user.setAge(18);
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registration.register(user));
    }

    @Test
    void passwordIsEmpty_NotOk() {
        user = new User();
        user.setLogin("Bob");
        user.setAge(18);
        user.setPassword("");
        assertThrows(RuntimeException.class, () -> registration.register(user));
    }

    @Test
    void sizeOfPassword_NotOk() {
        user = new User();
        user.setLogin("Bob");
        user.setAge(18);
        user.setPassword("12345");
        assertThrows(RuntimeException.class, () -> registration.register(user));
    }

    @Test
    void sizeOfPassword_Ok() {
        user = new User();
        user.setLogin("Bob");
        user.setAge(18);
        user.setPassword("123456");
        registration.register(user);
        assertTrue(Storage.people.contains(user));
        assertEquals(2, Storage.people.size(), "Test failed! Size of Storage should be " + 2
                + " but it is " + Storage.people.size());
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
