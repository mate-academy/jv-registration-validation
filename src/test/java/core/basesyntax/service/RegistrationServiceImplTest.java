package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private static User alice;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void init() {
        alice = new User();
        alice.setPassword("qwerty");
        alice.setAge(25);
        alice.setLogin("alice25");
    }

    @Test
    void register_validUser_Ok() {
        User alice = new User();
        alice.setPassword("qwerty");
        alice.setAge(25);
        alice.setLogin("alice123");
        User actualAlice = registrationService.register(alice);
        assertEquals(alice, actualAlice);
        User bob = new User();
        bob.setPassword("123456");
        bob.setAge(18);
        bob.setLogin("hamster18");
        User actualBob = registrationService.register(bob);
        assertEquals(bob, actualBob);
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RuntimeException.class, ()
                -> registrationService.register(null));
    }

    @Test
    void register_nullLogin_notOk() {
        User user = new User();
        user.setPassword("123456");
        user.setAge(18);
        user.setLogin(null);
        assertThrows(RuntimeException.class, ()
                -> registrationService.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        User user = new User();
        user.setPassword("123456");
        user.setAge(null);
        user.setLogin("bob25");
        assertThrows(RuntimeException.class, ()
                -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        User user = new User();
        user.setPassword(null);
        user.setAge(18);
        user.setLogin("bob25");
        assertThrows(RuntimeException.class, ()
                -> registrationService.register(user));
    }

    @Test
    void register_shortPassword_notOk() {
        User user = new User();
        user.setPassword("12345");
        user.setAge(18);
        user.setLogin("bob25");
        assertThrows(RuntimeException.class, ()
                -> registrationService.register(user));
    }

    @Test
    void register_lessAge_notOk() {
        User user = new User();
        user.setPassword("123456");
        user.setAge(12);
        user.setLogin("bob25");
        assertThrows(RuntimeException.class, ()
                -> registrationService.register(user));
    }

    @Test
    void register_existsLogin_notOk() {
        Storage.people.add(alice);
        assertThrows(RuntimeException.class, ()
                -> registrationService.register(alice));
        User bob = new User();
        bob.setPassword("123456");
        bob.setAge(18);
        bob.setLogin(bob.getLogin());
        assertThrows(RuntimeException.class, ()
                -> registrationService.register(bob));
    }
}
