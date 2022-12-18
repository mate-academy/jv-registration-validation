package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.except.InvalidDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final String EXPECTED_EXCEPTION =
            InvalidDataException.class.getSimpleName();
    private static User user;
    private static User user2;
    private static RegistrationService registrationService;
    private static StorageDao storageDao;
    private static InvalidDataException invalidDataException;

    @BeforeAll
    static void beforeAll() {
        user = new User();
        user2 = new User();
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_positiveTest_ok() {
        user.setAge(19);
        user.setLogin("User");
        user.setPassword("123456");
        storageDao.add(user);
    }

    @Test
    void register_nullUser_notOk() {
        user = null;
        invalidDataException = assertThrows(InvalidDataException.class,
                () -> storageDao.add(user),
                String.format("Should throw %s when user is null", EXPECTED_EXCEPTION));
    }

    @Test
    void register_existingLogin_notOk() {
        user.setAge(19);
        user.setPassword("123456");
        user.setLogin("User");
        storageDao.add(user);
        user2.setAge(19);
        user2.setPassword("123456");
        user2.setLogin("User");
        invalidDataException
                = assertThrows(InvalidDataException.class, () -> storageDao.add(user2),
                String.format("Should throw %s when login is already exists", EXPECTED_EXCEPTION));
    }

    @Test
    void register_nullLogin_notOk() {
        user.setAge(19);
        user.setPassword("123456");
        user.setLogin(null);
        invalidDataException
                = assertThrows(InvalidDataException.class, () -> storageDao.add(user),
                String.format("Should throw %s when login is null", EXPECTED_EXCEPTION));
    }

    @Test
    void register_nullAge_notOk() {
        user.setId(1L);
        user.setLogin("User");
        user.setPassword("123456");
        user.setAge(null);
        invalidDataException
                = assertThrows(InvalidDataException.class, () -> storageDao.add(user),
                String.format("Should throw %s when age is null", EXPECTED_EXCEPTION));
    }

    @Test
    void register_under18Age_notOk() {
        user.setLogin("User");
        user.setPassword("123456");
        user.setAge(17);
        invalidDataException
                = assertThrows(InvalidDataException.class, () -> storageDao.add(user),
                String.format("Should throw %s when age under %d",
                        EXPECTED_EXCEPTION, MIN_AGE));
    }

    @Test
    void register_lessThan6CharsPassword_notOk() {
        user.setLogin("User");
        user.setPassword("12345");
        user.setAge(19);
        invalidDataException
                = assertThrows(InvalidDataException.class, () -> storageDao.add(user),
                String.format("Should throw %s when password less than %d chars",
                        EXPECTED_EXCEPTION, MIN_PASSWORD_LENGTH));
    }

    @Test
    void register_nullPassword_notOk() {
        user.setLogin("User");
        user.setAge(19);
        try {
            user.setPassword(null);
        } catch (NullPointerException e) {
            invalidDataException
                    = assertThrows(InvalidDataException.class, () -> storageDao.add(user),
                    String.format("Should throw %s when age is null",
                            EXPECTED_EXCEPTION));
        }
    }
}
