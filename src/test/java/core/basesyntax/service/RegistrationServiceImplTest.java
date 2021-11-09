package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    static RegistrationServiceImpl registrationService;
    static StorageDaoImpl storageDao;
    static User user;


    @BeforeAll
    static void beforeAll() {
        registrationService
                = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
        user = new User();
    }

    @BeforeEach
    void setUp() {
        user.setAge(18);
        user.setLogin("user'sLogin");
        user.setPassword("user'sPassword");
    }

    @Test
    void _ValidUser_Ok() {
        registrationService.register(user);
        Assertions.assertEquals(user, storageDao.get(user.getLogin())
                , "User must be added to data");
    }

    @Test
    void _WhenAgeLessThan18_NotOk() {
        user.setAge(17);
        Assertions.assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        }, "Expected RuntimeException");
    }

    @Test
    void _WithSuchLogin_NotOk() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
            registrationService.register(user);
        }, "Expected RuntimeException");
    }

    @Test
    void _WithInvalidPassword_NotOk() {
        user.setPassword(Integer.toString(123));
        Assertions.assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        }, "Expected RuntimeException");
    }

    @Test
    void _WithNullPassword_NotOk() {
        user.setPassword(null);
        Assertions.assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        }, "Expected RuntimeException");
    }

    @Test
    void _WithNullLogin_NotOk() {
        user.setLogin(null);
        Assertions.assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        }, "Expected RuntimeException");
    }

    @Test
    void _WithNegativeAge_NotOk() {
        user.setAge(-15);
        Assertions.assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        }, "Expected RuntimeException");
    }

    @Test
    void _emptyLogin_NotOk() {
        user.setLogin("");
        Assertions.assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        }, "Expected RuntimeException");
    }
}
