package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService registrationService = new RegistrationServiceImpl();
    private StorageDao storageDao = new StorageDaoImpl();
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin("uniqueLogin");
        user.setPassword("1234567");
        user.setAge(22);
    }

    @Test
    void register_uniqueLogin_Ok() {
        User actual = registrationService.register(user);
        assertEquals(actual.getLogin(), user.getLogin());
    }

    @Test
    void register_notUniqueLogin_NotOk() {
        User userInStorage = new User();
        userInStorage.setLogin("uniqueLogin");
        storageDao.add(userInStorage);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullLogin_NotOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_correctPassword_Ok() {
        User actual = registrationService.register(user);
        assertEquals(actual.getPassword(), user.getPassword());
    }

    @Test
    void register_passwordLessThanSixCharacters_NotOk() {
        user.setPassword("abc");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullPassword_NotOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_correctAge_Ok() {
        User actual = registrationService.register(user);
        assertEquals(actual.getAge(), user.getAge());
    }

    @Test
    void register_ageUnderEighteen_NotOk() {
        user.setAge(15);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullAge_NotOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
