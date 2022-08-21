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
    void nullUser_NotOK() {
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void emptyUser_NotOk() {
        validUser.setAge(0);
        validUser.setLogin("");
        validUser.setPassword("");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @Test
    void validUser_Ok() {
        User actual = registrationService.register(validUser);
        assertEquals(validUser, actual);
        assertTrue(people.contains(validUser));
    }

    @Test
    void loginNull_NotOk() {
        validUser.setLogin(null);
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(validUser);
        });

    }

    @Test
    void loginIsInStorage_NotOk() {
        people.add(validUser);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @Test
    void emptyLogin_NotOk() {
        validUser.setLogin("");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @Test
    void lessMinAge_NotOk() {
        validUser.setAge(17);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @Test
    void negativeAge_NotOk() {
        validUser.setAge(-1);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @Test
    void nullAge_NotOk() {
        validUser.setAge(null);
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @Test
    void zeroAge() {
        validUser.setAge(0);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @Test
    void passwordNull_NotOk() {
        validUser.setPassword(null);
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @Test
    void passwordLessMinLength_NotOk() {
        validUser.setPassword("pas");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @AfterEach
    void tearDown() {
        people.clear();
    }
}
