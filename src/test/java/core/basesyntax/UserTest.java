package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserTest {
    private RegistrationServiceImpl register;
    private User user;
    private StorageDao storageDaoImpl;

    @BeforeEach
    void setUp() {
        register = new RegistrationServiceImpl();
        storageDaoImpl = new StorageDaoImpl();
        user = new User();
        user.setLogin("Ivan Zhuravlev");
        user.setAge(22);
        user.setPassword("my password 123");
    }

    @Test
    void register_Ok() {
        User actual = register.register(user);
        assertEquals(user, actual);
    }

    @Test
    void addedToStorage_Ok() {
        register.register(user);
        assertEquals(user, storageDaoImpl.get("Ivan Zhuravlev"));
    }

    @Test
    void objectUserIsNull_NotOk() {
        User actual = register.register(null);
        assertNull(actual);
    }

    @Test
    void loginIsNull_NotOk() {
        user.setLogin(null);
        User actual = register.register(user);
        assertNull(actual);
    }

    @Test
    void passwordIsNull_NotOk() {
        user.setPassword(null);
        User actual = register.register(user);
        assertNull(actual);
    }

    @Test
    void agedIsNull_NotOk() {
        user.setAge(null);
        User actual = register.register(user);
        assertNull(actual);
    }

    @Test
    void notValidAge_NotOk() {
        user.setAge(-10);
        User actual = register.register(user);
        assertNull(actual);
    }
    
    @Test
    void littleAge_NotOk() {
        user.setAge(17);
        User actual = register.register(user);
        assertNull(actual);
    }

    @Test
    void smallPassword_NotOk() {
        user.setPassword("12345");
        User actual = register.register(user);
        assertNull(actual);
    }
}

