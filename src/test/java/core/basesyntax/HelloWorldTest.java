package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationException;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HelloWorldTest {
    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        Storage.people.clear();
    }

    @Test
    void register_validUser_ok() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("validPass");
        user.setAge(20);

        User registered = registrationService.register(user);

        assertNotNull(registered);
        assertEquals("validLogin", registered.getLogin());
        assertEquals("validPass", registered.getPassword());
        assertEquals(20, registered.getAge());
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
        user.setLogin("abc");
        user.setPassword("123456");
        user.setAge(20);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_shortPassword_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("123");
        user.setAge(20);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_ageUnder18_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("validPass");
        user.setAge(17);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_duplicateLogin_notOk() {
        User user1 = new User();
        user1.setLogin("duplicate");
        user1.setPassword("password");
        user1.setAge(20);
        registrationService.register(user1);

        User user2 = new User();
        user2.setLogin("duplicate");
        user2.setPassword("password");
        user2.setAge(22);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user2);
        });
    }

    @Test
    void register_nullLogin_notOk() {
        User user = new User();
        user.setLogin(null);
        user.setPassword("password");
        user.setAge(20);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullPassword_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword(null);
        user.setAge(20);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullAge_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("validPass");
        user.setAge(null);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }
}
