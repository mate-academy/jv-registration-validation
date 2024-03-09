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
        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            testUser.setLogin(null);
            registrationService.register(testUser);
        });
        assertEquals("Login should be at least " + MIN_LENGTH, exception.getMessage());
    }

    @Test
    void register_shortLogin_notOk() {
        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            testUser.setLogin("short");
            registrationService.register(testUser);
        });
        assertEquals("Login should be at least " + MIN_LENGTH, exception.getMessage());
    }

    @Test
    void register_shortPassword_notOk() {
        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            testUser.setPassword("short");
            registrationService.register(testUser);
        });
        assertEquals("Password should be at least " + MIN_LENGTH, exception.getMessage());
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
        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(testUser);
            registrationService.register(testUser);
        });
        assertEquals("This login has already taken by another user", exception.getMessage());
    }

    @Test
    void register_nullPassword_notOk() {
        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            testUser.setPassword(null);
            registrationService.register(testUser);
        });
        assertEquals("Password should be at least " + MIN_LENGTH, exception.getMessage());
    }

    @Test
    void register_lowAge_notOk() {
        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            testUser.setAge(4);
            registrationService.register(testUser);
        });
        assertEquals("You should be at least " + MIN_AGE, exception.getMessage());
    }

    @Test
    void register_edgeAge_Ok() {
        testUser.setAge(18);
        registrationService.register(testUser);
        User expected = storageDao.get(testUser.getLogin());
        assertEquals(expected, testUser);
    }

    @Test
    void register_edgeLogin_Ok() {
        testUser.setLogin("123456");
        registrationService.register(testUser);
        User expected = storageDao.get(testUser.getLogin());
        assertEquals(expected, testUser);
    }

    @Test
    void register_edgePassword_Ok() {
        testUser.setPassword("123456");
        registrationService.register(testUser);
        User expected = storageDao.get(testUser.getLogin());
        assertEquals(expected, testUser);
    }

    @Test
    void register_validUser_Ok() {
        registrationService.register(testUser);
        User actual = storageDao.get(testUser.getLogin());
        assertEquals(testUser, actual);
    }
}
