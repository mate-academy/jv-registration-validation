
package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    static final String VALID_LOGIN = "LoveCraft";
    static final String VALID_PASSWORD = "NECRONOMICON";
    static final int VALID_AGE = 36;
    private User validUser = new User(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);
    private RegistrationService service = new RegistrationServiceImpl();

    @Test
    void register_nullAge_NotOk() {
        User user = new User(null, VALID_PASSWORD, VALID_AGE);
        assertThrows(RegistrationException.class, () -> service.register(user),
                "Expected RegistrationException to be thrown for null-login ");
    }

    @Test
    void register_loginIsEmpty_NotOk() {
        String emptyLogin = "";
        User user = new User(emptyLogin, VALID_PASSWORD, VALID_AGE);
        assertThrows(RegistrationException.class, () -> service.register(user),
                "Expected RegistrationException to be thrown for empty login ");
    }

    @Test
    void register_loginIsShorterThanValid_NotOk() {
        String shortLog = "short";
        User user = new User(shortLog, VALID_PASSWORD, VALID_AGE);
        assertThrows(RegistrationException.class, () -> service.register(user),
                "Expected RegistrationException to be thrown for login shorter than 6 characters");
    }

    @Test
    void register_loginHasValidLength_Ok() {
        String minLogin = "abcdef";
        User userMinLogin = new User(minLogin, VALID_PASSWORD, VALID_AGE);
        String message = "User with valid login length must be added";
        assertEquals(userMinLogin, service.register(userMinLogin), message);
        assertEquals(validUser, service.register(validUser), message);
    }

    @Test
    void register_passwordIsNull_NotOk() {
        User user = new User(VALID_LOGIN, null, VALID_AGE);
        assertThrows(RegistrationException.class, () -> service.register(user),
                "Expected RegistrationException to be thrown for null-password ");
    }

    @Test
    void register_passwordIsEmpty_NotOk() {
        String emptyPass = "";
        User user = new User(VALID_PASSWORD, emptyPass, VALID_AGE);
        assertThrows(RegistrationException.class, () -> service.register(user),
                "Expected RegistrationException to be thrown for empty password");
    }

    @Test
    void register_passwordIsShorterThanValid_NotOk() {
        String shortPass = "short";
        User user = new User(shortPass, VALID_PASSWORD, VALID_AGE);
        assertThrows(RegistrationException.class, () -> service.register(user),
                "Expected RegistrationException to be thrown for password shorter than 6 chars");
    }

    @Test
    void register_passwordHasValidLength_Ok() {
        String minPass = "123456";
        User userMinPass = new User(VALID_LOGIN, minPass, VALID_AGE);
        String message = "User with valid password length must be added";
        assertEquals(userMinPass, service.register(userMinPass), message);
        assertEquals(validUser, service.register(validUser), message);
    }

    @Test
    void register_ageIsLessThanZero_NotOk() {
        int age1 = -1;
        int age2 = -10;
        int age3 = -18;
        String message = "Expected RegistrationException to be thrown for age less 0";
        User unborn1 = new User(VALID_LOGIN, VALID_PASSWORD, age1);
        User unborn2 = new User(VALID_LOGIN, VALID_PASSWORD, age2);
        User unborn3 = new User(VALID_LOGIN, VALID_PASSWORD, age3);
        assertThrows(RegistrationException.class, () -> service.register(unborn1), message);
        assertThrows(RegistrationException.class, () -> service.register(unborn2), message);
        assertThrows(RegistrationException.class, () -> service.register(unborn3), message);
    }

    @Test
    void register_ageIsLessThanValid_NotOk() {
        int age1 = 0;
        int age2 = 13;
        int age3 = 17;
        String message = "Expected RegistrationException to be thrown for age less valid";
        User notAdult1 = new User(VALID_LOGIN, VALID_PASSWORD, age1);
        User notAdult2 = new User(VALID_LOGIN, VALID_PASSWORD, age2);
        User notAdult3 = new User(VALID_LOGIN, VALID_PASSWORD, age3);
        assertThrows(RegistrationException.class, () -> service.register(notAdult1), message);
        assertThrows(RegistrationException.class, () -> service.register(notAdult2), message);
        assertThrows(RegistrationException.class, () -> service.register(notAdult3), message);
    }

    @Test
    void register_ageIsValid_Ok() {
        int age = 18;
        User userJustAdult = new User(VALID_LOGIN, VALID_PASSWORD, age);
        String message = "User with valid age must be added";
        assertEquals(userJustAdult, service.register(userJustAdult), message);
        assertEquals(validUser, service.register(validUser), message);
    }
}
