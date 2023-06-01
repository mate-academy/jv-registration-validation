
package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RegistrationServiceImplTest {
    static final String VALID_LOGIN = "LoveCraft";
    static final String VALID_PASSWORD = "NECRONOMICON";
    static final int VALID_AGE = 36;
    private User validUser = new User(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);
    private RegistrationService service = new RegistrationServiceImpl();

    @BeforeEach
    void setUp() {
        Storage.people.clear();
    }

    @Test
    void register_userIsPresent_NotOk() {
        service.register(validUser);
        assertThrows(RegistrationException.class, () -> service.register(validUser),
                "User with current login already added");
    }

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
        String shortLogin = "short";
        User user = new User(shortLogin, VALID_PASSWORD, VALID_AGE);
        assertThrows(RegistrationException.class, () -> service.register(user),
                "Expected RegistrationException to be thrown for login shorter than 6 characters");
    }

    @Test
    void register_loginHasValidLength_Ok() {
        String minLogin = "abcdef";
        User userMinLogin = new User(minLogin, VALID_PASSWORD, VALID_AGE);
        String message = "User with valid login length must be added";
        service.register(userMinLogin);
        assertEquals(1, Storage.people.size(), message);
        service.register(validUser);
        assertEquals(2, Storage.people.size(), message);
    }

    @Test
    void register_passwordIsNull_NotOk() {
        User user = new User(VALID_LOGIN, null, VALID_AGE);
        assertThrows(RegistrationException.class, () -> service.register(user),
                "Expected RegistrationException to be thrown for null-password ");
    }

    @Test
    void register_passwordIsEmpty_NotOk() {
        String emptyPassword = "";
        User user = new User(VALID_PASSWORD, emptyPassword, VALID_AGE);
        assertThrows(RegistrationException.class, () -> service.register(user),
                "Expected RegistrationException to be thrown for empty password");
    }

    @Test
    void register_passwordIsShorterThanValid_NotOk() {
        String shortPassword = "short";
        User user = new User(shortPassword, VALID_PASSWORD, VALID_AGE);
        assertThrows(RegistrationException.class, () -> service.register(user),
                "Expected RegistrationException to be thrown for password shorter than 6 chars");
    }

    @Test
    void register_passwordHasValidLength_Ok() {
        User userMinPass = new User(VALID_LOGIN, "123456", VALID_AGE);
        String message = "User with valid password length must be added";
        service.register(userMinPass);
        assertEquals(1, Storage.people.size(), message);
        service.register(validUser);
        assertEquals(2, Storage.people.size(), message);
    }

    @Test
    void register_ageIsLessThanZero_NotOk() {
        String message = "Expected RegistrationException to be thrown for age less 0";
        User unborn1 = new User(VALID_LOGIN, VALID_PASSWORD, -1);
        User unborn2 = new User(VALID_LOGIN, VALID_PASSWORD, -10);
        User unborn3 = new User(VALID_LOGIN, VALID_PASSWORD, -18);
        assertThrows(RegistrationException.class, () -> service.register(unborn1), message);
        assertThrows(RegistrationException.class, () -> service.register(unborn2), message);
        assertThrows(RegistrationException.class, () -> service.register(unborn3), message);
    }

    @Test
    void register_ageIsLessThanValid_NotOk() {
        String message = "Expected RegistrationException to be thrown for age less valid";
        User notAdult1 = new User(VALID_LOGIN, VALID_PASSWORD, 0);
        User notAdult2 = new User(VALID_LOGIN, VALID_PASSWORD, 13);
        User notAdult3 = new User(VALID_LOGIN, VALID_PASSWORD, 17);
        assertThrows(RegistrationException.class, () -> service.register(notAdult1), message);
        assertThrows(RegistrationException.class, () -> service.register(notAdult2), message);
        assertThrows(RegistrationException.class, () -> service.register(notAdult3), message);
    }

    @Test
    void register_ageIsValid_Ok() {
        User userJustAdult = new User(VALID_LOGIN, VALID_PASSWORD, 18);
        String message = "User with valid age must be added";
        service.register(userJustAdult);
        assertEquals(1, Storage.people.size(), message);
        service.register(validUser);
        assertEquals(2, Storage.people.size(), message);
    }
}
