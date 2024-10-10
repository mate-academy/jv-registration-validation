package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;

    @BeforeAll
    static void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    public void register_validUser_ok() {
        User user = new User("validLogin", "password123", 20);
        User registeredUser = registrationService.register(user);
        assertEquals(user, registeredUser);
    }

    @Test
    public void register_existingLogin_notOk() {
        User user = new User("existingUser", "password123", 20);
        Storage.people.add(user);
        User newUser = new User("existingUser", "password123", 20);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @Test
    public void register_loginTooShort_notOk() {
        User user = new User("short", "password123", 20);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void register_passwordTooShort_notOk() {
        User user = new User("validLogin", "pass", 20);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void register_ageLessThan18_notOk() {
        User user = new User("validLogin", "password123", 17);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void register_loginNull_notOk() {
        User user = new User(null, "password123", 20);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void register_passwordNull_notOk() {
        User user = new User("validLogin", null, 20);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void register_ageNull_notOk() {
        User user = new User("validLogin", "password123", null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void register_negativeAge_notOk() {
        User user = new User("validLogin", "password123", -5);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }
}