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
    private static StorageDao storageDao;
    private int expectingSize;

    private static User userConstructor(long id, String login, String password, int age) {
        User user = new User();
        user.setId(id);
        user.setLogin(login);
        user.setPassword(password);
        user.setAge(age);
        return user;
    }

    @BeforeEach
    void setUp() {
        storageDao = new StorageDaoImpl();
        expectingSize = 0;
    }

    @Test
    void addAndGet_validUser_Ok() {
        int lowestValidAge = 18;
        int adultAge = 32;
        String lowestValidPass = "123456";
        User[] users = new User[3];
        users[0] = userConstructor(101L, "FirstUser", "password", adultAge);
        users[1] = userConstructor(102L, "SecondUser", lowestValidPass, adultAge + adultAge);
        users[2] = userConstructor(103L, "ThirdUser", "password", lowestValidAge);

        for (User user : users) {
            long oldId = user.getId();
            User actual = storageDao.add(user);
            assertNotNull(actual, "Returned Object must be not null");
            assertEquals(actual, user, "Registration method should return registered "
                    + "User Object");
            assertNotEquals(oldId, user.getId(), "User Id must should be changed "
                    + "due to registration");
            expectingSize++;
        }

        for (User user : users) {
            assertEquals(storageDao.get(user.getLogin()), user,
                    "Get method returned not expected user by login");
        }

        assertEquals(expectingSize, Storage.people.size(), "Storage size not changed properly");
    }

 /*   @Test
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
*/
    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
