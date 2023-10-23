package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationFailException;
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
        user.setLogin("ponomvrenko");
        user.setAge(21);
        user.setPassword("qwerty1234");
    }

    @Test
    void register_normalValidation_Ok() {
        registrationService.register(user);
        boolean actual = Storage.people.contains(user);
        assertTrue(actual);
    }

    @Test
    void register_loginAlreadyExistsInDB_notOk() {
        User userWithSameLogin = new User();
        userWithSameLogin.setLogin("ponomvrenko");
        Storage.people.add(userWithSameLogin);
        assertThrows(RegistrationFailException.class, () -> {
            registrationService.register(user);
        });
        Storage.people.remove(userWithSameLogin);
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RegistrationFailException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(RegistrationFailException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(RegistrationFailException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullUser_notOk() {
        user = null;
        assertThrows(RegistrationFailException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_belowZeroAge_notOk() {
        user.setAge(-100);
        assertThrows(RegistrationFailException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_emptyPassword_notOk() {
        user.setPassword("");
        assertThrows(RegistrationFailException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_smallLoginLength_notOK() {
        user.setLogin("small");
        assertThrows(RegistrationFailException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_returnCorrectUserAfterSuccessfulRegistration_Ok() {
        User registered = registrationService.register(user);
        assertEquals(user, registered);
        Storage.people.remove(registered);
    }
}
