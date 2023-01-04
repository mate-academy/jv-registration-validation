package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StorageDaoImplTest {
    static User user;
    static StorageDaoImpl storageDao;
    static User existUser;

    @BeforeAll
    static void beforeAll() {
        storageDao = new StorageDaoImpl();
        existUser = new User();
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @BeforeEach
    void setUp() {
        user = new User("LoginTest", "1234567", 18);
    }

    @Test
    void get_threeUsers_ok() {
        User user1 = new User("LoginTest1", "1234567", 18);
        User user2 = new User("LoginTest2", "1234567", 18);
        User user3 = new User("LoginTest3", "1234567", 18);
        storageDao.add(user1);
        storageDao.add(user2);
        storageDao.add(user3);
        assertEquals(user1, storageDao.get(user1.getLogin()));
        assertEquals(user2, storageDao.get(user2.getLogin()));
        assertEquals(user3, storageDao.get(user3.getLogin()));
    }

    @Test
    void add_threeUsers_ok() {
        User user1 = new User("LoginTest1", "1234567", 18);
        User user2 = new User("LoginTest2", "1234567", 18);
        User user3 = new User("LoginTest3", "1234567", 18);
        storageDao.add(user1);
        storageDao.add(user2);
        storageDao.add(user3);
        assertEquals(user1, Storage.people.get(0));
        assertEquals(user2, Storage.people.get(1));
        assertEquals(user3, Storage.people.get(2));
    }

    @Test
    void get_notExist_notOk() {
        assertNull(storageDao.get(user.getLogin()));
    }

    @Test
    void add_nullUser_notOk() {
        assertThrows(StorageDaoException.class, () -> storageDao.add(null));
    }

    @Test
    void add_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(StorageDaoException.class, () -> storageDao.add(user));
    }

    @Test
    void add_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(StorageDaoException.class, () -> storageDao.add(user));
    }

    @Test
    void add_nullAge_notOk() {
        user.setAge(null);
        assertThrows(StorageDaoException.class, () -> storageDao.add(user));
    }
}