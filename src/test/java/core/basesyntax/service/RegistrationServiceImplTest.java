package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static StorageDao storageDao;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin("ALice");
        user.setPassword("456lfkdgj");
        user.setAge(22);
    }

    @Test
    void register_emptyUser_NotOk() {
        Assertions.assertThrows(RuntimeException.class, () -> registrationService.register(null));
    }

    @Test
    void register_nullLogin_NotOk() {
        user.setLogin(null);
        Assertions.assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_emptyLogin_NotOk() {
        user.setLogin("");
        Assertions.assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_existingUser_NotOk() {
        storageDao.get(user.getLogin());
        storageDao.add(user);
        User user2 = new User();
        user2.setLogin(user.getLogin());
        user2.setPassword("slkgnUR12");
        user2.setAge(20);
        Assertions.assertThrows(RuntimeException.class, () -> registrationService.register(user2));
    }

    @Test
    void register_LowAge_NotOk() {
        user.setAge(15);
        Assertions.assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullAge_NotOk() {
        user.setAge(null);
        Assertions.assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_negativeAge_NotOk() {
        user.setAge(-1);
        Assertions.assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_minAcceptableAge_Ok() {
        user.setAge(18);
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void register_shortPassword_NotOk() {
        user.setPassword("pass");
        Assertions.assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_NotOk() {
        user.setPassword(null);
        Assertions.assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_validUser_Ok() {
        assertEquals(user, registrationService.register(user));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
