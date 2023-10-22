package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class StorageDaoImplTest {
    private static final StorageDao storageDao = new StorageDaoImpl();

    @BeforeAll
    public static void setupStorage() {
        Storage.people.add(new User());
        Storage.people.get(0).setLogin(null);
        Storage.people.add(new User());
        Storage.people.get(1).setLogin("admin");
        Storage.people.add(null);
    }

    @Test
    public void add_user_returnsSameUser() {
        User user = new User();
        user.setLogin("employee0001");
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
    public void add_null_returnsNull() {
        User user = null;
        int initialStorageSize = Storage.people.size();
        User addedUser = storageDao.add(user);
        assertNull(addedUser, "Method should return null if the user is null");
        assertEquals(Storage.people.size(),
                initialStorageSize,
                "Method should not add null to the Storage");
    }

    @Test
    public void get_storedUser_returnsUser() {
        User retrievedUser = storageDao.get("admin");
        assertNotNull(retrievedUser, "Method should return the stored User");
    }

    @Test
    public void get_nonStoredUser_returnsNull() {
        User retrievedUser = storageDao.get("employee0002");
        assertNull(retrievedUser, "Method should return null");
    }

    @Test
    public void get_storageContainsNull_ignoresNull() {
        assertDoesNotThrow(() -> {
            storageDao.get("employee0003");
        }, "Method should ignore empty values in the Storage");
    }

    @Test
    public void get_storageContainsNullLoginUser_ignoresUser() {
        assertDoesNotThrow(() -> {
            storageDao.get("employee0004");
        }, "Method should ignore Users with empty login in the Storage");
    }

    @Test
    public void get_null_returnsNull() {
        User retrievedUser = storageDao.get(null);
        assertNull(retrievedUser, "Method should return null");
    }
}
