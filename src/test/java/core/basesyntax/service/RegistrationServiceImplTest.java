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
    private static final int INVALID_AGE = 4;
    private static final int MIN_AGE = 18;
    private static final int MIN_LENGTH = 6;
    private static final String INVALID_AGE_MESSAGE = "You should be at least " + MIN_AGE;
    private static final String INVALID_LOGIN = "short";
    private static final String INVALID_LOGIN_MESSAGE = "Login length should be at least "
            + MIN_LENGTH + " symbols";
    private static final String INVALID_PASSWORD = "short";
    private static final String INVALID_PASSWORD_MESSAGE = "Password length should be at least "
            + MIN_LENGTH + " symbols";
    private static final String VALID_LOGIN = "123456";
    private static final String VALID_PASSWORD = "123456";
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
        assertEquals(INVALID_LOGIN_MESSAGE, exception.getMessage());
    }

    @Test
    void register_shortLogin_notOk() {
        testUser.setLogin(INVALID_LOGIN);
        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(testUser);
        });
        assertEquals(INVALID_LOGIN_MESSAGE, exception.getMessage());
    }

    @Test
    void register_shortPassword_notOk() {
        testUser.setPassword(INVALID_PASSWORD);
        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(testUser);
        });
        assertEquals(INVALID_PASSWORD_MESSAGE, exception.getMessage());
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
        assertEquals(INVALID_PASSWORD_MESSAGE, exception.getMessage());
    }

    @Test
    void register_lowAge_notOk() {
        testUser.setAge(INVALID_AGE);
        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(testUser);
        });
        assertEquals(INVALID_AGE_MESSAGE, exception.getMessage());
    }

    @Test
    void register_edgeAge_Ok() {
        testUser.setAge(MIN_AGE);
        registrationService.register(testUser);
        User actual = storageDao.get(testUser.getLogin());
        assertEquals(testUser, actual);
    }

    @Test
    void register_edgeLogin_Ok() {
        testUser.setLogin(VALID_LOGIN);
        registrationService.register(testUser);
        User actual = storageDao.get(testUser.getLogin());
        assertEquals(testUser, actual);
    }

    @Test
    void register_edgePassword_Ok() {
        testUser.setPassword(VALID_PASSWORD);
        registrationService.register(testUser);
        User actual = storageDao.get(testUser.getLogin());
        assertEquals(testUser, actual);
    }

    @Test
    void register_validUser_Ok() {
        registrationService.register(testUser);
        User actual = storageDao.get(testUser.getLogin());
        assertEquals(testUser, actual);
    }
}
