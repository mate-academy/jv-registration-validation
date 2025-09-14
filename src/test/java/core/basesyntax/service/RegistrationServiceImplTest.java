package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationServiceImpl service;
    private StorageDao storageDao;
    private User userExpected;
    private User duplicate;

    @BeforeEach
    void setUp() {
        storageDao = new StorageDaoImpl();
        service = new RegistrationServiceImpl();
        userExpected = new User();

        userExpected.setLogin("vlad1996@gmail.com");
        userExpected.setPassword("gggttt");
        userExpected.setAge(25);

        duplicate = new User();

        duplicate.setLogin("vlad19@gmail.com");
        duplicate.setPassword("gggttt");
        duplicate.setAge(25);

        Storage.people.clear();
    }

    @Test
    void register_validUser_ok() {
        User validUser = new User();
        validUser.setLogin("vlad1996@gmail.com");
        validUser.setPassword("securePass");
        validUser.setAge(25);

        User actual = service.register(validUser);

        assertEquals(validUser, actual);

        assertEquals(validUser, storageDao.get("vlad1996@gmail.com"));
    }

    @Test
    void register_isInList_notOk() {
        storageDao.add(userExpected);

        duplicate.setLogin("vlad1996@gmail.com");

        assertThrows(RegistrationException.class, () -> {
            service.register(duplicate);
        });
    }

    @Test
    void register_nullLogin_notOk() {
        userExpected.setLogin(null);
        storageDao.add(userExpected);

        duplicate.setLogin(null);

        assertThrows(RegistrationException.class, () -> {
            service.register(duplicate);
        });
    }

    @Test
    void register_loginIsZero_notOk() {
        userExpected.setLogin("");
        storageDao.add(userExpected);

        duplicate.setLogin("");

        assertThrows(RegistrationException.class, () -> {
            service.register(duplicate);
        });
    }

    @Test
    void register_loginIsThree_notOk() {
        userExpected.setLogin("gta");
        storageDao.add(userExpected);

        duplicate.setLogin("gta");

        assertThrows(RegistrationException.class, () -> {
            service.register(duplicate);
        });
    }

    @Test
    void register_loginIsFive_notOk() {
        userExpected.setLogin("gtaow");
        storageDao.add(userExpected);

        duplicate.setLogin("gtaow");

        assertThrows(RegistrationException.class, () -> {
            service.register(duplicate);
        });
    }

    @Test
    void register_loginIsSix_ok() {
        userExpected.setLogin("gtaow@gmail.com");
        storageDao.add(userExpected);

        duplicate.setLogin("gtaow@gmail.com");

        assertEquals(userExpected, duplicate);
    }

    @Test
    void register_nullPassword_notOk() {
        userExpected.setPassword(null);
        storageDao.add(userExpected);

        duplicate.setPassword(null);

        assertThrows(RegistrationException.class, () -> {
            service.register(duplicate);
        });
    }

    @Test
    void register_passwordIsZero_notOk() {
        userExpected.setPassword("");
        storageDao.add(userExpected);

        duplicate.setPassword("");

        assertThrows(RegistrationException.class, () -> {
            service.register(duplicate);
        });
    }

    @Test
    void register_passwordIsThree_notOk() {
        userExpected.setPassword("123");
        storageDao.add(userExpected);

        duplicate.setPassword("123");

        assertThrows(RegistrationException.class, () -> {
            service.register(duplicate);
        });
    }

    @Test
    void register_passwordIsFive_notOk() {
        userExpected.setPassword("12345");
        storageDao.add(userExpected);

        duplicate.setPassword("12345");

        assertThrows(RegistrationException.class, () -> {
            service.register(duplicate);
        });
    }


    @Test
    void register_nullAge_notOk() {
        userExpected.setAge(null);
        storageDao.add(userExpected);

        duplicate.setAge(null);

        assertThrows(RegistrationException.class, () -> {
            service.register(duplicate);
        });
    }

    @Test
    void register_ageLessThenEighteen_notOk() {
        userExpected.setAge(17);
        storageDao.add(userExpected);

        duplicate.setAge(17);

        assertThrows(RegistrationException.class, () -> {
            service.register(duplicate);
        });
    }
}
