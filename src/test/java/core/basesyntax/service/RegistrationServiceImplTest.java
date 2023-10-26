package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.CustomRegistrationException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String VALID_LOGIN = "valid_login";
    private static final String MINIMUM_VALID_LOGIN_SIZE = "little";
    private static final String LOGIN_FIVE_LENGTH = "login";
    private static final String NOT_ENOUGH_LOGIN_CHARACTERS = "log";
    private static final String LOGIN_EMPTY = "";
    private static final String VALID_PASSWORD = "valid_password";
    private static final String MINIMUM_VALID_PASSWORD_SIZE = "123456";
    private static final String PASSWORD_FIVE_LENGTH = "12345";
    private static final String PASSWORD_TOO_SMALL = "123";
    private static final String PASSWORD_EMPTY = "";
    private static final int VALID_AGE = 21;
    private static final int MINIMUM_AGE_ALLOWED = 18;
    private static final int AGE_NOT_ALLOWED = 17;
    private static final int AGE_MINUS_VALUE = -12;
    private static RegistrationService registrationService;
    private static StorageDao storageDao;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @Test
    void register_userEverythingIsValid_Ok() {
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
        User actual = registrationService.register(user);
        assertEquals(user, actual);

    }

    @Test
    void register_userMinimumValueValid_Ok() {
        user.setLogin(MINIMUM_VALID_LOGIN_SIZE);
        user.setPassword(MINIMUM_VALID_PASSWORD_SIZE);
        user.setAge(MINIMUM_AGE_ALLOWED);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_userAlmostEnoughValues_NotOk() {
        user.setLogin(LOGIN_FIVE_LENGTH);
        user.setPassword(PASSWORD_FIVE_LENGTH);
        user.setAge(AGE_NOT_ALLOWED);
        assertThrows(CustomRegistrationException.class,
                () -> registrationService.register(user),
                "Registration should be throw an exception when the userLogin less "
                        + "then 6 characters, userPassword less then "
                        + "6 characters and userAge less then 18!");
    }

    @Test
    void register_userIsNull_NotOk() {
        assertThrows(CustomRegistrationException.class,
                () -> registrationService.register(user),
                "Registration should be throw an exception when the user is null!");
    }

    @Test
    void register_userLoginIsNull_NotOk() {
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
        user.setLogin(null);
        assertThrows(CustomRegistrationException.class,
                () -> registrationService.register(user),
                "Registration should be throw an exception when the loginValue is null!");
    }

    @Test
    void register_userLoginIsSmall_NotOk() {
        user.setLogin(NOT_ENOUGH_LOGIN_CHARACTERS);
        user.setPassword(MINIMUM_VALID_PASSWORD_SIZE);
        user.setAge(MINIMUM_AGE_ALLOWED);
        assertThrows(CustomRegistrationException.class,
                () -> registrationService.register(user),
                "Registration should be throw an exception when "
                    + "the loginValue less then 6 characters!");
    }

    @Test
    void register_userLoginIsEmpty_NotOk() {
        user.setLogin(LOGIN_EMPTY);
        user.setPassword(MINIMUM_VALID_PASSWORD_SIZE);
        user.setAge(MINIMUM_AGE_ALLOWED);
        assertThrows(CustomRegistrationException.class, () -> registrationService.register(user),
                "Registration should be throw an exception when the loginValue is empty!");
    }

    @Test
    void register_userAgeIsNull_NotOk() {
        user.setAge(null);
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        assertThrows(CustomRegistrationException.class,
                () -> registrationService.register(user),
                "Registration should be throw an exception when the ageValue is null!");
    }

    @Test
    void register_userAgeIsLittle_NotOk() {
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(AGE_NOT_ALLOWED);
        assertThrows(CustomRegistrationException.class,
                () -> registrationService.register(user),
                "Registration should be throw an exception when age less then 18!");
    }

    @Test
    void register_userAgeIsEmpty_NotOk() {
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(AGE_NOT_ALLOWED);
        assertThrows(CustomRegistrationException.class,
                () -> registrationService.register(user),
                "Registration should be throw an exception when age is empty!");
    }

    @Test
    void register_userAgeMinusValue_NotOk() {
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(AGE_MINUS_VALUE);
        assertThrows(CustomRegistrationException.class,
                () -> registrationService.register(user),
                "Registration should be thrown an exception when age less then 18!");
    }

    @Test
    void register_userPasswordIsNull_NotOk() {
        user.setLogin(VALID_LOGIN);
        user.setAge(VALID_AGE);
        user.setPassword(null);
        assertThrows(CustomRegistrationException.class,
                () -> registrationService.register(user),
                "Registration should be throw an exception when the passwordValue is null!");
    }

    @Test
    void register_userPasswordIsSmall_NotOk() {
        user.setLogin(VALID_LOGIN);
        user.setPassword(PASSWORD_TOO_SMALL);
        user.setAge(MINIMUM_AGE_ALLOWED);
        assertThrows(CustomRegistrationException.class,
                  () -> registrationService.register(user),
                "Registration should be thrown an exception when password is "
                         + "less then 6 characters!");
    }

    @Test
    void register_userPasswordIsEmpty_NotOk() {
        user.setLogin(VALID_LOGIN);
        user.setPassword(PASSWORD_EMPTY);
        user.setAge(MINIMUM_AGE_ALLOWED);
        assertThrows(CustomRegistrationException.class,
                () -> registrationService.register(user),
                "Registration should be thrown an exception when password is empty!");
    }

    @Test
    void register_userDuplicateLogin_NotOk() {
        user.setLogin(VALID_LOGIN);
        user.setAge(VALID_AGE);
        user.setPassword(VALID_PASSWORD);
        User user2 = new User();
        user2.setLogin(VALID_LOGIN);
        user2.setPassword(VALID_PASSWORD);
        user2.setAge(VALID_AGE);
        storageDao.add(user);
        assertThrows(CustomRegistrationException.class,
                () -> registrationService.register(user2),
                  "Registration should be thrown an exception when such userLogin exist!");
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
