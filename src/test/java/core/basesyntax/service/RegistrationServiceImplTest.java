package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        user.setLogin("User123");
        user.setPassword("123456");
        user.setAge(20);

        User actual = registrationService.register(user);

        assertEquals(user, actual);
    }

    @Test
    void register_duplicateLogin_notOk() {
        User user1 = new User();
        user1.setLogin("User123");
        user1.setPassword("123456");
        user1.setAge(20);

        User user2 = new User();
        user2.setLogin("User123");
        user2.setPassword("654321");
        user2.setAge(25);

        registrationService.register(user1);

        assertThrows(RegistrationException.class,
                () -> registrationService.register(user2));
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(null));
    }

    @Test
    void register_nullLogin_notOk() {
        User user = new User();
        user.setLogin(null);
        user.setPassword("123456");
        user.setAge(20);

        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_shortLogin_notOk() {
        User user = new User();
        user.setLogin("User1");
        user.setPassword("123456");
        user.setAge(20);

        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_shortPassword_notOk() {
        User user = new User();
        user.setLogin("User123");
        user.setPassword("12345");
        user.setAge(20);

        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_ageBelow18_notOk() {
        User user = new User();
        user.setLogin("User123");
        user.setPassword("123456");
        user.setAge(17);

        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        User user = new User();
        user.setLogin("User123");
        user.setPassword(null);
        user.setAge(20);

        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_negativeAge_notOk() {
        User user = new User();
        user.setLogin("User123");
        user.setPassword("123456");
        user.setAge(-5);

        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_ageExactly18_ok() {
        User user = new User();
        user.setLogin("User123");
        user.setPassword("123456");
        user.setAge(18);

        User actual = registrationService.register(user);

        assertEquals(user, actual);
    }

    @Test
    void register_emptyLogin_notOk() {
        User user = new User();
        user.setLogin("");
        user.setPassword("123456");
        user.setAge(20);

        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_emptyPassword_notOk() {
        User user = new User();
        user.setLogin("User123");
        user.setPassword("");
        user.setAge(20);

        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }
}
