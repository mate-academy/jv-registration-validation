package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int VALID_LENGTH_PASSWORD = 6;
    private static final int VALID_AGE = 18;
    private static final String VALID_FIRST_LOGIN = "Bob";
    private static final String VALID_SECOND_LOGIN = "alice";
    private static final String VALID_THIRD_LOGIN = "joHn";
    private static final String VALID_PASSWORD = "*".repeat(VALID_LENGTH_PASSWORD);
    private static final String EXCEPTION = InvalidDataException.class.toString();
    private static RegistrationServiceImpl registrationService;
    private static StorageDaoImpl storageDao;
    private User validUser;
    private User invalidUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        validUser = new User();
        validUser.setAge(VALID_AGE);
        validUser.setLogin(VALID_FIRST_LOGIN);
        validUser.setPassword(VALID_PASSWORD);
        invalidUser = new User();
        invalidUser.setAge(VALID_AGE);
        invalidUser.setLogin(VALID_FIRST_LOGIN);
        invalidUser.setPassword(VALID_PASSWORD);
    }

    @Test
    void registerValidUser_Ok() {
        registrationService.register(validUser);
        User actual = storageDao.get(VALID_FIRST_LOGIN);
        Assertions.assertEquals(validUser, actual,
                "Valid user should be added");
    }

    @Test
    void registerUserWithEmptyLogin_NotOk() {
        invalidUser.setLogin("");
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(invalidUser),
                String.format("User should not be added with empty login %s", EXCEPTION));
    }

    @Test
    void registerNullUser_NotOk() {
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(null),
                String.format("Null user should not be added %s", EXCEPTION));
    }

    @Test
    void registerUserWithNullLogin_NotOk() {
        invalidUser.setLogin(null);
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(invalidUser),
                String.format("User should not be added with null login %s", EXCEPTION));
    }

    @Test
    void registerLoginAlreadyExist_NotOk() {
        registrationService.register(validUser);
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(invalidUser),
                String.format("This user already exist in storage %s", EXCEPTION));
    }

    @Test
    void registerUserWithNullAge_NotOk() {
        invalidUser.setAge(null);
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(invalidUser),
                String.format("User should not be added with null age %s", EXCEPTION));
    }

    @Test
    void registerUserWithLowerAge_NotOk() {
        invalidUser.setAge(VALID_AGE - 1);
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(invalidUser),
                String.format("User should not be added with lower age then expected %s",
                        EXCEPTION));
    }

    @Test
    void registerUserWithGreaterAge_Ok() {
        validUser.setAge(VALID_AGE + 1);
        registrationService.register(validUser);
        User actual = storageDao.get(validUser.getLogin());
        Assertions.assertEquals(validUser, actual,
                String.format("User should be added with age %d", VALID_AGE + 1));
    }

    @Test
    void registerEmptyPassword_NotOk() {
        invalidUser.setPassword("");
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(invalidUser),
                String.format("User should not be added with empty password %s", EXCEPTION));
    }

    @Test
    void registerNullPassword_NotOk() {
        invalidUser.setPassword(null);
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(invalidUser),
                String.format("User should not be added with null password %s", EXCEPTION));
    }

    @Test
    void registerTooShortPassword_NotOk() {
        String newPassword = "1234";
        invalidUser.setPassword(newPassword);
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(invalidUser),
                String.format("User should not be added with %s password %s",
                        newPassword, EXCEPTION));
    }

    @Test
    void registerLongPassword_Ok() {
        String newPassword = "123456789";
        validUser.setPassword(newPassword);
        registrationService.register(validUser);
        User actual = storageDao.get(validUser.getLogin());
        Assertions.assertEquals(validUser, actual,
                String.format("User should be added with %s password", newPassword));
    }

    @Test
    void registerManyValidUsers_Ok() {
        User alise = new User();
        alise.setLogin(VALID_SECOND_LOGIN);
        alise.setPassword(VALID_PASSWORD);
        alise.setAge(VALID_AGE);
        User john = new User();
        john.setLogin(VALID_THIRD_LOGIN);
        john.setPassword(VALID_PASSWORD);
        john.setAge(VALID_AGE);
        registrationService.register(alise);
        registrationService.register(john);
        registrationService.register(validUser);
        User actual = storageDao.get(validUser.getLogin());
        Assertions.assertEquals(validUser, actual,
                "Method should be added all users to storage, and return last");
    }

    @Test
    void registerAndGetInvalidUser_NotOk() {
        User alise = new User();
        alise.setLogin(VALID_SECOND_LOGIN);
        alise.setPassword(VALID_PASSWORD);
        alise.setAge(VALID_AGE);
        User john = new User();
        john.setLogin(VALID_THIRD_LOGIN);
        john.setPassword(VALID_PASSWORD);
        john.setAge(VALID_AGE);
        registrationService.register(alise);
        registrationService.register(john);
        User actual = storageDao.get(validUser.getLogin());
        Assertions.assertNull(actual,
                "Method should be returned null for invalid get login");
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
