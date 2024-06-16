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
    void register_nullLogin_NotOk() {
        user.setLogin(null);
        user.setPassword("valid123");
        user.setAge(20);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_InvalidLoginLength_NotOk() {
        user.setLogin("Steve");
        user.setPassword("valid123");
        user.setAge(20);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullPassword_NotOk() {
        user.setLogin("Steve123");
        user.setPassword(null);
        user.setAge(20);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_InvalidPasswordLength_NotOk() {
        user.setLogin("Steve123");
        user.setPassword("qwert");
        user.setAge(20);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullAge_NotOk() {
        user.setLogin("Steve123");
        user.setPassword("valid123");
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_InvalidAge_NotOk() {
        user.setLogin("Steve123");
        user.setPassword("valid123");
        user.setAge(15);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_NegativeAge_NotOk() {
        user.setLogin("Steve123");
        user.setPassword("valid123");
        user.setAge(-1);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_InvalidUser_NotOk() {
        user.setLogin("Steve");
        user.setPassword("qwert");
        user.setAge(15);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_ValidUser_Ok() {
        user.setLogin("Steve123");
        user.setPassword("valid123");
        user.setAge(20);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void registerUser_WithExistLogin_NotOk() {
        user.setLogin("Steve123");
        user.setPassword("valid123");
        user.setAge(20);
        storageDao.add(user);
        User newUser = new User();
        newUser.setLogin("Steve123");
        newUser.setPassword("valid123");
        newUser.setAge(25);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(newUser);
        });
    }
}
