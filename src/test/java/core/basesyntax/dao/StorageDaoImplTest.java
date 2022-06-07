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
    private static final int LOWEST_VALID_AGE = 18;
    private static final int ADULT_AGE = 32;
    private static final String LOWEST_VALID_PASS = "qw21re";
    private static User validUser1;
    private static User validUser2;
    private static User validUser3;
    private StorageDao storageDao;
    private int expectingSize;

    @BeforeAll
    static void beforeAll() {
        validUser1 = new User();
        validUser2 = new User();
        validUser3 = new User();
    }

    @BeforeEach
    void setUp() {
        storageDao = new StorageDaoImpl();

        validUser1.setId(101L);
        validUser1.setLogin("ValidUser1");
        validUser1.setPassword("q1w2e3r4");
        validUser1.setAge(ADULT_AGE);

        validUser2.setId(102L);
        validUser2.setLogin("ValidUser2");
        validUser2.setPassword(LOWEST_VALID_PASS);
        validUser2.setAge(ADULT_AGE + ADULT_AGE);

        validUser3.setId(103L);
        validUser3.setLogin("ValidUser3");
        validUser3.setPassword("q1w2e3r4");
        validUser3.setAge(LOWEST_VALID_AGE);

        expectingSize = 0;
    }

    @Test
    void addAndGet_normalUser_Ok() {
        User actual;
        long oldId;
        oldId = validUser1.getId();
        actual = storageDao.add(validUser1);
        assertNotNull(actual, "Returned Object must be not null");
        assertEquals(actual, validUser1, "Registration method should return registered "
                + "User Object");
        assertNotEquals(oldId, validUser1.getId(), "User Id must should be changed "
                + "due to registration");
        expectingSize++;

        oldId = validUser2.getId();
        actual = storageDao.add(validUser2);
        assertNotNull(actual, "Returned Object must be not null");
        assertEquals(actual, validUser2, "Registration method should return registered "
                + "User Object");
        assertNotEquals(oldId, validUser2.getId(), "User Id must should be changed "
                + "due to registration");
        expectingSize++;

        oldId = validUser3.getId();
        actual = storageDao.add(validUser3);
        assertNotNull(actual, "Returned Object must be not null");
        assertEquals(actual, validUser3, "Registration method should return registered "
                + "User Object");
        assertNotEquals(oldId, validUser3.getId(), "User Id must should be changed "
                + "due to registration");
        expectingSize++;

        assertEquals(storageDao.get(validUser1.getLogin()), validUser1,
                "Get method returned not expected user by login");
        assertEquals(storageDao.get(validUser2.getLogin()), validUser2,
                "Get method returned not expected user by login");
        assertEquals(storageDao.get(validUser3.getLogin()), validUser3,
                "Get method returned not expected user by login");
    }

    @Test
    void getNotExistingLogin_NotOk() {
        User actual;
        long oldId;
        oldId = validUser1.getId();
        actual = storageDao.add(validUser1);
        assertNotNull(actual, "Returned Object must be not null");
        assertEquals(actual, validUser1, "Registration method should return registered "
                + "User Object");
        assertNotEquals(oldId, validUser1.getId(), "User Id must should be changed "
                + "due to registration");
        expectingSize++;

        assertEquals(storageDao.get(validUser1.getLogin()), validUser1,
                "Get method returned not expected user by login");

        assertNull(storageDao.get(validUser2.getLogin()),
                "Get method not returned null for not existing login");

        assertNull(storageDao.get(null),
                "Get method not returned null for not null login");
    }

    @AfterEach
    void tearDown() {
        assertEquals(expectingSize, Storage.people.size(), "Storage size not changed properly");

        Storage.people.clear();
    }
}
