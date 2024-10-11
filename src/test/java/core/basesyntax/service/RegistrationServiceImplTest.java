package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_validUser_isOK() {
        User expected = new User("BobGoo", "123456", 18);
        User actual = registrationService.register(expected);
        assertEquals(expected, actual);
    }

    @Test
    void register_userPasswordIsShort_isNotOK() {
        User expected = new User("BobWithShortPassword", "12", 18);
        assertThrows(RegistrationException.class, () -> registrationService.register(expected));
    }

    @Test
    void register_userLoginIsShort_isNotOK() {
        User expected = new User("Bob", "1234567", 18);
        assertThrows(RegistrationException.class, () -> registrationService.register(expected));
    }

    @Test
    void register_userUnderage_isNotOK() {
        User expected = new User("BobChild", "1234567", 15);
        assertThrows(RegistrationException.class, () -> registrationService.register(expected));
    }

    @Test
    void register_userLoginIsNull_isNotOK() {
        User expected = new User(null, "1234567", 18);
        assertThrows(RegistrationException.class, () -> registrationService.register(expected));
    }

    @Test
    void register_userPasswordIsNull_isNotOk() {
        User expected = new User("BobWithoutPassword", null, 18);
        assertThrows(RegistrationException.class, () -> registrationService.register(expected));
    }

    @Test
    void register_userAgeIsNull_isNotOK() {
        User expected = new User("BobWithoutAge", "1234567", null);
        assertThrows(RegistrationException.class, () -> registrationService.register(expected));
    }

    @Test
    void register_userAlreadyExists_isNotOK() {
        User expected = new User("BobAlreadyExists", "1234567", 28);
        Storage.people.add(expected);
        assertThrows(RegistrationException.class, () -> registrationService.register(expected));
    }

    @Test
    void register_negativeAge_isNotOK() {
        User expected = new User("BobWithNegativeAge", "1234567", -28);
        assertThrows(RegistrationException.class, () -> registrationService.register(expected));
    }

    @Test
    void register_nullUser_isNotOK() {
        assertThrows(RegistrationException.class, () -> registrationService.register(null));
    }
}
