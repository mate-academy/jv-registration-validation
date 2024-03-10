package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.User;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class StorageDaoImplTest {
    private static final String VALID_LOGIN = "test_login_created_at " + LocalDateTime.now();
    private static final String VALID_PASSWORD = "Mine!$VAl1DP@ZSW0Rd!)(*&^%$#@";
    private static final int VALID_AGE = 20;

    private static StorageDao storageDao;

    @BeforeAll
    static void beforeAll() {
        storageDao = new StorageDaoImpl();
    }

    @Test
    public void register_newUser_indexChanged_OK() {
        User user = new User(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);
        User addedUser = storageDao.add(user);
        User user2 = new User(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);
        User addedUser2 = storageDao.add(user2);
        assertNotEquals(addedUser.getId(), addedUser2.getId(),"New User should have new id");
    }

    @Test
    public void search_UserIndexBigerThanZero_OK() {
        User user = new User(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);
        User addedUser = storageDao.add(user);
        boolean actual = addedUser.getId() > 0;
        assertTrue(actual, "User id cannot be lower than 0");
    }

    @Test
    public void search_UserLoginSavedCorrectly_Ok() {
        User user = new User(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);
        storageDao.add(user);
        String login = storageDao.get(VALID_LOGIN).getLogin();
        assertEquals(user.getLogin(), login, "Login of saved user must "
                + "be same as login used for registration");
    }
}
