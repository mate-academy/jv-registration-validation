package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageDaoImplTest {
    private StorageDao storageDao;

    @BeforeEach
    void initStorageDaoImpl() {
        storageDao = new StorageDaoImpl();
    }

    @Test
    void add_UserToMockStorage_Ok() {
        String testUserName = "test_user";
        String testUserPassword = "test_password";
        int testUserAge = 81;
        User testUser = new User(testUserName, testUserPassword, testUserAge);
        storageDao.add(testUser);

        assertEquals(1, testUser.getId());
        assertEquals(testUser.getLogin(), testUserName);
        assertEquals(testUser.getPassword(), testUserPassword);
        assertEquals(testUser.getAge(), testUserAge);

        Storage.people.remove(testUser);
    }

    @Test
    void add_NullToMockStorage_NotOk() {
        try {
            storageDao.add(null);
        } catch (NullPointerException e) {
            return;
        }
        fail("Passing null user to storageDao must cause NullPointerException");
    }

    @Test
    void get_ThreeUsersFromStorage_OK() {
        User user1 = new User("john_doe", "password123", 25);
        User user2 = new User("jane_smith", "secret123", 30);
        User user3 = new User("bob_jones", "qwerty", 22);
        Storage.people.add(user1);
        Storage.people.add(user2);
        Storage.people.add(user3);

        assertEquals(storageDao.get("john_doe"), user1);
        assertEquals(storageDao.get("jane_smith"), user2);
        assertEquals(storageDao.get("bob_jones"), user3);

        // Clear after test
        Storage.people.remove(user1);
        Storage.people.remove(user2);
        Storage.people.remove(user3);
    }

    @Test
    void get_userThatIsNotInStorage_OK() {
        assertNull(storageDao.get("john_doe"));
        assertNull(storageDao.get("jane_smith"));
        assertNull(storageDao.get("bob_jones"));
    }
}
