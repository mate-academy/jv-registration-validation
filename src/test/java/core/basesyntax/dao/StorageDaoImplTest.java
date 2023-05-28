package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageDaoImplTest {
    private StorageDao storageDao = new StorageDaoImpl();
    private List<User> users;

    private List<User> getValidUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User("IsaacAsimov", "EternityEnd", 35));
        users.add(new User("RobertHeinlein", "SpaceTroopers", 52));
        users.add(new User("ArthurClarke", "SpaceOdyssey", 51));
        return users;
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @BeforeEach
    void setUp() {
        users = getValidUsers();
    }

    @Test
    void isAdded_Ok() {
        assertEquals(users.get(0), storageDao.add(users.get(0)));
        assertEquals(1, Storage.people.size());
        assertEquals(users.get(1), storageDao.add(users.get(1)));
        assertEquals(2, Storage.people.size());
        assertEquals(users.get(2), storageDao.add(users.get(2)));
        assertEquals(3, Storage.people.size());
    }

    @Test
    void validGetUserIfPresent_Ok() {
        Storage.people.addAll(users);
        String message = "User with current login should be present";
        String loginFirst = users.get(0).getLogin();
        String loginSecond = users.get(1).getLogin();
        String loginThird = users.get(2).getLogin();
        assertEquals(users.get(0), storageDao.get(loginFirst), message);
        assertEquals(users.get(1), storageDao.get(loginSecond), message);
        assertEquals(users.get(2), storageDao.get(loginThird), message);
    }

    @Test
    void getUserIfAbsent_NotOk() {
        Storage.people.addAll(users);
        String message = "The null must be returned for absent user";
        String loginFirst = "StanislavLem";
        String loginSecond = "EdgarPoe";
        String loginThird = "GeorgeMartin";
        assertNull(storageDao.get(loginFirst), message);
        assertNull(storageDao.get(loginSecond), message);
        assertNull(storageDao.get(loginThird), message);
    }
}
