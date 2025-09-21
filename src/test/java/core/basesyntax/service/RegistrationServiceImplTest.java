package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationServiceImpl registrationService;

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(null));
    }

    @Test
    void register_nullLogin_notOk() {
        User user = new User();
        user.setPassword("password123");
        user.setAge(21);

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        User user = new User();
        user.setLogin("login123");
        user.setAge(21);

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        User user = new User();
        user.setLogin("login123");
        user.setPassword("password123");
        user.setAge(null);

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_negativeAge_notOk() {
        User user = new User();
        user.setLogin("login123");
        user.setPassword("password123");
        user.setAge(-1);

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginLength5_notOk() {
        User user = new User();
        user.setLogin("login");
        user.setPassword("password123");
        user.setAge(21);

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortPassword_notOk() {
        User user = new User();
        user.setLogin("login123");
        user.setPassword("12345");
        user.setAge(21);

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_tooYoung_notOk() {
        User user = new User();
        user.setLogin("login123");
        user.setPassword("password123");
        user.setAge(17);

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_duplicateLogin_notOk() {
        User user1 = new User();
        user1.setLogin("login123");
        user1.setPassword("password123");
        user1.setAge(21);
        Storage.people.add(user1);

        User user2 = new User();
        user2.setLogin("login123");
        user2.setPassword("password456");
        user2.setAge(25);

        assertThrows(RegistrationException.class, () -> registrationService.register(user2));
    }

    @Test
    void register_validUser_ok() {
        User user = new User();
        user.setLogin("login123");
        user.setPassword("password123");
        user.setAge(21);

        User registered = registrationService.register(user);

        assertEquals(user, registered);
        assertEquals(1, Storage.people.size());
    }

    @Test
    void register_edgeCase_minLoginAndPasswordAndAge_ok() {
        User user = new User();
        user.setLogin("logins");
        user.setPassword("passwd");
        user.setAge(18);

        User registered = registrationService.register(user);

        assertEquals(user, registered);
        assertEquals(1, Storage.people.size());
    }

    @Test
    void register_emptyLogin_notOk() {
        User user = new User();
        user.setLogin("");
        user.setPassword("password123");
        user.setAge(21);

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_emptyPassword_notOk() {
        User user = new User();
        user.setLogin("login123");
        user.setPassword("");
        user.setAge(21);

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }
}
