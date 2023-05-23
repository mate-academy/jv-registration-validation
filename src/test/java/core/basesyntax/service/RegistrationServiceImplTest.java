package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int MIN_AGE = 18;
    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setAge(MIN_AGE);
        user.setLogin("testLogin");
        user.setPassword("testPassword");
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_emptyLogin_notOk() {
        user.setLogin("");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_loginIsThreeUnderMinLength_notOk() {
        user.setLogin("abc");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_loginIsOneUnderMinLength_notOk() {
        user.setLogin("abcde");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_minLengthLogin_ok() {
        user.setLogin("abcdef");
        registrationService.register(user);
        assertTrue(Storage.people.contains(user));
    }

    @Test
    void register_loginIsTwoOverMinLength_ok() {
        user.setLogin("abcdefgh");
        registrationService.register(user);
        assertTrue(Storage.people.contains(user));
    }

    @Test
    void register_emptyPassword_notOk() {
        user.setPassword("");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_passwordIsThreeUnderMinLength_notOk() {
        user.setPassword("abc");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_passwordIsOneUnderMinLength_notOk() {
        user.setPassword("abcde");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_minLengthPassword_ok() {
        user.setPassword("abcdef");
        registrationService.register(user);
        assertTrue(Storage.people.contains(user));
    }

    @Test
    void register_passwordIsTwoOverMinLength_ok() {
        user.setPassword("abcdefgh");
        registrationService.register(user);
        assertTrue(Storage.people.contains(user));
    }

    @Test
    void register_negativeAge_notOk() {
        user.setAge(-1);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_underMinAge_notOk() {
        user.setAge(MIN_AGE - 1);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_minAge_ok() {
        user.setAge(MIN_AGE);
        registrationService.register(user);
        assertTrue(Storage.people.contains(user));
    }

    @Test
    void register_overMinAge_ok() {
        user.setAge(MIN_AGE + 1);
        registrationService.register(user);
        assertTrue(Storage.people.contains(user));
    }

    @Test
    void register_userAlreadyInStorage_notOk() {
        Storage.people.add(user);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }
}
