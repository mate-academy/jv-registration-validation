package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private final RegistrationServiceImpl registrationService = new RegistrationServiceImpl();
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin("Bob");
        user.setAge(20);
        user.setPassword("password");
    }

    @AfterEach
    void clearAfterEach() {
        Storage.people.clear();
    }

    @Test
    void register_nullLogin_NotOk() {
        user.setLogin(null);
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullPass_NotOk() {
        user.setPassword(null);
        assertThrows(NullPointerException.class, () -> {
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
    void register_nonExistsUser_Ok() {
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void register_alreadyExistsUser_NotOk() {
        Storage.people.add(user);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_age_NotOk() {
        user.setAge(17);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_age_Ok() {
        user.setAge(19);
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void register_passLength_NotOk() {
        user.setPassword("pass");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_passLength_Ok() {
        user.setPassword("password");
        assertEquals(user, registrationService.register(user));
    }
}
