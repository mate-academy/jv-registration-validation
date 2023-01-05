package core.basesyntax.service;

import core.basesyntax.customexception.InvalidDataException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int USER_VALID_AGE = 18;
    private static final String VALID_PASSWORD = "valid_password";
    private static final String VALID_LOGIN = "valid_login";
    private static final String EXISTS_LOGIN = "ValidLogin1";
    private static final String INVALID_PASSWORD = "12345";
    private static StorageDao storageDao = new StorageDaoImpl();
    private static RegistrationService registrationService;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        User user1 = new User(EXISTS_LOGIN, "ValidPassword1", 25);
        User user2 = new User("ValidLogin2", "ValidPassword2", 27);
        storageDao.add(user1);
        storageDao.add(user2);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_validUser_ok() {
        User user = new User(VALID_LOGIN, VALID_PASSWORD,USER_VALID_AGE);
        registrationService.register(user);
        User actual = storageDao.get(user.getLogin());
        Assertions.assertEquals(user, actual);
    }

    @Test
    void register_ageCheck_notOk() {
        User user = new User(VALID_LOGIN, VALID_PASSWORD, USER_VALID_AGE - 1);
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(user));

    }

    @Test
    void register_nullLoginCheck_notOk() {
        User user = new User(null, VALID_PASSWORD, USER_VALID_AGE);
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_existsLogin_notOk() {
        User user = new User(EXISTS_LOGIN, VALID_PASSWORD, USER_VALID_AGE);
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_checkPassword_notOk() {
        User user = new User(VALID_LOGIN, INVALID_PASSWORD, USER_VALID_AGE);
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        User user = new User(VALID_LOGIN, null, USER_VALID_AGE);
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(user));
    }
}
