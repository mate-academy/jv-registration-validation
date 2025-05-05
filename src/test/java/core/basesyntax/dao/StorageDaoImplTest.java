package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageDaoImplTest {
    private static final StorageDao storageDao = new StorageDaoImpl();
    private static final String DEFAULT_LOGIN = "TestLogin";
    private static final String DEFAULT_PASS = "123456";
    private static final Integer DEFAULT_AGE = 18;
    private User defaultUser;

    @BeforeEach
    public void setUp() {
        defaultUser = new User();
        defaultUser.setLogin(DEFAULT_LOGIN);
        defaultUser.setPassword(DEFAULT_PASS);
        defaultUser.setAge(DEFAULT_AGE);
    }

    @AfterEach
    public void tearDown() {
        Storage.people.clear();
    }

    @Test
    public void add_User_Ok() {
        User expected = defaultUser;
        User actual = storageDao.add(defaultUser);
        int sizeOfStorage = 1; //after "add" method;
        assertEquals(expected, actual);
        assertEquals(sizeOfStorage, Storage.people.size());
    }

    @Test
    public void get_User_Ok() {
        User expected = defaultUser;
        storageDao.add(defaultUser);
        assertEquals(expected, storageDao.get(defaultUser.getLogin()));

        User user1 = new User();
        user1.setLogin("user123");
        user1.setPassword("pass@word123");
        user1.setAge(25);
        User user2 = new User();
        user2.setLogin("john_doe");
        user2.setPassword("securePwd!567");
        user2.setAge(30);
        User user3 = new User();
        user3.setLogin("alice1985");
        user3.setPassword("myp@ssw0rd");
        user3.setAge(22);

        storageDao.add(user1);
        storageDao.add(user2);
        storageDao.add(user3);

        expected = new User();
        expected.setLogin("alice1985");
        expected.setPassword("myp@ssw0rd");
        expected.setAge(22);
        User actual = storageDao.get(user3.getLogin());
        assertEquals(expected, actual);
    }

    @Test
    public void get_InvalidUser_Null() {
        String testLogin = "12345massEffect";
        assertNull(storageDao.get(testLogin));
    }
}
