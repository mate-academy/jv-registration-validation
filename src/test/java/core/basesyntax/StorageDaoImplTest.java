package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StorageDaoImplTest {
    private static StorageDao storageDao;
    private static User user1;
    private static User user2;
    private static User user3;
    private static User user4;

    @BeforeAll
    public static void setUp() {
        storageDao = new StorageDaoImpl();
        user1 = new User();
        user2 = new User();
        user3 = new User();
        user4 = new User();
        user1.setAge(18);
        user2.setAge(17);
        user3.setAge(28);
        user4.setAge(34);
        user1.setLogin("asdf");
        user2.setLogin("qwerty");
        user3.setLogin("zxcvbn");
        user4.setLogin("mkinuuhf");
        user1.setPassword("123456");
        user2.setPassword("12345");
        user3.setPassword("12234");
        user4.setPassword("123456");
    }

    @BeforeEach
    public void clearStorage() {
        Storage.people.clear();
    }

    @Test
    public void addUsersToStorage() {
        storageDao.add(user1);
        storageDao.add(user2);
        storageDao.add(user3);
        storageDao.add(user4);
        assertEquals(user1, storageDao.get("asdf"));
        assertEquals(user2, storageDao.get("qwerty"));
        assertEquals(user3, storageDao.get("zxcvbn"));
        assertEquals(user4, storageDao.get("mkinuuhf"));
        assertEquals(4, Storage.people.size());
    }

    @Test
    public void addUsersToStorage_addNull() {
        assertThrows(NullPointerException.class, () -> storageDao.add(null));
    }

    @Test
    public void addSameUserToStorage() {
        storageDao.add(user1);
        assertEquals(user1, storageDao.get("asdf"));
        storageDao.add(user1);
        assertEquals(2, Storage.people.size());
    }

    @Test
    public void getWrongUser() {
        storageDao.add(user1);
        assertEquals(user1, storageDao.get("asdf"));
        assertNull(storageDao.get("qwerty"));
    }
}
