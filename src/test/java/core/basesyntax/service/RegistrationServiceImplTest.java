package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final RegistrationServiceImpl registrationService
            = new RegistrationServiceImpl();
    private User user;

    @BeforeEach
    void setUp() {
        user = new User("alice@gmail.com", "AliCe12345", 25);
    }

    @Test
    void register_loginCheck_isNotOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ageNull_isNotOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ageUnder_isNotOk() {
        user.setAge(13);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordNull_isNotOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordLength_isNotOk() {
        user.setPassword("alice");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userRegistration_isOk() {
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void register_userNull_isNotOk() {
        user = null;
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }
}
