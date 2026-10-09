package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
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
        user.setPassword("123456");
        user.setAge(20);

        User registeredUser = registrationService.register(user);

        assertNotNull(registeredUser);
        assertNotNull(registeredUser.getId());
        assertEquals(user.getLogin(), registeredUser.getLogin());
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(null));
    }

    @Test
    void register_nullLogin_notOk() {
        User user = new User();
        user.setLogin(null);
        user.setPassword("123456");
        user.setAge(20);

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_emptyLogin_notOk() {
        User user = new User();
        user.setLogin("");
        user.setPassword("123456");
        user.setAge(20);

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_fiveCharsLogin_notOk() {
        User user = new User();
        user.setLogin("12345");
        user.setPassword("123456");
        user.setAge(20);

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_minValidLogin_ok() {
        User user = new User();
        user.setLogin("qwerty");
        user.setPassword("123456");
        user.setAge(20);

        User registeredUser = registrationService.register(user);

        assertNotNull(registeredUser);
        assertEquals(user.getLogin(), registeredUser.getLogin());
    }

    @Test
    void register_existingUserLogin_notOk() {
        User user1 = new User();
        user1.setLogin("validLogin");
        user1.setPassword("123456");
        user1.setAge(20);

        Storage.people.add(user1);

        User user2 = new User();
        user2.setLogin("validLogin");
        user2.setPassword("654321");
        user2.setAge(25);

        assertThrows(RegistrationException.class, () -> registrationService.register(user2));
    }

    @Test
    void register_nullPassword_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword(null);
        user.setAge(20);

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_emptyPassword_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("");
        user.setAge(20);

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_fiveCharsPassword_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("12345");
        user.setAge(20);

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_minValidPassword_ok() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("123456");
        user.setAge(20);

        User registeredUser = registrationService.register(user);

        assertNotNull(registeredUser);
    }

    @Test
    void register_nullAge_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("123456");
        user.setAge(null);

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_under18Age_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("123456");
        user.setAge(17);

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_negativeAge_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("123456");
        user.setAge(-5);

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_minValidAge_ok() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("123456");
        user.setAge(18);

        User registeredUser = registrationService.register(user);

        assertNotNull(registeredUser);
    }
}

