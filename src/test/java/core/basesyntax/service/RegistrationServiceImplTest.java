package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int MIN_AGE = 18;
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final String VALID_DEFAULT_LOGIN = "user@gmail.com";
    private static final String VALID_DEFAULT_PASSWORD = "user1234";
    private static final int VALID_AGE = 25;
    private static final String INVALID_LOGIN_LENGTH = "user";
    private static final String INVALID_LOGIN_START_WITH_NUMBERS = "1234@gmail.com";
    private static final String INVALID_EMPTY_LOGIN = "";
    private static final String INVALID_PASSWORD_LENGTH = "pass";
    private static final String INVALID_EMPTY_PASSWORD = "";
    private static final int INVALID_USER_AGE = 16;

    private User user;
    private RegistrationException registrationException;
    private final RegistrationService registrationService = new RegistrationServiceImpl();
    private final StorageDao storageDao = new StorageDaoImpl();

    @BeforeEach
    void setUp() {
        user = new User();
        user.setAge(VALID_AGE);
        user.setLogin(VALID_DEFAULT_LOGIN);
        user.setPassword(VALID_DEFAULT_PASSWORD);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_nullValueForUser_notOk() {
        registrationException = Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(null));
        String expectedMessage = "User cannot be null.";
        String actualMessage = registrationException.getMessage();
        Assertions.assertEquals(expectedMessage, actualMessage);

    }

    @Test
    void register_registerValidUser_ok() {
        registrationService.register(user);
        Assertions.assertTrue(Storage.people.contains(user));
    }

    @Test
    void register_userAlreadyExist_notOk() {
        storageDao.add(user);
        Assertions.assertThrows(RegistrationException.class,
                 () -> registrationService.register(user));
    }

    @Test
    void register_invalidLoginLength_notOk() {
        user.setLogin(INVALID_LOGIN_LENGTH);
        registrationException = Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
        String expectedMessage = "Min length of login should be "
                + MIN_LOGIN_LENGTH + " characters.";
        String actualMessage = registrationException.getMessage();
        Assertions.assertEquals(expectedMessage, actualMessage);

    }

    @Test
    void register_userLoginIsEmpty_notOk() {
        user.setLogin(INVALID_EMPTY_LOGIN);
        registrationException = Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
        String expectedMessage = "Min length of login should be "
                + MIN_LOGIN_LENGTH + " characters.";
        String actualMessage = registrationException.getMessage();
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void register_userLoginStartWithNumber_notOk() {
        user.setLogin(INVALID_LOGIN_START_WITH_NUMBERS);
        registrationException = Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
        String expectedMessage = "Login can't start with number.";
        String actualMessage = registrationException.getMessage();
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void register_userLoginIsNull_notOk() {
        user.setLogin(null);
        registrationException = Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
        String expectedMessage = "Login can't be null.";
        String actualMessage = registrationException.getMessage();
        Assertions.assertEquals(expectedMessage, actualMessage);

    }

    @Test
    void register_invalidPasswordLength_notOk() {
        user.setPassword(INVALID_PASSWORD_LENGTH);
        registrationException = Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
        String expectedMessage = "Min length of password should be "
                + MIN_PASSWORD_LENGTH + " characters.";
        String actualMessage = registrationException.getMessage();
        Assertions.assertEquals(expectedMessage, actualMessage);

    }

    @Test
    void register_passwordIsEmpty_notOk() {
        user.setPassword(INVALID_EMPTY_PASSWORD);
        registrationException = Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
        String expectedMessage = "Min length of password should be 6 characters.";
        String actualMessage = registrationException.getMessage();
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void register_passwordIsNull_notOk() {
        user.setPassword(null);
        registrationException = Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
        String expectedMessage = "Password can't be null.";
        String actualMessage = registrationException.getMessage();
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void register_invalidAge_notOk() {
        user.setAge(INVALID_USER_AGE);
        registrationException = Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
        String expectedMessage = "Sorry. Age must be at least " + MIN_AGE + " years old.";
        String actualMessage = registrationException.getMessage();
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void register_ageIsNull_notOk() {
        user.setAge(null);
        registrationException = Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
        String expectedMessage = "You have to enter your age.";
        String actualMessage = registrationException.getMessage();
        Assertions.assertEquals(expectedMessage, actualMessage);
    }
}
