package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_nullFields_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(new User(null, null, null));
        });
    }

    @Test
    void register_nullLogin_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(new User(null, "123456789", 19));
        });
    }

    @Test
    void register_nullPassword_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(new User("vadim1337", null, 19));
        });
    }

    @Test
    void register_nullAge_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(new User("vadim1337", "123456789", null));
        });
    }

    @Test
    void register_invalidLogin_notOk() {
        User user = new User("v1337", "123456789", 19);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_invalidPassword_notOk() {
        User user = new User("vadim1337", "1234", 19);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_invalidAge_notOk() {
        User user = new User("vadim1337", "123456789", 16);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_userExistsInStorage_notOk() {
        User user1 = new User("vadim1337", "123456789", 19);
        registrationService.register(user1);
        User user2 = new User("vadim1337", "123456789", 19);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user2);
        });
    }

    @Test
    void register_validUser_Ok() {
        User user = new User("vadim133798", "123456789", 19);
        User registered = registrationService.register(user);
        assertEquals(user, registered);
    }
}
