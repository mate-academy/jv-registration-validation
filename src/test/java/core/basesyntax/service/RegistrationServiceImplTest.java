package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidAgeException;
import core.basesyntax.exception.LoginValidateException;
import core.basesyntax.exception.PasswordValidateException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static final String VALID_LOGIN = "user_login";
    private static final String VALID_PASSWORD = "user_password";
    private static final int VALID_AGE = 18;
    private static final String INVALID_LOGIN = "log";
    private static final String INVALID_PASSWORD = "1234";
    private static final String INVALID_LOGIN_WITH_NO_FIRST_LETTER = "1invalidLogin";
    private static final int INVALID_AGE = 17;

    private final RegistrationService registrationService = new RegistrationServiceImpl();
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setAge(VALID_AGE);
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_nullUser_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> registrationService.register(null));
    }

    @Test
    void register_invalidAge_throwsInvalidAgeException() {
        User user = new User();
        user.setLogin(VALID_LOGIN);
        user.setAge(INVALID_AGE);
        user.setPassword(VALID_PASSWORD);
        assertThrows(InvalidAgeException.class,
                () -> registrationService.register(user),"age should be 18 or above");
    }

    @Test
    void register_shortPassword_throwsPasswordValidateException() {
        User user = new User();
        user.setLogin(VALID_LOGIN);
        user.setAge(VALID_AGE);
        user.setPassword(INVALID_PASSWORD);
        assertThrows(PasswordValidateException.class,
                () -> registrationService.register(user),
                "password length should be minimum 6 symbols");
    }

    @Test
    void register_shortLogin_throwsLoginValidateException() {
        User user = new User();
        user.setLogin(INVALID_LOGIN);
        user.setAge(VALID_AGE);
        user.setPassword(VALID_PASSWORD);
        assertThrows(LoginValidateException.class,
                () -> registrationService.register(user),"login should be longer then 4 symbol");
    }

    @Test
    void register_loginWithNonLetterFirstCharacter_throwsLoginValidateException() {
        User user = new User();
        user.setLogin(INVALID_LOGIN_WITH_NO_FIRST_LETTER);
        user.setAge(VALID_AGE);
        user.setPassword(VALID_PASSWORD);
        assertThrows(LoginValidateException.class,
                () -> registrationService.register(user),"login should start from letter");
    }
}
