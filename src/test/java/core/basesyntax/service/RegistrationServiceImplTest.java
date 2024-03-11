package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidUserException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int USER_MIN_AGE = 18;
    private static final int USER_LOGIN_PASSWORD_MIN_LENGTH = 6;
    private static final int USER_VALID_AGE = 20;
    private static final String USER_VALID_LOGIN = "username";
    private static final String USER_VALID_PASSWORD = "password";
    private StorageDao storageDao = new StorageDaoImpl();
    private RegistrationService registrationService = new RegistrationServiceImpl();
    private User testUser;

    @BeforeEach
    void beforeEach() {
        testUser = createValidUser();
    }

    @AfterEach
    void afterEach() {
        Storage.people.clear();
    }

    @Test
    void register_userUnderMinAge_notOk() {
        testUser.setAge(USER_MIN_AGE - 1);
        InvalidUserException exception = assertThrows(InvalidUserException.class,
                () -> registrationService.register(testUser));
        assertEquals(exception.getMessage(), "Users under "
                + USER_MIN_AGE + " are not allowed!");
    }

    @Test
    void register_userWithNullAge_notOk() {
        testUser.setAge(null);
        InvalidUserException exception = assertThrows(InvalidUserException.class,
                () -> registrationService.register(testUser));
        assertEquals(exception.getMessage(), "User's age can't be 'null'!");
    }

    @Test
    void register_userWithShortLogin_notOk() {
        testUser.setLogin("short");
        InvalidUserException exception = assertThrows(InvalidUserException.class,
                () -> registrationService.register(testUser));
        assertEquals(exception.getMessage(), "Login must be at least "
                + USER_LOGIN_PASSWORD_MIN_LENGTH + " characters");
    }

    @Test
    void register_userWithNullLogin_notOk() {
        testUser.setLogin(null);
        InvalidUserException exception = assertThrows(InvalidUserException.class,
                () -> registrationService.register(testUser));
        assertEquals(exception.getMessage(), "User's login can't be 'null'!");
    }

    @Test
    void register_userWithShortPassword_notOk() {
        testUser.setPassword("pass");
        InvalidUserException exception = assertThrows(InvalidUserException.class,
                () -> registrationService.register(testUser));
        assertEquals(exception.getMessage(), "Password must be at least "
                + USER_LOGIN_PASSWORD_MIN_LENGTH + " characters");
    }

    @Test
    void register_userWithNullPassword_notOk() {
        testUser.setPassword(null);
        InvalidUserException exception = assertThrows(InvalidUserException.class,
                () -> registrationService.register(testUser));
        assertEquals(exception.getMessage(), "User's password can't be 'null'!");
    }

    @Test
    void register_userWithExistingLogin_notOk() {
        User user1 = createValidUser();
        storageDao.add(user1);
        InvalidUserException exception = assertThrows(InvalidUserException.class,
                () -> registrationService.register(testUser));
        assertEquals(exception.getMessage(), "User with login '"
                + USER_VALID_LOGIN + "' already exists");
    }

    @Test
    void register_validUser_isOK() {
        registrationService.register(testUser);
        User actual = storageDao.get(testUser.getLogin());
        assertEquals(testUser, actual);
    }

    private User createValidUser() {
        testUser = new User();
        testUser.setAge(USER_VALID_AGE);
        testUser.setLogin(USER_VALID_LOGIN);
        testUser.setPassword(USER_VALID_PASSWORD);
        return testUser;
    }
}
