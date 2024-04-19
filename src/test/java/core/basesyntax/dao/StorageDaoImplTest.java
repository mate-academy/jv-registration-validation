package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageDaoImplTest {
    private static StorageDao storageDao;
    private static final String CORRECT_LOGIN = "login555";
    private static final String CORRECT_PASSWORD = "password";
    private static final int CORRECT_AGE = 20;
    private User user;

    @BeforeAll
    static void beforeAll() {
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin(CORRECT_LOGIN);
        user.setPassword(CORRECT_PASSWORD);
        user.setAge(CORRECT_AGE);
    }

    @Test
    void testAddUser_Ok() {
        User addedUser = storageDao.add(user);
        assertEquals(2, addedUser.getId());
    }

    @Test
    void testGetUserByLogin_Ok() {
        storageDao.add(user);
        User addedUser = storageDao.get(CORRECT_LOGIN);
        assertEquals(user, addedUser);
    }

    @AfterEach
    void removeObject() {
        Storage.people.remove(user);
    }
}
