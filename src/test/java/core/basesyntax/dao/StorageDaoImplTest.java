package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StorageDaoImplTest {
    private static final User validUser = new User("Mitresko", "P@ssw0rd", 30);

    @BeforeEach
    public void clearData() {
        Storage.people.clear();
    }

    @Test
    public void addAndGet_validUser_Ok() {
        User expected = validUser;
        StorageDao storageDao = new StorageDaoImpl();
        storageDao.add(validUser);
        assertEquals(1, Storage.people.size());

        User actual = storageDao.get(validUser.getLogin());
        assertEquals(expected, actual);
    }
}
