package core.basesyntax.service;

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
        user.setLogin("userLogin");
        user.setPassword("qwerty123");
        user.setAge(30);
    }

    @Test
    void register_existingLogin_NotOk() {
        registrationService.register(user);
        User newUser = new User();
        user.setLogin("userLogin");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullLogin_NotOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_under18_NotOk() {
        user.setAge(17);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullAge_NotOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortPassword_NotOk() {
        user.setPassword("123");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_emptyPassword_NotOk() {
        user.setPassword("");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_NotOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }
}
