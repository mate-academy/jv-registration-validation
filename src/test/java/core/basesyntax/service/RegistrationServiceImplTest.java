package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static StorageDao storage;
    private static RegistrationService registrationService;
    private static User user;

    @BeforeAll
    static void beforeAll() {
        storage = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();
        user = new User();
    }

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        user.setAge(18);
        user.setLogin("marina");
        user.setPassword("password");
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        Assertions.assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        Assertions.assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        Assertions.assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_age_notOK() {
        user.setAge(17);
        Assertions.assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullUser_notOk() {
        User user1 = null;
        Assertions.assertThrows(RuntimeException.class, () -> registrationService.register(user1));
    }

    @Test
    void register_user_ok() {
        Assertions.assertEquals(user, registrationService.register(user));
    }

    @Test
    void register_theSameLogin_notOk() {
        User user2 = new User();
        user2.setAge(19);
        user2.setLogin("marina");
        user2.setPassword("passsword");
        registrationService.register(user2);
        Assertions.assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_password_notOk() {
        user.setPassword("12345");
        Assertions.assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }
}
