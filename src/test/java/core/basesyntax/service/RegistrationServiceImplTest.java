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
    }

    @Test
    void register_5LengthLogin_notOk() {
        user.setLogin("abcde");
        assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_6LengthLogin_Ok() {
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_8LengthLogin_Ok() {
        user.setLogin("login123");
        User actual = registrationService.register(user);
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
    void register_3LengthPassword_NotOk() {
        user.setPassword("123");
        assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_5LengthPassword_NotOk() {
        user.setPassword("12345");
        assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_6LengthPassword_Ok() {
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_8LengthPassword_Ok() {
        user.setPassword("12345678");
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_nullAge_NotOk() {
        user.setAge(null);
        assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_negativeAge_NotOk() {
        user.setAge(-10);
        assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_zeroAge_NotOk() {
        user.setAge(0);
        assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_17Age_NotOk() {
        user.setAge(17);
        assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_18Age_Ok() {
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_30Age_Ok() {
        user.setAge(30);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }
}