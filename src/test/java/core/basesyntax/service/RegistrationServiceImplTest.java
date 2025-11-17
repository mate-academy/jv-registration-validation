package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private RegistrationService registrationService = new RegistrationServiceImpl();

    @BeforeEach
    public void setUp() {
        Storage.people.clear();
    }

    @Test
    public void register_validUser_ok() {
        User validUser = new User();
        validUser.setId(123L);
        validUser.setLogin("user1@gmail.com");
        validUser.setPassword("user1password");
        validUser.setAge(22);
        User registeredUser = registrationService.register(validUser);
        assertEquals(1, Storage.people.size());
        assertEquals(validUser, Storage.people.get(0));
        assertEquals(validUser, registeredUser);
    }

    @Test
    public void register_ageExactly18_Ok() {
        User validUser = new User();
        validUser.setId(123L);
        validUser.setLogin("user1gmail.com");
        validUser.setPassword("user1password");
        validUser.setAge(18);
        User registeredUser = registrationService.register(validUser);
        assertEquals(1, Storage.people.size());
        assertEquals(validUser, Storage.people.get(0));
        assertEquals(validUser, registeredUser);
    }

    @Test
    public void register_nullUser_notOk() {
        User nullUser = null;
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(nullUser);
        });
    }

    @Test
    public void register_shortLogin_notOk() {
        User user = new User();
        user.setLogin("login");
        user.setPassword("password");
        user.setAge(22);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void register_shortPassword_notOk() {
        User user = new User();
        user.setPassword("pass");
        user.setLogin("user1gmail.com");
        user.setAge(22);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void register_underageUser_notOk() {
        User user = new User();
        user.setAge(17);
        user.setLogin("user1gmail.com");
        user.setPassword("password");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void register_nullLogin_notOk() {
        User user = new User();
        user.setLogin(null);
        user.setPassword("password");
        user.setAge(22);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void register_nullPassword_notOk() {
        User user = new User();
        user.setPassword(null);
        user.setLogin("user1gmail.com");
        user.setAge(22);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void register_nullAge_notOk() {
        User user = new User();
        user.setAge(null);
        user.setLogin("user1gmail.com");
        user.setPassword("password");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void register_duplicateLogin_notOk() {
        User user = new User();
        user.setLogin("user1gmail.com");
        user.setPassword("user1password");
        user.setAge(22);
        Storage.people.add(user);

        User user2 = new User();
        user2.setLogin("user1gmail.com");
        user2.setPassword("anotherPassword");
        user2.setAge(22);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user2);
        });
    }

    @Test
    public void register_loginExactly5Characters_notOk() {
        User user = new User();
        user.setLogin("12345");
        user.setPassword("user1password");
        user.setAge(22);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void register_passwordExactly5Characters_notOk() {
        User user = new User();
        user.setLogin("user1gmail.com");
        user.setPassword("12345");
        user.setAge(22);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }
}
