package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int MIN_AGE = 18;
    private static final int MIN_LENGTH = 6;
    private static final String INVALID_LOGIN = "Login length should be at least "
            + MIN_LENGTH + " symbols";
    private static final String INVALID_PASSWORD = "Password length should be at least "
            + MIN_LENGTH + " symbols";
    private static final String INVALID_AGE = "You should be at least " + MIN_AGE;
    private RegistrationService registrationService = new RegistrationServiceImpl();
    private StorageDao storageDao = new StorageDaoImpl();
    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User(1L, "validLogin", "validPassword", 21);
        Storage.people.clear();
    }

    @Test
    void register_nullLogin_notOk() {
        testUser.setLogin(null);
        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(testUser);
        });
        assertEquals(INVALID_LOGIN, exception.getMessage());
    }

    @Test
    void register_shortLogin_notOk() {
        testUser.setLogin("short");
        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(testUser);
        });
        assertEquals(INVALID_LOGIN, exception.getMessage());
    }

    @Test
    void register_shortPassword_notOk() {
        testUser.setPassword("short");
        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(testUser);
        });
        assertEquals(INVALID_PASSWORD, exception.getMessage());
    }

    @Test
    void register_nullUser_notOk() {
        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(null);
        });
        assertEquals("Error, user can not be null", exception.getMessage());
    }

    @Test
    void register_loginAlreadyPresent_notOk() {
        storageDao.add(testUser);
        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(testUser);
        });
        assertEquals("This login has already taken by another user", exception.getMessage());
    }

    @Test
    void register_nullPassword_notOk() {
        testUser.setPassword(null);
        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(testUser);
        });
        assertEquals(INVALID_PASSWORD, exception.getMessage());
    }

    @Test
    void register_lowAge_notOk() {
        testUser.setAge(4);
        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(testUser);
        });
        assertEquals(INVALID_AGE, exception.getMessage());
    }

    @Test
    void register_edgeAge_Ok() {
        testUser.setAge(18);
        registrationService.register(testUser);
        User actual = storageDao.get(testUser.getLogin());
        assertEquals(actual, testUser);
    }

    @Test
    void register_edgeLogin_Ok() {
        testUser.setLogin("123456");
        registrationService.register(testUser);
        User actual = storageDao.get(testUser.getLogin());
        assertEquals(actual, testUser);
    }

    @Test
    void register_edgePassword_Ok() {
        testUser.setPassword("123456");
        registrationService.register(testUser);
        User actual = storageDao.get(testUser.getLogin());
        assertEquals(actual, testUser);
    }

    @Test
    void register_validUser_Ok() {
        registrationService.register(testUser);
        User actual = storageDao.get(testUser.getLogin());
        assertEquals(testUser, actual);
    }
}
