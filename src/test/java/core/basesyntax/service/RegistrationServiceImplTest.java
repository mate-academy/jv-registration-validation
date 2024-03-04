package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static StorageDao storageDao;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void registerValidUser() {
        User validUser = new User();
        validUser.setLogin("validuser");
        validUser.setPassword("validpassword");
        validUser.setAge(20);
        validUser.setId(1234567L);
        assertNotNull(registrationService.register(validUser));
    }

    @Test
    void registerDublicateUser_notOk() {
        User user = new User();
        user.setLogin("user01");
        user.setPassword("password1");
        user.setAge(20);
        registrationService.register(user);
        User dublicatedUser = new User();
        dublicatedUser.setLogin("user01");
        dublicatedUser.setPassword("password");
        dublicatedUser.setAge(20);
        dublicatedUser.setId(1234567L);
        assertThrows(RuntimeException.class, () -> registrationService.register(dublicatedUser));
    }

    @Test
    void shortLogin_notOk() {
        User shortLoginUser = new User();
        shortLoginUser.setLogin("user");
        shortLoginUser.setPassword("password");
        shortLoginUser.setAge(20);
        shortLoginUser.setId(1234567L);
        assertThrows(RuntimeException.class, () -> registrationService.register(shortLoginUser));
    }

    @Test
    void shortPassword_notOk() {
        User shortLoginUser = new User();
        shortLoginUser.setLogin("userok");
        shortLoginUser.setPassword("pass");
        shortLoginUser.setAge(20);
        shortLoginUser.setId(1234567L);
        assertThrows(RuntimeException.class, () -> registrationService.register(shortLoginUser));
    }

    @Test
    void youngAge_notOk() {
        User youngUser = new User();
        youngUser.setLogin("userok");
        youngUser.setPassword("password");
        youngUser.setAge(17);
        youngUser.setId(1234567L);
        assertThrows(RuntimeException.class, () -> registrationService.register(youngUser));
    }

    @Test
    void loginIsNull_notOk() {
        User nullUser = new User();
        nullUser.setLogin(null);
        nullUser.setPassword("password");
        nullUser.setAge(20);
        nullUser.setId(1234567L);
        assertThrows(RuntimeException.class, () -> registrationService.register(nullUser));
    }

    @Test
    void passwordIsNull_notOk() {
        User shortLoginUser = new User();
        shortLoginUser.setLogin("userok");
        shortLoginUser.setPassword(null);
        shortLoginUser.setAge(20);
        shortLoginUser.setId(1234567L);
        assertThrows(RuntimeException.class, () -> registrationService.register(shortLoginUser));
    }

    @Test
    void getNullFromStorage_notOk() {
        assertThrows(NullPointerException.class, () -> storageDao.get(null));
    }

    @Test
    void userEquals_ok() {
        User expected = new User();
        expected.setLogin("userok");
        expected.setPassword("password");
        expected.setAge(20);
        expected.setId(1234567L);
        User actual = new User();
        actual.setLogin("userok");
        actual.setPassword("password");
        actual.setAge(20);
        actual.setId(1234567L);
        assertEquals(expected, actual);
    }

    @Test
    void userNotEquals_ok() {
        User expected = new User();
        expected.setLogin("userok");
        expected.setPassword("password");
        expected.setAge(20);
        expected.setId(1234567L);
        User actual = new User();
        actual.setLogin("anotheruser");
        actual.setPassword("anotherpassword");
        actual.setAge(20);
        actual.setId(1234567L);
        assertNotEquals(expected, actual);
    }
}
