package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageDaoImplTest {
    private static StorageDaoImpl storageDao;
    private static final Integer DEFAULT_AGE = 18;
    private static final String DEFAULT_PASSWORD = "123456";
    private static final String DEFAULT_LOGIN = "Ukraine";
    private User defaultUser;

    @BeforeAll
    static void init() {
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        defaultUser = new User();
        defaultUser.setAge(DEFAULT_AGE);
        defaultUser.setPassword(DEFAULT_PASSWORD);
        defaultUser.setLogin(DEFAULT_LOGIN);
    }

    @AfterEach
    void clearAfterTest() {
        Storage.people.clear();
    }

    @Test
    void getUser_OK() {
        User expected = defaultUser;
        storageDao.add(defaultUser);
        assertEquals(expected, storageDao.get(defaultUser.getLogin()));
    }

    @Test
    void getUserNotExist_OK() {
        User user1 = new User();
        user1.setLogin("Den76746");
        user1.setPassword("12345677");
        user1.setAge(34);
        User user2 = new User();
        user2.setLogin("Alex76746");
        user2.setPassword("76543211");
        user2.setAge(21);
        User user3 = new User();
        user3.setLogin("Tom76746");
        user3.setPassword("1029384756");
        user3.setAge(45);
        String testLogin = "Dimon6969";
        assertNull(storageDao.get(testLogin));
    }
}
