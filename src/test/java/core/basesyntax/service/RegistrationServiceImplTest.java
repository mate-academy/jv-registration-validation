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

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
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
            registrationService.register(new User(null,"qwerty",23));
        });
    }

    @Test
    void register_nullPassword_notOk() {
        assertThrows(ValidationException.class, () -> {
            registrationService.register(new User("Jimmy12",null,23));
        });
    }

    @Test
    void register_nullAge_notOk() {
        assertThrows(ValidationException.class, () -> {
            registrationService.register(new User("Jimmy12","qwerty",null));
        });
    }

    @Test
    void register_invalidPassword_notOk() {
        assertThrows(ValidationException.class, () -> {
            registrationService.register(new User("Jimmy12","qwert",22));
        });
        assertThrows(ValidationException.class, () -> {
            registrationService.register(new User("Jimmy12","",22));
        });
    }

    @Test
    void register_invalidLogin_notOk() {
        assertThrows(ValidationException.class, () -> {
            registrationService.register(new User("Jimmy","qwerty",22));
        });
        assertThrows(ValidationException.class, () -> {
            registrationService.register(new User("","qwerty",22));
        });
    }

    @Test
    void register_invalidAge_notOk() {
        assertThrows(ValidationException.class, () -> {
            registrationService.register(new User("Jimmy12","qwerty",15));
        });
        assertThrows(ValidationException.class, () -> {
            registrationService.register(new User("Jimmy12","qwerty",-200));
        });
    }

    @Test
    void register_validData_ok() {
        User expected = new User("Karabas", "Barabas", 20);
        User actual = registrationService.register(expected);
        assertEquals(expected, actual, "Users must be equals");
    }

    @Test
    void register_userExists_notOk() throws ValidationException {
        User validUser = new User("Karabas", "Barabas", 20);
        Storage.people.add(validUser);
        assertThrows(ValidationException.class, () -> {
            registrationService.register(validUser);
        });
    }
}
