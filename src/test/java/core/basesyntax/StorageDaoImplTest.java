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
    private static User user;

    @BeforeAll
    public static void setUp() {
        storageDao = new StorageDaoImpl();
        user = new User();
    }

    @BeforeEach
    public void clearStorage() {
        Storage.people.clear();
    }

    @Test
    public void add_usersToStorage_ok() {
        user.setAge(18);
        user.setLogin("asdf");
        user.setPassword("123456");
        storageDao.add(user);
        assertEquals(user, storageDao.get("asdf"));
        user.setAge(17);
        user.setLogin("qwerty");
        user.setPassword("12345");
        storageDao.add(user);
        assertEquals(user, storageDao.get("qwerty"));
        user.setAge(28);
        user.setLogin("zxcvbn");
        user.setPassword("12234");
        storageDao.add(user);
        assertEquals(user, storageDao.get("zxcvbn"));
        user.setAge(34);
        user.setLogin("mkinuuhf");
        user.setPassword("123456");
        storageDao.add(user);
        assertEquals(user, storageDao.get("mkinuuhf"));
        assertEquals(4, Storage.people.size());
    }

    @Test
    public void add_null_nullException() {
        assertThrows(NullPointerException.class, () -> storageDao.add(null));
    }

    @Test
    public void add_sameUser_ok() {
        user.setAge(18);
        user.setLogin("asdf");
        user.setPassword("123456");
        storageDao.add(user);
        assertEquals(user, storageDao.get("asdf"));
        storageDao.add(user);
        assertEquals(2, Storage.people.size());
    }

    @Test
    public void get_wrongUser_nullException() {
        user.setAge(18);
        user.setLogin("asdf");
        user.setPassword("123456");
        storageDao.add(user);
        assertEquals(user, storageDao.get("asdf"));
        assertNull(storageDao.get("qwerty"));
    }
}
