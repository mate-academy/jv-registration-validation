package core.basesyntax.service;

import static core.basesyntax.db.Storage.people;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
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
        user.setAge(19);
        user.setLogin("javalogin");
        user.setPassword("password");
    }

    @Test
    void register_nullUser_NotOK() {
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_emptyPassword_NotOk() {
        user.setPassword("");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_validUser_Ok() {
        User actual = registrationService.register(user);
        assertEquals(user, actual);
        assertTrue(people.contains(user));
    }

    @Test
    void register_loginNull_NotOk() {
        user.setLogin(null);
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(user);
        });

    }

    @Test
    void register_loginIsInStorage_NotOk() {
        people.add(user);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_emptyLogin_NotOk() {
        user.setLogin("");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_lessMinAge_NotOk() {
        user.setAge(17);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_negativeAge_NotOk() {
        user.setAge(-1);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullAge_NotOk() {
        user.setAge(null);
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_zeroAge_NotOk() {
        user.setAge(0);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_passwordNull_NotOk() {
        user.setPassword(null);
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_passwordLessMinLength_NotOk() {
        user.setPassword("notok");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @AfterEach
    void tearDown() {
        people.clear();
    }
}
