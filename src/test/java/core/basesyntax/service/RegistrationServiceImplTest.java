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
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private static final String DEFAULT_LOGIN = "Bob";
    private static final String DEFAULT_PASSWORD = "*".repeat(MIN_PASSWORD_LENGTH);
    private static final String EXCEPTION = InvalidDataException.class.toString();
    private static RegistrationServiceImpl registrationService;
    private static StorageDaoImpl storageDao;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @Test
    void register_UserWithValidCredentialsAddedToStorage_Ok() {
        User newUser = new User();
        newUser.setAge(MIN_AGE);
        newUser.setLogin(DEFAULT_LOGIN);
        newUser.setPassword(DEFAULT_PASSWORD);
        registrationService.register(newUser);
        User actual = storageDao.get(DEFAULT_LOGIN);
        Assertions.assertEquals(newUser, actual,
                "User with valid credentials should be added");
    }

    @Test
    void register_UserWithEmptyLogin_NotOk() {
        User newUser = new User();
        newUser.setAge(MIN_AGE);
        newUser.setPassword(DEFAULT_PASSWORD);
        newUser.setLogin("");
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(newUser),
                String.format("%S is expected when login is empty", EXCEPTION));
    }

    @Test
    void register_NullUser_NotOk() {
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(null),
                String.format("%s is expected when user is null", EXCEPTION));
    }

    @Test
    void register_UserWithNullLogin_NotOk() {
        User newUser = new User();
        newUser.setAge(MIN_AGE);
        newUser.setPassword(DEFAULT_PASSWORD);
        newUser.setLogin(null);
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(newUser),
                String.format("%s is expected when login is null", EXCEPTION));
    }

    @Test
    void register_LoginAlreadyExist_NotOk() {
        User newUser = new User();
        newUser.setAge(MIN_AGE);
        newUser.setLogin(DEFAULT_LOGIN);
        newUser.setPassword(DEFAULT_PASSWORD);
        registrationService.register(newUser);
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(newUser),
                String.format("%s is expected when user login already exist in storage",
                        EXCEPTION));
    }

    @Test
    void register_UserWithNullAge_NotOk() {
        User newUser = new User();
        newUser.setLogin(DEFAULT_LOGIN);
        newUser.setPassword(DEFAULT_PASSWORD);
        newUser.setAge(null);
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(newUser),
                String.format("%s is expected when user age is null", EXCEPTION));
    }

    @Test
    void register_UserAgeLowerThenValid_NotOk() {
        User newUser = new User();
        newUser.setLogin(DEFAULT_LOGIN);
        newUser.setPassword(DEFAULT_PASSWORD);
        newUser.setAge(MIN_AGE - 1);
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(newUser),
                String.format("%s is expected when user age lower then %d", EXCEPTION, MIN_AGE));
    }

    @Test
    void register_UserAgeGreaterThenValid_Ok() {
        User newUser = new User();
        newUser.setLogin(DEFAULT_LOGIN);
        newUser.setPassword(DEFAULT_PASSWORD);
        newUser.setAge(MIN_AGE + 1);
        registrationService.register(newUser);
        User actual = storageDao.get(newUser.getLogin());
        Assertions.assertEquals(newUser, actual,
                String.format("User should be added with age %d", MIN_AGE + 1));
    }

    @Test
    void register_UserWithEmptyPassword_NotOk() {
        User newUser = new User();
        newUser.setAge(MIN_AGE);
        newUser.setLogin(DEFAULT_LOGIN);
        newUser.setPassword("");
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(newUser),
                String.format("%s is expected when password is empty", EXCEPTION));
    }

    @Test
    void register_UserWithNullPassword_NotOk() {
        User newUser = new User();
        newUser.setAge(MIN_AGE);
        newUser.setLogin(DEFAULT_LOGIN);
        newUser.setPassword(null);
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(newUser),
                String.format("%s is expected when password is null", EXCEPTION));
    }

    @Test
    void register_UserPasswordShorterThenExpected_NotOk() {
        User newUser = new User();
        newUser.setAge(MIN_AGE);
        newUser.setLogin(DEFAULT_LOGIN);
        String newPassword = "1234";
        newUser.setPassword(newPassword);
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(newUser),
                String.format("%s is expected when password length lower then %d",
                        EXCEPTION, MIN_PASSWORD_LENGTH));
    }

    @Test
    void register_UserPasswordLongerThenExpected_Ok() {
        User newUser = new User();
        newUser.setAge(MIN_AGE);
        newUser.setLogin(DEFAULT_LOGIN);
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
