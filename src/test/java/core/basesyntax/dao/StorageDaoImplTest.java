package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageDaoImplTest {
    private StorageDao storageDao;

    @BeforeEach
    void setUp() {
        storageDao = new StorageDaoImpl();
        storageDao.clear();
    }

    @Test
    void shouldAddUserToStorage() {

        User user = new User();
        user.setLogin("correct_Login");
        user.setPassword("correct_Password");
        user.setAge(25);

        User addedUser = storageDao.add(user);

        assertEquals(user, addedUser);
        assertNotNull(addedUser.getId());
    }

    @Test
    void shouldGetUserByLogin() {
        User user = new User();
        user.setLogin("correct_Login");
        user.setPassword("correct_Password");
        user.setAge(25);

        storageDao.add(user);

        User getUser = storageDao.get("correct_Login");
        assertEquals(user, getUser);
    }

    @Test
    void shouldReturnNullForNonexistentUser() {
        User getUser = storageDao.get("nonexistent_Login");
        assertNull(getUser);
    }
}
