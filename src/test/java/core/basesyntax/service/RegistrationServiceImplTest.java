package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
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
        user.setPassword("validPassword");
        user.setAge(30);

        User registeredUser = registrationService.register(user);

        assertNotNull(registeredUser.getId());
        assertEquals(user.getLogin(), registeredUser.getLogin());
        assertEquals(user.getPassword(), registeredUser.getPassword());
        assertEquals(user.getAge(), registeredUser.getAge());

        assertEquals(1, Storage.people.size());
        assertTrue(Storage.people.contains(registeredUser));
    }

    @Test
    void register_nullUser_notOk() {
        User user = null;
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginLength5_notOk() {
        User user = new User();
        user.setLogin("short");
        user.setPassword("validPassword");
        user.setAge(30);

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginLength6_ok() {
        User user = new User();
        user.setLogin("loginn");
        user.setPassword("validPassword");
        user.setAge(30);

        User registeredUser = registrationService.register(user);

        assertNotNull(registeredUser);
        assertEquals("loginn", registeredUser.getLogin());
        assertEquals(1, Storage.people.size());
        assertTrue(Storage.people.contains(registeredUser));
    }

    @Test
    void register_existingLogin_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("validPassword");
        user.setAge(30);

        Storage.people.add(user);

        User newUser = new User();
        newUser.setLogin("validLogin");
        newUser.setPassword("anotherPassword");
        newUser.setAge(40);

        assertThrows(RegistrationException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_shortPassword_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setAge(30);
        user.setPassword("short");

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordLength3_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("pas");
        user.setAge(30);

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordLength5_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("short");
        user.setAge(30);

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordLength6_ok() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("passwo");
        user.setAge(30);

        User registeredUser = registrationService.register(user);

        assertNotNull(registeredUser);
        assertEquals("passwo", registeredUser.getPassword());
        assertEquals(1, Storage.people.size());
        assertTrue(Storage.people.contains(registeredUser));
    }

    @Test
    void register_passwordLength8_ok() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("password");
        user.setAge(30);

        User registeredUser = registrationService.register(user);

        assertNotNull(registeredUser);
        assertEquals("password", registeredUser.getPassword());
        assertEquals(1, Storage.people.size());
        assertTrue(Storage.people.contains(registeredUser));
    }

    @Test
    void register_emptyPassword_notOk() {
        User user = new User();
        user.setPassword("");
        user.setLogin("validLogin");
        user.setAge(20);

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ageBelow18_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("valisPassword");
        user.setAge(10);

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setAge(20);
        user.setPassword(null);

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullLogin_notOk() {
        User user = new User();
        user.setLogin(null);
        user.setAge(20);
        user.setPassword("validPassword");

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setAge(null);
        user.setPassword("validPassword");

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_negativeAge_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("validPassword");
        user.setAge(-10);

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_zeroAge_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("validPassword");
        user.setAge(0);

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_age17_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("validPassword");
        user.setAge(17);

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_age18_ok() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("validPassword");
        user.setAge(18);

        User registeredUser = registrationService.register(user);

        assertNotNull(registeredUser);
        assertEquals(18, registeredUser.getAge());
        assertEquals(1, Storage.people.size());
        assertTrue(Storage.people.contains(registeredUser));
    }

    @Test
    void register_age19_ok() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("validPassword");
        user.setAge(19);

        User registeredUser = registrationService.register(user);

        assertNotNull(registeredUser);
        assertEquals(19, registeredUser.getAge());
        assertEquals(1, Storage.people.size());
        assertTrue(Storage.people.contains(registeredUser));
    }

    @Test
    void register_shortLogins_notOk() {
        String[] shortLogins = {"", "a", "ab", "abc", "abcd"};
        for (String login : shortLogins) {
            User user = new User();
            user.setLogin(login);
            user.setPassword("validPassword");
            user.setAge(25);

            assertThrows(RegistrationException.class,
                    () -> registrationService.register(user),
                    "Should throw for login: " + login);
        }
    }
}
