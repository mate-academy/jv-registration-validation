package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private User user;
    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        Storage.people.clear();

        user = new User();
        user.setLogin("login1");
        user.setId(123456789L);
        user.setAge(18);
        user.setPassword("123456");
    }

    @Test
    void register_nullUser_notOK() {
        assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(null));
    }

    @Test
    void register_nullLogin_notOK() {
        user.setLogin(null);
        assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_emptyLogin_notOK() {
        user.setLogin("");
        assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_shortLogin_notOk() {
        user.setLogin("abc");
        assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(user));
        user.setLogin("abcde");
        assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_loginLength_Ok() {
        User actual = registrationService.register(user);
        assertEquals(user, actual);
        user.setLogin("login123");
        actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_loginAlreadyExists_NotOk() {
        Storage.people.add(user);
        assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_NotOk() {
        user.setPassword(null);
        assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_emptyPassword_NotOk() {
        user.setPassword("");
        assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_shortPassword_NotOk() {
        user.setPassword("123");
        assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(user));
        user.setPassword("12345");
        assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_passwordLength_Ok() {
        User actual = registrationService.register(user);
        assertEquals(user, actual);
        user.setPassword("12345678");
        actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_nullAge_NotOk() {
        user.setAge(null);
        assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_age_NotOk() {
        user.setAge(-10);
        assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(user));
        user.setAge(0);
        assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(user));
        user.setAge(17);
        assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_age_Ok() {
        User actual = registrationService.register(user);
        assertEquals(user, actual);
        user.setAge(30);
        actual = registrationService.register(user);
        assertEquals(user, actual);
    }
}