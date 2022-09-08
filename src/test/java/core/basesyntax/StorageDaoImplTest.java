package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StorageDaoImplTest {

    private final StorageDao storageDao = new StorageDaoImpl();
    private final User testUser = new User();
    private final Long testId = 1L;
    private final Integer testAge = 20;
    private final String testLogin = "testLogin";
    private final String testPassword = "testPassword";

    @BeforeEach
    public void initializeUser() {
        testUser.setId(testId);
        testUser.setAge(testAge);
        testUser.setLogin(testLogin);
        testUser.setPassword(testPassword);
    }

    @Test
    public void testGetFromEmptyListReturnsNull() {
        User user = storageDao.get(testLogin);
        assertNull(user);
    }

    @Test
    public void testAddAndReturnUserIsOk() {
        storageDao.add(testUser);
        assertEquals(storageDao.get(testLogin), testUser);
    }
}
