package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static StorageDao storageDao;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void registerUser_WithLoginNull_NotOk() {
        user.setLogin(null);
        user.setPassword("valid123");
        user.setAge(20);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void registerUser_WithInvalidLoginLength_NotOk() {
        user.setLogin("Steve");
        user.setPassword("valid123");
        user.setAge(20);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void registerUser_WithPasswordNull_NotOk() {
        user.setLogin("Steve123");
        user.setPassword(null);
        user.setAge(20);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void registerUser_WithInvalidPasswordLength_NotOk() {
        user.setLogin("Steve123");
        user.setPassword("qwert");
        user.setAge(20);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void registerUser_WithAgeNull_NotOk() {
        user.setLogin("Steve123");
        user.setPassword("valid123");
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void registerUser_WithInvalidAge_NotOk() {
        user.setLogin("Steve123");
        user.setPassword("valid123");
        user.setAge(15);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void registerUser_WithAllInvalidFields_NotOk() {
        user.setLogin("Steve");
        user.setPassword("qwert");
        user.setAge(15);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void registerUser_WithAllValidFields_Ok() {
        user.setLogin("Steve123");
        user.setPassword("valid123");
        user.setAge(20);
        registrationService.register(user);
        assertEquals(user, storageDao.get("Steve123"));
    }

    @Test
    void getUser_ByLogin_Ok() {
        user.setLogin("Steve123");
        user.setPassword("valid123");
        user.setAge(20);
        registrationService.register(user);
        User getUser = storageDao.get(user.getLogin());
        assertEquals(user, getUser);
    }

    @Test
    void getUser_ByNotExistLogin_NotOk() {
        user.setLogin("Steve123");
        user.setPassword("valid123");
        user.setAge(20);
        registrationService.register(user);
        User getUser = storageDao.get("Steve1234");
        assertNotEquals(user, getUser);
    }

    @Test
    void registerUser_WithExistLogin_NotOk() {
        user.setLogin("Steve123");
        user.setPassword("valid123");
        user.setAge(20);
        registrationService.register(user);
        User userToAdd = new User();
        userToAdd.setLogin("Steve123");
        userToAdd.setPassword("valid123");
        userToAdd.setAge(25);
        User getUser = storageDao.get(user.getLogin());
        assertNotEquals(userToAdd, getUser);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userToAdd);
        });
    }
}
