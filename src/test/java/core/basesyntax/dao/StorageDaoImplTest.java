package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.Constant;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageDaoImplTest {
    private StorageDao storage = new StorageDaoImpl();
    private User validUser = new User();
    private User validUser2 = new User();

    @BeforeEach
    public void each() {
        storage.clear();

        validUser.setId(null);
        validUser.setLogin(Constant.VALID_LOGIN);
        validUser.setAge(Constant.VALID_AGE);
        validUser.setPassword(Constant.VALID_PASSWORD);

        validUser2.setId(null);
        validUser2.setLogin(Constant.VALID_LOGIN2);
        validUser2.setAge(Constant.VALID_AGE2);
        validUser2.setPassword(Constant.VALID_PASSWORD2);
    }

    @Test
    void add_returnUser_Ok() {
        assertEquals(validUser, storage.add(validUser));
        assertEquals(validUser2, storage.add(validUser2));
    }

    @Test
    void add_setId_Ok() {
        storage.add(validUser);
        assertEquals(1, validUser.getId());
        storage.add(validUser2);
        assertEquals(2, validUser2.getId());
    }

    @Test
    void add_nullUser_notOk() {
        assertThrows(NullPointerException.class, () -> storage.add(null));
    }

    @Test
    void get_existUser_Ok() {
        storage.add(validUser);
        storage.add(validUser2);
        assertEquals(validUser, storage.get(validUser.getLogin()));
        assertEquals(validUser2, storage.get(validUser2.getLogin()));
    }

    @Test
    void get_nonExistUser_null() {
        assertNull(storage.get(validUser.getLogin()));
        storage.add(validUser);
        assertNull(storage.get(validUser2.getLogin()));
    }
}
