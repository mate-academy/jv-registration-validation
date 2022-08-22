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
    private User validUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        validUser = new User();
        validUser.setAge(19);
        validUser.setLogin("javalogin");
        validUser.setPassword("password");
    }

    @Test
    void register_nullUser_NotOK() {
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_emptyPassword_NotOk() {
        validUser.setPassword("");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @Test
    void register_validUser_Ok() {
        User actual = registrationService.register(validUser);
        assertEquals(validUser, actual);
        assertTrue(people.contains(validUser));
    }

    @Test
    void register_loginNull_NotOk() {
        validUser.setLogin(null);
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(validUser);
        });

    }

    @Test
    void register_loginIsInStorage_NotOk() {
        people.add(validUser);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @Test
    void register_emptyLogin_NotOk() {
        validUser.setLogin("");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @Test
    void register_lessMinAge_NotOk() {
        validUser.setAge(17);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @Test
    void register_negativeAge_NotOk() {
        validUser.setAge(-1);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @Test
    void register_nullAge_NotOk() {
        validUser.setAge(null);
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @Test
    void register_zeroAge() {
        validUser.setAge(0);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @Test
    void register_passwordNull_NotOk() {
        validUser.setPassword(null);
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @Test
    void register_passwordLessMinLength_NotOk() {
        validUser.setPassword("notok");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @AfterEach
    void tearDown() {
        people.clear();
    }
}
