package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidUserException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int USER_MIN_AGE = 18;
    private static final int USER_LOGIN_PASSWORD_MIN_LENGTH = 6;
    private static final int USER_VALID_AGE = 20;
    private static final String USER_VALID_LOGIN = "username";
    private static final String USER_VALID_PASSWORD = "password";
    private RegistrationService registrationService;
    private StorageDao storageDao;

    @BeforeEach
    void beforeEach() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl(storageDao);
    }

    @Test
    void register_userUnderMinAge_notOk() {
        User user = createValidUser();
        user.setAge(USER_MIN_AGE - 1);
        InvalidUserException exception = assertThrows(InvalidUserException.class,
                () -> registrationService.register(user));
        assertTrue(exception.getMessage().contains("Users under "
                + USER_MIN_AGE + " are not allowed!"));
    }

    @Test
    void register_userWithNullAge_notOk() {
        User user = createValidUser();
        user.setAge(null);
        InvalidUserException exception = assertThrows(InvalidUserException.class,
                () -> registrationService.register(user));
        assertTrue(exception.getMessage().contains("Users under "
                + USER_MIN_AGE + " are not allowed!"));
    }

    @Test
    void register_userWithShortLogin_notOk() {
        User user = createValidUser();
        user.setLogin("short");
        InvalidUserException exception = assertThrows(InvalidUserException.class,
                () -> registrationService.register(user));
        assertTrue(exception.getMessage().contains("Login must be at least "
                + USER_LOGIN_PASSWORD_MIN_LENGTH + " characters"));
    }

    @Test
    void register_userWithNullLogin_notOk() {
        User user = createValidUser();
        user.setLogin(null);
        InvalidUserException exception = assertThrows(InvalidUserException.class,
                () -> registrationService.register(user));
        assertTrue(exception.getMessage().contains("Login must be at least "
                + USER_LOGIN_PASSWORD_MIN_LENGTH + " characters"));
    }

    @Test
    void register_userWithShortPassword_notOk() {
        User user = createValidUser();
        user.setPassword("pass");
        InvalidUserException exception = assertThrows(InvalidUserException.class,
                () -> registrationService.register(user));
        assertTrue(exception.getMessage().contains("Password must be at least "
                + USER_LOGIN_PASSWORD_MIN_LENGTH + " characters"));
    }

    @Test
    void register_userWithNullPassword_notOk() {
        User user = createValidUser();
        user.setPassword(null);
        InvalidUserException exception = assertThrows(InvalidUserException.class,
                () -> registrationService.register(user));
        assertTrue(exception.getMessage().contains("Password must be at least "
                + USER_LOGIN_PASSWORD_MIN_LENGTH + " characters"));
    }

    @Test
    void register_userWithExistingLogin_notOk() {
        User user1 = createValidUser();
        User user2 = createValidUser();
        registrationService.register(user1);
        InvalidUserException exception = assertThrows(InvalidUserException.class,
                () -> registrationService.register(user2));
        assertTrue(exception.getMessage().contains("User with login '"
                + USER_VALID_LOGIN + "' already exists"));
    }

    private User createValidUser() {
        User user = new User();
        user.setAge(USER_VALID_AGE);
        user.setLogin(USER_VALID_LOGIN);
        user.setPassword(USER_VALID_PASSWORD);
        return user;
    }
}
