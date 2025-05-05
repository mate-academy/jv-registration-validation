package core.basesyntax.service;

import core.basesyntax.customexception.InvalidDataException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int MIN_AGE = 18;
    private static final String DEFAULT_PASSWORD = "valid_password";
    private static final String DEFAULT_LOGIN = "valid_login";
    private static final String EXCEPTION = InvalidDataException.class.toString();
    private static StorageDao storageDao;
    private static RegistrationService registrationService;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_userWithValidCredentialsAddedToStorage_ok() {
        User user = new User(DEFAULT_LOGIN, DEFAULT_PASSWORD, MIN_AGE);
        User actual = registrationService.register(user);
        Assertions.assertEquals(user, actual,
                "You should add user with valid credentials to storage");
    }

    @Test
    void register_checkUserAgeLowerThenMin_notOk() {
        User user = new User(DEFAULT_LOGIN, DEFAULT_PASSWORD, MIN_AGE - 1);
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(user),
                String.format("%S is expected when age less then min", EXCEPTION));

    }

    @Test
    void register_nullLoginCheck_notOk() {
        User user = new User(null, DEFAULT_PASSWORD, MIN_AGE);
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(user),
                String.format("%S is expected when login is null", EXCEPTION));
    }

    @Test
    void register_checkForExistsLogin_notOk() {
        User user = new User("ValidLogin1", DEFAULT_PASSWORD, MIN_AGE);
        registrationService.register(user);
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(user),
                String.format("%S is expected when login is exists", EXCEPTION));
    }

    @Test
    void register_checkForInvalidPassword_notOk() {
        User user = new User(DEFAULT_LOGIN, "12345", MIN_AGE);
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(user),
                String.format("%S is expected when password is invalid", EXCEPTION));
    }

    @Test
    void register_checkForNullPassword_notOk() {
        User user = new User(DEFAULT_LOGIN, null, MIN_AGE);
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(user),
                String.format("%S is expected when password is null", EXCEPTION));
    }

    @Test
    void register_checkEmptyLogin_notOk() {
        User user = new User("", DEFAULT_PASSWORD, MIN_AGE);
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(user),
                String.format("%S is expected when login is empty", EXCEPTION));
    }

    @Test
    void register_checkForNullAge_notOk() {
        User user = new User(DEFAULT_LOGIN, DEFAULT_PASSWORD, null);
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(user),
                String.format("%S is expected when age is null", EXCEPTION));
    }
}
