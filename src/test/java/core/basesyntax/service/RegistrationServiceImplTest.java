package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private static User user;

    @BeforeAll
    static void beforeAll() {
        registrationService
                = new RegistrationServiceImpl();
        user = new User();
    }

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        user.setAge(18);
        user.setLogin("user'sLogin");
        user.setPassword("user'sPassword");
    }

    @Test
    void register_validUser_Ok() {
        StorageDaoImpl storageDao = new StorageDaoImpl();
        registrationService.register(user);
        Assertions.assertEquals(user, storageDao.get(user.getLogin())
                , "User must be added to data");
    }

    @Test
    void register_whenAgeLessThan18_NotOk() {
        user.setAge(17);
        Assertions.assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_whenAgeMoreThan18_Ok() {
        StorageDaoImpl storageDao = new StorageDaoImpl();
        user.setAge(25);
        registrationService.register(user);
        Assertions.assertEquals(user, storageDao.get(user.getLogin())
                , "User must be added to data");
    }

    @Test
    void register_userWithExistingLogin_NotOk() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
            registrationService.register(user);
        });
    }

    @Test
    void register_withInvalidPassword_NotOk() {
        user.setPassword("123");
        Assertions.assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_withNullPassword_NotOk() {
        user.setPassword(null);
        Assertions.assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_withNullLogin_NotOk() {
        user.setLogin(null);
        Assertions.assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_withNullAge_NotOk() {
        user.setAge(null);
        Assertions.assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_withNegativeAge_NotOk() {
        user.setAge(-15);
        Assertions.assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_withEmptyLogin_NotOk() {
        user.setLogin("");
        Assertions.assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }
}
