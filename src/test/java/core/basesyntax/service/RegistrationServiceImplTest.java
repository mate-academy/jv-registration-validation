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
        user.setLogin("UserMate");
        user.setPassword("Mate2022");
        user.setAge(33);
    }

    @Test
    void userLogin_notOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void userPasswordNull_notOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void userAgeNull_notOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void userAge_notOk() {
        user.setAge(17);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void userAge_ok() {
        user.setAge(18);
        registrationService.register(user);
        Long actual = user.getId();
        Long expected = 2L;
        assertEquals(expected, actual);
    }

    @Test
    void userNegativeAge_notOk() {
        user.setAge(-18);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void userPassword_notOk() {
        user.setPassword("55555");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void userNull_notOk() {
        user = null;
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void user_ok() {
        user.setLogin("Login");
        registrationService.register(user);
        Long actual = user.getId();
        Long expected = 1L;
        assertEquals(expected, actual);
    }

    @Test
    void user_same_notOk() {
        user.setLogin("Login");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }
}
