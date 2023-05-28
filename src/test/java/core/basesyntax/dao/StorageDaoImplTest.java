package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StorageDaoImplTest {
    StorageDao storageDao = new StorageDaoImpl();
    List<User> users;

    private List<User> getValidUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User("IsaacAsimov", "EternityEnd", 35));
        users.add(new User("RobertHeinlein", "SpaceTroopers", 52));
        users.add(new User("ArthurClarke", "SpaceOdyssey", 51));
        return users;
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
        String loginFirst = users.get(0).getLogin();
        String loginSecond = users.get(1).getLogin();
        String loginThird = users.get(2).getLogin();
        Storage.people.addAll(users);
        String message = "User with current login should be present";
        assertEquals(users.get(0), storageDao.get(loginFirst), message);
        assertEquals(users.get(1), storageDao.get(loginSecond), message);
        assertEquals(users.get(2), storageDao.get(loginThird), message);
    }

    @Test
    void getUserIfAbsent_NotOk() {
        String loginFirst = "StanislavLem";
        String loginSecond = "EdgarPoe";
        String loginThird = "GeorgeMartin";
        Storage.people.addAll(users);
        String message = "The null must be returned for absent user";
        assertNull(storageDao.get(loginFirst), message);
        assertNull(storageDao.get(loginSecond), message);
        assertNull(storageDao.get(loginThird), message);
    }
}
