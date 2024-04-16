package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StorageDaoImplTest {
    private StorageDao storageDao;

    @BeforeEach
    public void setUp() {
        storageDao = new StorageDaoImpl();
    }

    @Test
    public void testAddUser_Ok() {
        User user = new User();
        user.setLogin("testUser");
        user.setPassword("password");
        user.setAge(20);

        User addedUser = storageDao.add(user);
        assertNotNull(addedUser.getId());
        assertEquals(user, addedUser);
    }

    @Test
    public void testGetUserByLogin_Ok() {
        User user = new User();
        user.setLogin("testUser");
        user.setPassword("password");
        user.setAge(20);

        storageDao.add(user);
        User retrievedUser = storageDao.get("testUser");
        assertEquals(user, retrievedUser);
    }
}
