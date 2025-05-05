package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageDaoImplTest {
    private static StorageDaoImpl storageDao;
    private int expectedSize;

    @BeforeEach
    void setUp() {
        storageDao = new StorageDaoImpl();
        expectedSize = 0;
    }

    @Test
    void addingAndGettingMultiple_validUser_Ok() {
        int lowestValidAge = 18;
        int adultAge = 32;
        String lowestValidPass = "123456";
        User[] users = new User[3];
        users[0] = createUser(101L, "FirstUser", "password", adultAge);
        users[1] = createUser(102L, "SecondUser", lowestValidPass, adultAge + adultAge);
        users[2] = createUser(103L, "ThirdUser", "password", lowestValidAge);

        for (User user : users) {
            long oldId = user.getId();
            User actual = storageDao.add(user);
            assertNotNull(actual, "Returned Object must be not null");
            assertEquals(actual, user, "Registration method should return registered "
                    + "User Object");
            assertNotEquals(oldId, user.getId(), "User Id must should be changed "
                    + "due to registration");
            expectedSize++;
        }

        for (User user : users) {
            assertEquals(storageDao.get(user.getLogin()), user,
                    "Get method returned not expected user by login");
        }
        assertEquals(expectedSize, Storage.people.size(), "Storage size not changed properly");
    }

    @Test
    void getNotExistentLogin_NotOk() {
        User user = createUser(101L, "FirstUser", "password", 32);
        long oldId = user.getId();
        User actual = storageDao.add(user);
        assertNotNull(actual, "Returned Object must be not null");
        assertEquals(actual, user, "Registration method should return registered "
                + "User Object");
        assertNotEquals(oldId, user.getId(), "User Id must should be changed "
                + "due to registration");
        expectedSize++;

        assertNull(storageDao.get("NotExistentLogin"),
                "Get method not returned null for not existing login");
        assertEquals(expectedSize, Storage.people.size(), "Storage size not changed properly");
    }

    @Test
    void getNullLogin_NotOk() {
        User user = createUser(101L, "FirstUser", "password", 32);
        long oldId = user.getId();
        User actual = storageDao.add(user);
        assertNotNull(actual, "Returned Object must be not null");
        assertEquals(actual, user, "Registration method should return registered "
                + "User Object");
        assertNotEquals(oldId, user.getId(), "User Id must should be changed "
                + "due to registration");
        expectedSize++;

        assertNull(storageDao.get(null),
                "Get method not returned null for not existing login");
        assertEquals(expectedSize, Storage.people.size(), "Storage size not changed properly");
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    private User createUser(long id, String login, String password, int age) {
        User user = new User();
        user.setId(id);
        user.setLogin(login);
        user.setPassword(password);
        user.setAge(age);
        return user;
    }
}
