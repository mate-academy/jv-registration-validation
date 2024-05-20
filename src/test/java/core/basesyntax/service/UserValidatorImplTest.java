package core.basesyntax.service;

import core.basesyntax.exceptions.ValidationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserValidatorImplTest {
    private static UserValidator userValidator;
    private static final String LOGIN_OK = "example";
    private static final String PASSWORD_OK = "123456";
    private static final String LOGIN_NOT_OK = "examp";
    private static final String PASSWORD_NOT_OK = "12345";
    private static final int AGE_NOT_OK = 17;
    private User userNotOk;

    @BeforeAll
    static void beforeAll() {
        userValidator = new UserValidatorImpl();
    }

    @BeforeEach
    void setUp() {
        userNotOk = new User();
        userNotOk.setId(3423432423L);
        userNotOk.setLogin(LOGIN_NOT_OK);
        userNotOk.setPassword(PASSWORD_NOT_OK);
        userNotOk.setAge(AGE_NOT_OK);
    }

    @Test
    void validate_nullUser_notOk() {
        assertThrows(ValidationException.class, () -> {
            userValidator.validate(null);
        });
    }

    @Test
    void validate_userLoginNull_notOk() {
        userNotOk.setLogin(null);
        assertThrows(RuntimeException.class, () -> {
            userValidator.validate(userNotOk);
        });
    }

    @Test
    void validate_userLoginToShort_notOk() {
        assertThrows(ValidationException.class, () -> {
            userValidator.validate(userNotOk);
        });
    }

    @Test
    void validate_userLoginContainSpace_notOk() {
        assertThrows(ValidationException.class, () -> {
            userValidator.validate(userNotOk);
        });
    }

    @Test
    void validate_userPasswordNull_notOk() {
        userNotOk.setPassword(null);
        assertThrows(RuntimeException.class, () -> {
            userValidator.validate(userNotOk);
        });
    }

    @Test
    void validate_userPasswordToShort_notOk() {
        userNotOk.setLogin(LOGIN_OK);
        assertThrows(ValidationException.class, () -> {
            userValidator.validate(userNotOk);
        });
    }

    @Test
    void validate_userAgeNull_notOk() {
        userNotOk.setLogin(LOGIN_OK);
        userNotOk.setPassword(PASSWORD_OK);
        assertThrows(RuntimeException.class, () -> {
            userValidator.validate(userNotOk);
        });
    }

    @Test
    void validate_userAgeToSmall_notOk() {
        userNotOk.setLogin(LOGIN_OK);
        userNotOk.setPassword(PASSWORD_OK);
        assertThrows(ValidationException.class, () -> {
            userValidator.validate(userNotOk);
        });
    }

    @Test
    void validateLogin_userLoginNull_notOk() {
        userNotOk.setLogin(null);
        assertThrows(RuntimeException.class, () -> {
            userValidator.validateLogin(userNotOk.getLogin());
        });
    }

    @Test
    void validateLogin_userLoginToShort_notOk() {
        assertThrows(ValidationException.class, () -> {
            userValidator.validateLogin(userNotOk.getLogin());
        });
    }

    @Test
    void validateLogin_userLoginContainSpace_notOk() {
        userNotOk.setLogin("examp le");
        assertThrows(ValidationException.class, () -> {
            userValidator.validateLogin(userNotOk.getLogin());
        });
    }

    @Test
    void validatePassword_userPasswordNull_notOk() {
        userNotOk.setPassword(null);
        assertThrows(RuntimeException.class, () -> {
            userValidator.validatePassword(userNotOk.getPassword());
        });
    }

    @Test
    void validatePassword_userPasswordToShort_notOk() {
        assertThrows(ValidationException.class, () -> {
            userValidator.validatePassword(userNotOk.getPassword());
        });
    }

    @Test
    void validateAge_userAgeNull_notOk() {
        userNotOk.setAge(null);
        assertThrows(RuntimeException.class, () -> {
            userValidator.validateAge(userNotOk.getAge());
        });
    }

    @Test
    void validateAge_userAgeToSmall_notOk() {
        assertThrows(ValidationException.class, () -> {
            userValidator.validateAge(userNotOk.getAge());
        });
    }
}