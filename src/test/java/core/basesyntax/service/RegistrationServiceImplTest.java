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
    private static User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void init() {
        user = new User();
        user.setPassword("MatePassword");
        user.setAge(23);
        user.setLogin("mate_lover");
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RuntimeException.class, ()
                -> registrationService.register(null));
    }

    @Test
    void register_validUser_Ok() {
        User user = new User();
        user.setPassword("qwerty");
        user.setAge(20);
        user.setLogin("alice123");
        User actualUser = registrationService.register(user);
        assertEquals(user, actualUser);
        User maks = new User();
        maks.setPassword("maksMate");
        maks.setAge(23);
        maks.setLogin("maks23");
        User actualMaks = registrationService.register(maks);
        assertEquals(maks, actualMaks);
    }

    @Test
    void register_nullLogin_notOk() {
        User user = new User();
        user.setPassword("lhfdskdf");
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
        user.setLogin("nullAge");
        assertThrows(RuntimeException.class, ()
                -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        User user = new User();
        user.setPassword(null);
        user.setAge(18);
        user.setLogin("nullPassword");
        assertThrows(RuntimeException.class, ()
                -> registrationService.register(user));
    }

    @Test
    void register_lessAge_notOk() {
        User user = new User();
        user.setPassword("666666");
        user.setAge(16);
        user.setLogin("user16");
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
    void register_existsLogin_notOk() {
        Storage.people.add(user);
        assertThrows(RuntimeException.class, ()
                -> registrationService.register(user));
        User maks = new User();
        maks.setPassword("123456");
        maks.setAge(23);
        maks.setLogin(user.getLogin());
        assertThrows(RuntimeException.class, ()
                -> registrationService.register(maks));
    }
}