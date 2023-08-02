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
    void passwordNull_NotOK() {
        User user = new User("abcbdddal",null, 20);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void passwordLessCharacters_NotOK() {
        User user = new User("abcbdddal", "abc", 20);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void loginNull_NotOK() {
        User user = new User(null,"abscdsj", 20);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void loginLessCharacters_NotOK() {
        User user = new User("abc", "abscdsj", 20);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void ageNull_NotOK() {
        User user = new User("abscdsj", "abscdds", null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void ageLess_NotOK() {
        User user = new User("abscdsj", "abscdds",12);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user); });
    }

    @Test
    void user_OK() {
        User user = new User("adfghjkl", "akjhytvd", 20);
        assertEquals(user, registrationService.register(user));
    }
}
