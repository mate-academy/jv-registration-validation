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
        defaultCorrectUser = new User();
        defaultCorrectUser.setAge(20);
        defaultCorrectUser.setLogin("correct login");
        defaultCorrectUser.setPassword("correctPassword");
    }

    @Test
    void register_NullUser_NotOk() {
        User user = null;
        assertThrows(InvalidUserException.class,
                () -> REGISTRATION_SERVICE.register(user));
    }

    @Test
    void register_MinorUser_NotOk() {
        defaultCorrectUser.setAge(15);
        assertThrows(InvalidUserException.class,
                () -> REGISTRATION_SERVICE.register(defaultCorrectUser));
    }

    @Test
    void register_IncorrectPassword_NotOk() {
        defaultCorrectUser.setPassword("123");
        assertThrows(InvalidUserException.class,
                () -> REGISTRATION_SERVICE.register(defaultCorrectUser));
    }

    @Test
    void register_IncorrectLogin_NotOk() {
        defaultCorrectUser.setLogin("4321");
        assertThrows(InvalidUserException.class,
                () -> REGISTRATION_SERVICE.register(defaultCorrectUser));
    }

    @Test
    void register_ExistingUser_NotOk() {
        Storage.people.clear();
        REGISTRATION_SERVICE.register(defaultCorrectUser);
        User user = new User();
        user.setLogin(defaultCorrectUser.getLogin());
        user.setAge(20);
        user.setPassword("abcdefg");
        assertThrows(InvalidUserException.class,
                () -> REGISTRATION_SERVICE.register(defaultCorrectUser));
    }

    @Test
    void register_CorrectUser_Ok() {
        REGISTRATION_SERVICE.register(defaultCorrectUser);
        assertFalse(REGISTRATION_SERVICE.checkUserLoginForIdentity(defaultCorrectUser));
    }
}
