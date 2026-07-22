package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin("validLogin");
        user.setPassword("validPassword");
        user.setAge(20);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_validUser_ok() {
        User registeredUser = registrationService.register(user);
        assertEquals(user, registeredUser);
        assertEquals(user, Storage.people.get(Storage.people.size() - 1));
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(null));
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortLogin_notOk() {
        user.setLogin("");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));

        user.setLogin("a");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));

        user.setLogin("abcde");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_minLengthLogin_ok() {
        user.setLogin("abcdef");
        User registeredUser = registrationService.register(user);
        assertEquals(user, registeredUser);
    }

    @Test
    void register_longLogin_ok() {
        user.setLogin("abcdefgh");
        User registeredUser = registrationService.register(user);
        assertEquals(user, registeredUser);
    }

    @Test
    void register_existingLogin_notOk() {
        registrationService.register(user);
        User sameLoginUser = new User();
        sameLoginUser.setLogin(user.getLogin());
        sameLoginUser.setPassword("otherPassword");
        sameLoginUser.setAge(25);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(sameLoginUser));
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortPassword_notOk() {
        user.setPassword("");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));

        user.setPassword("a");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));

        user.setPassword("abcde");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_minLengthPassword_ok() {
        user.setPassword("abcdef");
        User registeredUser = registrationService.register(user);
        assertEquals(user, registeredUser);
    }

    @Test
    void register_longPassword_ok() {
        user.setPassword("abcdefgh");
        User registeredUser = registrationService.register(user);
        assertEquals(user, registeredUser);
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_negativeAge_notOk() {
        user.setAge(-5);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_zeroAge_notOk() {
        user.setAge(0);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_underageUser_notOk() {
        user.setAge(17);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_minAllowedAge_ok() {
        user.setAge(18);
        User registeredUser = registrationService.register(user);
        assertEquals(user, registeredUser);
    }

    @Test
    void register_adultUser_ok() {
        user.setAge(30);
        User registeredUser = registrationService.register(user);
        assertEquals(user, registeredUser);
    }
}
