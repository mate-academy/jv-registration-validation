package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int VALID_PASSWORD_LENGTH = 6;
    private static final int VALID_AGE = 18;
    private static final String VALID_LOGIN = "Bob";
    private static final String VALID_PASSWORD = "*".repeat(VALID_PASSWORD_LENGTH);
    private static final String EXCEPTION = InvalidDataException.class.toString();
    private static RegistrationServiceImpl registrationService;
    private static StorageDaoImpl storageDao;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @Test
    void registerValidUser_Ok() {
        User newUser = new User();
        newUser.setAge(VALID_AGE);
        newUser.setLogin(VALID_LOGIN);
        newUser.setPassword(VALID_PASSWORD);
        registrationService.register(newUser);
        User actual = storageDao.get(VALID_LOGIN);
        Assertions.assertEquals(newUser, actual,
                "Valid user should be added");
    }

    @Test
    void registerUserWithEmptyLogin_NotOk() {
        User newUser = new User();
        newUser.setAge(VALID_AGE);
        newUser.setPassword(VALID_PASSWORD);
        newUser.setLogin("");
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(newUser),
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
        User newUser = new User();
        newUser.setAge(VALID_AGE);
        newUser.setPassword(VALID_PASSWORD);
        newUser.setLogin(null);
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(newUser),
                String.format("%s is expected when login is null", EXCEPTION));
    }

    @Test
    void registerLoginAlreadyExist_NotOk() {
        User newUser = new User();
        newUser.setAge(VALID_AGE);
        newUser.setLogin(VALID_LOGIN);
        newUser.setPassword(VALID_PASSWORD);
        registrationService.register(newUser);
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(newUser),
                String.format("%s is expected when user login already exist in storage",
                        EXCEPTION));
    }

    @Test
    void registerUserWithNullAge_NotOk() {
        User newUser = new User();
        newUser.setLogin(VALID_LOGIN);
        newUser.setPassword(VALID_PASSWORD);
        newUser.setAge(null);
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(newUser),
                String.format("%s is expected when user age is null", EXCEPTION));
    }

    @Test
    void registerUserWithLowerAge_NotOk() {
        User newUser = new User();
        newUser.setLogin(VALID_LOGIN);
        newUser.setPassword(VALID_PASSWORD);
        newUser.setAge(VALID_AGE - 1);
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(newUser),
                String.format("%s is expected when user age lower then %d", EXCEPTION, VALID_AGE));
    }

    @Test
    void registerUserWithGreaterAge_Ok() {
        User newUser = new User();
        newUser.setLogin(VALID_LOGIN);
        newUser.setPassword(VALID_PASSWORD);
        newUser.setAge(VALID_AGE + 1);
        registrationService.register(newUser);
        User actual = storageDao.get(newUser.getLogin());
        Assertions.assertEquals(newUser, actual,
                String.format("User should be added with age %d", VALID_AGE + 1));
    }

    @Test
    void registerEmptyPassword_NotOk() {
        User newUser = new User();
        newUser.setAge(VALID_AGE);
        newUser.setLogin(VALID_LOGIN);
        newUser.setPassword("");
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(newUser),
                String.format("%s is expected when password is empty", EXCEPTION));
    }

    @Test
    void registerNullPassword_NotOk() {
        User newUser = new User();
        newUser.setAge(VALID_AGE);
        newUser.setLogin(VALID_LOGIN);
        newUser.setPassword(null);
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(newUser),
                String.format("%s is expected when password is null", EXCEPTION));
    }

    @Test
    void registerTooShortPassword_NotOk() {
        User newUser = new User();
        newUser.setAge(VALID_AGE);
        newUser.setLogin(VALID_LOGIN);
        String newPassword = "1234";
        newUser.setPassword(newPassword);
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(newUser),
                String.format("%s is expected when password length lower then %d",
                        EXCEPTION, VALID_PASSWORD_LENGTH));
    }

    @Test
    void registerLongPassword_Ok() {
        User newUser = new User();
        newUser.setAge(VALID_AGE);
        newUser.setLogin(VALID_LOGIN);
        String newPassword = "123456789";
        newUser.setPassword(newPassword);
        registrationService.register(newUser);
        User actual = storageDao.get(newUser.getLogin());
        Assertions.assertEquals(newUser, actual,
                String.format("User should be added with %s password", newPassword));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
