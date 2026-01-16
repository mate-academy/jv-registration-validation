package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RegistrationServiceImplTest {
    private RegistrationServiceImpl registrationService;

    @BeforeEach
    void beforeEach() {
        registrationService = new RegistrationServiceImpl();
        Storage.people.clear();
    }

    @Test
    void register_nullLogin_throwsException() {
        User actual = new User(null, "abcdef", 25);
        assertThrows(RegistrationException.class, () -> registrationService.register(actual));
    }

    @Test
    void register_nullPassword_throwsException() {
        User actual = new User("abcdef", null, 25);
        assertThrows(RegistrationException.class, () -> registrationService.register(actual));
    }

    @Test
    void register_nullAge_ThrowsException() {
        User actual = new User("abcdef", "abcdef", null);
        assertThrows(RegistrationException.class, () -> registrationService.register(actual));
    }

    @Test
    void register_toShortLogin_throwsException() {
        User actual = new User("abcde", "abcdef", 25);
        assertThrows(RegistrationException.class, () -> registrationService.register(actual));
    }

    @Test
    void register_toShortPassword_throwsException() {
        User actual = new User("abcdef", "abcde", 25);
        assertThrows(RegistrationException.class, () -> registrationService.register(actual));
    }

    @Test
    void register_duplicateLogin_throwsException() {
        User actual = new User("abcdef", "abcdef", 25);
        Storage.people.add(actual);
        assertThrows(RegistrationException.class, () -> registrationService.register(actual));
    }

    @Test
    void register_negativeAge_ThrowsException() {
        User actual = new User("abcdef", "abcdef", -10);
        assertThrows(RegistrationException.class, () -> registrationService.register(actual));
    }

    @Test
    void register_toSmallAge_ThrowsException() {
        User actual = new User("abcdef", "abcdef", 17);
        assertThrows(RegistrationException.class, () -> registrationService.register(actual));
    }

    @Test
    void register_exactLogin_Ok() {
        User actual = new User("abcdef", "abcdehjf", 22);
        User registredUser = registrationService.register(actual);
        assertEquals(actual, registredUser);
    }

    @Test
    void register_exactPassword_Ok() {
        User actual = new User("abcdefdsfdf", "abcdef", 22);
        User registredUser = registrationService.register(actual);
        assertEquals(actual, registredUser);
    }

    @Test
    void register_exactAge_Ok() {
        User actual = new User("abcdefdsfdf", "abcdedsfddf", 18);
        User registredUser = registrationService.register(actual);
        assertEquals(actual, registredUser);
    }

    @Test
    void register_emptyLogin_throwsException() {
        User actual = new User("", "abcdef", 25);
        assertThrows(RegistrationException.class, () -> registrationService.register(actual));
    }

    @Test
    void register_emptyPassword_throwsException() {
        User actual = new User("abcdef", "", 25);
        assertThrows(RegistrationException.class, () -> registrationService.register(actual));
    }

}
