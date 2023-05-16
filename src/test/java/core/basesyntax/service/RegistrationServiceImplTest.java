package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String LONG_LOGIN = "roberto";
    private static final String SHORT_LOGIN = "bob";
    private static final String GOOD_LOGIN = "morris";
    private static final String LOGIN_WITH_SPACE = "gary  ";
    private static final String EMPTY_LOGIN = "";
    private static final int VALID_AGE = 22;
    private static final int ENOUGH_AGE = 18;
    private static final int NOT_ENOUGH_AGE = 12;
    private static final int NEGATIVE_AGE = -10;
    private static final String LONG_PASSWORD = "robinson134";
    private static final String SHORT_PASSWORD = "984";
    private static final String GOOD_PASSWORD = "123456";
    private static final String PASSWORD_WITH_SPACE = "robi   ";
    private static final String EMPTY_PASSWORD = "";
    private static RegistrationService registrationService;
    private static StorageDao storageDao;
    private User firstUser;
    private User secondUser;
    private User thirdUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        firstUser = new User(GOOD_LOGIN, LONG_PASSWORD, VALID_AGE);
        secondUser = new User(LONG_LOGIN, GOOD_PASSWORD, ENOUGH_AGE);
        thirdUser = new User(GOOD_LOGIN, GOOD_PASSWORD, VALID_AGE);
    }

    @Test
    void userIsAlreadyRegistered() {
        storageDao.add(firstUser);
        User actual = registrationService.register(firstUser);
        Assertions.assertNotEquals(firstUser, actual);
    }

    @Test
    void validUser_Ok() {
        User actual = registrationService.register(firstUser);
        Assertions.assertEquals(firstUser, actual);
        Assertions.assertEquals(firstUser, storageDao.get(firstUser.getLogin()));
    }

    @Test
    void invalidLogin_NotOk() {
        firstUser.setLogin(SHORT_LOGIN);
        secondUser.setLogin(EMPTY_LOGIN);
        thirdUser.setLogin(LOGIN_WITH_SPACE);
        User actualFirstUser = registrationService.register(firstUser);
        User actualSecondUser = registrationService.register(secondUser);
        User actualThirdUser = registrationService.register(thirdUser);
        Assertions.assertNotEquals(firstUser, actualFirstUser);
        Assertions.assertNotEquals(secondUser, actualSecondUser);
        Assertions.assertNotEquals(thirdUser, actualThirdUser);
    }

    @Test
    void invalidPassword_NotOk() {
        firstUser.setPassword(SHORT_PASSWORD);
        secondUser.setPassword(EMPTY_PASSWORD);
        thirdUser.setPassword(PASSWORD_WITH_SPACE);
        User actualFirstUser = registrationService.register(firstUser);
        User actualSecondUser = registrationService.register(secondUser);
        User actualThirdUser = registrationService.register(thirdUser);
        Assertions.assertNotEquals(firstUser, actualFirstUser);
        Assertions.assertNotEquals(secondUser, actualSecondUser);
        Assertions.assertNotEquals(thirdUser, actualThirdUser);
    }

    @Test
    void invalidAge_NotOk() {
        firstUser.setAge(NOT_ENOUGH_AGE);
        secondUser.setAge(NEGATIVE_AGE);
        User actualFirstUser = registrationService.register(firstUser);
        User actualSecondUser = registrationService.register(secondUser);
        Assertions.assertNotEquals(firstUser, actualFirstUser);
        Assertions.assertNotEquals(secondUser, actualSecondUser);
    }

    @Test
    void nullUserValue_NotOk() {
        Assertions.assertThrows(RegistrationException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void nullPasswordValue_NotOk() {
        Assertions.assertThrows(RegistrationException.class, () -> {
            firstUser.setPassword(null);
            registrationService.register(firstUser);
        });
    }

    @Test
    void nullLoginValue_NotOk() {
        Assertions.assertThrows(RegistrationException.class, () -> {
            firstUser.setLogin(null);
            registrationService.register(firstUser);
        });
    }

    @Test
    void nullAgeValue_NotOk() {
        Assertions.assertThrows(RegistrationException.class, () -> {
            firstUser.setAge(null);
            registrationService.register(firstUser);
        });
    }
}
