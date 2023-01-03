package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.ValidationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private static final String VALID_LOGIN_1 = "Bob";
    private static final String VALID_LOGIN_2 = "Alice";
    private static final String VALID_LOGIN_3 = "John";
    private static final String VALID_PASSWORD = "123456";
    private static RegistrationServiceImpl registrationService;
    private static StorageDao storageDao;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        User user1 = new User();
        user1.setLogin(VALID_LOGIN_1);
        user1.setAge(MIN_AGE);
        user1.setPassword(VALID_PASSWORD);
        User user2 = new User();
        user2.setLogin(VALID_LOGIN_2);
        user2.setAge(MIN_AGE);
        user2.setPassword(VALID_PASSWORD);
        storageDao.add(user1);
        storageDao.add(user2);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_newUserIsNull_NotOk() {
        User newUser = null;
        Assertions.assertThrows(ValidationException.class,
                () -> registrationService.register(newUser),
                "User should not be null");
    }

    @Test
    void register_loginIsNull_NotOk() {
        User newUser = new User();
        newUser.setAge(MIN_AGE);
        newUser.setPassword(VALID_PASSWORD);
        newUser.setLogin(null);
        Assertions.assertThrows(ValidationException.class,
                () -> registrationService.register(newUser),
                "Login should not be null");
    }

    @Test
    void register_ageIsNull_NotOk() {
        User newUser = new User();
        newUser.setAge(null);
        newUser.setPassword(VALID_PASSWORD);
        newUser.setLogin(VALID_LOGIN_3);
        Assertions.assertThrows(ValidationException.class,
                () -> registrationService.register(newUser),
                "Age should not be null");
    }

    @Test
    void register_passwordIsNull_NotOk() {
        User newUser = new User();
        newUser.setAge(MIN_AGE);
        newUser.setPassword(null);
        newUser.setLogin(VALID_LOGIN_3);
        Assertions.assertThrows(ValidationException.class,
                () -> registrationService.register(newUser),
                "Password should not be null");
    }

    @Test
    void register_ageIsLess_NotOk() {
        User newUser = new User();
        newUser.setAge(MIN_AGE - 1);
        newUser.setPassword(VALID_PASSWORD);
        newUser.setLogin(VALID_LOGIN_3);
        Assertions.assertThrows(ValidationException.class,
                () -> registrationService.register(newUser),
                "Age have to be 18 or more");
    }

    @Test
    void register_ageMoreThan_Ok() {
        User newUser = new User();
        newUser.setAge(MIN_AGE + 1);
        newUser.setPassword(VALID_PASSWORD);
        newUser.setLogin(VALID_LOGIN_3);
        registrationService.register(newUser);
        User actual = storageDao.get(newUser.getLogin());
        Assertions.assertEquals(newUser, actual);

    }

    @Test
    void register_passwordIsLess_NotOk() {
        User newUser = new User();
        newUser.setAge(MIN_AGE);
        newUser.setPassword(VALID_PASSWORD.substring(0, MIN_PASSWORD_LENGTH - 1));
        newUser.setLogin(VALID_LOGIN_3);
        Assertions.assertThrows(ValidationException.class,
                () -> registrationService.register(newUser),
                "Password should be 6 or more symbols");
    }

    @Test
    void register_passwordMoreThan_Ok() {
        User newUser = new User();
        newUser.setAge(MIN_AGE);
        newUser.setPassword(VALID_PASSWORD + 1);
        newUser.setLogin(VALID_LOGIN_3);
        registrationService.register(newUser);
        User actual = storageDao.get(newUser.getLogin());
        Assertions.assertEquals(newUser, actual);
    }

    @Test
    void register_userExist_NotOk() {
        User newUser = new User();
        newUser.setAge(MIN_AGE);
        newUser.setPassword(VALID_PASSWORD);
        newUser.setLogin(VALID_LOGIN_1);
        Assertions.assertThrows(ValidationException.class,
                () -> registrationService.register(newUser),
                "User is already exists");
    }

    @Test
    void register_userNotExist_Ok() {
        User newUser = new User();
        newUser.setAge(MIN_AGE);
        newUser.setPassword(VALID_PASSWORD);
        newUser.setLogin(VALID_LOGIN_3);
        registrationService.register(newUser);
        User actual = storageDao.get(newUser.getLogin());
        Assertions.assertEquals(newUser, actual);
    }
}
