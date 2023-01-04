package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageDaoImplTest {
    private static User user;
    private static StorageDaoImpl storageDao;

    @BeforeAll
    static void beforeAll() {
        storageDao = new StorageDaoImpl();
        User existUser = new User();
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin("LoginTest");
        user.setPassword("1234567");
        user.setAge(18);
    }

    @Test
    void get_threeUsers_ok() {
        User user1 = new User();
        user1.setLogin("first");
        user1.setPassword("password");
        user1.setAge(18);
        User user2 = new User();
        user2.setLogin("second");
        user2.setPassword("password");
        user2.setAge(18);
        User user3 = new User();
        user3.setLogin("third");
        user3.setPassword("password");
        user3.setAge(18);
        storageDao.add(user1);
        storageDao.add(user2);
        storageDao.add(user3);
        Assertions.assertEquals(user1, storageDao.get(user1.getLogin()));
        Assertions.assertEquals(user2, storageDao.get(user2.getLogin()));
        Assertions.assertEquals(user3, storageDao.get(user3.getLogin()));
    }

    @Test
    void add_threeUsers_ok() {
        User user1 = new User();
        user1.setLogin("LoginTest1");
        user1.setPassword("password");
        user1.setAge(18);
        User user2 = new User();
        user2.setLogin("LoginTest2");
        user2.setPassword("password");
        user2.setAge(18);
        User user3 = new User();
        user3.setLogin("LoginTest3");
        user3.setPassword("password");
        user3.setAge(18);
        storageDao.add(user1);
        storageDao.add(user2);
        storageDao.add(user3);
        Assertions.assertEquals(user1, Storage.people.get(0));
        Assertions.assertEquals(user2, Storage.people.get(1));
        Assertions.assertEquals(user3, Storage.people.get(2));
    }

    @Test
    void get_notExist_notOk() {
        Assertions.assertNull(storageDao.get(user.getLogin()));
    }

    @Test
    void add_nullUser_notOk() {
        Assertions.assertThrows(StorageDaoException.class, () -> storageDao.add(null));
    }

    @Test
    void add_nullPassword_notOk() {
        user.setPassword(null);
        Assertions.assertThrows(StorageDaoException.class, () -> storageDao.add(user));
    }

    @Test
    void add_nullLogin_notOk() {
        user.setLogin(null);
        Assertions.assertThrows(StorageDaoException.class, () -> storageDao.add(user));
    }

    @Test
    void add_nullAge_notOk() {
        user.setAge(null);
        Assertions.assertThrows(StorageDaoException.class, () -> storageDao.add(user));
    }
}
