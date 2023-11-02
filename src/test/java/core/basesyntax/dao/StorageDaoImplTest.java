package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

public class StorageDaoImplTest {
    private static final StorageDao storageDao = new StorageDaoImpl();

    @AfterAll
    public static void teardown() {
        Storage.people.clear();
    }

    @Test
    public void add_newUser_ok() {
        User user = new User();
        user.setLogin("employee1");
        Long userId = user.getId();
        int initialStorageSize = Storage.people.size();
        User addedUser = storageDao.add(user);
        assertSame(user, addedUser, "Method should return the same user");
        assertNotEquals(user.getId(), userId, "Method should set a relevant ID");
        assertEquals(Storage.people.size(),
                initialStorageSize + 1,
                "Method should not add null to the Storage");
    }

    @Test
    public void add_null_notOk() {
        assertThrows(NullPointerException.class,
                () -> storageDao.add(null),
                "Method cannot operate with null User");
    }

    @Test
    public void get_storedUser_ok() {
        User storedUser = new User();
        storedUser.setLogin("administrator");
        Storage.people.add(storedUser);
        User retrievedUser = storageDao.get("administrator");
        assertNotNull(retrievedUser, "Method should return the stored User");
    }

    @Test
    public void get_nonStoredUser_ok() {
        User retrievedUser = storageDao.get("employee2");
        assertNull(retrievedUser, "Method should return null");
    }

    @Test
    public void get_null_ok() {
        User retrievedUser = storageDao.get(null);
        assertNull(retrievedUser, "Method should return null");
    }
}
