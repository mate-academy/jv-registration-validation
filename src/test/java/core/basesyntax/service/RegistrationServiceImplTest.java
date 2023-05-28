package core.basesyntax.service;

import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    static final String VALID_LOG = "LoveCraft";
    static final String VALID_PASS = "NECRONOMICON";
    static final int VALID_AGE = 36;
    User validUser = new User(VALID_LOG, VALID_PASS, VALID_AGE);
    RegistrationService service = new RegistrationServiceImpl();


    @Test
    void loginIsNull_NotOk() {
        RegistrationService service = new RegistrationServiceImpl();
        User user = new User(null, VALID_PASS, VALID_AGE);
        assertThrows(RegistrationException.class, () -> service.register(user),
                "Expected RegistrationException to be thrown for null-login ");
    }

    @Test
    void loginIsEmpty_NotOk() {
        String emptyLogin = "";
        User user = new User(emptyLogin, VALID_PASS, VALID_AGE);
        assertThrows(RegistrationException.class, () -> service.register(user),
                "Expected RegistrationException to be thrown for empty login ");
    }

    @Test
    void loginIsShorterThanValid_NotOk() {
        String shortLog = "short";
        User user = new User(shortLog, VALID_PASS, VALID_AGE);
        assertThrows(RegistrationException.class, () -> service.register(user),
                "Expected RegistrationException to be thrown for login shorter than 6 characters");
    }

    @Test
    void loginHasValidLength_Ok() {
        String minLogin = "abcdef";
        User userMinLogin = new User(minLogin, VALID_PASS, VALID_AGE);
        String message = "User with valid login length must be added";
        assertEquals(userMinLogin, service.register(userMinLogin), message);
        assertEquals(validUser, service.register(validUser), message);
    }

    @Test
    void passwordIsNull_NotOk() {
        User user = new User(VALID_LOG, null, VALID_AGE);
        assertThrows(RegistrationException.class, () -> service.register(user),
                "Expected RegistrationException to be thrown for null-password ");
    }

    @Test
    void passwordIsEmpty_NotOk() {
        String emptyPass = "";
        User user = new User(VALID_PASS, emptyPass, VALID_AGE);
        assertThrows(RegistrationException.class, () -> service.register(user),
                "Expected RegistrationException to be thrown for empty password ");
    }

    @Test
    void passwordIsShorterThanValid_NotOk() {
        String shortPass = "short";
        User user = new User(shortPass, VALID_PASS, VALID_AGE);
        assertThrows(RegistrationException.class, () -> service.register(user),
                "Expected RegistrationException to be thrown for password shorter than 6 characters");
    }

    @Test
    void passwordHasValidLength_Ok() {
        String minPass = "123456";
        User userMinPass = new User(VALID_LOG, minPass, VALID_AGE);
        String message = "User with valid password length must be added";
        assertEquals(userMinPass, service.register(userMinPass), message);
        assertEquals(validUser, service.register(validUser), message);
    }

    @Test
    void ageIsLessThanZero_NotOk() {
        int age1 = -1;
        int age2 = -10;
        int age3 = -18;
        String message = "Expected RegistrationException to be thrown for age less 0";
        User unborn1 = new User(VALID_LOG, VALID_PASS, age1);
        User unborn2 = new User(VALID_LOG, VALID_PASS, age2);
        User unborn3 = new User(VALID_LOG, VALID_PASS, age3);
        assertThrows(RegistrationException.class, () -> service.register(unborn1), message);
        assertThrows(RegistrationException.class, () -> service.register(unborn2), message);
        assertThrows(RegistrationException.class, () -> service.register(unborn3), message);
    }

    @Test
    void ageIsLessThanValid_NotOk() {
        int age1 = 0;
        int age2 = 13;
        int age3 = 17;
        String message = "Expected RegistrationException to be thrown for age less valid";
        User notAdult1 = new User(VALID_LOG, VALID_PASS, age1);
        User notAdult2 = new User(VALID_LOG, VALID_PASS, age2);
        User notAdult3 = new User(VALID_LOG, VALID_PASS, age3);
        assertThrows(RegistrationException.class, () -> service.register(notAdult1), message);
        assertThrows(RegistrationException.class, () -> service.register(notAdult2), message);
        assertThrows(RegistrationException.class, () -> service.register(notAdult3), message);
    }

    @Test
    void ageIsValid_Ok() {
        int age = 18;
        User userJustAdult = new User(VALID_LOG, VALID_PASS, age);
        String message = "User with valid age must be added";
        assertEquals(userJustAdult, service.register(userJustAdult), message);
        assertEquals(validUser, service.register(validUser), message);
    }

}