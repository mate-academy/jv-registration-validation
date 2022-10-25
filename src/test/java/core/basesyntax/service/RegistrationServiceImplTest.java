package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static StorageDao storageDao;
    private static RegistrationService service;
    private User defaultUser;
    private User bohdan;
    private User dan;

    @BeforeAll
    static void beforeAll() {
        storageDao = new StorageDaoImpl();
        service = new RegistrationServiceImpl();
    }

    @BeforeEach
    void getService() {
        defaultUser = new User("artemk", "12345678", 18);
        bohdan = new User("Bohdan", "password", 58);
        dan = new User("Dan", "NothingPassword", 68);
        Storage.people.clear();
    }

    @Test
    void setNullUser_notOk() {
        assertThrows(RuntimeException.class, () -> {
            service.register(new User());
        });
    }

    @Test
    void setNullLoginUser_notOk() {
        defaultUser.setLogin(null);
        assertThrows(RuntimeException.class, () -> {
            service.register(defaultUser);
        });
    }

    @Test
    void setNullPasswordUser_notOk() {
        defaultUser.setPassword(null);
        assertThrows(RuntimeException.class, () -> {
            service.register(defaultUser);
        });
    }

    @Test
    void setNullAgeUser_notOk() {
        defaultUser.setAge(null);
        assertThrows(RuntimeException.class, () -> {
            service.register(defaultUser);
        });
    }

    @Test
    void setMinorUserAge_notOk() {
        defaultUser.setAge(17);
        assertThrows(RuntimeException.class, () -> {
            service.register(defaultUser);
        });
    }

    @Test
    void setNegativeAge_notOk() {
        defaultUser.setAge(-17);
        assertThrows(RuntimeException.class, () -> {
            service.register(defaultUser);
        });
    }

    @Test
    void setIllegalPasswordUser_notOk() {
        defaultUser.setPassword("12345");
        assertThrows(RuntimeException.class, () -> {
            service.register(defaultUser);
        });

    }

    @Test
    void registerWithValidParams_Ok() {
        service.register(bohdan);
        service.register(dan);
        assertEquals(bohdan, storageDao.get(bohdan.getLogin()));
        assertEquals(dan, storageDao.get(dan.getLogin()));
    }

    @Test
    void addUserWithSameLogin_notOk() {
        service.register(defaultUser);
        assertThrows(RuntimeException.class, () -> {
            service.register(defaultUser);
        });
    }
}
