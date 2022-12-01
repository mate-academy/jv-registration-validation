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
    private static final String NAME_OK = "Alina";
    private static final String NAME_NOT_OK = "Maksym";

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
        user.setName(NAME_OK);
    }

    @Test
    void user_Not_Ok() {
        user = null;
        Assertions.assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void young_Name_Not_Ok() {
        user.setName(NAME_NOT_OK);
        Assertions.assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void null_Name_Not_Ok() {
        user.setName(null);
        Assertions.assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void young_Age_Not_Ok() {
        user.setAge(AGE_NOT_OK);
        Assertions.assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void null_Age_Not_Ok() {
        user.setAge(null);
        Assertions.assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void unavailable_Login_Not_Ok() {
        Storage.people.add(user);
        Assertions.assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void null_Login_Not_Ok() {
        user.setLogin(null);
        Assertions.assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void short_Password_Not_Ok() {
        user.setPassword(PASSWORD_NOT_OK);
        Assertions.assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
      void null_Password_Not_Ok() {
        user.setPassword(null);
        Assertions.assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }
}
