package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.ValidationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static final String DEFAULT_LOGIN = "Karabas";
    private static final String DEFAULT_PASSWORD = "BARABAS";
    private static final int DEFAULT_AGE = 20;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_userIsNull_notOk() {
        assertThrows(ValidationException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_nullLogin_notOk() {
        assertThrows(ValidationException.class, () -> {
            registrationService.register(new User(null, "DEFAULT_PASSWORD", DEFAULT_AGE));
        });
    }

    @Test
    void register_nullPassword_notOk() {
        assertThrows(ValidationException.class, () -> {
            registrationService.register(new User("DEFAULT_LOGIN", null, DEFAULT_AGE));
        });
    }

    @Test
    void register_nullAge_notOk() {
        assertThrows(ValidationException.class, () -> {
            registrationService.register(new User("DEFAULT_LOGIN", "DEFAULT_PASSWORD", null));
        });
    }

    @Test
    void register_invalidPassword_notOk() {
        assertThrows(ValidationException.class, () -> {
            registrationService.register(new User("DEFAULT_LOGIN", "qwert", DEFAULT_AGE));
        });
        assertThrows(ValidationException.class, () -> {
            registrationService.register(new User("DEFAULT_LOGIN", "", DEFAULT_AGE));
        });
    }

    @Test
    void register_invalidLogin_notOk() {
        assertThrows(ValidationException.class, () -> {
            registrationService.register(new User("Jimmy", "DEFAULT_PASSWORD", DEFAULT_AGE));
        });
        assertThrows(ValidationException.class, () -> {
            registrationService.register(new User("", "DEFAULT_PASSWORD", DEFAULT_AGE));
        });
    }

    @Test
    void register_invalidAge_notOk() {
        assertThrows(ValidationException.class, () -> {
            registrationService.register(new User("DEFAULT_LOGIN", "DEFAULT_PASSWORD", 15));
        });
        assertThrows(ValidationException.class, () -> {
            registrationService.register(new User("DEFAULT_LOGIN", "DEFAULT_PASSWORD", -200));
        });
    }

    @Test
    void register_validData_ok() {
        User expected = new User("DEFAULT_LOGIN", "DEFAULT_PASSWORD", DEFAULT_AGE);
        User actual = registrationService.register(expected);
        assertEquals(expected, actual, "Users must be equals");
    }

    @Test
    void register_userExists_notOk() throws ValidationException {
        User validUser = new User("DEFAULT_LOGIN", "DEFAULT_PASSWORD", DEFAULT_AGE);
        Storage.people.add(validUser);
        assertThrows(ValidationException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
