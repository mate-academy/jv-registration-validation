package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService registrationService;
    private User user;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        user = new User("testLogin", "corectPasswod", 25);
    }

    @Test
    void register_validUser_Ok() {
        User register = registrationService.register(user);

        assertEquals(user, register);
    }

    @Test
    void register_shortLogin_notOk() {
        user.setLogin("log");

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortPassword_notOk() {
        user.setPassword("111");

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_incorrectAge_notOk() {
        user.setAge(15);

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }
}
