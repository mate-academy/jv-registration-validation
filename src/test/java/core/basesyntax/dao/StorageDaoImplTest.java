package core.basesyntax.dao;

import static core.basesyntax.db.Storage.people;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class StorageDaoImplTest {

    private final int defaultAge = 18;
    private final String defaultLogin = "login";
    private final String defaultPass = "password";
    private final int arraySize = 5;
    private final StorageDao storageDao = new StorageDaoImpl();
    private final List<User> defaultUsers = new ArrayList<>();
    private User defaultUser;

    @BeforeEach
    void beforeAll() {
        for (int i = 0; i < arraySize; i++) {
            User user = new User();
            user.setLogin(defaultLogin + i);
            user.setAge(defaultAge);
            user.setPassword(defaultPass);
            defaultUsers.add(user);
        }
        defaultUser = new User();
        defaultUser.setLogin(defaultLogin);
        defaultUser.setAge(defaultAge);
        defaultUser.setPassword(defaultPass);
    }

    @Test
    void storage_getUsersByLogin_ok() {
        for (int i = 0; i < arraySize; i++) {
            people.add(defaultUsers.get(i));
        }
        for (int i = 0; i < arraySize; i++) {
            User actual = storageDao.get(defaultLogin + i);
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
        for (int i = 0; i < arraySize; i++) {
            assertEquals(defaultUsers.get(i), storageDao.get(defaultUsers.get(i).getLogin()));
        }
    }

    @AfterEach
    void afterAll() {
        people.clear();
    }
}
