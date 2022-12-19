package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.except.InvalidDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 100;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final String EXPECTED_EXCEPTION =
            InvalidDataException.class.getSimpleName();
    private static RegistrationService registrationService;
    private static StorageDao storageDao;
    private static InvalidDataException invalidDataException;

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
    void register_positiveTest_ok() {
        User user = new User();
        user.setAge(19);
        user.setLogin("User");
        user.setPassword("123456");
        storageDao.add(user);
    }

    @Test
    void register_nullUser_notOk() {
        invalidDataException = assertThrows(InvalidDataException.class,
                () -> registrationService.register(null),
                String.format("Should throw %s when user is null", EXPECTED_EXCEPTION));
    }

    @Test
    void register_newLogin_Ok() {
        User user = new User();
        user.setLogin("User");
        user.setPassword("123456");
        user.setAge(21);
        registrationService.register(user);
        User actual = storageDao.get(user.getLogin());
        Assertions.assertEquals(user, actual,
                "User should be added if user login doesnt exist");
    }

    @Test
    void register_existingLogin_notOk() {
        User userOne = new User();
        userOne.setAge(19);
        userOne.setPassword("123456");
        userOne.setLogin("User");
        storageDao.add(userOne);
        User userTwo = new User();
        userTwo.setAge(19);
        userTwo.setPassword("123456");
        userTwo.setLogin("User");
        invalidDataException
                = assertThrows(InvalidDataException
                        .class, () -> registrationService.register(userTwo),
                String.format("Should throw %s when login is already exists",
                        EXPECTED_EXCEPTION));
    }

    @Test
    void register_nullLogin_notOk() {
        User user = new User();
        user.setAge(19);
        user.setPassword("123456");
        user.setLogin(null);
        invalidDataException
                = assertThrows(InvalidDataException.class, () -> registrationService.register(user),
                String.format("Should throw %s when login is null", EXPECTED_EXCEPTION));
    }

    @Test
    void register_over18Age_Ok() {
        User user = new User();
        user.setLogin("User");
        user.setPassword("123456");
        user.setAge(21);
        registrationService.register(user);
        User actual = storageDao.get(user.getLogin());
        Assertions.assertEquals(user, actual,
                "User should be added if user age is valid");
    }

    @Test
    void register_nullAge_notOk() {
        User user = new User();
        user.setLogin("User");
        user.setPassword("123456");
        user.setAge(null);
        invalidDataException
                = assertThrows(InvalidDataException.class, () -> registrationService.register(user),
                String.format("Should throw %s when age is null", EXPECTED_EXCEPTION));
    }

    @Test
    void register_minValueIntegerAge_notOk() {
        User user = new User();
        user.setLogin("User");
        user.setPassword("123456");
        user.setAge(Integer.MIN_VALUE);
        invalidDataException
                = assertThrows(InvalidDataException.class, () -> registrationService.register(user),
                String.format("Should throw %s when age "
                        + "is less than %d years", EXPECTED_EXCEPTION, MIN_AGE));
    }

    @Test
    void register_maxValueIntegerAge_notOk() {
        User user = new User();
        user.setLogin("User");
        user.setPassword("123456");
        user.setAge(Integer.MAX_VALUE);
        invalidDataException
                = assertThrows(InvalidDataException.class, () -> registrationService.register(user),
                String.format("Should throw %s when age"
                        + " is less than %d years", EXPECTED_EXCEPTION, MAX_AGE));
    }

    @Test
    void register_under18Age_notOk() {
        User user = new User();
        user.setLogin("User");
        user.setPassword("123456");
        user.setAge(17);
        invalidDataException
                = assertThrows(InvalidDataException
                        .class, () -> registrationService.register(user),
                String.format("Should throw %s when age under %d",
                        EXPECTED_EXCEPTION, MIN_AGE));
    }

    @Test
    void register_validPassword_Ok() {
        User user = new User();
        user.setLogin("User");
        user.setPassword("12345678910");
        user.setAge(21);
        registrationService.register(user);
        User actual = storageDao.get(user.getLogin());
        Assertions.assertEquals(user, actual,
                "User should be added if user password is valid");
    }

    @Test
    void register_lessThan6CharsPassword_notOk() {
        User user = new User();
        user.setLogin("User");
        user.setPassword("12345");
        user.setAge(19);
        invalidDataException
                = assertThrows(InvalidDataException.class, () -> registrationService.register(user),
                String.format("Should throw %s when password less than %d chars",
                        EXPECTED_EXCEPTION, MIN_PASSWORD_LENGTH));
    }

    @Test
    void register_nullPassword_notOk() {
        User user = new User();
        user.setLogin("User");
        user.setAge(19);
        try {
            user.setPassword(null);
        } catch (NullPointerException e) {
            invalidDataException
                    = assertThrows(InvalidDataException
                            .class, () -> registrationService.register(user),
                    String.format("Should throw %s when age is null",
                            EXPECTED_EXCEPTION));
        }
    }
}
