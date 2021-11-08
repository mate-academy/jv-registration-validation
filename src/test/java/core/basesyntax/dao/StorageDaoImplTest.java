package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StorageDaoImplTest {
    private static StorageDao storageDao;
    User user1;
    User user2;

    @BeforeEach
    void setUp() {
        storageDao = new StorageDaoImpl();
        Storage.people.clear();
        user1 = new User();
        user2 = new User();
        user1.setAge(23);
        user1.setLogin("mylogin");
        user1.setPassword("SuperPassword666");
        user2.setAge(40);
        user2.setLogin("Vasya81");
        user2.setPassword("Password81");
    }

    @Test
    void LoginAlreadyExists_notOk() {
        user1.setLogin("Vasya81");
        storageDao.add(user1);
        try {
            assertNotEquals(user2, storageDao.add(user2));
        } catch (AssertionError e) {
            throw new RuntimeException("Login already exists", e);
        }
    }

    @Test
    void loginIsNull_notOk() {
        user1.setLogin(null);
        try {
            assertNotEquals(user1, storageDao.add(user1));
        } catch (AssertionError e) {
            throw new RuntimeException("Login is null", e);
        }
    }

    @Test
    void loginNotOnlyLettersNumbers_notOk() {
        user1.setLogin("log!@#$%^&*()<>?in");
        storageDao.add(user1);
        try {
            assertNull(storageDao.get("log!@#$%^&*()<>?in"));
        } catch (AssertionError e) {
            throw new RuntimeException("Login should contain letters and numbers only", e);
        }
    }

    @Test
    void ageIsLegal_Ok() {
        storageDao.add(user1);
        assertEquals(user1, storageDao.get("mylogin"));
    }

    @Test
    void ageIsIllegal_notOk() {
        user1.setAge(15);
        storageDao.add(user1);
        try {
            assertNull(storageDao.get("mylogin"));
        } catch (AssertionError e) {
            throw new RuntimeException("Age is less than allowed", e);
        }
    }

    @Test
    void ageIsFantastic_notOk() {
        user1.setAge(120);
        storageDao.add(user1);
        try {
            assertNull(storageDao.get("mylogin"));
        } catch (AssertionError e) {
            throw new RuntimeException("Age is higher than allowed", e);
        }
    }

    @Test
    void ageIsNegative_notOk() {
        user1.setAge(-3);
        storageDao.add(user1);
        try {
            assertNull(storageDao.get("mylogin"));
        } catch (AssertionError e) {
            throw new RuntimeException("Age is negative", e);
        }
    }

    @Test
    void ageIsNull_notOk() {
        user1.setAge(null);
        storageDao.add(user1);
        try {
            assertNull(storageDao.get("mylogin"));
        } catch (AssertionError e) {
            throw new RuntimeException("Age is null", e);
        }
    }

    @Test
    void passwordIsShorterThan6_notOk() {
        user1.setPassword("Pass1");
        storageDao.add(user1);
        try {
            assertNull(storageDao.get("mylogin"));
        } catch (AssertionError e) {
            throw new RuntimeException("Password should be not less than 6 symbols", e);
        }
    }

    @Test
    void passwordIsNotShorterThan6_Ok() {
        storageDao.add(user1);
        assertEquals(user1, storageDao.get("mylogin"));
    }

    @Test
    void passwordNotOnlyLettersDigitsCaps_notOk() {
        user1.setPassword("Pass10!@#$%^&*()><?");
        user2.setPassword("+=_:;dek83F");
        storageDao.add(user1);
        storageDao.add(user2);
        try {
            assertTrue(storageDao.get("mylogin") == null
                    && storageDao.get("Vasya81") == null);
        } catch (AssertionError e) {
            throw new RuntimeException("Password should contain letters and numbers only", e);
        }
    }

    @Test
    void passwordNoCapsAndNumbers_notOk() {
        user1.setPassword("password1");
        user2.setPassword("PassWord");
        storageDao.add(user1);
        storageDao.add(user2);
        try {
            assertTrue(storageDao.get("mylogin") == null
                    && storageDao.get("Vasya81") == null);
        } catch (AssertionError e) {
            throw new RuntimeException("Password should contain caps and numbers", e);
        }
    }

    @Test
    void PasswordIsNullNotOk() {
        user1.setPassword(null);
        storageDao.add(user1);
        try {
            assertNull(storageDao.get("mylogin"));
        } catch (AssertionError e) {
            throw new RuntimeException("Password is null", e);
        }
    }

    @Test
    void allFieldsAreOk() {
        storageDao.add(user1);
        storageDao.add(user2);
        assertTrue(user1.equals(storageDao.get("mylogin"))
                && user2.equals(storageDao.get("Vasya81")));
    }
}