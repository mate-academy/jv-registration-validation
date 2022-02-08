package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
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
        user.setLogin("validUser");
        user.setAge(18);
        user.setPassword("password");
        Storage.people.clear();
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(null);
        }, "When User is null, the method should throw RuntimeException");
    }

    @Test
    void register_userWithNullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        }, "When login is null, the method should throw RuntimeException");
    }

    @Test
    void register_userWithNullPassword_notOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        }, "When password is null, the method should throw RuntimeException");
    }

    @Test
    void register_userWithNullAge_notOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        }, "When age is null, the method should throw RuntimeException");
    }

    @Test
    void register_validUser_ok() {
        User expected = user;
        User actual = registrationService.register(user);
        assertEquals(expected, actual, "Test failed! Method should return registered user");
        int expectedSize = 1;
        int actualSize = Storage.people.size();
        assertEquals(expectedSize, actualSize, "Size of storage should be 1");
    }

    @Test
    void register_theSameUser_notOk() {
        storageDao.add(user);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        }, "When user already registered, the method should throw RuntimeException");
    }

    @Test
    void register_userWithNotValidPassword_notOk() {
        user.setPassword("12345");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        }, "When password is less then 6 characters, the method should throw RuntimeException");
        user.setPassword("");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        }, "When password is empty, the method should throw RuntimeException");
    }

    @Test
    void register_userWithNotValidAge_notOk() {
        user.setAge(17);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        }, "When age is less than 18, the method should throw RuntimeException");
        user.setAge(-17);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        }, "When age is negative, the method should throw RuntimeException");
    }
}
