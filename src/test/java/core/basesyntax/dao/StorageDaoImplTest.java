package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import core.basesyntax.model.User;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class StorageDaoImplTest {
    private static final String VALID_LOGIN = "test_login_created_at " + LocalDateTime.now();
    private static final String VALID_PASSWORD = "Mine!$VAl1DP@ZSW0Rd!)(*&^%$#@";
    private static final int VALID_AGE = 20;

    private StorageDaoImpl storageDao = new StorageDaoImpl();

    @Test
    public void newUser_indexChanged_OK() {
        User user = new User(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);
        User addedUser = storageDao.add(user);
        User user2 = new User(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);
        User addedUser2 = storageDao.add(user2);
        assertNotEquals(addedUser.getId(), addedUser2.getId());
    }
}
