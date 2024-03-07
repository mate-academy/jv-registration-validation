package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String DEFAULT_LOGIN = "user_login";
    private static final String DEFAULT_PASSWORD = "user_password";
    private static final long DEFAULT_ID = 123456789L;
    private static final int DEFAULT_AGE = 23;
    private static final String INVALID_LOGIN = "login";
    private static final String INVALID_PASSWORD = "12345";
    private static final int INVALID_AGE = 17;
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;

    private final RegistrationService registrationService = new RegistrationServiceImpl();
    private final StorageDao storageDao = new StorageDaoImpl();
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setAge(DEFAULT_AGE);
        user.setId(DEFAULT_ID);
        user.setLogin(DEFAULT_LOGIN);
        user.setPassword(DEFAULT_PASSWORD);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_validUser_ok() {
        registrationService.register(user);
        User actual = storageDao.get(user.getLogin());
        assertEquals(actual, user);
    }

    @Test
    void register_nullUser_notOk() {
        user = null;
        InvalidDataException invalidDataException = assertThrows(InvalidDataException.class,
                () -> registrationService.register(user));
        assertEquals(invalidDataException.getMessage(),
                "User cannot be NULL!");
    }

    @Test
    void register_loginAlreadyExists_notOk() {
        Storage.people.add(user);
        User userCopy = user;
        InvalidDataException invalidDataException = assertThrows(InvalidDataException.class,
                () -> registrationService.register(userCopy));
        assertEquals(invalidDataException.getMessage(),
                "Login is already taken!");
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        InvalidDataException invalidDataException = assertThrows(InvalidDataException.class,
                () -> registrationService.register(user));
        assertEquals(invalidDataException.getMessage(),
                "Login should not be NULL!");
    }

    @Test
    void register_loginLength_notOk() {
        user.setLogin(INVALID_LOGIN);
        InvalidDataException invalidDataException = assertThrows(InvalidDataException.class,
                () -> registrationService.register(user));
        assertEquals(invalidDataException.getMessage(),
                "Login must be at least " + MIN_LOGIN_LENGTH
                        + " characters, but was " + user.getLogin().length() + "!");
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        InvalidDataException invalidDataException = assertThrows(InvalidDataException.class,
                () -> registrationService.register(user));
        assertEquals(invalidDataException.getMessage(),
                "The password should not be NULL!");
    }

    @Test
    void register_userPasswordLength_notOk() {
        user.setPassword(INVALID_PASSWORD);
        InvalidDataException invalidDataException = assertThrows(InvalidDataException.class,
                () -> registrationService.register(user));
        assertEquals(invalidDataException.getMessage(),
                "The password must be at least " + MIN_PASSWORD_LENGTH
                        + " characters, but was " + user.getPassword().length() + "!");
    }

    @Test
    void register_notValidUserAge_notOk() {
        user.setAge(INVALID_AGE);
        InvalidDataException invalidDataException = assertThrows(InvalidDataException.class,
                () -> registrationService.register(user));
        assertEquals(invalidDataException.getMessage(),
                "User age must be at least " + MIN_AGE
                        + " years, but was " + user.getAge() + "!");
    }
}
