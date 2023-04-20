package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final RegistrationServiceImpl REGISTRATION_SERVICE
            = new RegistrationServiceImpl();
    private User defaultCorrectUser;

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        defaultCorrectUser = new User();
        defaultCorrectUser.setAge(20);
        defaultCorrectUser.setLogin("correct login");
        defaultCorrectUser.setPassword("correctPassword");
    }

    @Test
    void register_nullUser_NotOk() {
        User user = null;
        assertThrows(InvalidUserException.class,
                () -> REGISTRATION_SERVICE.register(user));
    }

    @Test
    void register_minorUser_NotOk() {
        defaultCorrectUser.setAge(15);
        assertThrows(InvalidUserException.class,
                () -> REGISTRATION_SERVICE.register(defaultCorrectUser));

    }

    @Test
    void register_correctAge_Ok() {
        defaultCorrectUser.setAge(80);
        REGISTRATION_SERVICE.register(defaultCorrectUser);
        assertFalse(REGISTRATION_SERVICE.checkLogin(defaultCorrectUser));
    }

    @Test
    void register_nullAge_NotOk() {
        defaultCorrectUser.setAge(null);
        assertThrows(InvalidUserException.class,
                () -> REGISTRATION_SERVICE.register(defaultCorrectUser));
    }

    @Test
    void register_incorrectPassword_NotOk() {
        defaultCorrectUser.setPassword("123");
        assertThrows(InvalidUserException.class,
                () -> REGISTRATION_SERVICE.register(defaultCorrectUser));
    }

    @Test
    void register_emptyPassword_NotOk() {
        defaultCorrectUser.setPassword("");
        assertThrows(InvalidUserException.class,
                () -> REGISTRATION_SERVICE.register(defaultCorrectUser));
    }

    @Test
    void register_nullPassword_NotOk() {
        defaultCorrectUser.setPassword(null);
        assertThrows(InvalidUserException.class,
                () -> REGISTRATION_SERVICE.register(defaultCorrectUser));
    }

    @Test
    void register_fiveLengthPassword_NotOk() {
        defaultCorrectUser.setPassword("12345");
        assertThrows(InvalidUserException.class,
                () -> REGISTRATION_SERVICE.register(defaultCorrectUser));
    }

    @Test
    void register_sixLengthPassword_Ok() {
        defaultCorrectUser.setPassword("123456");
        REGISTRATION_SERVICE.register(defaultCorrectUser);
        assertFalse(REGISTRATION_SERVICE.checkLogin(defaultCorrectUser));
    }

    @Test
    void register_longPassword_Ok() {
        defaultCorrectUser.setPassword("12345678901234");
        REGISTRATION_SERVICE.register(defaultCorrectUser);
        assertFalse(REGISTRATION_SERVICE.checkLogin(defaultCorrectUser));
    }

    @Test
    void register_fiveLengthLogin_NotOk() {
        defaultCorrectUser.setLogin("12345");
        assertThrows(InvalidUserException.class,
                () -> REGISTRATION_SERVICE.register(defaultCorrectUser));
    }

    @Test
    void register_sixLengthLogin_Ok() {
        defaultCorrectUser.setLogin("123456");
        REGISTRATION_SERVICE.register(defaultCorrectUser);
        assertFalse(REGISTRATION_SERVICE.checkLogin(defaultCorrectUser));
    }

    @Test
    void register_nullLogin_NotOk() {
        defaultCorrectUser.setLogin(null);
        assertThrows(InvalidUserException.class,
                () -> REGISTRATION_SERVICE.register(defaultCorrectUser));
    }

    @Test
    void register_shortLogin_NotOk() {
        defaultCorrectUser.setLogin("12");
        assertThrows(InvalidUserException.class,
                () -> REGISTRATION_SERVICE.register(defaultCorrectUser));
    }

    @Test
    void register_longLogin_Ok() {
        defaultCorrectUser.setLogin("124567890123");
        REGISTRATION_SERVICE.register(defaultCorrectUser);
        assertFalse(REGISTRATION_SERVICE.checkLogin(defaultCorrectUser));
    }

    @Test
    void register_existingUser_NotOk() {
        Storage.people.add(defaultCorrectUser);
        User user = new User();
        user.setLogin(defaultCorrectUser.getLogin());
        user.setAge(20);
        user.setPassword("abcdefg");
        assertThrows(InvalidUserException.class,
                () -> REGISTRATION_SERVICE.register(defaultCorrectUser));
    }

    @Test
    void register_correctUser_Ok() {
        REGISTRATION_SERVICE.register(defaultCorrectUser);
        assertFalse(REGISTRATION_SERVICE.checkLogin(defaultCorrectUser));
    }
}
