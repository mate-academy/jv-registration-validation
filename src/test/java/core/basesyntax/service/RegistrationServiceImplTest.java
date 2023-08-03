package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void clear() {
        Storage.people.clear();
    }

    @Test
    void register_nullPassword_notOk() {
        User user = new User("abcbdddal",null, 20);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_passwordTheeLessCharacters_notOk() {
        User user = new User("abcbdddal", "abc", 20);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_passwordFiveLessCharacters_notOk() {
        User user = new User("abcbdddal", "abcdf", 20);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullLogin_notOk() {
        User user = new User(null,"abscdsj", 20);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_loginThreeLessCharacters_notOk() {
        User user = new User("abc", "abscdsj", 20);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_loginFiveLessCharacters_notOk() {
        User user = new User("abcdf", "abscdsj", 20);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullAge_notOk() {
        User user = new User("abscdsj", "abscdds", null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_ageLess_notOk() {
        User user = new User("abscdsj", "abscdds",12);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user); });
    }

    @Test
    void register_userEightLength_ok() {
        User user = new User("adfghjkl", "akjhytvd", 20);
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void register_userSixLength_ok() {
        User user = new User("adfghj", "akjhyt", 20);
        assertEquals(user, registrationService.register(user));
    }
}
