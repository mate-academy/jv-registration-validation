package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserTest {
    private RegistrationServiceImpl registration;
    private User user;
    private StorageDao storageDaoImpl;

    @BeforeEach
    void setUp() {
        registration = new RegistrationServiceImpl();
        storageDaoImpl = new StorageDaoImpl();
        user = new User();
        user.setLogin("Ivan Zhuravlev");
        user.setAge(18);
        user.setPassword("my password 123");
    }

    @Test
    void register_Ok() {
        User actual = registration.register(user);
        assertEquals(user, actual);
    }

    @Test
    void addedToStorage_Ok() {
        registration.register(user);
        assertEquals(user, storageDaoImpl.get("Ivan Zhuravlev"));
    }

    @Test
    void objectUserIsNull_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            registration.register(null);
        });
    }

    @Test
    void loginIsNull_NotOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> {
            registration.register(user);
        });
    }

    @Test
    void passwordIsNull_NotOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> {
            registration.register(user);
        });
    }

    @Test
    void ageIsNull_NotOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> {
            registration.register(user);
        });
    }

    @Test
    void notValidAge_NotOk() {
        user.setAge(-10);
        User actual = registration.register(user);
        assertNull(actual);
    }

    @Test
    void littleAge_NotOk() {
        user.setAge(17);
        User actual = registration.register(user);
        assertNull(actual);
    }

    @Test
    void smallPassword_NotOk() {
        user.setPassword("12345");
        User actual = registration.register(user);
        assertNull(actual);
    }
}

