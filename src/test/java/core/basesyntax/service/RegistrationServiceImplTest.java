package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.UserRegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl REGISTRATION_SERVICE;
    private static User VALID_USER;

    @BeforeAll
    static void beforeAll() {
        REGISTRATION_SERVICE = new RegistrationServiceImpl();
        VALID_USER = User.of("valid_login", "valid_password", 18);
    }

    @BeforeEach
    void setUp() {
        Storage.people.clear();
    }

    @Test
    void validUserCase_Ok() {
        User expected = VALID_USER;
        User actual = REGISTRATION_SERVICE.register(VALID_USER);
        assertEquals(expected, actual);
    }

    @Test
    void userWithExistingLogin_NotOk() {
        REGISTRATION_SERVICE.register(VALID_USER);
        User existingLoginUser = User.of(VALID_USER.getLogin(), "else_password", 19);
        assertThrows(UserRegistrationException.class, () -> {
            REGISTRATION_SERVICE.register(existingLoginUser);
        });
    }

    @Test
    void nullUser_NotOk() {
        assertThrows(UserRegistrationException.class, () -> {
            REGISTRATION_SERVICE.register(null);
        });
    }

    @Test
    void userWithInvalidLogin_NotOk() {
        String invalidLogin = "12345";
        User invalidLoginUser = User.of(invalidLogin, VALID_USER.getPassword(),
                VALID_USER.getAge());
        assertThrows(UserRegistrationException.class, () -> {
            REGISTRATION_SERVICE.register(invalidLoginUser);
        });
        String nullLogin = null;
        User nullLoginUser = User.of(nullLogin, VALID_USER.getPassword(), VALID_USER.getAge());
        assertThrows(UserRegistrationException.class, () -> {
            REGISTRATION_SERVICE.register(nullLoginUser);
        });
    }

    @Test
    void userWithInvalidPassword_NotOk() {
        String invalidPassword = "12345";
        User invalidPasswordUser = User.of(VALID_USER.getLogin(), invalidPassword,
                VALID_USER.getAge());
        assertThrows(UserRegistrationException.class, () -> {
            REGISTRATION_SERVICE.register(invalidPasswordUser);
        });
        String nullPassword = null;
        User nullPasswordUser = User.of(VALID_USER.getLogin(), nullPassword, VALID_USER.getAge());
        assertThrows(UserRegistrationException.class, () -> {
            REGISTRATION_SERVICE.register(nullPasswordUser);
        });
    }

    @Test
    void userWithInvalidAge_NotOk() {
        Integer invalidLoverLimit = 17;
        User invalidAgeUserLover = User.of(VALID_USER.getLogin(), VALID_USER.getPassword(),
                invalidLoverLimit);
        assertThrows(UserRegistrationException.class, () -> {
            REGISTRATION_SERVICE.register(invalidAgeUserLover);
        });
        Integer invalidUpperLimit = 151;
        User invalidAgeUserUpper = User.of(VALID_USER.getLogin(), VALID_USER.getPassword(),
                invalidUpperLimit);
        assertThrows(UserRegistrationException.class, () -> {
            REGISTRATION_SERVICE.register(invalidAgeUserUpper);
        });
        Integer nullAge = null;
        User nullAgeUser = User.of(VALID_USER.getLogin(), VALID_USER.getPassword(), nullAge);
        assertThrows(UserRegistrationException.class, () -> {
            REGISTRATION_SERVICE.register(nullAgeUser);
        });
    }
}
