package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageDaoImplTest {
    private static final String TEST_LOGIN_USER = "testLogin";
    private static final String TEST_WRONG_LOGIN_USER = "testLoginWrong";
    private static final String TEST_PASSWORD_USER = "password";
    private static final int TEST_AGE_USER = 19;
    private static StorageDao storageDao;
    private User testUser;

    @BeforeAll
    static void beforeAll() {
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setLogin(TEST_LOGIN_USER);
        testUser.setPassword(TEST_PASSWORD_USER);
        testUser.setAge(TEST_AGE_USER);
    }

    @Test
    void add_user_ok() {
        User actual = storageDao.add(testUser);
        assertNotNull(actual.getId(),
                "Test failed to check user.id, expected not null, but actual: " + actual.getId());
        assertEquals(testUser, actual,
                "Test failed to compare actual user data with expected user data");
    }

    @Test
    void add_nullUser_notOk() {
        assertThrows(NullPointerException.class, () -> {
            storageDao.add(null);
        });
    }

    @Test
    void get_correctLogin_ok() {
        Storage.people.add(testUser);
        User actual = storageDao.get(TEST_LOGIN_USER);
        assertEquals(testUser, actual, "Test failed for login: " + "'" + TEST_LOGIN_USER + "'");
    }

    @Test
    void get_wrongLogin_ok() {
        Storage.people.add(testUser);
        User actual = storageDao.get(TEST_WRONG_LOGIN_USER);
        assertNull(actual, "Test failed expected null");
    }

    @Test
    void get_nullLogin_notOk() {
        Storage.people.add(testUser);
        User actual = storageDao.get(null);
        assertNull(actual, "Test failed expected null");
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
