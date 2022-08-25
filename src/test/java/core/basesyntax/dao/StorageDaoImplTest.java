package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageDaoImplTest {
    private StorageDao storageDao = new StorageDaoImpl();
    private User defaultUser = new User();
    private final int age = 18;
    private final String login = "Ivanov";
    private final String password = "Hello_Guys";

    @BeforeEach
    void setUp() {
        defaultUser.setAge(age);
        defaultUser.setLogin(login);
        defaultUser.setPassword(password);
    }

    @Test
     void add_nullUser_NotOk() {
        assertThrows(RuntimeException.class, () -> storageDao.add(null),
                "User can not be null");
    }

    @Test
    void add_increaseID_Ok() {
        assertNotEquals(defaultUser.getId(), storageDao.add(defaultUser).getId(),"After add user "
                + "id should be increase from 0 to 1");
    }

    @Test
    void add_increaseCapacityStorage_Ok() {
        int expected = Storage.people.size();
        int countUsers = 19;
        User[] users = new User[countUsers];
        for (int i = 0; i < users.length; i++) {
            users[i] = new User();
            users[i].setLogin("User's_login_" + i);
            users[i].setAge(32);
            users[i].setPassword("User's_password_" + i);
        }
        for (User user: users) {
            assertNotEquals(user.getId(), storageDao.add(user).getId(),"User with ID "
                    + user.getId() + "don't add to the Storage");
        }
        int actual = Storage.people.size();
        assertNotEquals(expected,actual);
    }

    @Test
    void get_nullLogin_NotOk() {
        assertThrows(RuntimeException.class, () -> storageDao.get(null),
                "Login can not be null");
    }

    @Test
    void get_twentyUsers_Ok() {
        int countUsers = 19;
        User[] users = new User[countUsers];
        for (int i = 0; i < users.length; i++) {
            users[i] = new User();
            users[i].setLogin("User's_login_" + i);
            users[i].setAge(32);
            users[i].setPassword("User's_password_" + i);
        }
        for (User user: users) {
            storageDao.add(user);
            String expected = user.getLogin();
            String actual = storageDao.get(expected).getLogin();
            assertEquals(expected,actual,"Didn't find User's login");
        }
    }

    @Test
    void get_notFindUser_Ok() {
        String newLogin = "BestPractice";
        defaultUser.setLogin(newLogin);
        storageDao.add(defaultUser);
        User expected = storageDao.get(login);
        assertNull(expected,"If can't find user - return null");
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
