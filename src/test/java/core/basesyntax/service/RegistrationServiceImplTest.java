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
    private static final int AGE_OK = 19;
    private static final int AGE_NOT_OK = 11;
    private static final String PASSWORD_OK = "asdfjkl";
    private static final String PASSWORD_NOT_OK = "asdf";
    private static final String LOGIN_OK = "Login";

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
        user.setAge(AGE_OK);
        user.setPassword(PASSWORD_OK);
        user.setLogin(LOGIN_OK);
    }

    @Test
    void register_nullUser_notOk() {
        user = null;
        Assertions.assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_youngAge_notOk() {
        user.setAge(AGE_NOT_OK);
        Assertions.assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        Assertions.assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_unavailableLogin_notOk() {
        Storage.people.add(user);
        Assertions.assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        Assertions.assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_shortPassword_notOk() {
        user.setPassword(PASSWORD_NOT_OK);
        Assertions.assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
      void register_nullPassword_notOk() {
        user.setPassword(null);
        Assertions.assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }
}
