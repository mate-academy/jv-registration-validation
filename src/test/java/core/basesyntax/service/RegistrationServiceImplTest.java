package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    private static final String DEFAULT_LOGIN = "login";
    private static final String VALID_PASSWORD = "password";
    private static final int VALID_AGE = 24;
    private RegistrationService registrationService = new RegistrationServiceImpl();
    private StorageDao storageDao = new StorageDaoImpl();

    @BeforeEach
    void setUp() {
        registrationService.register(storageDao.add(new User("user_Alise", "1234567", 23)));
        registrationService.register(storageDao.add(new User("user_Bob", "password", 41)));
        registrationService.register(storageDao.add(new User("user_Lili", "student", 24)));
    }

    @Test
    void register_validData_registerOk() {
        User actual = registrationService.register((storageDao
                .add((new User(DEFAULT_LOGIN, VALID_PASSWORD, VALID_AGE)))));
        assertEquals(new User(DEFAULT_LOGIN, VALID_PASSWORD, VALID_AGE), actual);
    }

    @Test
    void register_notValidAge_callException() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(storageDao.add(new User(DEFAULT_LOGIN, VALID_PASSWORD, 13)));
            registrationService.register(storageDao.add(new User(DEFAULT_LOGIN, VALID_PASSWORD, 0)));
            registrationService.register(storageDao.add(new User(DEFAULT_LOGIN, VALID_PASSWORD, 145)));
        });
    }

    @Test
    void register_notValidPassword_callException() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(storageDao.add(new User(DEFAULT_LOGIN, "123", VALID_AGE)));
        });
    }

    @Test
    void register_nullPassword_callException() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(storageDao.add(new User(DEFAULT_LOGIN, null, VALID_AGE)));
        });
    }

    @Test
    void register_existingLogin_callException() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(storageDao.add(new User("user_Lili", VALID_PASSWORD, VALID_AGE)));
        });
    }

    @Test
    void register_nullLogin_callException() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(storageDao.add(new User(null, VALID_PASSWORD, VALID_AGE)));
        });
    }
}