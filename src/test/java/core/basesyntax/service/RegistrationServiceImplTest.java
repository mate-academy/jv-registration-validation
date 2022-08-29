package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private static User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        user = new User();
    }

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        user.setLogin("someLogin");
        user.setPassword("somePassword");
        user.setAge(39);
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(null));
    }

    @Test
    void register_userExists_notOk() {
        registrationService.register(user);
        assertThrowRuntimeException();
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrowRuntimeException();
    }

    @Test
    void register_emptyLogin_notOk() {
        user.setLogin("");
        assertThrowRuntimeException();
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrowRuntimeException();
    }

    @Test
    void register_emptyPassword_notOk() {
        user.setPassword("");
        assertThrowRuntimeException();
    }

    @Test
    void register_invalidPassword_notOk() {
        user.setPassword("pass");
        assertThrowRuntimeException();
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrowRuntimeException();
    }

    @Test
    void register_smallAge_notOk() {
        user.setAge(9);
        assertThrowRuntimeException();
    }

    @Test
    void register_userCorrect_ok() {
        assertEquals(user, registrationService.register(user));
    }

    private void assertThrowRuntimeException() {
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }
}
