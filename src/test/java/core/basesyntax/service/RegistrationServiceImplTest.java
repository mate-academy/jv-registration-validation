package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationServiceImpl registrationService = new RegistrationServiceImpl();
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setPassword("123456");
        user.setLogin("123456");
        user.setAge(18);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_PasswordLengthEdge_isOk() {
        registrationService.register(user);
        int expected = 1;
        int actual = Storage.people.size();
        assertEquals(expected, actual);
    }

    @Test
    void register_PasswordLength_notOk() {
        user.setPassword("12345");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_LoginLengthEdge_isOk() {
        registrationService.register(user);
        int expected = 1;
        int actual = Storage.people.size();
        assertEquals(expected, actual);
    }

    @Test
    void register_LoginLength_notOk() {
        user.setLogin("12345");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_Age_isOk() {
        registrationService.register(user);
        int expected = 1;
        int actual = Storage.people.size();
        assertEquals(expected, actual);
    }

    @Test
    void register_underAge_notOk() {
        user.setAge(17);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_LoginAlreadyExist_notOk() {
        registrationService.register(user);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }
}
