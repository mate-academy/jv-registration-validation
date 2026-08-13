package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_validUser_ok() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("strongPwd");
        user.setAge(25);

        User registered = registrationService.register(user);

        assertNotNull(registered.getId());
        assertEquals(user.getLogin(), registered.getLogin());
        assertEquals(user.getPassword(), registered.getPassword());
        assertEquals(user.getAge(), registered.getAge());

        // storage contains equal user
        User fromStorage = Storage.people.stream()
                .filter(u -> "validLogin".equals(u.getLogin()))
                .findFirst()
                .orElse(null);
        assertNotNull(fromStorage);
        assertEquals(user, fromStorage);
    }

    @Test
    void register_existingLogin_notOk() {
        User first = new User();
        first.setLogin("duplicate");
        first.setPassword("password");
        first.setAge(30);

        // use low-level setup as requested
        Storage.people.add(first);

        User second = new User();
        second.setLogin("duplicate");
        second.setPassword("anotherPwd");
        second.setAge(22);

        assertThrows(RegistrationException.class, () -> registrationService.register(second));
    }

    @Test
    void register_shortLogin_notOk() {
        User user = new User();
        user.setLogin("short"); // 5 chars
        user.setPassword("password");
        user.setAge(20);

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortPassword_notOk() {
        User user = new User();
        user.setLogin("longEnough");
        user.setPassword("12345"); // 5 chars
        user.setAge(20);

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_underage_notOk() {
        User user = new User();
        user.setLogin("teenager");
        user.setPassword("password");
        user.setAge(17);

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_negativeAge_notOk() {
        User user = new User();
        user.setLogin("negAge");
        user.setPassword("password");
        user.setAge(-5);

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullLogin_notOk() {
        User user = new User();
        user.setLogin(null);
        user.setPassword("password");
        user.setAge(20);

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
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
    void register_nullAge_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("password");
        user.setAge(null);

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(null));
    }

    @Test
    void register_loginExactlySix_ok() {
        User user = new User();
        user.setLogin("login6"); // 6 chars
        user.setPassword("passwd6"); // >=6
        user.setAge(18);

        User registered = registrationService.register(user);

        assertNotNull(registered.getId());
        assertEquals("login6", registered.getLogin());
    }

    @Test
    void register_passwordExactlySix_ok() {
        User user = new User();
        user.setLogin("uniqueLogin");
        user.setPassword("passw6"); // 6 chars
        user.setAge(18);

        User registered = registrationService.register(user);

        assertNotNull(registered.getId());
        assertEquals("passw6", registered.getPassword());
    }
}
