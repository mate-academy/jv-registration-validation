package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;

    @BeforeAll
    static void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(null),
                "User cannot be null");
    }

    @Test
    void register_nullLogin_notOk() {
        User user = new User();
        user.setLogin(null);
        user.setPassword("password123");
        user.setAge(20);

        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "Login cannot be null");
    }

    @Test
    void register_shortLogin_notOk() {
        User user = new User();
        user.setLogin("abc");
        user.setPassword("password123");
        user.setAge(20);

        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "Login must be at least 6 characters long");
    }

    @Test
    void register_existingLogin_notOk() {
        User user = new User();
        user.setLogin("existingUser");
        user.setPassword("password123");
        user.setAge(20);

        Storage.people.add(user);
        User newUser = new User();
        newUser.setLogin("existingUser");
        newUser.setPassword("newPassword456");
        newUser.setAge(25);

        assertThrows(RegistrationException.class, () -> registrationService.register(newUser),
                "User with login existingUser already exists");
    }

    @Test
    void register_nullPassword_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword(null);
        user.setAge(20);

        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "Password cannot be null");
    }

    @Test
    void register_shortPassword_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("abc");
        user.setAge(20);

        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "Password must be at least 6 characters long");
    }

    @Test
    void register_nullAge_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("password123");
        user.setAge(null);

        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "Age cannot be null");
    }

    @Test
    void register_underageUser_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("password123");
        user.setAge(17);

        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "User must be at least 18 years old");
    }

    @Test
    void register_validUser_ok() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("password123");
        user.setAge(20);

        User result = registrationService.register(user);
        assertEquals(user, result);
    }

    @Test
    void register_userWithAge18_ok() {
        User user = new User();
        user.setLogin("validLogin18");
        user.setPassword("password123");
        user.setAge(18);

        User result = registrationService.register(user);
        assertEquals(user, result, "User with age 18 should be registered successfully");
    }
}
