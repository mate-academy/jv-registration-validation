package core.basesyntax.dao;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class StorageDaoImplTest {
    private static StorageDao storageDao;
    private final String ACTUAL_LOGIN = "userLogin";
    private User user;

    @BeforeAll
    static void beforeAll() {
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin("userLogin");
        user.setPassword("strongPassword");
        user.setAge(20);
    }

    @Test
    void testAddUser_Ok() {
        User addedUser = storageDao.add(user);
        assertNotNull(addedUser.getId());
        assertEquals(user, addedUser);
    }

    @Test
    void testGetUserByLogin_Ok() {
        storageDao.add(user);
        User addedUser = storageDao.get(ACTUAL_LOGIN);
        assertEquals(user, addedUser);
    }
}
