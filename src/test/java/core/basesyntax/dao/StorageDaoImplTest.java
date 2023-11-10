package core.basesyntax.dao;

import core.basesyntax.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class StorageDaoImplTest {

    private static StorageDaoImpl storageDao;

    @BeforeAll
    static void beforeAll() {
        storageDao = new StorageDaoImpl();
    }

    @Test
    void add_addedNewUserToStorage_Ok() {
        User bob = new User("bob123", "password1", 25);
        User actual = storageDao.add(bob);
        Assertions.assertEquals(actual, bob);
    }

    @Test
    void get_GettingUserFromStorage_Ok() {
        User bob = new User("bob123", "password1", 25);
        storageDao.add(bob);
        User actual = storageDao.get(bob.getLogin());
        Assertions.assertEquals(actual, bob);
    }

    @Test
    void get_GettingUserNotExist_Null_Ok() {
        User bob = new User("bob1234", "password1", 25);
        Assertions.assertNull(storageDao.get(bob.getLogin()));
    }
}
