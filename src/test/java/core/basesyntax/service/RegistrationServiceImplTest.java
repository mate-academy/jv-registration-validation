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
    private static final int VALID_PASSWORD_LENGTH = 6;
    private static final int VALID_AGE = 18;
    private static final String VALID_FIRST_LOGIN = "Bob";
    private static final String VALID_PASSWORD = "*".repeat(VALID_PASSWORD_LENGTH);
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
                String.format("%S is expected when login is empty", EXCEPTION));
    }

    @Test
    void registerNullUser_NotOk() {
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(null),
                String.format("%s is expected when user is null", EXCEPTION));
    }

    @Test
    void registerUserWithNullLogin_NotOk() {
        invalidUser.setLogin(null);
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(invalidUser),
                String.format("%s is expected when login is null", EXCEPTION));
    }

    @Test
    void registerLoginAlreadyExist_NotOk() {
        registrationService.register(validUser);
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(invalidUser),
                String.format("%s is expected when user already exist in storage", EXCEPTION));
    }

    @Test
    void registerUserWithNullAge_NotOk() {
        invalidUser.setAge(null);
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(invalidUser),
                String.format("%s is expected when user age is null", EXCEPTION));
    }

    @Test
    void registerUserWithLowerAge_NotOk() {
        invalidUser.setAge(VALID_AGE - 1);
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(invalidUser),
                String.format("%s is expected when user age lower then %d", EXCEPTION, VALID_AGE));
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
                String.format("%s is expected when password is empty", EXCEPTION));
    }

    @Test
    void registerNullPassword_NotOk() {
        invalidUser.setPassword(null);
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(invalidUser),
                String.format("%s is expected when password is null", EXCEPTION));
    }

    @Test
    void registerTooShortPassword_NotOk() {
        String newPassword = "1234";
        invalidUser.setPassword(newPassword);
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(invalidUser),
                String.format("%s is expected when password length lower then %d",
                        EXCEPTION, VALID_PASSWORD_LENGTH));
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

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
