package core.basesyntax.dao;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StorageDaoImplTest {
    private static StorageDao storageDao;
    User user;

    @BeforeAll
    static void beforeAll() {
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin("Jack");
        user.setAge(24);
        user.setPassword("1234567");
    }

    @Test
    void addNull_notOk() {
        user = null;
        assertThrows(NullPointerException.class, () -> storageDao.add(user),
                "User can't be null");
    }

    @Test
    void addUser_Ok() {
        assertEquals(storageDao.add(user), user, "user mast be added " + user);
    }

    @Test
    void getUser_Ok() {
        for (int i = 0; i < 38; i++) {
            User temp = new User();
            temp.setLogin("" + i);
            storageDao.add(temp);
        }
        for (int i = 0 ; i < 38; i++) {
            User temp = new User();
            temp.setLogin("" + i);
            assertEquals(storageDao.get("" + i), temp
                    , "User " + i + "must be equal " + temp);
        }
    }

    @Test
    void getWrongUser_tOk() {
        String login = "Alice";
        storageDao.add(user);
        assertNull(storageDao.get(login), "There is no user " + login);
    }
}
