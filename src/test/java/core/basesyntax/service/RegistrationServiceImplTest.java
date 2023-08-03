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
    void register_passwordLessCharacters_notOk() {
        User user = new User("abcbdddal", "abc", 20);
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
    void register_loginLessCharacters_notOk() {
        User user = new User("abc", "abscdsj", 20);
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
    void register_user_ok() {
        User user = new User("adfghjkl", "akjhytvd", 20);
        assertEquals(user, registrationService.register(user));
    }
}
