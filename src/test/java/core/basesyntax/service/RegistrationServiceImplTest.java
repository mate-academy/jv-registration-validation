package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int VALID_AGE = 22;
    private static final String VALID_PASSWORD = "password";
    private static final String VALID_LOGIN = "login";
    private static final String INVALID_PASSWORD = "111";
    private static final int INVALID_AGE = 11;
    private static RegistrationService usersRegistration;
    private static StorageDao storageDao;
    private User userTest;

    @BeforeEach
    void setUp() {
        userTest = new User();
        userTest.setAge(VALID_AGE);
        userTest.setPassword(VALID_PASSWORD);
        userTest.setLogin(VALID_LOGIN);
        usersRegistration = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @Test
    void register_correctRegister_Ok() {
        User actual = storageDao.add(userTest);
        assertTrue(Storage.people.contains(actual));
    }

    @Test
    void register_uniqueLogin_NotOk() {
        storageDao.add(userTest);
        assertThrows(RuntimeException.class, () -> usersRegistration.register(userTest));
    }

    @Test
    void register_loginIsNull_notOk() {
        userTest.setLogin(null);
        assertThrows(RuntimeException.class, () -> usersRegistration.register(userTest));
    }

    @Test
    void register_emptyLogin_notOk() {
        userTest.setLogin("   ");
        assertThrows(RuntimeException.class, () -> usersRegistration.register(userTest));
    }

    @Test
    void register_passwordIsSafe_notOk() {
        userTest.setPassword(INVALID_PASSWORD);
        assertThrows(RuntimeException.class, () -> usersRegistration.register(userTest));
    }

    @Test
    void register_passwordIsNull_notOk() {
        userTest.setPassword(null);
        assertThrows(RuntimeException.class, () -> usersRegistration.register(userTest));
    }

    @Test
    void register_isAdult_NotOk() {
        userTest.setAge(INVALID_AGE);
        assertThrows(RuntimeException.class, () -> usersRegistration.register(userTest));
    }

    @Test
    void register_ageIsNull_notOk() {
        userTest.setAge(null);
        assertThrows(RuntimeException.class, () -> usersRegistration.register(userTest));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
