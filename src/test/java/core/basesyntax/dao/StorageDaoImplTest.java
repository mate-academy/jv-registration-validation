package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StorageDaoImplTest {
    private final StorageDao storageDao = new StorageDaoImpl();

    @BeforeEach
    void SetUp() {
        for (User user : Storage.people) {
            storageDao.add(new User(1L,"log000","ffffff",46));
        }
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void userAgeLow18_NotOk() {
        assertThrows(RuntimeException.class, () -> storageDao.add(new User(1L,"gfgf", "ffffff",1)));
    }

    @Test
    void suchLoginAlreadyExist_NotOk() {
        storageDao.add(new User(1L,"user1","ffffff",56));
        assertThrows(RuntimeException.class, () -> storageDao.add(new User(1L,"user1","ffffff",26)));
    }

    @Test
    void passwordLess6Charicters_NotOk() {
        assertThrows(RuntimeException.class, () -> storageDao.add(new User(1L,"user1","fff",26)));
    }
}
