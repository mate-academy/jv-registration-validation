package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationServiceImpl registrationService;
    private User defaultCorrectUser;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
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
                () -> registrationService.register(user));
    }

    @Test
    void register_minorUser_NotOk() {
        defaultCorrectUser.setAge(15);
        assertThrows(InvalidUserException.class,
                () -> registrationService.register(defaultCorrectUser));

    }

    @Test
    void register_correctAge_Ok() {
        defaultCorrectUser.setAge(80);
        registrationService.register(defaultCorrectUser);
        assertFalse(registrationService.checkLogin(defaultCorrectUser));
    }

    @Test
    void register_nullAge_NotOk() {
        defaultCorrectUser.setAge(null);
        assertThrows(InvalidUserException.class,
                () -> registrationService.register(defaultCorrectUser));
    }

    @Test
    void register_incorrectPassword_NotOk() {
        defaultCorrectUser.setPassword("123");
        assertThrows(InvalidUserException.class,
                () -> registrationService.register(defaultCorrectUser));
    }

    @Test
    void register_emptyPassword_NotOk() {
        defaultCorrectUser.setPassword("");
        assertThrows(InvalidUserException.class,
                () -> registrationService.register(defaultCorrectUser));
    }

    @Test
    void register_nullPassword_NotOk() {
        defaultCorrectUser.setPassword(null);
        assertThrows(InvalidUserException.class,
                () -> registrationService.register(defaultCorrectUser));
    }

    @Test
    void register_fiveLengthPassword_NotOk() {
        defaultCorrectUser.setPassword("12345");
        assertThrows(InvalidUserException.class,
                () -> registrationService.register(defaultCorrectUser));
    }

    @Test
    void register_sixLengthPassword_Ok() {
        defaultCorrectUser.setPassword("123456");
        registrationService.register(defaultCorrectUser);
        assertFalse(registrationService.checkLogin(defaultCorrectUser));
    }

    @Test
    void register_longPassword_Ok() {
        defaultCorrectUser.setPassword("12345678901234");
        registrationService.register(defaultCorrectUser);
        assertFalse(registrationService.checkLogin(defaultCorrectUser));
    }

    @Test
    void register_fiveLengthLogin_NotOk() {
        defaultCorrectUser.setLogin("12345");
        assertThrows(InvalidUserException.class,
                () -> registrationService.register(defaultCorrectUser));
    }

    @Test
    void register_sixLengthLogin_Ok() {
        defaultCorrectUser.setLogin("123456");
        registrationService.register(defaultCorrectUser);
        assertFalse(registrationService.checkLogin(defaultCorrectUser));
    }

    @Test
    void register_nullLogin_NotOk() {
        defaultCorrectUser.setLogin(null);
        assertThrows(InvalidUserException.class,
                () -> registrationService.register(defaultCorrectUser));
    }

    @Test
    void register_shortLogin_NotOk() {
        defaultCorrectUser.setLogin("12");
        assertThrows(InvalidUserException.class,
                () -> registrationService.register(defaultCorrectUser));
    }

    @Test
    void register_longLogin_Ok() {
        defaultCorrectUser.setLogin("124567890123");
        registrationService.register(defaultCorrectUser);
        assertFalse(registrationService.checkLogin(defaultCorrectUser));
    }

    @Test
    void register_existingUser_NotOk() {
        Storage.people.add(defaultCorrectUser);
        assertThrows(InvalidUserException.class,
                () -> registrationService.register(defaultCorrectUser));
    }

    @Test
    void register_correctUser_Ok() {
        registrationService.register(defaultCorrectUser);
        assertFalse(registrationService.checkLogin(defaultCorrectUser));
    }
}
