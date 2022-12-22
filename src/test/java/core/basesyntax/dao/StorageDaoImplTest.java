package core.basesyntax.dao;

import core.basesyntax.model.User;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static core.basesyntax.db.Storage.people;
import static org.junit.jupiter.api.Assertions.*;

class StorageDaoImplTest {
    private final static int DEFAULT_AGE = 18;
    private final static String DEFAULT_LOGIN = "login";
    private final static String DEFAULT_PASS = "password";
    private final static int ARRAY_SIZE = 5;
    private final StorageDao storageDao = new StorageDaoImpl();
    private final List<User> defaultUsers = new ArrayList<>();
    User defaultUser;

    @BeforeEach
    void beforeAll() {
        for (int i = 0; i < ARRAY_SIZE; i++) {
            User user = new User();
            user.setLogin(DEFAULT_LOGIN + i);
            user.setAge(DEFAULT_AGE);
            user.setPassword(DEFAULT_PASS);
            defaultUsers.add(user);
        }
        defaultUser = new User();
        defaultUser.setLogin(DEFAULT_LOGIN);
        defaultUser.setAge(DEFAULT_AGE);
        defaultUser.setPassword(DEFAULT_PASS);
    }

    @Test
    void storage_getUsersByLogin_ok() {
        for (int i = 0; i < ARRAY_SIZE; i++) {
            people.add(defaultUsers.get(i));
        }
        for (int i = 0; i < ARRAY_SIZE; i++) {
            User actual = storageDao.get(DEFAULT_LOGIN + i);
            assertEquals(defaultUsers.get(i), actual);
        }
    }

    @Test
    void storage_indexCheck_ok() {
        storageDao.add(defaultUser);
        Long expected = defaultUser.getId();
        for (User user : defaultUsers) {
            storageDao.add(user);
        }
        for (User user : people) {
            Long actual = user.getId();
            assertEquals(expected, actual);
            expected++;
        }
    }

    @Test
    void storage_getByNullString() {
        assertThrows(NullPointerException.class, () ->
                storageDao.get(null));
    }

    @Test
    void storage_addUserWithNullAge_notOk() {
        defaultUser.setAge(null);
        assertThrows(NullPointerException.class, () ->
                storageDao.add(defaultUser));
    }

    @Test
    void storage_addUserWithNullPassword_notOk() {
        defaultUser.setPassword(null);
        assertThrows(NullPointerException.class, () ->
                storageDao.add(defaultUser));
    }

    @Test
    void storage_addUserWithNullLogin_notOk() {
        defaultUser.setLogin(null);
        assertThrows(NullPointerException.class, () ->
                storageDao.add(defaultUser));
    }

    @Test
    void storage_addArrayOfUsers_ok() {
        for (User user : defaultUsers) {
            storageDao.add(user);
        }
        for (int i = 0; i < ARRAY_SIZE; i++) {
            assertEquals(defaultUsers.get(i), storageDao.get(defaultUsers.get(i).getLogin()));
        }
    }

    @AfterEach
    void afterAll() {
        people.clear();
    }
}