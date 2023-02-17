package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageDaoImplTest {
    private static final String DEFAULT_LOGIN = "login";
    private static final String DEFAULT_PASSWORD = "password123$*#";
    private static final String VALID_PASSWORD = "abc123$*#";
    private static final int DEFAULT_VALID_AGE = 18;
    private static final int DEFAULT_ADULT_AGE = 35;
    private static final int DEFAULT_ELDERLY_AGE = 70;
    private static StorageDaoImpl storageDao;
    private static User user;
    private int expectedSize;

    @BeforeAll
    static void beforeAll() {
        storageDao = new StorageDaoImpl();
        user = new User();
    }

    @BeforeEach
    void setUp() {
        expectedSize = 0;
        user = user.createUser(750_001L, DEFAULT_LOGIN, DEFAULT_PASSWORD, DEFAULT_ADULT_AGE);
    }

    @Test
    void addingAndGetting_MultipleValidUsers_OK() {
        User[] users = new User[3];
        users[0] = user.createUser(750_001L, "User1", DEFAULT_PASSWORD, DEFAULT_ADULT_AGE);
        users[1] = user.createUser(750_002L, "User2", VALID_PASSWORD, DEFAULT_ELDERLY_AGE);
        users[2] = user.createUser(750_003L, "User3", DEFAULT_PASSWORD, DEFAULT_VALID_AGE);

        for (User user : users) {
            long oldId = user.getId();
            User actual = storageDao.add(user);
            assertNotNull(actual, "Returned User cannot be null");
            assertEquals(actual, user, "Registration method "
                    + "must return registered User");
            assertNotEquals(oldId, user.getId(), "User Id must changed due to registration");
            expectedSize++;
        }

        for (User user : users) {
            assertEquals(storageDao.get(user.getLogin()), user,
                    "Get method returned not expected user by login");
        }
        assertEquals(expectedSize, Storage.people.size(),
                "Storage size not changed properly");
    }

    @Test
    void getting_NotExistingUser_notOK() {
        long oldId = user.getId();
        User actual = storageDao.add(user);
        assertNotNull(actual, "Returned User cannot be null");
        assertEquals(actual, user, "Registration method "
                + "must return registered User");
        assertNotEquals(oldId, user.getId(), "User Id method "
                + "should be changed due to registration");
        expectedSize++;

        assertNull(storageDao.get("NotExistentLogin"),
                "Get method not returned null for not existing login");
        assertEquals(expectedSize, Storage.people.size(),
                "Storage size not changed properly");
    }

    @Test
    void getting_NullLoginUser_notOK() {
        long oldId = user.getId();
        User actual = storageDao.add(user);
        assertNotNull(actual, "Returned User cannot be null");
        assertEquals(actual, user, "Registration method "
                + "must return registered User");
        assertNotEquals(oldId, user.getId(), "User Id method "
                + "should be changed due to registration");
        expectedSize++;

        assertNull(storageDao.get(null),
                "Get method not returned null for not existing login");
        assertEquals(expectedSize, Storage.people.size(),
                "Storage size not changed properly");
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
