package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.UserRegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String VALID_LOGIN = "valid login";
    private static final String VALID_PASSWORD = "valid password";
    private static final int VALID_AGE = 18;
    private static final String INVALID_LOGIN_PASSWORD = "test";
    private final StorageDaoImpl storageDao = new StorageDaoImpl();
    private final RegistrationServiceImpl registrationService = new RegistrationServiceImpl();
    private String expectedMessage;
    private User validUser;

    @BeforeEach
    void init() {
        validUser = new User(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);
        Storage.people.clear();
    }

    @Test
    @DisplayName("register_throws_UserRegistrationException_"
            + "if_user_is_null")
    void register_nullUser_notOk() {
        expectedMessage = "User can`t be null";
        UserRegistrationException expectedException = assertThrows(
                UserRegistrationException.class,
                () -> registrationService.register(null)
        );
        assertEquals(
                expectedMessage,
                expectedException.getMessage()
        );
    }

    @Test
    @DisplayName("register_throws_UserRegistrationException_"
            + "if_user_login_is_null")
    void register_nullUserLogin_notOk() {
        validUser.setLogin(null);
        expectedMessage = "User login can`t be null";
        UserRegistrationException expectedException = assertThrows(
                UserRegistrationException.class,
                () -> registrationService.register(validUser)
        );
        assertEquals(
                expectedMessage,
                expectedException.getMessage()
        );
    }

    @Test
    @DisplayName("register_throws_UserRegistrationException_"
            + "if_user_password_is_null")
    void register_nullUserPassword_notOk() {
        validUser.setPassword(null);
        expectedMessage = "User password can`t be null";
        UserRegistrationException expectedException = assertThrows(
                UserRegistrationException.class,
                () -> registrationService.register(validUser)
        );
        assertEquals(
                expectedMessage,
                expectedException.getMessage()
        );
    }

    @Test
    @DisplayName("register_throws_UserRegistrationException_"
            + "if_user_age_is_null")
    void register_nullUserAge_notOk() {
        validUser.setAge(null);
        expectedMessage = "User age can`t be null";
        UserRegistrationException expectedException = assertThrows(
                UserRegistrationException.class,
                () -> registrationService.register(validUser)
        );
        assertEquals(
                expectedMessage,
                expectedException.getMessage()
        );
    }

    @Test
    @DisplayName("register_throws_UserRegistrationException_"
            + "if_user_login_length_less_then_six_characters")
    void register_UserLoginLengthLessThenSixCharacters_notOk() {
        validUser.setLogin(INVALID_LOGIN_PASSWORD);
        expectedMessage = "User login %s length less then %d characters";
        UserRegistrationException expectedException = assertThrows(
                UserRegistrationException.class,
                () -> registrationService.register(validUser)
        );
        assertEquals(
                String.format(expectedMessage, validUser.getLogin(), 6),
                expectedException.getMessage()
        );
    }

    @Test
    @DisplayName("register_throws_UserRegistrationException_"
            + "if_user_password_length_less_then_six_characters")
    void register_UserPasswordLengthLessThenSixCharacters_notOk() {
        validUser.setPassword(INVALID_LOGIN_PASSWORD);
        expectedMessage = "User password %s length less then %d characters";
        UserRegistrationException expectedException = assertThrows(
                UserRegistrationException.class,
                () -> registrationService.register(validUser)
        );
        assertEquals(
                String.format(expectedMessage, validUser.getPassword(), 6),
                expectedException.getMessage()
        );
    }

    @Test
    @DisplayName("register_throws_UserRegistrationException_"
            + "if_user_age_less_then_eighteen")
    void register_UserAgeLessThenEighteen_notOk() {
        validUser.setAge(17);
        expectedMessage = "User age %d less then %d years";
        UserRegistrationException expectedException = assertThrows(
                UserRegistrationException.class,
                () -> registrationService.register(validUser)
        );
        assertEquals(
                String.format(expectedMessage, validUser.getAge(), 18),
                expectedException.getMessage()
        );
    }

    @Test
    @DisplayName("register_throws_UserRegistrationException_"
            + "if_user_already_exists")
    void register_UserAlreadyExists_notOk() {
        storageDao.add(validUser);
        User invalidUser = new User(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);
        expectedMessage = "User with login %s already exists";
        UserRegistrationException expectedException = assertThrows(
                UserRegistrationException.class,
                () -> registrationService.register(invalidUser)
        );
        assertEquals(
                String.format(expectedMessage, invalidUser.getLogin()),
                expectedException.getMessage()
        );
    }

    @Test
    @DisplayName("register_returns_new_registered_user")
    void register_registerNewUser_ok() {
        User expectedUser = new User(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);
        expectedUser.setId(1L);
        int expectedSize = Storage.people.size() + 1;
        assertNull(
                validUser.getId(),
                "User id must be null before registration complete"
        );
        assertEquals(
                expectedUser,
                registrationService.register(validUser)
        );
        assertEquals(
                expectedSize,
                Storage.people.size()
        );
    }
}
