package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
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
    void register_user_isOK() {
        User expected = new User("Ivan2004", "1234567", 20);
        User actual = registrationService.register(expected);
        assertEquals(actual, expected);
    }

    @Test
    void register_userPassword_isNotOK() {
        User expected = new User("Ivan204567", "12", 20);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(expected);
        });
    }

    @Test
    void register_userLogin_isNotOK() {
        User expected = new User("Ivan", "1234567", 18);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(expected);
        });
    }

    @Test
    void register_userLoginIsNull_isNotOK() {
        User expected = new User(null, "1234567", 18);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(expected);
        });
    }

    @Test
    void register_userPasswordIsNull_isNotOK() {
        User expected = new User("Ivan20044567", null, 18);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(expected);
        });
    }

    @Test
    void register_userAgeIsNull_isNotOK() {
        User expected = new User("John2395", "1234567", null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(expected);
        });
    }

    @Test
    void register_ageIsNot_isNotOK() {
        User expected = new User("Iv45HG", "1234567", 15);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(expected);
        });
    }

    @Test
    void register_userInDb_isNotOK() {
        User user = new User("JohnDoe", "john123", 28);
        Storage.people.add(user);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }
}
