package core.basesyntax.service;

import static core.basesyntax.db.Storage.people;

import core.basesyntax.exception.InvalidRegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String VALID_LOGIN = "Jameson";
    private static final String VALID_PASSWORD = "P@ssw0rd";
    private static final int VALID_AGE = 18;
    private RegistrationServiceImpl registrationService;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_validUser_Ok() {
        User expected = new User(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);
        User actual = registrationService.register(expected);
        Assertions.assertEquals(expected, actual);
        Assertions.assertTrue(people.contains(expected));
    }

    @Test
    void register_nullLogin_notOk() {
        User expected = new User(null, VALID_PASSWORD, VALID_AGE);
        Assertions.assertThrows(InvalidRegistrationException.class, () -> {
            registrationService.register(expected);
        });
        Assertions.assertFalse(people.contains(expected));
    }

    @Test
    void register_nullAge_notOk() {
        User expected = new User(VALID_LOGIN, VALID_PASSWORD, null);
        Assertions.assertThrows(InvalidRegistrationException.class, () -> {
            registrationService.register(expected);
        });
        Assertions.assertFalse(people.contains(expected));
    }

    @Test
    void register_nullPassword_notOk() {
        User expected = new User(VALID_LOGIN, null, VALID_AGE);
        Assertions.assertThrows(InvalidRegistrationException.class, () -> {
            registrationService.register(expected);
        });
        Assertions.assertFalse(people.contains(expected));
    }

    @Test
    void register_badPassword_notOk() {
        User expected = new User(VALID_LOGIN, "da1", VALID_AGE);
        Assertions.assertThrows(InvalidRegistrationException.class, () -> {
            registrationService.register(expected);
        });
        Assertions.assertFalse(people.contains(expected));
    }

    @Test
    void register_badAge_notOk() {
        User expected = new User(VALID_LOGIN, VALID_PASSWORD, 2);
        Assertions.assertThrows(InvalidRegistrationException.class, () -> {
            registrationService.register(expected);
        });
        Assertions.assertFalse(people.contains(expected));
    }

    @Test
    void register_badLogin_notOk() {
        User expected = new User("j2", VALID_PASSWORD, VALID_AGE);
        Assertions.assertThrows(InvalidRegistrationException.class, () -> {
            registrationService.register(expected);
        });
        Assertions.assertFalse(people.contains(expected));
    }

    @Test
    void register_invalidUser_notOk() {
        User expected = new User("j2", "dda", 5);
        Assertions.assertThrows(InvalidRegistrationException.class, () -> {
            registrationService.register(expected);
        });
        Assertions.assertFalse(people.contains(expected));
    }

    @Test
    void register_containsUser_notOk() {
        User expected = new User(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);
        people.add(expected);
        User actual = new User(VALID_LOGIN, "dasds21", 23);
        Assertions.assertThrows(InvalidRegistrationException.class, () -> {
            registrationService.register(actual);
        });
        Assertions.assertFalse(people.contains(actual));
    }

    @Test
    void register_negativeAge_notOk() {
        User expected = new User(VALID_LOGIN, VALID_PASSWORD, -10);
        Assertions.assertThrows(InvalidRegistrationException.class, () -> {
            registrationService.register(expected);
        });
        Assertions.assertFalse(people.contains(expected));
    }

    @AfterEach
    void clearData() {
        people.clear();
    }
}
