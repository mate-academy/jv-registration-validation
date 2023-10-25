package core.basesyntax.service;

import core.basesyntax.CustomRegistrationException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    private static final String VALID_LOGIN = "valid_login";
    private static final String VALID_PASSWORD = "valid_password";
    private static final int VALID_AGE = 21;
    private static final String MINIMUM_VALID_LOGIN_SIZE = "little";
    private static final String MINIMUM_VALID_PASSWORD_SIZE = "123456";
    private static final int MINIMUM_AGE_ALLOWED = 18;
    private static final String NOT_ENOUGH_LOGIN_CHARACTERS = "login";
    private static final int AGE_NOT_ALLOWED = 17;
    private static final String PASSWORD_TOO_SMALL = "abcde";

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
    void userEverythingIsValid_Ok() {
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
        User expected = storageDao.add(user);
        User actual = registrationService.register(user);
        assertEquals(expected, actual);

    }

    @Test
    void userMinimumValueValid_Ok() {
        user.setLogin(MINIMUM_VALID_LOGIN_SIZE);
        user.setPassword(MINIMUM_VALID_PASSWORD_SIZE);
        user.setAge(MINIMUM_AGE_ALLOWED);
        User expected = storageDao.add(user);
        User actual = registrationService.register(user);
        assertEquals(expected, actual);
    }

    @Test
    void userIsNull_NotOk() {
        user = null;
        assertThrows(CustomRegistrationException.class, () -> registrationService.register(user), "Registration"
                + " should be throw an exception when the user is null!");
    }

    @Test
    void userAgeIsNull_NotOk() {
        user.setAge(null);
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        assertThrows(CustomRegistrationException.class,
                () -> registrationService.register(user), "Registration should be throw an "
                        + "exception when the ageValue is null!");
    }

    @Test
    void userPasswordIsNull_NotOk() {
        user.setLogin(VALID_LOGIN);
        user.setAge(VALID_AGE);
        user.setPassword(null);
        assertThrows(CustomRegistrationException.class,
                () -> registrationService.register(user), "Registration should be throw an "
                        + "exception when the passwordValue is null!");
    }

    @Test
    void userLoginIsNull_NotOk() {
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
        user.setLogin(null);
        assertThrows(CustomRegistrationException.class,
                () -> registrationService.register(user), "Registration should be throw an "
                        + "exception when the loginValue is null!");
    }

    @Test
    void userLoginIsSmall_NotOk() {
        user.setLogin(NOT_ENOUGH_LOGIN_CHARACTERS);
        user.setPassword(MINIMUM_VALID_PASSWORD_SIZE);
        user.setAge(MINIMUM_AGE_ALLOWED);
        assertThrows(CustomRegistrationException.class, () ->registrationService.register(user));
    }

    @Test
    void userAgeIsLittle_NotOk() {
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(AGE_NOT_ALLOWED);
        assertThrows(CustomRegistrationException.class, () -> registrationService.register(user),
                "Registration should be throw an exception when age less then 18");
    }

    @Test
    void userPasswordIsSmall_NotOk() {
        user.setLogin(VALID_LOGIN);
        user.setPassword(PASSWORD_TOO_SMALL);
        user.setAge(MINIMUM_AGE_ALLOWED);
        assertThrows(CustomRegistrationException.class, () -> registrationService.register(user),
                "Registration should be thrown an exception when password is less then 6 characters!");
    }
}