package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class StorageDaoTest {
    private static final String DEFAULT_LOGIN = "vitalii@gmail.com";
    private static final String DEFAULT_PASSWORD = "012345";
    private static final int DEFAULT_AGE = 30;
    private static User user;

    @BeforeAll
    static void beforeAll() {
        user = new User(DEFAULT_LOGIN, DEFAULT_PASSWORD, DEFAULT_AGE);
    }

    @Test
    void storageDao_validBehavior_Ok() {
        StorageDao storageDao = new StorageDaoImpl();
        String invalidLogin = "invalid login";
        assertNull(storageDao.get(invalidLogin), "");
        storageDao.add(user);
        String expectedLogin = DEFAULT_LOGIN;
        String actualLogin = storageDao.get(user.getLogin()).getLogin();
        assertEquals(expectedLogin, actualLogin,
                "Expected login: " + expectedLogin + " | Actual login" + actualLogin);
        long expectedUserId = 1L;
        long actualUserId = user.getId();
        assertEquals(expectedLogin, actualLogin,
                "Expected id: " + expectedUserId + " | Actual id" + actualUserId);
    }
}
