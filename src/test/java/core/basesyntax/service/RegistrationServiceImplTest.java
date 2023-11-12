package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.InvalidUserDataException;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;

    @BeforeAll
    static void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    public void register_nullUser_notOk() {
        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    public void register_ok() {
        User user = new User();
        user.setLogin("newUser");
        user.setPassword("password123");
        user.setAge(20);

        User result = registrationService.register(user);

        assertNotNull(result);
        assertEquals(user.getLogin(), result.getLogin());
        assertEquals(1, Storage.people.size());
    }

    @Test
    public void register_invalidAge_notOk() {
        User user = new User();
        user.setLogin("newUser");
        user.setPassword("password123");
        user.setAge(17);

        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void register_invalidPassword_notOk() {
        User user = new User();
        user.setLogin("newUser");
        user.setPassword("pass");
        user.setAge(20);

        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void register_invalidLogin_notOk() {
        User user = new User();
        user.setLogin("usr");
        user.setPassword("password123");
        user.setAge(20);

        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void register_userExists_notOk() {
        User existingUser = new User();
        existingUser.setLogin("existingUser");
        existingUser.setPassword("password123");
        existingUser.setAge(20);
        Storage.people.add(existingUser);

        User newUser = new User();
        newUser.setLogin("existingUser");
        newUser.setPassword("password123");
        newUser.setAge(20);

        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(newUser);
        });
    }
}
