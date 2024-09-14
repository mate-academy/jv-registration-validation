package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;

    @BeforeAll
    static void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_validUser_ok() {
        User user = new User();
        user.setLogin("valid user");
        user.setPassword("valid password");
        user.setAge(20);

        User registeredUser = registrationService.register(user);

        assertNotNull(registeredUser);
        assertEquals(user.getLogin(), registeredUser.getLogin());
        assertEquals(user.getPassword(), registeredUser.getPassword());
        assertEquals(user.getAge(), registeredUser.getAge());
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_shortLogin_notOk() {
        User user = new User();
        user.setLogin("short");
        user.setPassword("validPassword");
        user.setAge(20);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_shortPassword_notOk() {
        User user = new User();
        user.setLogin("validUser");
        user.setPassword("123");
        user.setAge(20);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_underageUser_notOk() {
        User user = new User();
        user.setLogin("validUser");
        user.setPassword("validPassword");
        user.setAge(17);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_negativeAgeUser_notOk() {
        User user = new User();
        user.setLogin("validUser");
        user.setPassword("validPassword");
        user.setAge(-5);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_existingUserLogin_notOk() {
        Storage.people.clear();

        User user = new User();
        user.setLogin("validUser");
        user.setPassword("validPassword");
        user.setAge(20);
        Storage.people.add(user);

        User newUser = new User();
        newUser.setLogin("validUser");
        newUser.setPassword("anotherPassword");
        newUser.setAge(22);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @Test
    void register_nullLogin_notOk() {
        User user = new User();
        user.setLogin(null);
        user.setPassword("validPassword");
        user.setAge(20);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullPassword_notOk() {
        User user = new User();
        user.setLogin("validUser");
        user.setPassword(null);
        user.setAge(20);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullAge_notOk() {
        User user = new User();
        user.setLogin("validUser");
        user.setPassword("validPassword");
        user.setAge(null);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }
}
