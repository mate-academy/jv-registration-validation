package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
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

    private static final String LOGIN_ALREADY_TAKEN = "This login is already taken";
    private static final String LOGIN_TOO_SHORT = "Login cannot be null or less than 6 characters";
    private static final String PASSWORD_TOO_SHORT
            = "Password cannot be null or less than 6 characters";
    private static final String AGE_TOO_YOUNG = "User's age must be at least 18 and cannot be null";
    private StorageDao storageDao;
    private RegistrationService registrationService;

    @BeforeEach
    public void setUp() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();
    }

    @AfterEach
    public void clear() {
        Storage.people.clear();
    }

    @Test
    void register_shortLogin_notOk() {
        User user = new User();
        user.setLogin("1");
        InvalidUserException exception = assertThrows(InvalidUserException.class,
                () -> registrationService.register(user));
        assertEquals(LOGIN_TOO_SHORT, exception.getMessage());

        user.setLogin("12");
        exception = assertThrows(InvalidUserException.class,
                () -> registrationService.register(user));
        assertEquals(LOGIN_TOO_SHORT, exception.getMessage());

        user.setLogin("123");
        exception = assertThrows(InvalidUserException.class,
                () -> registrationService.register(user));
        assertEquals(LOGIN_TOO_SHORT, exception.getMessage());

        user.setLogin("1234");
        exception = assertThrows(InvalidUserException.class,
                () -> registrationService.register(user));
        assertEquals(LOGIN_TOO_SHORT, exception.getMessage());

        user.setLogin("12345");
        exception = assertThrows(InvalidUserException.class,
                () -> registrationService.register(user));
        assertEquals(LOGIN_TOO_SHORT, exception.getMessage());
    }

    @Test
    void register_Login_Ok() {
        User user = new User();
        user.setLogin("123456");
        user.setPassword("123456");
        user.setAge(18);
        assertDoesNotThrow(() -> registrationService.register(user));
        assertEquals(user, storageDao.get(user.getLogin()));
    }

    @Test
    void register_shortPassword_notOk() {
        User user = new User();
        user.setLogin("123456");
        user.setPassword("12345");
        InvalidUserException exception = assertThrows(InvalidUserException.class,
                () -> registrationService.register(user));
        assertEquals(PASSWORD_TOO_SHORT, exception.getMessage());

        user.setPassword("1");
        exception = assertThrows(InvalidUserException.class,
                () -> registrationService.register(user));
        assertEquals(PASSWORD_TOO_SHORT, exception.getMessage());

        user.setPassword("12");
        exception = assertThrows(InvalidUserException.class,
                () -> registrationService.register(user));
        assertEquals(PASSWORD_TOO_SHORT, exception.getMessage());
        user.setPassword("123");
        exception = assertThrows(InvalidUserException.class,
                () -> registrationService.register(user));
        assertEquals(PASSWORD_TOO_SHORT, exception.getMessage());
        user.setPassword("1234");
        exception = assertThrows(InvalidUserException.class,
                () -> registrationService.register(user));
        assertEquals(PASSWORD_TOO_SHORT, exception.getMessage());
        user.setPassword("12345");
        exception = assertThrows(InvalidUserException.class,
                () -> registrationService.register(user));
        assertEquals(PASSWORD_TOO_SHORT, exception.getMessage());
    }

    @Test
    void register_invalidAge_notOk() {
        User user = new User();
        user.setLogin("123456");
        user.setPassword("123456");
        user.setAge(16);
        InvalidUserException exception = assertThrows(InvalidUserException.class,
                () -> registrationService.register(user));
        assertEquals(AGE_TOO_YOUNG, exception.getMessage());

        user.setAge(-16);
        InvalidUserException exceptionNegativeAge = assertThrows(InvalidUserException.class,
                () -> registrationService.register(user));
        assertEquals(AGE_TOO_YOUNG, exception.getMessage());
    }

    @Test
    void register_duplicateLogin_throwsException() {
        User userExpected = new User();
        userExpected.setLogin("123456");
        userExpected.setPassword("123456");
        userExpected.setAge(18);
        Storage.people.add(userExpected);
        assertEquals(userExpected, storageDao.get(userExpected.getLogin()));

        User userWithSameLogin = new User();
        userWithSameLogin.setLogin("123456");
        userWithSameLogin.setPassword("654321");
        userWithSameLogin.setAge(20);

        InvalidUserException exception = assertThrows(InvalidUserException.class,
                () -> registrationService.register(userWithSameLogin));

        assertEquals(LOGIN_ALREADY_TAKEN, exception.getMessage());
    }

    @Test
    void userOneOfFieldNull_NotOk() {
        User user = new User();
        user.setLogin(null);
        user.setPassword("123456");
        user.setAge(18);
        InvalidUserException exceptionLogin = assertThrows(InvalidUserException.class,
                () -> registrationService.register(user));
        assertEquals(LOGIN_TOO_SHORT, exceptionLogin.getMessage());

        user.setLogin("123456");
        user.setPassword(null);
        user.setAge(18);
        InvalidUserException exceptionPassword = assertThrows(InvalidUserException.class,
                () -> registrationService.register(user));
        assertEquals(PASSWORD_TOO_SHORT, exceptionPassword.getMessage());

        user.setLogin("123456");
        user.setPassword("123456");
        user.setAge(null);
        InvalidUserException exception = assertThrows(InvalidUserException.class,
                () -> registrationService.register(user));
        assertEquals(AGE_TOO_YOUNG, exception.getMessage());
    }

    //there is no user with such login in the Storage yet
    //user's login is at least 6 characters
    //user's password is at least 6 characters
    //user's age is at least 18 years old
}
