package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exceptions.RegistrationException;
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
    void register_AddNullUser_notOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(null));
    }

    @Test
    void register_UseNullLogin_notOk() {
        User user = new User(null, "123456", 20);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_UseShortLogin_notOk() {
        User user = new User("short", "123456", 20);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_UseNullPassword_notOk() {
        User user = new User("logiin", null, 20);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_UseShortPassword_notOk() {
        User user = new User("logiin", "short", 20);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_AddUnderageUser_notOk() {
        User user = new User("logiin", "123456", 17);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_AddValidUser_ok() {
        User user = new User("logiin", "123456", 20);
        assertEquals(user, registrationService.register(user));
    }
}
