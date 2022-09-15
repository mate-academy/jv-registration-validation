package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    private RegistrationService registrServ = new RegistrationServiceImpl();
    private User user;
    private StorageDao storageDao;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin("userLogin");
        user.setPassword("userPassword123");
        user.setAge(19);
        storageDao = new StorageDaoImpl();
    }

    @Test
    void register_UserIsNull_NotOk() {
        user = null;
        assertThrows(RuntimeException.class,
                () -> registrServ.register(user));
    }

    @Test
    void register_UserThatAlreadyExist_NotOk() {
        registrServ.register(user);
        assertThrows(RuntimeException.class, () -> registrServ.register(user));
    }

    @Test
    void register_UserLoginIsNull_NotOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrServ.register(user));
    }

    @Test
    void register_UserAge16_NotOk() {
        user.setAge(10);
        assertThrows(RuntimeException.class, () -> registrServ.register(user));
    }

    @Test
    void register_UserPasswordLength2_NotOk() {
        user.setPassword("12");
        assertThrows(RuntimeException.class, () -> registrServ.register(user));
    }

    @Test
    void register_User_Ok() {
        user.setLogin("newLogin");
        registrServ.register(user);
        assertEquals(user, storageDao.get(user.getLogin()));
    }
}
