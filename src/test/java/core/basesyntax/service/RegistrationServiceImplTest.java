package core.basesyntax.service;

import core.basesyntax.Exception.InvalidInputDataException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    private static final int VALID_AGE = 18;
    private static final int VALID_PASSWORD_LENGTH = 6;
    private static final String VALID_LOGIN_ONE = "FirstLogin";
    private static final String VALID_LOGIN_TWO = "SecondLogin";
    private static final String VALID_LOGIN_NEW = "NewLogin";
    private static final String VALID_PASSWORD = "#".repeat(VALID_PASSWORD_LENGTH);
    private static final String EXPECTED_EXCEPTION = InvalidInputDataException.class.getSimpleName();
    private static RegistrationService registrationService;
    private static StorageDao storageDao;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        User userOne = new User();
        User userTwo = new User();
        userOne.setAge(VALID_AGE);
        userOne.setLogin(VALID_LOGIN_ONE);
        userOne.setPassword(VALID_PASSWORD);
        userTwo.setAge(VALID_AGE);
        userTwo.setLogin(VALID_LOGIN_TWO);
        userTwo.setPassword(VALID_PASSWORD);
        storageDao.add(userOne);
        storageDao.add(userTwo);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_nullUser_notOk() {
        User newUser = null;
        Assertions.assertThrows(InvalidInputDataException.class,
                () -> registrationService.register(newUser),
                String.format("Should throw %s when user is null", EXPECTED_EXCEPTION));
    }

    @Test
    void register_nullLogin_notOk() {
        User newUser = new User();
        newUser.setAge(VALID_AGE);
        newUser.setLogin(null);
        newUser.setPassword(VALID_PASSWORD);
        Assertions.assertThrows(InvalidInputDataException.class,
                () -> registrationService.register(newUser),
                String.format("Should throw %s when login is null", EXPECTED_EXCEPTION));
    }

    @Test
    void register_userLoginAlreadyExists_notOk() {
        User newUser = new User();
        newUser.setAge(VALID_AGE);
        newUser.setLogin(VALID_LOGIN_ONE);
        newUser.setPassword(VALID_PASSWORD);
        Assertions.assertThrows(InvalidInputDataException.class,
                () -> registrationService.register(newUser),
                String.format("Should throw %s when user with the same login already exists", EXPECTED_EXCEPTION));
    }

    @Test
    void register_userLoginDoesNotExists_ok() {
        User newUser = new User();
        newUser.setAge(VALID_AGE);
        newUser.setLogin(VALID_LOGIN_NEW);
        newUser.setPassword(VALID_PASSWORD);
        registrationService.register(newUser);
        User actual = storageDao.get(newUser.getLogin());
        Assertions.assertEquals(newUser, actual,
                "User should be added if user with such login doesn't exist");
    }

    @Test
    void register_userAgeNull_notOk() {
        User newUser = new User();
        newUser.setAge(null);
        newUser.setLogin(VALID_LOGIN_NEW);
        newUser.setPassword(VALID_PASSWORD);
        Assertions.assertThrows(InvalidInputDataException.class,
                () -> registrationService.register(newUser),
                String.format("Should throw %s when age is null", EXPECTED_EXCEPTION));
    }

    @Test
    void register_userAgeLowerThenExpected_notOk() {
        User newUser = new User();
        int invalidAge = VALID_AGE - 1;
        newUser.setAge(invalidAge);
        newUser.setLogin(VALID_LOGIN_NEW);
        newUser.setPassword(VALID_PASSWORD);
        Assertions.assertThrows(InvalidInputDataException.class,
                () -> registrationService.register(newUser),
                String.format("Should throw %s when age is lower then %d", EXPECTED_EXCEPTION, VALID_AGE));
    }

    @Test
    void register_userAgeEqualsExpected_ok() {
        User newUser = new User();
        newUser.setAge(VALID_AGE);
        newUser.setLogin(VALID_LOGIN_NEW);
        newUser.setPassword(VALID_PASSWORD);
        registrationService.register(newUser);
        User actual = storageDao.get(newUser.getLogin());
        Assertions.assertEquals(newUser, actual,
                String.format("User should be added if age is equals %d", VALID_AGE));
    }

    @Test
    void register_userAgeGreaterThenExpected_ok() {
        User newUser = new User();
        int validAge = VALID_AGE + 1;
        newUser.setAge(validAge);
        newUser.setLogin(VALID_LOGIN_NEW);
        newUser.setPassword(VALID_PASSWORD);
        registrationService.register(newUser);
        User actual = storageDao.get(newUser.getLogin());
        Assertions.assertEquals(newUser, actual,
                String.format("User should be added if age is greater then %d", VALID_AGE));
    }

    @Test
    void register_nullPassword_notOk() {
        User newUser = new User();
        newUser.setAge(VALID_AGE);
        newUser.setLogin(VALID_PASSWORD);
        newUser.setPassword(null);
        Assertions.assertThrows(InvalidInputDataException.class,
                () -> registrationService.register(newUser),
                String.format("Should throw %s when password is null", EXPECTED_EXCEPTION));
    }

    @Test
    void register_passwordLengthLowerThenExpected_notOk() {
        User newUser = new User();
        newUser.setAge(VALID_AGE);
        newUser.setLogin(VALID_LOGIN_NEW);
        String invalidPassword = VALID_PASSWORD.substring(1);
        newUser.setPassword(invalidPassword);
        Assertions.assertThrows(InvalidInputDataException.class,
                () -> registrationService.register(newUser),
                String.format("Should throw %s when password length is lower then %d",
                        EXPECTED_EXCEPTION, VALID_PASSWORD_LENGTH));
    }

    @Test
    void register_userPasswordLengthEqualsExpected_ok() {
        User newUser = new User();
        newUser.setAge(VALID_AGE);
        newUser.setLogin(VALID_LOGIN_NEW);
        newUser.setPassword(VALID_PASSWORD);
        registrationService.register(newUser);
        User actual = storageDao.get(newUser.getLogin());
        Assertions.assertEquals(newUser, actual,
                String.format("User should be added if password length equals %d", VALID_PASSWORD_LENGTH));
    }

    @Test
    void register_userPasswordLengthGreaterThenExpected_ok() {
        User newUser = new User();
        newUser.setAge(VALID_AGE);
        newUser.setLogin(VALID_LOGIN_NEW);
        String validPassword = VALID_PASSWORD + "#";
        newUser.setPassword(validPassword);
        registrationService.register(newUser);
        User actual = storageDao.get(newUser.getLogin());
        Assertions.assertEquals(newUser, actual,
                String.format("User should be added if password length greater then %d", VALID_PASSWORD_LENGTH));
    }

    @Test
    void register_userReturnedAfterAddedToStorage_ok() {
        User newUser = new User();
        newUser.setAge(VALID_AGE);
        newUser.setLogin(VALID_LOGIN_NEW);
        newUser.setPassword(VALID_PASSWORD);
        User actual = registrationService.register(newUser);
        Assertions.assertEquals(newUser, actual,
                "Method should return user that was added to storage");
    }
}