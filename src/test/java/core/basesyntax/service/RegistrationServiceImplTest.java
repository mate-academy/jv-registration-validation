package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin("abcdef@gmail.com");
        user.setAge(22);
        user.setPassword("abc123");
    }

    @Test
    void expectedBehavior_SetLoginNull_NotOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void expectedBehaviorValid_SetLogin_Ok() {
        final User actual = registrationService.register(user);
        assertEquals(user, actual, "error");
    }

    @Test
    void expectedBehavior_SetAgeNull_NotOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void expectedBehaviorValid_SetAge_NotOk() {
        user.setAge(16);
        assertThrows(RuntimeException.class, () ->
                registrationService.register(user));
    }

    @Test
    void expectedBehavior_SetPasswordNull_NotOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void expectedBehaviorValid_setPassword_NotOk() {
        user.setPassword("1234");
        assertThrows(RuntimeException.class, () ->
                registrationService.register(user));
    }
}
