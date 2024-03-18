package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidAgeException;
import core.basesyntax.exception.InvalidLoginException;
import core.basesyntax.exception.InvalidPasswordException;
import core.basesyntax.exception.InvalidUserException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static final String VALID_LOGIN = "user_login";
    private static final String VALID_PASSWORD = "user_password";
    private static final int MINIMUM_LOGIN_LENGTH = 4;
    private static final int MINIMUM_PASSWORD_LENGTH = 6;
    private static final int MINIMAL_AGE = 18;
    private static final String INVALID_LOGIN = "log";
    private static final String INVALID_PASSWORD = "1234";
    private static final int INVALID_AGE = 17;
    private final RegistrationService registrationService = new RegistrationServiceImpl();
    private User user;

    @BeforeEach
    void setUp() {
        user = new User(VALID_LOGIN, VALID_PASSWORD, MINIMAL_AGE);
    }

    @BeforeEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_validUser_successfullyRegistered() {
        User registeredUser = registrationService.register(user);
        assertEquals(user, registeredUser);
        assertTrue(Storage.people.contains(user));
    }

    @Test
    void register_nullUser_throwsIllegalArgumentException() {
        user = null;
        InvalidUserException actualException = assertThrows(InvalidUserException.class,
                () -> registrationService.register(user));
        assertEquals("User cannot be null",actualException.getMessage());
    }

    @Test
    void register_existingLogin_throwsInvalidLoginException() {
        registrationService.register(user);
        User newUserWithExistingLogin = new User(VALID_LOGIN, VALID_PASSWORD, MINIMAL_AGE);
        InvalidLoginException actualException = assertThrows(InvalidLoginException.class,
                () -> registrationService.register(newUserWithExistingLogin));
        assertEquals("consumer with " + user.getLogin()
                + "login is already existing", actualException.getMessage());
    }

    @Test
    void register_invalidAge_throwsInvalidAgeException() {
        user.setAge(INVALID_AGE);
        InvalidAgeException actualException = assertThrows(InvalidAgeException.class,
                () -> registrationService.register(user));
        assertEquals("age should be " + MINIMAL_AGE,actualException.getMessage());
    }

    @Test
    void register_nullPassword_trowsPasswordValidateException() {
        user.setPassword(null);
        InvalidPasswordException exception = assertThrows(InvalidPasswordException.class,
                () -> registrationService.register(user));
        assertEquals("password can`t be null", exception.getMessage());
    }

    @Test
    void register_shortPassword_throwsPasswordValidateException() {
        user.setPassword(INVALID_PASSWORD);
        InvalidPasswordException expectedExeption = assertThrows(InvalidPasswordException.class,
                () -> registrationService.register(user));
        assertEquals("password must be longer than " + MINIMUM_PASSWORD_LENGTH
                + " characters", expectedExeption.getMessage());
    }

    @Test
    void register_nullLogin_trowsLoginValidateException() {
        user.setLogin(null);
        InvalidLoginException actualException = assertThrows(InvalidLoginException.class,
                () -> registrationService.register(user));
        assertEquals("Login can`t be Null", actualException.getMessage());
    }

    @Test
    void register_shortLogin_throwsLoginValidateException() {
        user.setLogin(INVALID_LOGIN);
        InvalidLoginException actualException = assertThrows(InvalidLoginException.class,
                () -> registrationService.register(user));
        assertEquals("your login must be more than "
                + MINIMUM_LOGIN_LENGTH + " characters", actualException.getMessage());
    }
}
