package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceTest {
    private static RegistrationService registrationService;
    private static StorageDao storageDao;

    @BeforeAll
    static void initialize() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @Test
    void nullUser_NotOK() {
        assertThrows(RuntimeException.class, () -> {
            User register = registrationService.register(null);
        });
    }

    @Test
    void nullLogin_NotOK() {
        User user = new User();
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void nullPassword_NotOK() {
        User user = new User();
        user.setLogin("User login");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void nullAge_NotOK() {
        User user = new User();
        user.setLogin("User login");
        user.setLogin("User password");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void invalidAge_NotOK() {
        User user = new User();
        user.setLogin("User login");
        user.setPassword("User password");
        user.setAge(17);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void invalidPassword_NotOK() {
        User user = new User();
        user.setLogin("User");
        user.setPassword("12345");
        user.setAge(25);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void registerAnotherUserWithSameLogin_NotOK() {
        User firstUser = new User();
        firstUser.setLogin("FirstUserLogin");
        firstUser.setPassword("First user password");
        firstUser.setAge(28);
        registrationService.register(firstUser);
        User secondUser = new User();
        secondUser.setLogin("FirstUserLogin");
        secondUser.setPassword("Second user password");
        secondUser.setAge(33);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(secondUser);
        });
    }

    @Test
    void reRegisterUser_NotOK() {
        User user = new User();
        user.setLogin("UserLogin");
        user.setPassword("UserPassword");
        user.setAge(28);
        registrationService.register(user);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void registerWithMinAge_OK() {
        User user = new User();
        user.setLogin("User login");
        user.setPassword("User password");
        user.setAge(18);
        registrationService.register(user);
        assertEquals(user, storageDao.get(user.getLogin()));
    }

    @Test
    void registerWithMinPasswordLength_OK() {
        User user = new User();
        user.setLogin("User with min password length login");
        user.setPassword("123456");
        user.setAge(21);
        registrationService.register(user);
        assertEquals(user, storageDao.get(user.getLogin()));
    }

    @Test
    void validAllData_OK() {
        User user = new User();
        user.setLogin("valid login");
        user.setPassword("valid password");
        user.setAge(48);
        registrationService.register(user);
        assertEquals(user, storageDao.get(user.getLogin()));
    }
}
