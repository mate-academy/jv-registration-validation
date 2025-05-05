package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.UserRegisterException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceTest {
    private static StorageDao storageDao;
    private static RegistrationServiceImpl registrationService;
    private static User user;

    @BeforeAll
    static void beforeAll() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();
        user = new User();
    }

    @BeforeEach
    void setUp() {
        user.setLogin("bob2020");
        user.setAge(20);
        user.setPassword("123456");
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(UserRegisterException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_emptyLogin_notOk() {
        user.setLogin("");
        assertThrows(UserRegisterException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_sameLogin_notOk() {
        User sameLoginUser = new User();
        sameLoginUser.setLogin("bob2020");
        storageDao.add(user);
        assertThrows(UserRegisterException.class, () -> {
            registrationService.register(sameLoginUser);
        });
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(UserRegisterException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_emptyPassword_notOk() {
        user.setPassword("");
        assertThrows(UserRegisterException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_shortPassword_notOk() {
        user.setPassword("123");
        assertThrows(UserRegisterException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(UserRegisterException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_negativeAge_notOk() {
        user.setAge(-1);
        assertThrows(UserRegisterException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_underAge_notOk() {
        user.setAge(17);
        assertThrows(UserRegisterException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_minAge_ok() {
        user.setAge(18);
        registrationService.register(user);
        boolean actual = Storage.people.contains(user);
        assertTrue(actual);
    }

    @Test
    void register_overAge_ok() {
        user.setAge(19);
        registrationService.register(user);
        boolean actual = Storage.people.contains(user);
        assertTrue(actual);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
