package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.InvalidUserDataException;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private User user;

    @BeforeAll
    static void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void createValidUser() {
        user = new User();
        user.setLogin("existingUser");
        user.setPassword("password123");
        user.setAge(20);
    }

    @Test
    public void register_nullUser_notOk() {
        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    public void register_ok() {
        User result = registrationService.register(user);

        assertNotNull(result);
        assertEquals(user.getLogin(), result.getLogin());
        assertEquals(1, Storage.people.size());
    }

    @Test
    public void register_invalidAge_notOk() {
        user.setAge(16);

        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void register_shortPassword_notOk() {
        user.setPassword("12345");

        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void register_shortLogin_notOk() {
        user.setLogin("defgt");

        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void register_userExists_notOk() {
        Storage.people.add(user);

        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(user);
        });
    }
}
