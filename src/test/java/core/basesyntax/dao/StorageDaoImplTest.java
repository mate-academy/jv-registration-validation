package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageDaoImplTest {
    private static StorageDao storageDao;
    private static List<User> users;

    @BeforeAll
    static void initializeVariables() {
        storageDao = new StorageDaoImpl();
        users = new ArrayList<>();
    }

    @BeforeEach
    void fillCollection() {
        User user1 = new User(1L, "login1", "password1", 1);
        User user2 = new User(2L, "login2", "password2", 2);
        User user3 = new User(3L, "login3", "password3", 3);

        users.add(user1);
        users.add(user2);
        users.add(user3);
    }

    @Test
    void add_checkIfContains_isOk() {
        storageDao.add(users.get(0));
        assertTrue(Storage.people.contains(users.get(0)));
    }

    @Test
    void add_checkSize_isOk() {
        storageDao.add(users.get(0));
        storageDao.add(users.get(1));
        storageDao.add(users.get(2));
        assertEquals(3, Storage.people.size());
    }

    @Test
    void get_checkIfCorrect_isOk() {
        storageDao.add(users.get(0));
        assertEquals(users.get(0), storageDao.get(users.get(0).getLogin()));
    }

    @Test
    void get_checkIfCorrect_isNotOk() {
        storageDao.add(users.get(0));
        assertNull(storageDao.get(users.get(1).getLogin()));
    }

    @AfterEach
    void clearStorage() {
        Storage.people.removeAll(Storage.people);
    }
}
