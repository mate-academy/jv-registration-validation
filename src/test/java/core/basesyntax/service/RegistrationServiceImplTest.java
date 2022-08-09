package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD = 6;
    private static RegistrationService registration;
    private static StorageDao storageDao;
    private User user;

    @BeforeAll
    static void initial() {
        registration = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin("Alice");
        user.setAge(22);
        user.setPassword("1234567");
    }

    @Test
    void userIsNull_NotOK() {
        assertThrows(NullPointerException.class, () -> registration.register(null));
    }

    @Test
    void userLoginIsNull_NotOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> registration.register(user));
    }

    @Test
    void userLoginIsEmpty_NotOk() {
        user.setLogin("");
        assertThrows(RuntimeException.class, () -> registration.register(user));
    }

    @Test
    void userByLoginIsAlreadyExist_NotOK() {
        storageDao = new StorageDaoImpl();
        storageDao.add(user);
        assertThrows(RuntimeException.class, () -> registration.register(user));
    }

    @Test
    void ageOfUserIsNull_NotOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> registration.register(user));
    }

    @Test
    void ageOfUser_NotOk() {
        user.setAge(12);
        assertThrows(RuntimeException.class, () -> registration.register(user));
    }

    @Test
    void ageOfUser_Ok() {
        user.setAge(18);
        assertTrue(user.getAge() >= MIN_AGE);
    }

    @Test
    void passwordIsNull_NotOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registration.register(user));
    }

    @Test
    void passwordIsEmpty_NotOk() {
        user.setPassword("");
        assertThrows(RuntimeException.class, () -> registration.register(user));
    }

    @Test
    void sizeOfPassword_NotOk() {
        user.setPassword("12345");
        assertThrows(RuntimeException.class, () -> registration.register(user));
    }

    @Test
    void sizeOfPassword_Ok() {
        user.setPassword("123456");
        assertTrue(user.getPassword().length() >= MIN_PASSWORD);
    }
}
