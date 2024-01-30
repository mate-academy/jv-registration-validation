package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService registrationService;
    private Storage storage;
    private StorageDao storageDao;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin("Panama");
        user.setAge(24);
        user.setPassword("hsjkhFD12h");
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_uniqueUsername_ok() {
        User actual = registrationService.register(user);
        assertEquals("Panama", actual.getLogin());
    }

    @Test
    void register_theSameLoginInTheStorage_notOk() {
        storageDao.add(user);
        User user1 = new User();
        user1.setLogin("Panama");
        user1.setPassword("1234567");
        user1.setAge(28);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user1);
        });
    }

    @Test
    void register_loginTooShort_notOk() {
        user.setLogin("");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
        user.setLogin("Bob");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
        user.setLogin("Bobun");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_loginLength_ok() {
        user.setLogin("Boboro");
        User actual = registrationService.register(user);
        assertEquals("Boboro", actual.getLogin());
        storage.people.clear();
        user.setLogin("Boboroda");
        User actual1 = registrationService.register(user);
        assertEquals("Boboroda", actual1.getLogin());
    }

    @Test
    void register_passwordFiveLength_notOk() {
        user.setPassword("12345");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_underage_notOk() {
        user.setAge(0);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
        user.setAge(9);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
        user.setAge(17);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_adult_ok() {
        user.setLogin("abcdef");
        user.setAge(100);
        User actual = registrationService.register(user);
        assertEquals(100, actual.getAge());
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }
}
