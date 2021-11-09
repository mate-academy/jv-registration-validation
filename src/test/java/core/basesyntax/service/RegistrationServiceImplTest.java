package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private final RegistrationServiceImpl registrationService
            = new RegistrationServiceImpl();
    private final StorageDaoImpl storageDao = new StorageDaoImpl();
    private User user;
    private User actual;
    private String login;

    @BeforeEach
    void setUp() {
        user = new User();
        actual = new User();
    }

    @Test
    void registrationValidUser_Ok() {
        user.setAge(20);
        user.setLogin(Integer.toString(user.hashCode()));
        user.setPassword(Integer.toString(12345678));
        login = user.getLogin();
        actual = registrationService.register(user);
        Assertions.assertNotNull(actual, "Method must return user");
        Assertions.assertEquals(user, storageDao.get(login), "User must be added to data");
    }

    @Test
    void registrationWhenAgeLessThan18_NotOk() {
        user.setAge(17);
        user.setLogin(Integer.toString(user.hashCode()));
        user.setPassword(Integer.toString(12345678));
        login = user.getLogin();
        actual = registrationService.register(user);
        Assertions.assertNull(actual, "Method must return null, "
                + "invalid age");
        Assertions.assertNull(storageDao.get(login), "User mustn't be added to data, "
                + "invalid age");
    }

    @Test
    void registrationWithSuchLogin_NotOk() {
        user.setAge(20);
        user.setLogin(Integer.toString(user.hashCode()));
        user.setPassword(Integer.toString(12345678));
        login = user.getLogin();
        registrationService.register(user);
        actual = registrationService.register(user);
        Assertions.assertNull(actual, "Method must return null, "
                + "user with a such login is already registered");
    }

    @Test
    void registrationWithInvalidPassword_NotOk() {
        user.setAge(20);
        user.setLogin(Integer.toString(user.hashCode()));
        user.setPassword(Integer.toString(123));
        login = user.getLogin();
        actual = registrationService.register(user);
        Assertions.assertNull(actual, "Method must return null, "
                + "invalid password");
        Assertions.assertNull(storageDao.get(login), "User mustn't be added to data, "
                + "invalid password");
    }

    @Test
    void registrationWithNullPassword_NotOk() {
        user.setAge(20);
        user.setLogin(Integer.toString(user.hashCode()));
        user.setPassword(null);
        Assertions.assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        }, "Expected RuntimeException");
    }

    @Test
    void registrationWithNullLogin_NotOk() {
        user.setAge(20);
        user.setLogin(null);
        user.setPassword(Integer.toString(12345678));
        Assertions.assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        }, "Expected RuntimeException");
    }

    @Test
    void registrationWithNegativeAge_NotOk() {
        user.setAge(-15);
        user.setLogin(Integer.toString(user.hashCode()));
        user.setPassword(Integer.toString(12345678));
        Assertions.assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        }, "Expected RuntimeException");
    }
}