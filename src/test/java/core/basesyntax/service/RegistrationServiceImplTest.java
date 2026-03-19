package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.appexception.RegisterException;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationServiceImpl registrationService;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        Storage.people.clear();
    }

    @Test
    void login_cant_be_null() {
        User user = new User();
        user.setLogin(null);

        assertThrows(RegisterException.class,
                () -> registrationService.register(user),
                "Login can't be null");
    }

    @Test
    void password_cant_be_null() {
        User user = new User();
        user.setPassword(null);

        assertThrows(RegisterException.class,
                () -> registrationService.register(user),
                "Password is souldn't be null");
    }

    @Test
    void age_cant_be_null() {
        User user = new User();
        user.setAge(null);

        assertThrows(RegisterException.class,
                () -> registrationService.register(user),
                "age cant be null");
    }

    @Test
    void age_cant_be_negative() {
        User user = new User();
        user.setAge(-1);

        assertThrows(RegisterException.class,
                () -> registrationService.register(user),
                "allowed negative AGE");
    }

    @Test
    void register_validUser_ok() {
        User user = new User();
        user.setId(10L);
        user.setLogin("regen6");
        user.setPassword("qwerty");
        user.setAge(18);

        User registeredUser = registrationService.register(user);
        assertTrue(Storage.people.contains(registeredUser),
                "User was not added to storage");
        assertEquals(user, registeredUser);
    }

    @Test
    void register_login_isLessThan_6() {
        User user = new User();
        user.setLogin("login");

        assertThrows(RegisterException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_password_isLessThan_6() {
        User user = new User();
        user.setPassword("12345");

        assertThrows(RegisterException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_age_isLessThan_18() {
        User user = new User();
        user.setAge(17);

        assertThrows(RegisterException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_existingLogin_notOk() {
        User user1 = new User();
        user1.setLogin("login123");
        user1.setPassword("password123");
        user1.setAge(20);

        User user2 = new User();
        user2.setLogin("login123");
        user2.setPassword("password456");
        user2.setAge(25);

        registrationService.register(user1);

        assertThrows(RegisterException.class, () -> {
            registrationService.register(user2);
        });
    }
}

